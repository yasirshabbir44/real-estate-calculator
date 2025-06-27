package ae.smartdubai.iid.realestateapp.repository;

import ae.smartdubai.iid.realestateapp.model.PdfReport;
import ae.smartdubai.iid.realestateapp.model.Property;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
public class PdfReportRepositoryTest {

    @Autowired
    private PdfReportRepository pdfReportRepository;

    @Autowired
    private PropertyRepository propertyRepository;

    private Property property1;
    private Property property2;
    private PdfReport report1;
    private PdfReport report2;
    private PdfReport report3;

    @BeforeEach
    public void setup() {
        // Clear any existing data
        pdfReportRepository.deleteAll();
        propertyRepository.deleteAll();

        // Create test properties
        property1 = new Property();
        property1.setName("Test Property 1");
        property1.setLocation("Test Location 1");
        property1.setPrice(1000000.0);
        property1.setSize(200.0);
        property1.setBedrooms(3);
        property1.setBathrooms(2);
        property1.setPropertyType("Apartment");
        property1.setCommunityName("Test Community 1");
        property1.setIsFurnished(false);
        property1.setYearBuilt(2020);
        property1 = propertyRepository.save(property1);

        property2 = new Property();
        property2.setName("Test Property 2");
        property2.setLocation("Test Location 2");
        property2.setPrice(1500000.0);
        property2.setSize(300.0);
        property2.setBedrooms(4);
        property2.setBathrooms(3);
        property2.setPropertyType("Villa");
        property2.setCommunityName("Test Community 2");
        property2.setIsFurnished(true);
        property2.setYearBuilt(2019);
        property2 = propertyRepository.save(property2);

        // Create test PDF reports
        report1 = new PdfReport();
        report1.setProperty(property1);
        report1.setTitle("Cost Breakdown Report for Property 1");
        report1.setReportType("COST_BREAKDOWN");
        report1.setFilePath("/reports/property1_cost_breakdown_20230115.pdf");
        report1.setFileSize(1024L); // 1MB
        report1.setGenerationDate(LocalDate.of(2023, 1, 15));
        report1.setSharedToEmail("user1@example.com");
        report1.setSharedDate(LocalDate.of(2023, 1, 16));
        report1.setIncludesCostBreakdown(true);
        report1.setIncludesLoanCalculation(false);
        report1.setIncludesPropertyComparison(false);
        report1.setIncludesDocumentChecklist(false);
        report1.setIncludesServiceChargeEstimate(false);
        report1 = pdfReportRepository.save(report1);

        report2 = new PdfReport();
        report2.setProperty(property1);
        report2.setTitle("Comprehensive Report for Property 1");
        report2.setReportType("COMPREHENSIVE");
        report2.setFilePath("/reports/property1_comprehensive_20230220.pdf");
        report2.setFileSize(2048L); // 2MB
        report2.setGenerationDate(LocalDate.of(2023, 2, 20)); // More recent date
        report2.setSharedToEmail("user2@example.com");
        report2.setSharedDate(LocalDate.of(2023, 2, 21));
        report2.setIncludesCostBreakdown(true);
        report2.setIncludesLoanCalculation(true);
        report2.setIncludesPropertyComparison(false);
        report2.setIncludesDocumentChecklist(true);
        report2.setIncludesServiceChargeEstimate(true);
        report2 = pdfReportRepository.save(report2);

        report3 = new PdfReport();
        report3.setProperty(property2);
        report3.setTitle("Property Comparison Report");
        report3.setReportType("COMPARISON");
        report3.setFilePath("/reports/property_comparison_20230310.pdf");
        report3.setFileSize(1536L); // 1.5MB
        report3.setGenerationDate(LocalDate.of(2023, 3, 10));
        report3.setSharedToEmail("user3@example.com");
        report3.setSharedDate(LocalDate.of(2023, 3, 11));
        report3.setIncludesCostBreakdown(false);
        report3.setIncludesLoanCalculation(false);
        report3.setIncludesPropertyComparison(true);
        report3.setIncludesDocumentChecklist(false);
        report3.setIncludesServiceChargeEstimate(false);
        report3 = pdfReportRepository.save(report3);
    }

    @Test
    public void testFindByProperty() {
        // Test finding PDF reports by property
        List<PdfReport> results = pdfReportRepository.findByProperty(property1);
        
        // Verify results
        assertEquals(2, results.size());
        assertTrue(results.contains(report1));
        assertTrue(results.contains(report2));
        assertFalse(results.contains(report3));
    }

