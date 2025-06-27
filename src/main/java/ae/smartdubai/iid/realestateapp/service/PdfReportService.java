package ae.smartdubai.iid.realestateapp.service;

import ae.smartdubai.iid.realestateapp.model.CostBreakdown;
import ae.smartdubai.iid.realestateapp.model.DocumentChecklist;
import ae.smartdubai.iid.realestateapp.model.LoanCalculation;
import ae.smartdubai.iid.realestateapp.model.PdfReport;
import ae.smartdubai.iid.realestateapp.model.Property;
import ae.smartdubai.iid.realestateapp.model.PropertyComparison;
import ae.smartdubai.iid.realestateapp.model.ServiceChargeEstimate;
import ae.smartdubai.iid.realestateapp.repository.CostBreakdownRepository;
import ae.smartdubai.iid.realestateapp.repository.DocumentChecklistRepository;
import ae.smartdubai.iid.realestateapp.repository.LoanCalculationRepository;
import ae.smartdubai.iid.realestateapp.repository.PdfReportRepository;
import ae.smartdubai.iid.realestateapp.repository.PropertyComparisonRepository;
import ae.smartdubai.iid.realestateapp.repository.PropertyRepository;
import ae.smartdubai.iid.realestateapp.repository.ServiceChargeEstimateRepository;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service for managing PDF reports.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class PdfReportService {

    private final PdfReportRepository pdfReportRepository;
    private final PropertyRepository propertyRepository;
    private final CostBreakdownRepository costBreakdownRepository;
    private final LoanCalculationRepository loanCalculationRepository;
    private final PropertyComparisonRepository propertyComparisonRepository;
    private final DocumentChecklistRepository documentChecklistRepository;
    private final ServiceChargeEstimateRepository serviceChargeEstimateRepository;
    
    // Directory to store PDF files
    private static final String PDF_DIRECTORY = "pdf-reports";
    
    // Date formatter for PDF content
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    /**
     * Get all PDF reports.
     *
     * @return list of all PDF reports
     */
    @Transactional(readOnly = true)
    public List<PdfReport> getAllPdfReports() {
        return pdfReportRepository.findAll();
    }

    /**
     * Get PDF report by ID.
     *
     * @param id the PDF report ID
     * @return the PDF report if found
     */
    @Transactional(readOnly = true)
    public Optional<PdfReport> getPdfReportById(Long id) {
        return pdfReportRepository.findById(id);
    }

    /**
     * Get PDF reports by property.
     *
     * @param propertyId the property ID
     * @return list of PDF reports for the specified property
     */
    @Transactional(readOnly = true)
    public List<PdfReport> getPdfReportsByProperty(Long propertyId) {
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new RuntimeException("Property not found with id " + propertyId));
        return pdfReportRepository.findByProperty(property);
    }

    /**
     * Generate a PDF report for a property with various calculations.
     *
     * @param propertyId the property ID
     * @param title the report title
     * @param includeCostBreakdown whether to include cost breakdown
     * @param costBreakdownId the cost breakdown ID (if including cost breakdown)
     * @param includeLoanCalculation whether to include loan calculation
     * @param loanCalculationId the loan calculation ID (if including loan calculation)
     * @param includePropertyComparison whether to include property comparison
     * @param propertyComparisonId the property comparison ID (if including property comparison)
     * @param includeDocumentChecklist whether to include document checklist
     * @param documentChecklistId the document checklist ID (if including document checklist)
     * @param includeServiceChargeEstimate whether to include service charge estimate
     * @param serviceChargeEstimateId the service charge estimate ID (if including service charge estimate)
     * @return the generated PDF report
     */
    public PdfReport generatePdfReport(Long propertyId, String title,
                                     Boolean includeCostBreakdown, Long costBreakdownId,
                                     Boolean includeLoanCalculation, Long loanCalculationId,
                                     Boolean includePropertyComparison, Long propertyComparisonId,
                                     Boolean includeDocumentChecklist, Long documentChecklistId,
                                     Boolean includeServiceChargeEstimate, Long serviceChargeEstimateId) {
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new RuntimeException("Property not found with id " + propertyId));
        
        // Create PDF report entity
        PdfReport pdfReport = new PdfReport();
        pdfReport.setProperty(property);
        pdfReport.setTitle(title);
        pdfReport.setGenerationDate(LocalDate.now());
        
        // Set report type based on included components
        String reportType = determineReportType(includeCostBreakdown, includeLoanCalculation,
                includePropertyComparison, includeDocumentChecklist, includeServiceChargeEstimate);
        pdfReport.setReportType(reportType);
        
        // Set inclusion flags
        pdfReport.setIncludesCostBreakdown(includeCostBreakdown);
        pdfReport.setIncludesLoanCalculation(includeLoanCalculation);
        pdfReport.setIncludesPropertyComparison(includePropertyComparison);
        pdfReport.setIncludesDocumentChecklist(includeDocumentChecklist);
        pdfReport.setIncludesServiceChargeEstimate(includeServiceChargeEstimate);
        
        // Set references to included components
        if (includeCostBreakdown && costBreakdownId != null) {
            CostBreakdown costBreakdown = costBreakdownRepository.findById(costBreakdownId)
                    .orElseThrow(() -> new RuntimeException("Cost breakdown not found with id " + costBreakdownId));
            pdfReport.setCostBreakdown(costBreakdown);
        }
        
        if (includeLoanCalculation && loanCalculationId != null) {
            LoanCalculation loanCalculation = loanCalculationRepository.findById(loanCalculationId)
                    .orElseThrow(() -> new RuntimeException("Loan calculation not found with id " + loanCalculationId));
            pdfReport.setLoanCalculation(loanCalculation);
        }
        
        if (includePropertyComparison && propertyComparisonId != null) {
            PropertyComparison propertyComparison = propertyComparisonRepository.findById(propertyComparisonId)
                    .orElseThrow(() -> new RuntimeException("Property comparison not found with id " + propertyComparisonId));
            pdfReport.setPropertyComparison(propertyComparison);
        }
        
        if (includeDocumentChecklist && documentChecklistId != null) {
            DocumentChecklist documentChecklist = documentChecklistRepository.findById(documentChecklistId)
                    .orElseThrow(() -> new RuntimeException("Document checklist not found with id " + documentChecklistId));
            pdfReport.setDocumentChecklist(documentChecklist);
        }
        
        if (includeServiceChargeEstimate && serviceChargeEstimateId != null) {
            ServiceChargeEstimate serviceChargeEstimate = serviceChargeEstimateRepository.findById(serviceChargeEstimateId)
                    .orElseThrow(() -> new RuntimeException("Service charge estimate not found with id " + serviceChargeEstimateId));
            pdfReport.setServiceChargeEstimate(serviceChargeEstimate);
        }
        
        // Generate PDF file
        String filePath = generatePdfFile(pdfReport);
        pdfReport.setFilePath(filePath);
        
        // Get file size
        try {
            Path path = Paths.get(filePath);
            long fileSize = Files.size(path) / 1024; // Convert to KB
            pdfReport.setFileSize(fileSize);
        } catch (IOException e) {
            throw new RuntimeException("Error getting file size: " + e.getMessage(), e);
        }
        
        return pdfReportRepository.save(pdfReport);
    }

    /**
     * Determine the report type based on included components.
     *
     * @param includeCostBreakdown whether cost breakdown is included
     * @param includeLoanCalculation whether loan calculation is included
     * @param includePropertyComparison whether property comparison is included
     * @param includeDocumentChecklist whether document checklist is included
     * @param includeServiceChargeEstimate whether service charge estimate is included
     * @return the report type
     */
    private String determineReportType(Boolean includeCostBreakdown, Boolean includeLoanCalculation,
                                     Boolean includePropertyComparison, Boolean includeDocumentChecklist,
                                     Boolean includeServiceChargeEstimate) {
        if (includeCostBreakdown && !includeLoanCalculation && !includePropertyComparison && 
                !includeDocumentChecklist && !includeServiceChargeEstimate) {
            return "COST_BREAKDOWN";
        } else if (!includeCostBreakdown && includeLoanCalculation && !includePropertyComparison && 
                !includeDocumentChecklist && !includeServiceChargeEstimate) {
            return "LOAN_CALCULATION";
        } else if (!includeCostBreakdown && !includeLoanCalculation && includePropertyComparison && 
                !includeDocumentChecklist && !includeServiceChargeEstimate) {
            return "PROPERTY_COMPARISON";
        } else if (!includeCostBreakdown && !includeLoanCalculation && !includePropertyComparison && 
                includeDocumentChecklist && !includeServiceChargeEstimate) {
            return "DOCUMENT_CHECKLIST";
        } else if (!includeCostBreakdown && !includeLoanCalculation && !includePropertyComparison && 
                !includeDocumentChecklist && includeServiceChargeEstimate) {
            return "SERVICE_CHARGE_ESTIMATE";
        } else {
            return "COMPREHENSIVE";
        }
    }

    /**
     * Generate a PDF file for the report.
     *
     * @param pdfReport the PDF report entity
     * @return the file path of the generated PDF
     */
    private String generatePdfFile(PdfReport pdfReport) {
        // Create directory if it doesn't exist
        File directory = new File(PDF_DIRECTORY);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        
        // Generate unique file name
        String fileName = "report_" + UUID.randomUUID().toString() + ".pdf";
        String filePath = PDF_DIRECTORY + File.separator + fileName;
        
        try (PDDocument document = new PDDocument()) {
            // Add a page
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);
            
            // Create a content stream for writing to the page
            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                // Set fonts
                PDType1Font titleFont = PDType1Font.HELVETICA_BOLD;
                PDType1Font headingFont = PDType1Font.HELVETICA_BOLD;
                PDType1Font textFont = PDType1Font.HELVETICA;
                
                // Add title
                contentStream.beginText();
                contentStream.setFont(titleFont, 18);
                contentStream.newLineAtOffset(50, 750);
                contentStream.showText(pdfReport.getTitle());
                contentStream.endText();
                
                // Add property details
                Property property = pdfReport.getProperty();
                contentStream.beginText();
                contentStream.setFont(headingFont, 14);
                contentStream.newLineAtOffset(50, 720);
                contentStream.showText("Property Details");
                contentStream.endText();
                
                contentStream.beginText();
                contentStream.setFont(textFont, 12);
                contentStream.newLineAtOffset(50, 700);
                contentStream.showText("Name: " + property.getName());
                contentStream.newLineAtOffset(0, -15);
                contentStream.showText("Location: " + property.getLocation());
                contentStream.newLineAtOffset(0, -15);
                contentStream.showText("Price: AED " + formatNumber(property.getPrice()));
                contentStream.newLineAtOffset(0, -15);
                contentStream.showText("Size: " + formatNumber(property.getSize()) + " sq ft");
                contentStream.newLineAtOffset(0, -15);
                contentStream.showText("Bedrooms: " + (property.getBedrooms() != null ? property.getBedrooms() : "N/A"));
                contentStream.newLineAtOffset(0, -15);
                contentStream.showText("Bathrooms: " + (property.getBathrooms() != null ? property.getBathrooms() : "N/A"));
                contentStream.newLineAtOffset(0, -15);
                contentStream.showText("Property Type: " + (property.getPropertyType() != null ? property.getPropertyType() : "N/A"));
                contentStream.endText();
                
                int yPosition = 580;
                
                // Add cost breakdown if included
                if (pdfReport.getIncludesCostBreakdown() && pdfReport.getCostBreakdown() != null) {
                    yPosition = addCostBreakdownToPdf(contentStream, pdfReport.getCostBreakdown(), headingFont, textFont, yPosition);
                }
                
                // Add loan calculation if included
                if (pdfReport.getIncludesLoanCalculation() && pdfReport.getLoanCalculation() != null) {
                    yPosition = addLoanCalculationToPdf(contentStream, pdfReport.getLoanCalculation(), headingFont, textFont, yPosition);
                }
                
                // Add property comparison if included
                if (pdfReport.getIncludesPropertyComparison() && pdfReport.getPropertyComparison() != null) {
                    yPosition = addPropertyComparisonToPdf(contentStream, pdfReport.getPropertyComparison(), headingFont, textFont, yPosition);
                }
                
                // Add document checklist if included
                if (pdfReport.getIncludesDocumentChecklist() && pdfReport.getDocumentChecklist() != null) {
                    yPosition = addDocumentChecklistToPdf(contentStream, pdfReport.getDocumentChecklist(), headingFont, textFont, yPosition);
                }
                
                // Add service charge estimate if included
                if (pdfReport.getIncludesServiceChargeEstimate() && pdfReport.getServiceChargeEstimate() != null) {
                    yPosition = addServiceChargeEstimateToPdf(contentStream, pdfReport.getServiceChargeEstimate(), headingFont, textFont, yPosition);
                }
                
                // Add footer
                contentStream.beginText();
                contentStream.setFont(textFont, 10);
                contentStream.newLineAtOffset(50, 50);
                contentStream.showText("Generated on: " + pdfReport.getGenerationDate().format(DATE_FORMATTER));
                contentStream.newLineAtOffset(0, -15);
                contentStream.showText("Real Estate Cost Calculator - A Micro SaaS Application");
                contentStream.endText();
            }
            
            // Save the document
            document.save(filePath);
        } catch (IOException e) {
            throw new RuntimeException("Error generating PDF: " + e.getMessage(), e);
        }
        
        return filePath;
    }

    /**
     * Add cost breakdown to PDF.
     *
     * @param contentStream the PDF content stream
     * @param costBreakdown the cost breakdown
     * @param headingFont the heading font
     * @param textFont the text font
     * @param yPosition the current Y position
     * @return the new Y position
     */
    private int addCostBreakdownToPdf(PDPageContentStream contentStream, CostBreakdown costBreakdown,
                                    PDType1Font headingFont, PDType1Font textFont, int yPosition) throws IOException {
        // Add section heading
        contentStream.beginText();
        contentStream.setFont(headingFont, 14);
        contentStream.newLineAtOffset(50, yPosition);
        contentStream.showText("Cost Breakdown");
        contentStream.endText();
        
        yPosition -= 20;
        
        // Add cost breakdown details
        contentStream.beginText();
        contentStream.setFont(textFont, 12);
        contentStream.newLineAtOffset(50, yPosition);
        contentStream.showText("DLD Fee: AED " + formatNumber(costBreakdown.getDldFee()));
        contentStream.newLineAtOffset(0, -15);
        contentStream.showText("Agency Fee: AED " + formatNumber(costBreakdown.getAgencyFee()));
        contentStream.newLineAtOffset(0, -15);
        contentStream.showText("Registration Fee: AED " + formatNumber(costBreakdown.getRegistrationFee()));
        contentStream.newLineAtOffset(0, -15);
        
        if (costBreakdown.getMortgageRegistrationFee() != null && costBreakdown.getMortgageRegistrationFee() > 0) {
            contentStream.showText("Mortgage Registration Fee: AED " + formatNumber(costBreakdown.getMortgageRegistrationFee()));
            contentStream.newLineAtOffset(0, -15);
        }
        
        if (costBreakdown.getValuationFee() != null && costBreakdown.getValuationFee() > 0) {
            contentStream.showText("Valuation Fee: AED " + formatNumber(costBreakdown.getValuationFee()));
            contentStream.newLineAtOffset(0, -15);
        }
        
        if (costBreakdown.getMortgageProcessingFee() != null && costBreakdown.getMortgageProcessingFee() > 0) {
            contentStream.showText("Mortgage Processing Fee: AED " + formatNumber(costBreakdown.getMortgageProcessingFee()));
            contentStream.newLineAtOffset(0, -15);
        }
        
        if (costBreakdown.getLifeInsuranceCost() != null && costBreakdown.getLifeInsuranceCost() > 0) {
            contentStream.showText("Life Insurance Cost: AED " + formatNumber(costBreakdown.getLifeInsuranceCost()));
            contentStream.newLineAtOffset(0, -15);
        }
        
        contentStream.showText("Property Insurance Cost: AED " + formatNumber(costBreakdown.getPropertyInsuranceCost()));
        contentStream.newLineAtOffset(0, -15);
        
        if (costBreakdown.getMaintenanceDeposit() != null && costBreakdown.getMaintenanceDeposit() > 0) {
            contentStream.showText("Maintenance Deposit: AED " + formatNumber(costBreakdown.getMaintenanceDeposit()));
            contentStream.newLineAtOffset(0, -15);
        }
        
        contentStream.showText("Utility Connection Fees: AED " + formatNumber(costBreakdown.getUtilityConnectionFees()));
        contentStream.newLineAtOffset(0, -15);
        contentStream.showText("Moving Costs: AED " + formatNumber(costBreakdown.getMovingCosts()));
        contentStream.newLineAtOffset(0, -15);
        contentStream.showText("Total Cost: AED " + formatNumber(costBreakdown.getTotalCost()));
        contentStream.endText();
        
        return yPosition - 180;
    }

    /**
     * Add loan calculation to PDF.
     *
     * @param contentStream the PDF content stream
     * @param loanCalculation the loan calculation
     * @param headingFont the heading font
     * @param textFont the text font
     * @param yPosition the current Y position
     * @return the new Y position
     */
    private int addLoanCalculationToPdf(PDPageContentStream contentStream, LoanCalculation loanCalculation,
                                      PDType1Font headingFont, PDType1Font textFont, int yPosition) throws IOException {
        // Add section heading
        contentStream.beginText();
        contentStream.setFont(headingFont, 14);
        contentStream.newLineAtOffset(50, yPosition);
        contentStream.showText("Loan Calculation");
        contentStream.endText();
        
        yPosition -= 20;
        
        // Add loan calculation details
        contentStream.beginText();
        contentStream.setFont(textFont, 12);
        contentStream.newLineAtOffset(50, yPosition);
        contentStream.showText("Loan Amount: AED " + formatNumber(loanCalculation.getLoanAmount()));
        contentStream.newLineAtOffset(0, -15);
        contentStream.showText("Down Payment: AED " + formatNumber(loanCalculation.getDownPayment()));
        contentStream.newLineAtOffset(0, -15);
        contentStream.showText("Interest Rate: " + formatNumber(loanCalculation.getInterestRate()) + "%");
        contentStream.newLineAtOffset(0, -15);
        contentStream.showText("Tenure: " + loanCalculation.getTenureYears() + " years");
        contentStream.newLineAtOffset(0, -15);
        contentStream.showText("Monthly EMI: AED " + formatNumber(loanCalculation.getMonthlyEmi()));
        contentStream.newLineAtOffset(0, -15);
        contentStream.showText("Total Interest: AED " + formatNumber(loanCalculation.getTotalInterest()));
        contentStream.newLineAtOffset(0, -15);
        contentStream.showText("Total Payable: AED " + formatNumber(loanCalculation.getTotalPayable()));
        contentStream.newLineAtOffset(0, -15);
        contentStream.showText("Loan to Value Ratio: " + formatNumber(loanCalculation.getLoanToValueRatio()) + "%");
        contentStream.endText();
        
        return yPosition - 140;
    }

    /**
     * Add property comparison to PDF.
     *
     * @param contentStream the PDF content stream
     * @param propertyComparison the property comparison
     * @param headingFont the heading font
     * @param textFont the text font
     * @param yPosition the current Y position
     * @return the new Y position
     */
    private int addPropertyComparisonToPdf(PDPageContentStream contentStream, PropertyComparison propertyComparison,
                                         PDType1Font headingFont, PDType1Font textFont, int yPosition) throws IOException {
        // Add section heading
        contentStream.beginText();
        contentStream.setFont(headingFont, 14);
        contentStream.newLineAtOffset(50, yPosition);
        
        if (propertyComparison.getIsRentVsBuy()) {
            contentStream.showText("Rent vs Buy Comparison");
        } else {
            contentStream.showText("Property Comparison");
        }
        
        contentStream.endText();
        
        yPosition -= 20;
        
        // Add property comparison details
        contentStream.beginText();
        contentStream.setFont(textFont, 12);
        contentStream.newLineAtOffset(50, yPosition);
        
        if (propertyComparison.getIsRentVsBuy()) {
            // Rent vs Buy comparison
            contentStream.showText("Monthly Rent: AED " + formatNumber(propertyComparison.getMonthlyRent()));
            contentStream.newLineAtOffset(0, -15);
            contentStream.showText("Annual Rent Increase: " + formatNumber(propertyComparison.getAnnualRentIncrease()) + "%");
            contentStream.newLineAtOffset(0, -15);
            contentStream.showText("Investment Return Rate: " + formatNumber(propertyComparison.getInvestmentReturnRate()) + "%");
            contentStream.newLineAtOffset(0, -15);
            contentStream.showText("Property Appreciation Rate: " + formatNumber(propertyComparison.getPropertyAppreciationRate()) + "%");
            contentStream.newLineAtOffset(0, -15);
            contentStream.showText("Holding Period: " + propertyComparison.getHoldingPeriodYears() + " years");
            contentStream.newLineAtOffset(0, -15);
            contentStream.showText("Break-even Point: " + formatNumber(propertyComparison.getBreakEvenYears()) + " years");
            contentStream.newLineAtOffset(0, -15);
            contentStream.showText("Buying NPV: AED " + formatNumber(propertyComparison.getBuyingNpv()));
            contentStream.newLineAtOffset(0, -15);
            contentStream.showText("Renting NPV: AED " + formatNumber(propertyComparison.getRentingNpv()));
            contentStream.newLineAtOffset(0, -15);
            
            if (propertyComparison.getBuyingNpv() > propertyComparison.getRentingNpv()) {
                contentStream.showText("Recommendation: Buying is financially better in the long run");
            } else {
                contentStream.showText("Recommendation: Renting is financially better in the long run");
            }
        } else {
            // Property vs Property comparison
            contentStream.showText("Property 1: " + propertyComparison.getProperty1().getName());
            contentStream.newLineAtOffset(0, -15);
            contentStream.showText("Property 2: " + propertyComparison.getProperty2().getName());
            contentStream.newLineAtOffset(0, -15);
            contentStream.showText("Property Appreciation Rate: " + formatNumber(propertyComparison.getPropertyAppreciationRate()) + "%");
            contentStream.newLineAtOffset(0, -15);
            contentStream.showText("Holding Period: " + propertyComparison.getHoldingPeriodYears() + " years");
            contentStream.newLineAtOffset(0, -15);
            contentStream.showText("Property 1 Total Cost: AED " + formatNumber(propertyComparison.getProperty1TotalCost()));
            contentStream.newLineAtOffset(0, -15);
            contentStream.showText("Property 2 Total Cost: AED " + formatNumber(propertyComparison.getProperty2TotalCost()));
            contentStream.newLineAtOffset(0, -15);
            contentStream.showText("Cost Difference: AED " + formatNumber(propertyComparison.getCostDifference()));
            contentStream.newLineAtOffset(0, -15);
            contentStream.showText("Property 1 ROI: " + formatNumber(propertyComparison.getProperty1Roi()) + "%");
            contentStream.newLineAtOffset(0, -15);
            contentStream.showText("Property 2 ROI: " + formatNumber(propertyComparison.getProperty2Roi()) + "%");
            contentStream.newLineAtOffset(0, -15);
            
            if (propertyComparison.getProperty1Roi() > propertyComparison.getProperty2Roi()) {
                contentStream.showText("Recommendation: Property 1 has better ROI");
            } else {
                contentStream.showText("Recommendation: Property 2 has better ROI");
            }
        }
        
        contentStream.endText();
        
        return yPosition - 180;
    }

    /**
     * Add document checklist to PDF.
     *
     * @param contentStream the PDF content stream
     * @param documentChecklist the document checklist
     * @param headingFont the heading font
     * @param textFont the text font
     * @param yPosition the current Y position
     * @return the new Y position
     */
    private int addDocumentChecklistToPdf(PDPageContentStream contentStream, DocumentChecklist documentChecklist,
                                        PDType1Font headingFont, PDType1Font textFont, int yPosition) throws IOException {
        // Add section heading
        contentStream.beginText();
        contentStream.setFont(headingFont, 14);
        contentStream.newLineAtOffset(50, yPosition);
        contentStream.showText("Document Checklist");
        contentStream.endText();
        
        yPosition -= 20;
        
        // Add document checklist details
        contentStream.beginText();
        contentStream.setFont(textFont, 12);
        contentStream.newLineAtOffset(50, yPosition);
        contentStream.showText("Buyer Type: " + documentChecklist.getBuyerType());
        contentStream.newLineAtOffset(0, -15);
        contentStream.showText("Selected Bank: " + (documentChecklist.getSelectedBank() != null ? documentChecklist.getSelectedBank() : "N/A"));
        contentStream.newLineAtOffset(0, -15);
        contentStream.showText("Nationality: " + documentChecklist.getNationality());
        contentStream.newLineAtOffset(0, -15);
        contentStream.showText("Residence Status: " + documentChecklist.getResidenceStatus());
        contentStream.endText();
        
        yPosition -= 60;
        
        // Add identity documents
        if (documentChecklist.getIdentityDocuments() != null && !documentChecklist.getIdentityDocuments().isEmpty()) {
            contentStream.beginText();
            contentStream.setFont(headingFont, 12);
            contentStream.newLineAtOffset(50, yPosition);
            contentStream.showText("Identity Documents:");
            contentStream.endText();
            
            yPosition -= 15;
            
            contentStream.beginText();
            contentStream.setFont(textFont, 10);
            contentStream.newLineAtOffset(70, yPosition);
            
            for (String document : documentChecklist.getIdentityDocuments()) {
                contentStream.showText("• " + document);
                contentStream.newLineAtOffset(0, -12);
                yPosition -= 12;
            }
            
            contentStream.endText();
        }
        
        yPosition -= 10;
        
        // Add income proof documents
        if (documentChecklist.getIncomeProofDocuments() != null && !documentChecklist.getIncomeProofDocuments().isEmpty()) {
            contentStream.beginText();
            contentStream.setFont(headingFont, 12);
            contentStream.newLineAtOffset(50, yPosition);
            contentStream.showText("Income Proof Documents:");
            contentStream.endText();
            
            yPosition -= 15;
            
            contentStream.beginText();
            contentStream.setFont(textFont, 10);
            contentStream.newLineAtOffset(70, yPosition);
            
            for (String document : documentChecklist.getIncomeProofDocuments()) {
                contentStream.showText("• " + document);
                contentStream.newLineAtOffset(0, -12);
                yPosition -= 12;
            }
            
            contentStream.endText();
        }
        
        return yPosition - 20;
    }

    /**
     * Add service charge estimate to PDF.
     *
     * @param contentStream the PDF content stream
     * @param serviceChargeEstimate the service charge estimate
     * @param headingFont the heading font
     * @param textFont the text font
     * @param yPosition the current Y position
     * @return the new Y position
     */
    private int addServiceChargeEstimateToPdf(PDPageContentStream contentStream, ServiceChargeEstimate serviceChargeEstimate,
                                            PDType1Font headingFont, PDType1Font textFont, int yPosition) throws IOException {
        // Add section heading
        contentStream.beginText();
        contentStream.setFont(headingFont, 14);
        contentStream.newLineAtOffset(50, yPosition);
        contentStream.showText("Service Charge Estimate");
        contentStream.endText();
        
        yPosition -= 20;
        
        // Add service charge estimate details
        contentStream.beginText();
        contentStream.setFont(textFont, 12);
        contentStream.newLineAtOffset(50, yPosition);
        contentStream.showText("Community: " + serviceChargeEstimate.getCommunityName());
        contentStream.newLineAtOffset(0, -15);
        contentStream.showText("Property Type: " + serviceChargeEstimate.getPropertyType());
        contentStream.newLineAtOffset(0, -15);
        contentStream.showText("Property Size: " + formatNumber(serviceChargeEstimate.getPropertySize()) + " sq ft");
        contentStream.newLineAtOffset(0, -15);
        contentStream.showText("Service Charge Rate: AED " + formatNumber(serviceChargeEstimate.getServiceChargeRatePerSqFt()) + " per sq ft");
        contentStream.newLineAtOffset(0, -15);
        contentStream.showText("Annual Service Charge: AED " + formatNumber(serviceChargeEstimate.getAnnualServiceCharge()));
        contentStream.newLineAtOffset(0, -15);
        
        if (serviceChargeEstimate.getCoolingCharges() != null && serviceChargeEstimate.getCoolingCharges() > 0) {
            contentStream.showText("Cooling Charges: AED " + formatNumber(serviceChargeEstimate.getCoolingCharges()));
            contentStream.newLineAtOffset(0, -15);
        }
        
        contentStream.showText("Building Maintenance Fee: AED " + formatNumber(serviceChargeEstimate.getBuildingMaintenanceFee()));
        contentStream.newLineAtOffset(0, -15);
        contentStream.showText("Security Fee: AED " + formatNumber(serviceChargeEstimate.getSecurityFee()));
        contentStream.newLineAtOffset(0, -15);
        contentStream.showText("Cleaning Fee: AED " + formatNumber(serviceChargeEstimate.getCleaningFee()));
        contentStream.newLineAtOffset(0, -15);
        
        if (serviceChargeEstimate.getParkingFee() != null && serviceChargeEstimate.getParkingFee() > 0) {
            contentStream.showText("Parking Fee: AED " + formatNumber(serviceChargeEstimate.getParkingFee()));
            contentStream.newLineAtOffset(0, -15);
        }
        
        contentStream.showText("Gym and Pool Fee: AED " + formatNumber(serviceChargeEstimate.getGymAndPoolFee()));
        contentStream.newLineAtOffset(0, -15);
        contentStream.showText("Miscellaneous Charges: AED " + formatNumber(serviceChargeEstimate.getMiscCharges()));
        contentStream.newLineAtOffset(0, -15);
        contentStream.showText("Total Annual Charges: AED " + formatNumber(serviceChargeEstimate.getTotalAnnualCharges()));
        contentStream.newLineAtOffset(0, -15);
        contentStream.showText("Monthly Charges: AED " + formatNumber(serviceChargeEstimate.getMonthlyCharges()));
        contentStream.endText();
        
        return yPosition - 180;
    }

    /**
     * Format a number for display.
     *
     * @param number the number to format
     * @return the formatted number
     */
    private String formatNumber(Double number) {
        if (number == null) {
            return "N/A";
        }
        return String.format("%,.2f", number);
    }

    /**
     * Share a PDF report via email.
     *
     * @param reportId the PDF report ID
     * @param email the email address to share with
     * @return the updated PDF report
     */
    public PdfReport sharePdfReport(Long reportId, String email) {
        PdfReport pdfReport = pdfReportRepository.findById(reportId)
                .orElseThrow(() -> new RuntimeException("PDF report not found with id " + reportId));
        
        // In a real application, you would send an email with the PDF attachment here
        // For this example, we'll just update the report entity
        
        pdfReport.setSharedToEmail(email);
        pdfReport.setSharedDate(LocalDate.now());
        
        return pdfReportRepository.save(pdfReport);
    }

    /**
     * Delete a PDF report.
     *
     * @param id the PDF report ID
     */
    public void deletePdfReport(Long id) {
        PdfReport pdfReport = pdfReportRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("PDF report not found with id " + id));
        
        // Delete the PDF file
        try {
            Files.deleteIfExists(Paths.get(pdfReport.getFilePath()));
        } catch (IOException e) {
            // Log the error but continue with deleting the entity
            System.err.println("Error deleting PDF file: " + e.getMessage());
        }
        
        pdfReportRepository.deleteById(id);
    }

    /**
     * Delete all PDF reports for a property.
     *
     * @param propertyId the property ID
     */
    public void deleteAllPdfReportsForProperty(Long propertyId) {
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new RuntimeException("Property not found with id " + propertyId));
        
        List<PdfReport> reports = pdfReportRepository.findByProperty(property);
        
        // Delete all PDF files
        for (PdfReport report : reports) {
            try {
                Files.deleteIfExists(Paths.get(report.getFilePath()));
            } catch (IOException e) {
                // Log the error but continue with deleting the entities
                System.err.println("Error deleting PDF file: " + e.getMessage());
            }
        }
        
        pdfReportRepository.deleteByProperty(property);
    }
}