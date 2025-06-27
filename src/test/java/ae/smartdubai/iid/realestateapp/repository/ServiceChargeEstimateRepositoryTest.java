package ae.smartdubai.iid.realestateapp.repository;

import ae.smartdubai.iid.realestateapp.model.ServiceChargeEstimate;
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
public class ServiceChargeEstimateRepositoryTest {

    @Autowired
    private ServiceChargeEstimateRepository serviceChargeEstimateRepository;

    @Autowired
    private PropertyRepository propertyRepository;

    private Property property1;
    private Property property2;
    private ServiceChargeEstimate estimate1;
    private ServiceChargeEstimate estimate2;
    private ServiceChargeEstimate estimate3;
    private ServiceChargeEstimate estimate4;

    @BeforeEach
    public void setup() {
        // Clear any existing data
        serviceChargeEstimateRepository.deleteAll();
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
        property1.setCommunityName("Dubai Marina");
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
        property2.setCommunityName("Arabian Ranches");
        property2.setIsFurnished(true);
        property2.setYearBuilt(2019);
        property2 = propertyRepository.save(property2);

        // Create test service charge estimates
        estimate1 = new ServiceChargeEstimate();
        estimate1.setProperty(property1);
        estimate1.setCommunityName("Dubai Marina");
        estimate1.setPropertyType("Apartment");
        estimate1.setPropertySize(1200.0);
        estimate1.setServiceChargeRatePerSqFt(15.0);
        estimate1.setAnnualServiceCharge(18000.0); // 1200 * 15
        estimate1.setCoolingCharges(6000.0);
        estimate1.setBuildingMaintenanceFee(3000.0);
        estimate1.setSecurityFee(2000.0);
        estimate1.setCleaningFee(1500.0);
        estimate1.setParkingFee(1000.0);
        estimate1.setGymAndPoolFee(2500.0);
        estimate1.setMiscCharges(1000.0);
        estimate1.setTotalAnnualCharges(35000.0); // Sum of all charges
        estimate1.setMonthlyCharges(2916.67); // 35000 / 12
        estimate1.setEstimateYear(2023);
        estimate1.setEstimateDate(LocalDate.of(2023, 1, 15));
        estimate1.setIsPreFilled(false);
        estimate1 = serviceChargeEstimateRepository.save(estimate1);

        estimate2 = new ServiceChargeEstimate();
        estimate2.setProperty(property1);
        estimate2.setCommunityName("Dubai Marina");
        estimate2.setPropertyType("Apartment");
        estimate2.setPropertySize(1200.0);
        estimate2.setServiceChargeRatePerSqFt(16.0); // Increased rate
        estimate2.setAnnualServiceCharge(19200.0); // 1200 * 16
        estimate2.setCoolingCharges(6500.0);
        estimate2.setBuildingMaintenanceFee(3200.0);
        estimate2.setSecurityFee(2100.0);
        estimate2.setCleaningFee(1600.0);
        estimate2.setParkingFee(1100.0);
        estimate2.setGymAndPoolFee(2600.0);
        estimate2.setMiscCharges(1100.0);
        estimate2.setTotalAnnualCharges(37400.0); // Sum of all charges
        estimate2.setMonthlyCharges(3116.67); // 37400 / 12
        estimate2.setEstimateYear(2023);
        estimate2.setEstimateDate(LocalDate.of(2023, 2, 20)); // More recent date
        estimate2.setIsPreFilled(false);
        estimate2 = serviceChargeEstimateRepository.save(estimate2);

        estimate3 = new ServiceChargeEstimate();
        estimate3.setProperty(property2);
        estimate3.setCommunityName("Arabian Ranches");
        estimate3.setPropertyType("Villa");
        estimate3.setPropertySize(3000.0);
        estimate3.setServiceChargeRatePerSqFt(10.0);
        estimate3.setAnnualServiceCharge(30000.0); // 3000 * 10
        estimate3.setCoolingCharges(8000.0);
        estimate3.setBuildingMaintenanceFee(5000.0);
        estimate3.setSecurityFee(3000.0);
        estimate3.setCleaningFee(2000.0);
        estimate3.setParkingFee(0.0); // No parking fee for villa
        estimate3.setGymAndPoolFee(4000.0);
        estimate3.setMiscCharges(2000.0);
        estimate3.setTotalAnnualCharges(54000.0); // Sum of all charges
        estimate3.setMonthlyCharges(4500.0); // 54000 / 12
        estimate3.setEstimateYear(2023);
        estimate3.setEstimateDate(LocalDate.of(2023, 3, 10));
        estimate3.setIsPreFilled(false);
        estimate3 = serviceChargeEstimateRepository.save(estimate3);

        // Pre-filled estimate (not associated with a specific property)
        estimate4 = new ServiceChargeEstimate();
        estimate4.setProperty(null);
        estimate4.setCommunityName("Dubai Marina");
        estimate4.setPropertyType("Apartment");
        estimate4.setPropertySize(1000.0);
        estimate4.setServiceChargeRatePerSqFt(15.0);
        estimate4.setAnnualServiceCharge(15000.0); // 1000 * 15
        estimate4.setCoolingCharges(5000.0);
        estimate4.setBuildingMaintenanceFee(2500.0);
        estimate4.setSecurityFee(1800.0);
        estimate4.setCleaningFee(1200.0);
        estimate4.setParkingFee(900.0);
        estimate4.setGymAndPoolFee(2000.0);
        estimate4.setMiscCharges(800.0);
        estimate4.setTotalAnnualCharges(29200.0); // Sum of all charges
        estimate4.setMonthlyCharges(2433.33); // 29200 / 12
        estimate4.setEstimateYear(2023);
        estimate4.setEstimateDate(LocalDate.of(2023, 1, 1));
        estimate4.setIsPreFilled(true);
        estimate4 = serviceChargeEstimateRepository.save(estimate4);
    }