    @Test
    public void testFindFirstByPropertyOrderByGenerationDateDesc() {
        // Test finding the latest PDF report for a property
        Optional<PdfReport> result = pdfReportRepository.findFirstByPropertyOrderByGenerationDateDesc(property1);
        
        // Verify result
        assertTrue(result.isPresent());
        assertEquals(report2, result.get()); // report2 has a more recent date
    }

    @Test
    public void testFindByReportType() {
        // Test finding PDF reports by report type
        List<PdfReport> results = pdfReportRepository.findByReportType("COST_BREAKDOWN");
        
        // Verify results
        assertEquals(1, results.size());
        assertTrue(results.contains(report1));
        assertFalse(results.contains(report2));
        assertFalse(results.contains(report3));
    }

    @Test
    public void testFindByGenerationDateBetween() {
        // Test finding PDF reports by generation date range
        LocalDate startDate = LocalDate.of(2023, 2, 1);
        LocalDate endDate = LocalDate.of(2023, 3, 15);
        List<PdfReport> results = pdfReportRepository.findByGenerationDateBetween(startDate, endDate);
        
        // Verify results
        assertEquals(2, results.size());
        assertTrue(results.contains(report2));
        assertTrue(results.contains(report3));
        assertFalse(results.contains(report1));
    }

    @Test
    public void testFindBySharedToEmail() {
        // Test finding PDF reports by shared email
        List<PdfReport> results = pdfReportRepository.findBySharedToEmail("user2@example.com");
        
        // Verify results
        assertEquals(1, results.size());
        assertTrue(results.contains(report2));
        assertFalse(results.contains(report1));
        assertFalse(results.contains(report3));
    }

    @Test
    public void testFindBySharedDateBetween() {
        // Test finding PDF reports by shared date range
        LocalDate startDate = LocalDate.of(2023, 2, 1);
        LocalDate endDate = LocalDate.of(2023, 3, 15);
        List<PdfReport> results = pdfReportRepository.findBySharedDateBetween(startDate, endDate);
        
        // Verify results
        assertEquals(2, results.size());
        assertTrue(results.contains(report2));
        assertTrue(results.contains(report3));
        assertFalse(results.contains(report1));
    }

    @Test
    public void testFindByIncludesCostBreakdownTrue() {
        // Test finding PDF reports that include cost breakdown
        List<PdfReport> results = pdfReportRepository.findByIncludesCostBreakdownTrue();
        
        // Verify results
        assertEquals(2, results.size());
        assertTrue(results.contains(report1));
        assertTrue(results.contains(report2));
        assertFalse(results.contains(report3));
    }

    @Test
    public void testFindByIncludesLoanCalculationTrue() {
        // Test finding PDF reports that include loan calculation
        List<PdfReport> results = pdfReportRepository.findByIncludesLoanCalculationTrue();
        
        // Verify results
        assertEquals(1, results.size());
        assertTrue(results.contains(report2));
        assertFalse(results.contains(report1));
        assertFalse(results.contains(report3));
    }

    @Test
    public void testFindByIncludesPropertyComparisonTrue() {
        // Test finding PDF reports that include property comparison
        List<PdfReport> results = pdfReportRepository.findByIncludesPropertyComparisonTrue();
        
        // Verify results
        assertEquals(1, results.size());
        assertTrue(results.contains(report3));
        assertFalse(results.contains(report1));
        assertFalse(results.contains(report2));
    }

    @Test
    public void testFindByIncludesDocumentChecklistTrue() {
        // Test finding PDF reports that include document checklist
        List<PdfReport> results = pdfReportRepository.findByIncludesDocumentChecklistTrue();
        
        // Verify results
        assertEquals(1, results.size());
        assertTrue(results.contains(report2));
        assertFalse(results.contains(report1));
        assertFalse(results.contains(report3));
    }

    @Test
    public void testFindByIncludesServiceChargeEstimateTrue() {
        // Test finding PDF reports that include service charge estimate
        List<PdfReport> results = pdfReportRepository.findByIncludesServiceChargeEstimateTrue();
        
        // Verify results
        assertEquals(1, results.size());
        assertTrue(results.contains(report2));
        assertFalse(results.contains(report1));
        assertFalse(results.contains(report3));
    }

    @Test
    public void testDeleteByProperty() {
        // Test deleting PDF reports by property
        pdfReportRepository.deleteByProperty(property1);
        
        // Verify deletion
        List<PdfReport> remainingReports = pdfReportRepository.findAll();
        assertEquals(1, remainingReports.size());
        assertTrue(remainingReports.contains(report3));
        assertFalse(remainingReports.contains(report1));
        assertFalse(remainingReports.contains(report2));
    }
}