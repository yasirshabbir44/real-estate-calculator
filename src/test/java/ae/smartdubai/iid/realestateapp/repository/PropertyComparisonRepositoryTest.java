package ae.smartdubai.iid.realestateapp.repository;

import ae.smartdubai.iid.realestateapp.model.PropertyComparison;
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
public class PropertyComparisonRepositoryTest {

    @Autowired
    private PropertyComparisonRepository propertyComparisonRepository;

    @Autowired
    private PropertyRepository propertyRepository;

    private Property property1;
    private Property property2;
    private Property property3;
    private PropertyComparison comparison1;
    private PropertyComparison comparison2;
    private PropertyComparison comparison3;

    @BeforeEach
    public void setup() {
        // Clear any existing data
        propertyComparisonRepository.deleteAll();
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

        property3 = new Property();
        property3.setName("Test Property 3");
        property3.setLocation("Test Location 3");
        property3.setPrice(1200000.0);
        property3.setSize(250.0);
        property3.setBedrooms(3);
        property3.setBathrooms(2);
        property3.setPropertyType("Townhouse");
        property3.setCommunityName("Test Community 3");
        property3.setIsFurnished(false);
        property3.setYearBuilt(2021);
        property3 = propertyRepository.save(property3);

        // Create test property comparisons
        // Property vs Property comparison
        comparison1 = new PropertyComparison();
        comparison1.setProperty1(property1);
        comparison1.setProperty2(property2);
        comparison1.setIsRentVsBuy(false);
        comparison1.setPropertyAppreciationRate(5.0);
        comparison1.setHoldingPeriodYears(10);
        comparison1.setComparisonDate(LocalDate.of(2023, 1, 15));
        comparison1.setProperty1TotalCost(1200000.0); // Including all costs
        comparison1.setProperty2TotalCost(1800000.0); // Including all costs
        comparison1.setProperty1Roi(25.0);
        comparison1.setProperty2Roi(20.0);
        comparison1 = propertyComparisonRepository.save(comparison1);

        // Another Property vs Property comparison
        comparison2 = new PropertyComparison();
        comparison2.setProperty1(property1);
        comparison2.setProperty2(property3);
        comparison2.setIsRentVsBuy(false);
        comparison2.setPropertyAppreciationRate(4.5);
        comparison2.setHoldingPeriodYears(15);
        comparison2.setComparisonDate(LocalDate.of(2023, 2, 20)); // More recent date
        comparison2.setProperty1TotalCost(1250000.0); // Including all costs
        comparison2.setProperty2TotalCost(1450000.0); // Including all costs
        comparison2.setProperty1Roi(30.0);
        comparison2.setProperty2Roi(28.0);
        comparison2 = propertyComparisonRepository.save(comparison2);

        // Rent vs Buy comparison
        comparison3 = new PropertyComparison();
        comparison3.setProperty1(property2);
        comparison3.setProperty2(null); // No second property for rent vs buy
        comparison3.setIsRentVsBuy(true);
        comparison3.setMonthlyRent(6000.0);
        comparison3.setAnnualRentIncrease(3.0);
        comparison3.setInvestmentReturnRate(4.0);
        comparison3.setPropertyAppreciationRate(5.0);
        comparison3.setHoldingPeriodYears(20);
        comparison3.setComparisonDate(LocalDate.of(2023, 3, 10));
        comparison3.setBreakEvenYears(7.5);
        comparison3.setBuyingNpv(500000.0);
        comparison3.setRentingNpv(350000.0);
        comparison3.setProperty1TotalCost(1800000.0); // Total cost of buying
        comparison3 = propertyComparisonRepository.save(comparison3);
    }

    @Test
    public void testFindByProperty1() {
        // Test finding property comparisons by first property
        List<PropertyComparison> results = propertyComparisonRepository.findByProperty1(property1);
        
        // Verify results
        assertEquals(2, results.size());
        assertTrue(results.contains(comparison1));
        assertTrue(results.contains(comparison2));
        assertFalse(results.contains(comparison3));
    }

    @Test
    public void testFindByProperty2() {
        // Test finding property comparisons by second property
        List<PropertyComparison> results = propertyComparisonRepository.findByProperty2(property2);
        
        // Verify results
        assertEquals(1, results.size());
        assertTrue(results.contains(comparison1));
        assertFalse(results.contains(comparison2));
        assertFalse(results.contains(comparison3));
    }

    @Test
    public void testFindByProperty1AndProperty2() {
        // Test finding property comparisons by both properties
        List<PropertyComparison> results = propertyComparisonRepository.findByProperty1AndProperty2(property1, property2);
        
        // Verify results
        assertEquals(1, results.size());
        assertTrue(results.contains(comparison1));
        assertFalse(results.contains(comparison2));
        assertFalse(results.contains(comparison3));
    }

    @Test
    public void testFindFirstByProperty1OrderByComparisonDateDesc() {
        // Test finding the latest property comparison for a property
        Optional<PropertyComparison> result = propertyComparisonRepository.findFirstByProperty1OrderByComparisonDateDesc(property1);
        
        // Verify result
        assertTrue(result.isPresent());
        assertEquals(comparison2, result.get()); // comparison2 has a more recent date
    }

    @Test
    public void testFindByComparisonDateBetween() {
        // Test finding property comparisons by date range
        LocalDate startDate = LocalDate.of(2023, 2, 1);
        LocalDate endDate = LocalDate.of(2023, 3, 15);
        List<PropertyComparison> results = propertyComparisonRepository.findByComparisonDateBetween(startDate, endDate);
        
        // Verify results
        assertEquals(2, results.size());
        assertTrue(results.contains(comparison2));
        assertTrue(results.contains(comparison3));
        assertFalse(results.contains(comparison1));
    }

    @Test
    public void testFindByIsRentVsBuyTrue() {
        // Test finding rent vs buy comparisons
        List<PropertyComparison> results = propertyComparisonRepository.findByIsRentVsBuyTrue();
        
        // Verify results
        assertEquals(1, results.size());
        assertTrue(results.contains(comparison3));
        assertFalse(results.contains(comparison1));
        assertFalse(results.contains(comparison2));
    }

    @Test
    public void testFindByIsRentVsBuyFalse() {
        // Test finding property vs property comparisons
        List<PropertyComparison> results = propertyComparisonRepository.findByIsRentVsBuyFalse();
        
        // Verify results
        assertEquals(2, results.size());
        assertTrue(results.contains(comparison1));
        assertTrue(results.contains(comparison2));
        assertFalse(results.contains(comparison3));
    }

    @Test
    public void testDeleteByProperty1OrProperty2() {
        // Test deleting property comparisons by property
        propertyComparisonRepository.deleteByProperty1OrProperty2(property1, property1);
        
        // Verify deletion
        List<PropertyComparison> remainingComparisons = propertyComparisonRepository.findAll();
        assertEquals(1, remainingComparisons.size());
        assertTrue(remainingComparisons.contains(comparison3));
        assertFalse(remainingComparisons.contains(comparison1));
        assertFalse(remainingComparisons.contains(comparison2));
    }
}