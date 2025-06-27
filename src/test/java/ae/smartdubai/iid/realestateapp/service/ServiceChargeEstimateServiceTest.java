package ae.smartdubai.iid.realestateapp.service;

import ae.smartdubai.iid.realestateapp.model.Property;
import ae.smartdubai.iid.realestateapp.model.ServiceChargeEstimate;
import ae.smartdubai.iid.realestateapp.repository.PropertyRepository;
import ae.smartdubai.iid.realestateapp.repository.ServiceChargeEstimateRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ServiceChargeEstimateServiceTest {

    @Mock
    private ServiceChargeEstimateRepository serviceChargeEstimateRepository;

    @Mock
    private PropertyRepository propertyRepository;

    @InjectMocks
    private ServiceChargeEstimateService serviceChargeEstimateService;

    private Property property1;
    private Property property2;
    private ServiceChargeEstimate estimate1;
    private ServiceChargeEstimate estimate2;
    private ServiceChargeEstimate estimate3;
    private ServiceChargeEstimate estimate4;

    @BeforeEach
    public void setup() {
        // Create test properties
        property1 = new Property();
        property1.setId(1L);
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

        property2 = new Property();
        property2.setId(2L);
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

        // Create test service charge estimates
        estimate1 = new ServiceChargeEstimate();
        estimate1.setId(1L);
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

        estimate2 = new ServiceChargeEstimate();
        estimate2.setId(2L);
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

        estimate3 = new ServiceChargeEstimate();
        estimate3.setId(3L);
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

        // Pre-filled estimate (not associated with a specific property)
        estimate4 = new ServiceChargeEstimate();
        estimate4.setId(4L);
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
    }

    @Test
    public void testGetAllServiceChargeEstimates() {
        // Arrange
        List<ServiceChargeEstimate> expectedEstimates = Arrays.asList(estimate1, estimate2, estimate3, estimate4);
        when(serviceChargeEstimateRepository.findAll()).thenReturn(expectedEstimates);

        // Act
        List<ServiceChargeEstimate> actualEstimates = serviceChargeEstimateService.getAllServiceChargeEstimates();

        // Assert
        assertEquals(expectedEstimates.size(), actualEstimates.size());
        assertEquals(expectedEstimates, actualEstimates);
        verify(serviceChargeEstimateRepository, times(1)).findAll();
    }

    @Test
    public void testGetServiceChargeEstimateById() {
        // Arrange
        when(serviceChargeEstimateRepository.findById(1L)).thenReturn(Optional.of(estimate1));
        when(serviceChargeEstimateRepository.findById(5L)).thenReturn(Optional.empty());

        // Act
        Optional<ServiceChargeEstimate> foundEstimate = serviceChargeEstimateService.getServiceChargeEstimateById(1L);
        Optional<ServiceChargeEstimate> notFoundEstimate = serviceChargeEstimateService.getServiceChargeEstimateById(5L);

        // Assert
        assertTrue(foundEstimate.isPresent());
        assertEquals(estimate1, foundEstimate.get());
        assertFalse(notFoundEstimate.isPresent());
        verify(serviceChargeEstimateRepository, times(1)).findById(1L);
        verify(serviceChargeEstimateRepository, times(1)).findById(5L);
    }

    @Test
    public void testGetServiceChargeEstimatesByProperty() {
        // Arrange
        List<ServiceChargeEstimate> property1Estimates = Arrays.asList(estimate1, estimate2);
        when(propertyRepository.findById(1L)).thenReturn(Optional.of(property1));
        when(serviceChargeEstimateRepository.findByProperty(property1)).thenReturn(property1Estimates);

        // Act
        List<ServiceChargeEstimate> actualEstimates = serviceChargeEstimateService.getServiceChargeEstimatesByProperty(1L);

        // Assert
        assertEquals(property1Estimates.size(), actualEstimates.size());
        assertEquals(property1Estimates, actualEstimates);
        verify(propertyRepository, times(1)).findById(1L);
        verify(serviceChargeEstimateRepository, times(1)).findByProperty(property1);
    }

    @Test
    public void testGetServiceChargeEstimatesByPropertyNotFound() {
        // Arrange
        when(propertyRepository.findById(5L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            serviceChargeEstimateService.getServiceChargeEstimatesByProperty(5L);
        });
        verify(propertyRepository, times(1)).findById(5L);
        verify(serviceChargeEstimateRepository, never()).findByProperty(any());
    }

    @Test
    public void testGetPreFilledEstimatesByCommunity() {
        // Arrange
        List<ServiceChargeEstimate> preFilledEstimates = Arrays.asList(estimate4);
        when(serviceChargeEstimateRepository.findByIsPreFilledTrueAndCommunityNameIgnoreCase("Dubai Marina"))
                .thenReturn(preFilledEstimates);

        // Act
        List<ServiceChargeEstimate> actualEstimates = serviceChargeEstimateService.getPreFilledEstimatesByCommunity("Dubai Marina");

        // Assert
        assertEquals(preFilledEstimates.size(), actualEstimates.size());
        assertEquals(preFilledEstimates, actualEstimates);
        verify(serviceChargeEstimateRepository, times(1)).findByIsPreFilledTrueAndCommunityNameIgnoreCase("Dubai Marina");
    }

    @Test
    public void testCalculateServiceCharges() {
        // Arrange
        when(propertyRepository.findById(1L)).thenReturn(Optional.of(property1));
        when(serviceChargeEstimateRepository.save(any(ServiceChargeEstimate.class))).thenAnswer(invocation -> {
            ServiceChargeEstimate savedEstimate = invocation.getArgument(0);
            savedEstimate.setId(5L); // Simulate auto-generated ID
            return savedEstimate;
        });

        // Act
        ServiceChargeEstimate calculatedEstimate = serviceChargeEstimateService.calculateServiceCharges(
                1L, "Dubai Marina", "Apartment", 1000.0);

        // Assert
        assertNotNull(calculatedEstimate);
        assertEquals(5L, calculatedEstimate.getId());
        assertEquals(property1, calculatedEstimate.getProperty());
        assertEquals("Dubai Marina", calculatedEstimate.getCommunityName());
        assertEquals("Apartment", calculatedEstimate.getPropertyType());
        assertEquals(1000.0, calculatedEstimate.getPropertySize());
        assertEquals(15.0, calculatedEstimate.getServiceChargeRatePerSqFt()); // From the static map in service
        assertEquals(15000.0, calculatedEstimate.getAnnualServiceCharge()); // 1000 * 15
        assertEquals(6000.0, calculatedEstimate.getCoolingCharges()); // 1000 * 6 (from static map)
        assertFalse(calculatedEstimate.getIsPreFilled());
        verify(propertyRepository, times(1)).findById(1L);
        verify(serviceChargeEstimateRepository, times(1)).save(any(ServiceChargeEstimate.class));
    }

    @Test
    public void testCalculateServiceChargesPropertyNotFound() {
        // Arrange
        when(propertyRepository.findById(5L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            serviceChargeEstimateService.calculateServiceCharges(5L, "Dubai Marina", "Apartment", 1000.0);
        });
        verify(propertyRepository, times(1)).findById(5L);
        verify(serviceChargeEstimateRepository, never()).save(any());
    }

    @Test
    public void testCalculateServiceChargesInvalidPropertySize() {
        // Arrange
        when(propertyRepository.findById(1L)).thenReturn(Optional.of(property1));

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            serviceChargeEstimateService.calculateServiceCharges(1L, "Dubai Marina", "Apartment", -100.0);
        });
        verify(propertyRepository, times(1)).findById(1L);
        verify(serviceChargeEstimateRepository, never()).save(any());
    }

    @Test
    public void testDeleteServiceChargeEstimate() {
        // Arrange
        doNothing().when(serviceChargeEstimateRepository).deleteById(1L);

        // Act
        serviceChargeEstimateService.deleteServiceChargeEstimate(1L);

        // Assert
        verify(serviceChargeEstimateRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteAllServiceChargeEstimatesForProperty() {
        // Arrange
        when(propertyRepository.findById(1L)).thenReturn(Optional.of(property1));
        doNothing().when(serviceChargeEstimateRepository).deleteByProperty(property1);

        // Act
        serviceChargeEstimateService.deleteAllServiceChargeEstimatesForProperty(1L);

        // Assert
        verify(propertyRepository, times(1)).findById(1L);
        verify(serviceChargeEstimateRepository, times(1)).deleteByProperty(property1);
    }

    @Test
    public void testDeleteAllServiceChargeEstimatesForPropertyNotFound() {
        // Arrange
        when(propertyRepository.findById(5L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            serviceChargeEstimateService.deleteAllServiceChargeEstimatesForProperty(5L);
        });
        verify(propertyRepository, times(1)).findById(5L);
        verify(serviceChargeEstimateRepository, never()).deleteByProperty(any());
    }

    @Test
    public void testCreatePreFilledEstimates() {
        // Arrange
        when(serviceChargeEstimateRepository.save(any(ServiceChargeEstimate.class))).thenAnswer(invocation -> {
            ServiceChargeEstimate savedEstimate = invocation.getArgument(0);
            savedEstimate.setId(10L); // Simulate auto-generated ID
            return savedEstimate;
        });

        // Act
        serviceChargeEstimateService.createPreFilledEstimates();

        // Assert
        // There are 15 communities, 3 property types, and 4 property sizes, so 15*3*4 = 180 estimates should be created
        verify(serviceChargeEstimateRepository, times(180)).save(any(ServiceChargeEstimate.class));
    }
}