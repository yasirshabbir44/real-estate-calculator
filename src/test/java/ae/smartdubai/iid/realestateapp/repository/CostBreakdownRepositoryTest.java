package ae.smartdubai.iid.realestateapp.repository;

import ae.smartdubai.iid.realestateapp.model.CostBreakdown;
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
public class CostBreakdownRepositoryTest {

    @Autowired
    private CostBreakdownRepository costBreakdownRepository;

    @Autowired
    private PropertyRepository propertyRepository;

    private Property property1;
    private Property property2;
    private CostBreakdown costBreakdown1;
    private CostBreakdown costBreakdown2;
    private CostBreakdown costBreakdown3;

    @BeforeEach
    public void setup() {
        // Clear any existing data
        costBreakdownRepository.deleteAll();
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

        // Create test cost breakdowns
        costBreakdown1 = new CostBreakdown();
        costBreakdown1.setProperty(property1);
        costBreakdown1.setDldFee(40000.0); // 4% of property price
        costBreakdown1.setAgencyFee(20000.0); // 2% of property price
        costBreakdown1.setRegistrationFee(2000.0);
        costBreakdown1.setMortgageRegistrationFee(1000.0);
        costBreakdown1.setValuationFee(3000.0);
        costBreakdown1.setMortgageProcessingFee(5000.0);
        costBreakdown1.setLifeInsuranceCost(2000.0);
        costBreakdown1.setPropertyInsuranceCost(1500.0);
        costBreakdown1.setMaintenanceDeposit(5000.0);
        costBreakdown1.setUtilityConnectionFees(1000.0);
        costBreakdown1.setMovingCosts(3000.0);
        costBreakdown1.setTotalCost(1083500.0); // Sum of all costs including property price
        costBreakdown1.setCalculationDate(LocalDate.of(2023, 1, 15));
        costBreakdown1 = costBreakdownRepository.save(costBreakdown1);

        costBreakdown2 = new CostBreakdown();
        costBreakdown2.setProperty(property1);
        costBreakdown2.setDldFee(40000.0);
        costBreakdown2.setAgencyFee(20000.0);
        costBreakdown2.setRegistrationFee(2000.0);
        costBreakdown2.setMortgageRegistrationFee(1000.0);
        costBreakdown2.setValuationFee(3000.0);
        costBreakdown2.setMortgageProcessingFee(5000.0);
        costBreakdown2.setLifeInsuranceCost(2000.0);
        costBreakdown2.setPropertyInsuranceCost(1500.0);
        costBreakdown2.setMaintenanceDeposit(5000.0);
        costBreakdown2.setUtilityConnectionFees(1000.0);
        costBreakdown2.setMovingCosts(3000.0);
        costBreakdown2.setTotalCost(1083500.0);
        costBreakdown2.setCalculationDate(LocalDate.of(2023, 2, 20)); // More recent date
        costBreakdown2 = costBreakdownRepository.save(costBreakdown2);

        costBreakdown3 = new CostBreakdown();
        costBreakdown3.setProperty(property2);
        costBreakdown3.setDldFee(60000.0); // 4% of property price
        costBreakdown3.setAgencyFee(30000.0); // 2% of property price
        costBreakdown3.setRegistrationFee(2000.0);
        costBreakdown3.setMortgageRegistrationFee(1500.0);
        costBreakdown3.setValuationFee(3500.0);
        costBreakdown3.setMortgageProcessingFee(6000.0);
        costBreakdown3.setLifeInsuranceCost(2500.0);
        costBreakdown3.setPropertyInsuranceCost(2000.0);
        costBreakdown3.setMaintenanceDeposit(7000.0);
        costBreakdown3.setUtilityConnectionFees(1200.0);
        costBreakdown3.setMovingCosts(4000.0);
        costBreakdown3.setTotalCost(1619700.0); // Sum of all costs including property price
        costBreakdown3.setCalculationDate(LocalDate.of(2023, 3, 10));
        costBreakdown3 = costBreakdownRepository.save(costBreakdown3);
    }

    @Test
    public void testFindByProperty() {
        // Test finding cost breakdowns by property
        List<CostBreakdown> results = costBreakdownRepository.findByProperty(property1);
        
        // Verify results
        assertEquals(2, results.size());
        assertTrue(results.contains(costBreakdown1));
        assertTrue(results.contains(costBreakdown2));
        assertFalse(results.contains(costBreakdown3));
    }

    @Test
    public void testFindFirstByPropertyOrderByCalculationDateDesc() {
        // Test finding the latest cost breakdown for a property
        Optional<CostBreakdown> result = costBreakdownRepository.findFirstByPropertyOrderByCalculationDateDesc(property1);
        
        // Verify result
        assertTrue(result.isPresent());
        assertEquals(costBreakdown2, result.get()); // costBreakdown2 has a more recent date
    }

    @Test
    public void testFindByCalculationDateBetween() {
        // Test finding cost breakdowns by date range
        LocalDate startDate = LocalDate.of(2023, 2, 1);
        LocalDate endDate = LocalDate.of(2023, 3, 15);
        List<CostBreakdown> results = costBreakdownRepository.findByCalculationDateBetween(startDate, endDate);
        
        // Verify results
        assertEquals(2, results.size());
        assertTrue(results.contains(costBreakdown2));
        assertTrue(results.contains(costBreakdown3));
        assertFalse(results.contains(costBreakdown1));
    }

    @Test
    public void testFindByTotalCostBetween() {
        // Test finding cost breakdowns by total cost range
        Double minTotalCost = 1100000.0;
        Double maxTotalCost = 1700000.0;
        List<CostBreakdown> results = costBreakdownRepository.findByTotalCostBetween(minTotalCost, maxTotalCost);
        
        // Verify results
        assertEquals(1, results.size());
        assertTrue(results.contains(costBreakdown3));
        assertFalse(results.contains(costBreakdown1));
        assertFalse(results.contains(costBreakdown2));
    }

    @Test
    public void testDeleteByProperty() {
        // Test deleting cost breakdowns by property
        costBreakdownRepository.deleteByProperty(property1);
        
        // Verify deletion
        List<CostBreakdown> remainingBreakdowns = costBreakdownRepository.findAll();
        assertEquals(1, remainingBreakdowns.size());
        assertTrue(remainingBreakdowns.contains(costBreakdown3));
        assertFalse(remainingBreakdowns.contains(costBreakdown1));
        assertFalse(remainingBreakdowns.contains(costBreakdown2));
    }
}