package ae.smartdubai.iid.realestateapp.service;

import ae.smartdubai.iid.realestateapp.model.DocumentChecklist;
import ae.smartdubai.iid.realestateapp.model.Property;
import ae.smartdubai.iid.realestateapp.repository.DocumentChecklistRepository;
import ae.smartdubai.iid.realestateapp.repository.PropertyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Service for managing document checklists.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class DocumentChecklistService {

    private final DocumentChecklistRepository documentChecklistRepository;
    private final PropertyRepository propertyRepository;

    /**
     * Get all document checklists.
     *
     * @return list of all document checklists
     */
    @Transactional(readOnly = true)
    public List<DocumentChecklist> getAllDocumentChecklists() {
        return documentChecklistRepository.findAll();
    }

    /**
     * Get document checklist by ID.
     *
     * @param id the document checklist ID
     * @return the document checklist if found
     */
    @Transactional(readOnly = true)
    public Optional<DocumentChecklist> getDocumentChecklistById(Long id) {
        return documentChecklistRepository.findById(id);
    }

    /**
     * Get document checklists by property.
     *
     * @param propertyId the property ID
     * @return list of document checklists for the specified property
     */
    @Transactional(readOnly = true)
    public List<DocumentChecklist> getDocumentChecklistsByProperty(Long propertyId) {
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new RuntimeException("Property not found with id " + propertyId));
        return documentChecklistRepository.findByProperty(property);
    }

    /**
     * Generate a document checklist for a property.
     *
     * @param propertyId the property ID
     * @param buyerType the type of buyer (SALARIED, SELF_EMPLOYED, INVESTOR, etc.)
     * @param selectedBank the selected bank for mortgage (if applicable)
     * @param nationality the nationality of the buyer
     * @param residenceStatus the residence status of the buyer (UAE_RESIDENT, NON_RESIDENT)
     * @param isMortgageRequired whether a mortgage is required
     * @param isOffPlan whether the property is off-plan
     * @param isReady whether the property is ready
     * @return the generated document checklist
     */
    public DocumentChecklist generateDocumentChecklist(Long propertyId, String buyerType, String selectedBank,
                                                     String nationality, String residenceStatus,
                                                     Boolean isMortgageRequired, Boolean isOffPlan, Boolean isReady) {
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new RuntimeException("Property not found with id " + propertyId));
        
        DocumentChecklist checklist = new DocumentChecklist();
        checklist.setProperty(property);
        checklist.setBuyerType(buyerType);
        checklist.setSelectedBank(selectedBank);
        checklist.setNationality(nationality);
        checklist.setResidenceStatus(residenceStatus);
        checklist.setIsMortgageRequired(isMortgageRequired);
        checklist.setIsOffPlan(isOffPlan);
        checklist.setIsReady(isReady);
        checklist.setCreationDate(LocalDate.now());
        
        // Generate identity documents
        List<String> identityDocuments = generateIdentityDocuments(nationality, residenceStatus);
        checklist.setIdentityDocuments(identityDocuments);
        
        // Generate income proof documents
        List<String> incomeProofDocuments = generateIncomeProofDocuments(buyerType);
        checklist.setIncomeProofDocuments(incomeProofDocuments);
        
        // Generate property documents
        List<String> propertyDocuments = generatePropertyDocuments(isOffPlan, isReady);
        checklist.setPropertyDocuments(propertyDocuments);
        
        // Generate bank documents
        List<String> bankDocuments = generateBankDocuments(isMortgageRequired, selectedBank);
        checklist.setBankDocuments(bankDocuments);
        
        // Generate visa/residency documents
        List<String> visaDocuments = generateVisaDocuments(nationality, residenceStatus);
        checklist.setVisaDocuments(visaDocuments);
        
        // Generate additional documents
        List<String> additionalDocuments = generateAdditionalDocuments(buyerType, isMortgageRequired, isOffPlan);
        checklist.setAdditionalDocuments(additionalDocuments);
        
        // Generate notes
        String notes = generateNotes(buyerType, nationality, residenceStatus, isMortgageRequired, selectedBank);
        checklist.setNotes(notes);
        
        return documentChecklistRepository.save(checklist);
    }

    /**
     * Generate identity documents based on nationality and residence status.
     *
     * @param nationality the nationality of the buyer
     * @param residenceStatus the residence status of the buyer
     * @return list of required identity documents
     */
    private List<String> generateIdentityDocuments(String nationality, String residenceStatus) {
        List<String> documents = new ArrayList<>();
        
        // Common documents for all
        documents.add("Passport copy");
        
        if ("UAE_RESIDENT".equals(residenceStatus)) {
            documents.add("UAE Residence Visa copy");
            documents.add("Emirates ID copy");
        }
        
        if ("UAE".equals(nationality)) {
            documents.add("UAE National ID copy");
            documents.add("Family Book copy");
        } else {
            documents.add("Home country ID copy");
        }
        
        return documents;
    }

    /**
     * Generate income proof documents based on buyer type.
     *
     * @param buyerType the type of buyer
     * @return list of required income proof documents
     */
    private List<String> generateIncomeProofDocuments(String buyerType) {
        List<String> documents = new ArrayList<>();
        
        if ("SALARIED".equals(buyerType)) {
            documents.add("Salary certificate (less than 1 month old)");
            documents.add("Last 6 months bank statements showing salary credits");
            documents.add("Employment contract");
            documents.add("Labor contract from Ministry of Labor (if applicable)");
        } else if ("SELF_EMPLOYED".equals(buyerType)) {
            documents.add("Trade license copy");
            documents.add("Memorandum of Association");
            documents.add("Last 2 years audited financial statements");
            documents.add("Last 6 months personal and company bank statements");
            documents.add("Proof of business ownership");
        } else if ("INVESTOR".equals(buyerType)) {
            documents.add("Proof of investments (shares, bonds, etc.)");
            documents.add("Last 6 months investment account statements");
            documents.add("Last 6 months personal bank statements");
        } else {
            documents.add("Proof of income");
            documents.add("Last 6 months bank statements");
        }
        
        return documents;
    }

    /**
     * Generate property documents based on property status.
     *
     * @param isOffPlan whether the property is off-plan
     * @param isReady whether the property is ready
     * @return list of required property documents
     */
    private List<String> generatePropertyDocuments(Boolean isOffPlan, Boolean isReady) {
        List<String> documents = new ArrayList<>();
        
        if (isOffPlan) {
            documents.add("Sale and Purchase Agreement (SPA)");
            documents.add("Reservation form");
            documents.add("Developer payment plan");
            documents.add("Proof of payments made to developer");
            documents.add("OQOOD pre-registration receipt");
        }
        
        if (isReady) {
            documents.add("Title deed copy (if available)");
            documents.add("DEWA connection proof");
            documents.add("Service charge payment receipts");
            documents.add("NOC from developer for resale");
            documents.add("Property layout/floor plan");
        }
        
        documents.add("Property valuation report (for mortgage)");
        
        return documents;
    }

    /**
     * Generate bank documents based on mortgage requirement and selected bank.
     *
     * @param isMortgageRequired whether a mortgage is required
     * @param selectedBank the selected bank for mortgage
     * @return list of required bank documents
     */
    private List<String> generateBankDocuments(Boolean isMortgageRequired, String selectedBank) {
        List<String> documents = new ArrayList<>();
        
        if (isMortgageRequired) {
            documents.add("Mortgage application form");
            documents.add("Mortgage pre-approval letter");
            documents.add("Life insurance application");
            documents.add("Property insurance application");
            
            if ("EMIRATES_NBD".equals(selectedBank)) {
                documents.add("Emirates NBD account statement (if existing customer)");
                documents.add("Emirates NBD specific forms");
            } else if ("ADCB".equals(selectedBank)) {
                documents.add("ADCB account statement (if existing customer)");
                documents.add("ADCB specific forms");
            } else if ("DIB".equals(selectedBank)) {
                documents.add("DIB account statement (if existing customer)");
                documents.add("DIB specific forms");
            } else if ("MASHREQ".equals(selectedBank)) {
                documents.add("Mashreq account statement (if existing customer)");
                documents.add("Mashreq specific forms");
            } else {
                documents.add("Bank account statement (if existing customer)");
                documents.add("Bank specific forms");
            }
        }
        
        return documents;
    }

    /**
     * Generate visa/residency documents based on nationality and residence status.
     *
     * @param nationality the nationality of the buyer
     * @param residenceStatus the residence status of the buyer
     * @return list of required visa/residency documents
     */
    private List<String> generateVisaDocuments(String nationality, String residenceStatus) {
        List<String> documents = new ArrayList<>();
        
        if (!"UAE".equals(nationality)) {
            if ("UAE_RESIDENT".equals(residenceStatus)) {
                documents.add("UAE Residence Visa copy");
                documents.add("Entry stamp page copy");
            } else {
                documents.add("Visit visa copy (if in UAE)");
                documents.add("Entry stamp page copy (if in UAE)");
            }
        }
        
        return documents;
    }

    /**
     * Generate additional documents based on buyer type, mortgage requirement, and property status.
     *
     * @param buyerType the type of buyer
     * @param isMortgageRequired whether a mortgage is required
     * @param isOffPlan whether the property is off-plan
     * @return list of additional documents
     */
    private List<String> generateAdditionalDocuments(String buyerType, Boolean isMortgageRequired, Boolean isOffPlan) {
        List<String> documents = new ArrayList<>();
        
        documents.add("Signed DLD transfer forms");
        documents.add("Manager's cheque for DLD fees");
        
        if (isMortgageRequired) {
            documents.add("Manager's cheque for down payment");
            documents.add("Credit card statement (if applicable)");
            documents.add("Liability letter from existing banks");
        }
        
        if ("SELF_EMPLOYED".equals(buyerType)) {
            documents.add("Power of Attorney (if applicable)");
            documents.add("Board resolution for property purchase (if company purchase)");
        }
        
        if (isOffPlan) {
            documents.add("Escrow account details");
        }
        
        return documents;
    }

    /**
     * Generate notes based on buyer details.
     *
     * @param buyerType the type of buyer
     * @param nationality the nationality of the buyer
     * @param residenceStatus the residence status of the buyer
     * @param isMortgageRequired whether a mortgage is required
     * @param selectedBank the selected bank for mortgage
     * @return notes for the document checklist
     */
    private String generateNotes(String buyerType, String nationality, String residenceStatus,
                               Boolean isMortgageRequired, String selectedBank) {
        StringBuilder notes = new StringBuilder();
        
        notes.append("This document checklist is personalized based on your profile as a ")
             .append(buyerType.toLowerCase()).append(" buyer");
        
        if (!"UAE".equals(nationality)) {
            notes.append(" with ").append(nationality).append(" nationality");
        }
        
        notes.append(".\n\n");
        
        if (isMortgageRequired) {
            notes.append("For mortgage applications with ").append(selectedBank)
                 .append(", please ensure all documents are less than 1 month old unless specified otherwise.\n");
            notes.append("Pre-approval typically takes 3-5 working days, and final approval takes 7-10 working days.\n\n");
        }
        
        if ("NON_RESIDENT".equals(residenceStatus)) {
            notes.append("As a non-resident buyer, you may need to provide additional documentation and attestations from your home country.\n");
            notes.append("All foreign documents must be attested by the UAE embassy in your country and the Ministry of Foreign Affairs in the UAE.\n\n");
        }
        
        notes.append("Please note that this checklist is a guide and additional documents may be requested by the authorities, developer, or bank during the process.");
        
        return notes.toString();
    }

    /**
     * Delete a document checklist.
     *
     * @param id the document checklist ID
     */
    public void deleteDocumentChecklist(Long id) {
        documentChecklistRepository.deleteById(id);
    }

    /**
     * Delete all document checklists for a property.
     *
     * @param propertyId the property ID
     */
    public void deleteAllDocumentChecklistsForProperty(Long propertyId) {
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new RuntimeException("Property not found with id " + propertyId));
        documentChecklistRepository.deleteByProperty(property);
    }
}