    @Test
    public void testFindByProperty() {
        // Test finding service charge estimates by property
        List<ServiceChargeEstimate> results = serviceChargeEstimateRepository.findByProperty(property1);
        
        // Verify results
        assertEquals(2, results.size());
        assertTrue(results.contains(estimate1));
        assertTrue(results.contains(estimate2));
        assertFalse(results.contains(estimate3));
        assertFalse(results.contains(estimate4));
    }

    @Test
    public void testFindFirstByPropertyOrderByEstimateDateDesc() {
        // Test finding the latest service charge estimate for a property
        Optional<ServiceChargeEstimate> result = serviceChargeEstimateRepository.findFirstByPropertyOrderByEstimateDateDesc(property1);
        
        // Verify result
        assertTrue(result.isPresent());
        assertEquals(estimate2, result.get()); // estimate2 has a more recent date
    }

    @Test
    public void testFindByCommunityNameIgnoreCase() {
        // Test finding service charge estimates by community name
        List<ServiceChargeEstimate> results = serviceChargeEstimateRepository.findByCommunityNameIgnoreCase("dubai marina");
        
        // Verify results
        assertEquals(3, results.size());
        assertTrue(results.contains(estimate1));
        assertTrue(results.contains(estimate2));
        assertTrue(results.contains(estimate4));
        assertFalse(results.contains(estimate3));
    }

    @Test
    public void testFindByPropertyTypeIgnoreCase() {
        // Test finding service charge estimates by property type
        List<ServiceChargeEstimate> results = serviceChargeEstimateRepository.findByPropertyTypeIgnoreCase("apartment");
        
        // Verify results
        assertEquals(3, results.size());
        assertTrue(results.contains(estimate1));
        assertTrue(results.contains(estimate2));
        assertTrue(results.contains(estimate4));
        assertFalse(results.contains(estimate3));
    }

    @Test
    public void testFindByPropertySizeBetween() {
        // Test finding service charge estimates by property size range
        Double minSize = 1100.0;
        Double maxSize = 2000.0;
        List<ServiceChargeEstimate> results = serviceChargeEstimateRepository.findByPropertySizeBetween(minSize, maxSize);
        
        // Verify results
        assertEquals(2, results.size());
        assertTrue(results.contains(estimate1));
        assertTrue(results.contains(estimate2));
        assertFalse(results.contains(estimate3));
        assertFalse(results.contains(estimate4));
    }

    @Test
    public void testFindByAnnualServiceChargeBetween() {
        // Test finding service charge estimates by annual service charge range
        Double minCharge = 20000.0;
        Double maxCharge = 40000.0;
        List<ServiceChargeEstimate> results = serviceChargeEstimateRepository.findByAnnualServiceChargeBetween(minCharge, maxCharge);
        
        // Verify results
        assertEquals(1, results.size());
        assertFalse(results.contains(estimate1));
        assertFalse(results.contains(estimate2));
        assertTrue(results.contains(estimate3));
        assertFalse(results.contains(estimate4));
    }

    @Test
    public void testFindByEstimateDateBetween() {
        // Test finding service charge estimates by estimate date range
        LocalDate startDate = LocalDate.of(2023, 2, 1);
        LocalDate endDate = LocalDate.of(2023, 3, 15);
        List<ServiceChargeEstimate> results = serviceChargeEstimateRepository.findByEstimateDateBetween(startDate, endDate);
        
        // Verify results
        assertEquals(2, results.size());
        assertFalse(results.contains(estimate1));
        assertTrue(results.contains(estimate2));
        assertTrue(results.contains(estimate3));
        assertFalse(results.contains(estimate4));
    }

    @Test
    public void testFindByIsPreFilledTrue() {
        // Test finding pre-filled service charge estimates
        List<ServiceChargeEstimate> results = serviceChargeEstimateRepository.findByIsPreFilledTrue();
        
        // Verify results
        assertEquals(1, results.size());
        assertFalse(results.contains(estimate1));
        assertFalse(results.contains(estimate2));
        assertFalse(results.contains(estimate3));
        assertTrue(results.contains(estimate4));
    }

    @Test
    public void testFindByIsPreFilledTrueAndCommunityNameIgnoreCase() {
        // Test finding pre-filled service charge estimates by community name
        List<ServiceChargeEstimate> results = serviceChargeEstimateRepository.findByIsPreFilledTrueAndCommunityNameIgnoreCase("dubai marina");
        
        // Verify results
        assertEquals(1, results.size());
        assertFalse(results.contains(estimate1));
        assertFalse(results.contains(estimate2));
        assertFalse(results.contains(estimate3));
        assertTrue(results.contains(estimate4));
    }

    @Test
    public void testDeleteByProperty() {
        // Test deleting service charge estimates by property
        serviceChargeEstimateRepository.deleteByProperty(property1);
        
        // Verify deletion
        List<ServiceChargeEstimate> remainingEstimates = serviceChargeEstimateRepository.findAll();
        assertEquals(2, remainingEstimates.size());
        assertTrue(remainingEstimates.contains(estimate3));
        assertTrue(remainingEstimates.contains(estimate4));
        assertFalse(remainingEstimates.contains(estimate1));
        assertFalse(remainingEstimates.contains(estimate2));
    }
}