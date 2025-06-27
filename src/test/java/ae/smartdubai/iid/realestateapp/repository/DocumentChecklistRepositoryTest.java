package ae.smartdubai.iid.realestateapp.repository;

import ae.smartdubai.iid.realestateapp.model.DocumentChecklist;
import ae.smartdubai.iid.realestateapp.model.Property;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
public class DocumentChecklistRepositoryTest {

    @Autowired
    private DocumentChecklistRepository documentChecklistRepository;

    @Autowired
    private PropertyRepository propertyRepository;

    private Property property1;
    private Property property2;
    private DocumentChecklist checklist1;
    private DocumentChecklist checklist2;
    private DocumentChecklist checklist3;

    @BeforeEach
    public void setup() {
        // Clear any existing data
        documentChecklistRepository.deleteAll();
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

        // Create test document checklists
        checklist1 = new DocumentChecklist();
        checklist1.setProperty(property1);
        checklist1.setBuyerType("SALARIED");
        checklist1.setSelectedBank("Emirates NBD");
        checklist1.setNationality("UAE");
        checklist1.setResidenceStatus("UAE Resident");
        checklist1.setIdentityDocuments(Arrays.asList("Emirates ID", "Passport", "Visa"));
        checklist1.setIncomeProofDocuments(Arrays.asList("Salary Certificate", "Bank Statements"));
        checklist1.setPropertyDocuments(Arrays.asList("Title Deed", "Floor Plan"));
        checklist1.setBankDocuments(Arrays.asList("Bank Statement", "Credit Report"));
        checklist1.setVisaDocuments(Arrays.asList("Residence Visa"));
        checklist1.setAdditionalDocuments(Arrays.asList("NOC from Employer"));
        checklist1.setNotes("Standard documentation for UAE resident salaried employee");
        checklist1.setCreationDate(LocalDate.of(2023, 1, 15));
        checklist1.setIsMortgageRequired(true);
        checklist1.setIsOffPlan(false);
        checklist1.setIsReady(true);
        checklist1 = documentChecklistRepository.save(checklist1);

        checklist2 = new DocumentChecklist();
        checklist2.setProperty(property1);
        checklist2.setBuyerType("SELF_EMPLOYED");
        checklist2.setSelectedBank("Dubai Islamic Bank");
        checklist2.setNationality("UAE");
        checklist2.setResidenceStatus("UAE Resident");
        checklist2.setIdentityDocuments(Arrays.asList("Emirates ID", "Passport", "Visa"));
        checklist2.setIncomeProofDocuments(Arrays.asList("Trade License", "Financial Statements", "Bank Statements"));
        checklist2.setPropertyDocuments(Arrays.asList("Title Deed", "Floor Plan"));
        checklist2.setBankDocuments(Arrays.asList("Bank Statement", "Credit Report"));
        checklist2.setVisaDocuments(Arrays.asList("Residence Visa"));
        checklist2.setAdditionalDocuments(Arrays.asList("Business Registration"));
        checklist2.setNotes("Documentation for UAE resident self-employed individual");
        checklist2.setCreationDate(LocalDate.of(2023, 2, 20)); // More recent date
        checklist2.setIsMortgageRequired(true);
        checklist2.setIsOffPlan(false);
        checklist2.setIsReady(true);
        checklist2 = documentChecklistRepository.save(checklist2);

        checklist3 = new DocumentChecklist();
        checklist3.setProperty(property2);
        checklist3.setBuyerType("INVESTOR");
        checklist3.setSelectedBank(null); // No mortgage
        checklist3.setNationality("UK");
        checklist3.setResidenceStatus("Non-Resident");
        checklist3.setIdentityDocuments(Arrays.asList("Passport", "UK ID"));
        checklist3.setIncomeProofDocuments(Arrays.asList("Bank Statements", "Investment Portfolio"));
        checklist3.setPropertyDocuments(Arrays.asList("Title Deed", "Floor Plan", "Property Valuation"));
        checklist3.setBankDocuments(Arrays.asList("International Bank Statement"));
        checklist3.setVisaDocuments(Arrays.asList("Entry Permit"));
        checklist3.setAdditionalDocuments(Arrays.asList("Source of Funds Declaration"));
        checklist3.setNotes("Documentation for non-resident investor from UK");
        checklist3.setCreationDate(LocalDate.of(2023, 3, 10));
        checklist3.setIsMortgageRequired(false);
        checklist3.setIsOffPlan(true);
        checklist3.setIsReady(false);
        checklist3 = documentChecklistRepository.save(checklist3);
    }

    @Test
    public void testFindByProperty() {
        // Test finding document checklists by property
        List<DocumentChecklist> results = documentChecklistRepository.findByProperty(property1);
        
        // Verify results
        assertEquals(2, results.size());
        assertTrue(results.contains(checklist1));
        assertTrue(results.contains(checklist2));
        assertFalse(results.contains(checklist3));
    }

    @Test
    public void testFindFirstByPropertyOrderByCreationDateDesc() {
        // Test finding the latest document checklist for a property
        Optional<DocumentChecklist> result = documentChecklistRepository.findFirstByPropertyOrderByCreationDateDesc(property1);
        
        // Verify result
        assertTrue(result.isPresent());
        assertEquals(checklist2, result.get()); // checklist2 has a more recent date
    }

    @Test
    public void testFindByBuyerType() {
        // Test finding document checklists by buyer type
        List<DocumentChecklist> results = documentChecklistRepository.findByBuyerType("SALARIED");
        
        // Verify results
        assertEquals(1, results.size());
        assertTrue(results.contains(checklist1));
        assertFalse(results.contains(checklist2));
        assertFalse(results.contains(checklist3));
    }

    @Test
    public void testFindBySelectedBank() {
        // Test finding document checklists by selected bank
        List<DocumentChecklist> results = documentChecklistRepository.findBySelectedBank("Emirates NBD");
        
        // Verify results
        assertEquals(1, results.size());
        assertTrue(results.contains(checklist1));
        assertFalse(results.contains(checklist2));
        assertFalse(results.contains(checklist3));
    }

    @Test
    public void testFindByNationality() {
        // Test finding document checklists by nationality
        List<DocumentChecklist> results = documentChecklistRepository.findByNationality("UAE");
        
        // Verify results
        assertEquals(2, results.size());
        assertTrue(results.contains(checklist1));
        assertTrue(results.contains(checklist2));
        assertFalse(results.contains(checklist3));
    }

    @Test
    public void testFindByResidenceStatus() {
        // Test finding document checklists by residence status
        List<DocumentChecklist> results = documentChecklistRepository.findByResidenceStatus("Non-Resident");
        
        // Verify results
        assertEquals(1, results.size());
        assertTrue(results.contains(checklist3));
        assertFalse(results.contains(checklist1));
        assertFalse(results.contains(checklist2));
    }

    @Test
    public void testFindByCreationDateBetween() {
        // Test finding document checklists by date range
        LocalDate startDate = LocalDate.of(2023, 2, 1);
        LocalDate endDate = LocalDate.of(2023, 3, 15);
        List<DocumentChecklist> results = documentChecklistRepository.findByCreationDateBetween(startDate, endDate);
        
        // Verify results
        assertEquals(2, results.size());
        assertTrue(results.contains(checklist2));
        assertTrue(results.contains(checklist3));
        assertFalse(results.contains(checklist1));
    }

    @Test
    public void testFindByIsMortgageRequired() {
        // Test finding document checklists by mortgage requirement
        List<DocumentChecklist> results = documentChecklistRepository.findByIsMortgageRequired(false);
        
        // Verify results
        assertEquals(1, results.size());
        assertTrue(results.contains(checklist3));
        assertFalse(results.contains(checklist1));
        assertFalse(results.contains(checklist2));
    }

    @Test
    public void testDeleteByProperty() {
        // Test deleting document checklists by property
        documentChecklistRepository.deleteByProperty(property1);
        
        // Verify deletion
        List<DocumentChecklist> remainingChecklists = documentChecklistRepository.findAll();
        assertEquals(1, remainingChecklists.size());
        assertTrue(remainingChecklists.contains(checklist3));
        assertFalse(remainingChecklists.contains(checklist1));
        assertFalse(remainingChecklists.contains(checklist2));
    }
}