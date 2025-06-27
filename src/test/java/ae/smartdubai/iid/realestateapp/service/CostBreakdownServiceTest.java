package ae.smartdubai.iid.realestateapp.service;

import ae.smartdubai.iid.realestateapp.model.CostBreakdown;
import ae.smartdubai.iid.realestateapp.model.Property;
import ae.smartdubai.iid.realestateapp.repository.CostBreakdownRepository;
import ae.smartdubai.iid.realestateapp.repository.PropertyRepository;
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
public class CostBreakdownServiceTest {

    @Mock
    private CostBreakdownRepository costBreakdownRepository;

    @Mock
    private PropertyRepository propertyRepository;

    @InjectMocks
    private CostBreakdownService costBreakdownService;

    private Property property1;
    private Property property2;
    private CostBreakdown costBreakdown1;
    private CostBreakdown costBreakdown2;

    @BeforeEach
    public void setup() {
        // Create test properties
        property1 = new Property();
        property1.setId(1L);
        property1.setName("Test Property 1");
        property1.setLocation("Dubai Marina");
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
        property2.setLocation("Arabian Ranches");
        property2.setPrice(1500000.0);
        property2.setSize(300.0);
        property2.setBedrooms(4);
        property2.setBathrooms(3);
        property2.setPropertyType("Villa");
        property2.setCommunityName("Arabian Ranches");
        property2.setIsFurnished(true);
        property2.setYearBuilt(2019);

        // Create test cost breakdowns
        costBreakdown1 = new CostBreakdown();
        costBreakdown1.setId(1L);
        costBreakdown1.setProperty(property1);
        costBreakdown1.setDldFee(40000.0); // 4% of 1,000,000
        costBreakdown1.setAgencyFee(20000.0); // 2% of 1,000,000
        costBreakdown1.setRegistrationFee(4000.0);
        costBreakdown1.setMortgageRegistrationFee(1875.0); // 0.25% of 750,000
        costBreakdown1.setValuationFee(3000.0);
        costBreakdown1.setMortgageProcessingFee(7500.0); // 1% of 750,000
        costBreakdown1.setLifeInsuranceCost(5000.0);
        costBreakdown1.setPropertyInsuranceCost(2500.0); // 0.25% of 1,000,000
        costBreakdown1.setMaintenanceDeposit(2000.0);
        costBreakdown1.setUtilityConnectionFees(1500.0);
        costBreakdown1.setMovingCosts(3000.0);
        costBreakdown1.setTotalCost(1090375.0); // Sum of property price and all fees
        costBreakdown1.setCalculationDate(LocalDate.of(2023, 1, 15));

        costBreakdown2 = new CostBreakdown();
        costBreakdown2.setId(2L);
        costBreakdown2.setProperty(property2);
        costBreakdown2.setDldFee(60000.0); // 4% of 1,500,000
        costBreakdown2.setAgencyFee(30000.0); // 2% of 1,500,000
        costBreakdown2.setRegistrationFee(4000.0);
        costBreakdown2.setMortgageRegistrationFee(0.0); // No mortgage
        costBreakdown2.setValuationFee(0.0); // No mortgage
        costBreakdown2.setMortgageProcessingFee(0.0); // No mortgage
        costBreakdown2.setLifeInsuranceCost(0.0); // No life insurance
        costBreakdown2.setPropertyInsuranceCost(3750.0); // 0.25% of 1,500,000
        costBreakdown2.setMaintenanceDeposit(3000.0);
        costBreakdown2.setUtilityConnectionFees(2000.0);
        costBreakdown2.setMovingCosts(5000.0);
        costBreakdown2.setTotalCost(1607750.0); // Sum of property price and all fees
        costBreakdown2.setCalculationDate(LocalDate.of(2023, 2, 20));
    }

    @Test
    public void testGetAllCostBreakdowns() {
        // Arrange
        List<CostBreakdown> expectedBreakdowns = Arrays.asList(costBreakdown1, costBreakdown2);
        when(costBreakdownRepository.findAll()).thenReturn(expectedBreakdowns);

        // Act
        List<CostBreakdown> actualBreakdowns = costBreakdownService.getAllCostBreakdowns();

        // Assert
        assertEquals(expectedBreakdowns.size(), actualBreakdowns.size());
        assertEquals(expectedBreakdowns, actualBreakdowns);
        verify(costBreakdownRepository, times(1)).findAll();
    }

    @Test
    public void testGetCostBreakdownById() {
        // Arrange
        when(costBreakdownRepository.findById(1L)).thenReturn(Optional.of(costBreakdown1));
        when(costBreakdownRepository.findById(3L)).thenReturn(Optional.empty());

        // Act
        Optional<CostBreakdown> foundBreakdown = costBreakdownService.getCostBreakdownById(1L);
        Optional<CostBreakdown> notFoundBreakdown = costBreakdownService.getCostBreakdownById(3L);

        // Assert
        assertTrue(foundBreakdown.isPresent());
        assertEquals(costBreakdown1, foundBreakdown.get());
        assertFalse(notFoundBreakdown.isPresent());
        verify(costBreakdownRepository, times(1)).findById(1L);
        verify(costBreakdownRepository, times(1)).findById(3L);
    }

    @Test
    public void testGetCostBreakdownsByProperty() {
        // Arrange
        List<CostBreakdown> property1Breakdowns = Arrays.asList(costBreakdown1);
        when(propertyRepository.findById(1L)).thenReturn(Optional.of(property1));
        when(costBreakdownRepository.findByProperty(property1)).thenReturn(property1Breakdowns);

        // Act
        List<CostBreakdown> actualBreakdowns = costBreakdownService.getCostBreakdownsByProperty(1L);

        // Assert
        assertEquals(property1Breakdowns.size(), actualBreakdowns.size());
        assertEquals(property1Breakdowns, actualBreakdowns);
        verify(propertyRepository, times(1)).findById(1L);
        verify(costBreakdownRepository, times(1)).findByProperty(property1);
    }

    @Test
    public void testGetCostBreakdownsByPropertyNotFound() {
        // Arrange
        when(propertyRepository.findById(3L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            costBreakdownService.getCostBreakdownsByProperty(3L);
        });
        verify(propertyRepository, times(1)).findById(3L);
        verify(costBreakdownRepository, never()).findByProperty(any());
    }

    @Test
    public void testCalculateCostBreakdownWithMortgage() {
        // Arrange
        when(propertyRepository.findById(1L)).thenReturn(Optional.of(property1));
        when(costBreakdownRepository.save(any(CostBreakdown.class))).thenAnswer(invocation -> {
            CostBreakdown savedBreakdown = invocation.getArgument(0);
            savedBreakdown.setId(3L); // Simulate auto-generated ID
            return savedBreakdown;
        });

        Double mortgageAmount = 750000.0;
        Double lifeInsuranceCost = 5000.0;
        Double maintenanceDeposit = 2000.0;
        Double utilityConnectionFees = 1500.0;
        Double movingCosts = 3000.0;

        // Act
        CostBreakdown calculatedBreakdown = costBreakdownService.calculateCostBreakdown(
                1L, mortgageAmount, lifeInsuranceCost, maintenanceDeposit, utilityConnectionFees, movingCosts);

        // Assert
        assertNotNull(calculatedBreakdown);
        assertEquals(3L, calculatedBreakdown.getId());
        assertEquals(property1, calculatedBreakdown.getProperty());
        assertEquals(40000.0, calculatedBreakdown.getDldFee()); // 4% of 1,000,000
        assertEquals(20000.0, calculatedBreakdown.getAgencyFee()); // 2% of 1,000,000
        assertEquals(4000.0, calculatedBreakdown.getRegistrationFee());
        assertEquals(1875.0, calculatedBreakdown.getMortgageRegistrationFee()); // 0.25% of 750,000
        assertEquals(3000.0, calculatedBreakdown.getValuationFee());
        assertEquals(7500.0, calculatedBreakdown.getMortgageProcessingFee()); // 1% of 750,000
        assertEquals(5000.0, calculatedBreakdown.getLifeInsuranceCost());
        assertEquals(2500.0, calculatedBreakdown.getPropertyInsuranceCost()); // 0.25% of 1,000,000
        assertEquals(2000.0, calculatedBreakdown.getMaintenanceDeposit());
        assertEquals(1500.0, calculatedBreakdown.getUtilityConnectionFees());
        assertEquals(3000.0, calculatedBreakdown.getMovingCosts());
        
        // Total cost should be property price + all fees
        double expectedTotalCost = 1000000.0 + 40000.0 + 20000.0 + 4000.0 + 1875.0 + 3000.0 + 
                                  7500.0 + 5000.0 + 2500.0 + 2000.0 + 1500.0 + 3000.0;
        assertEquals(expectedTotalCost, calculatedBreakdown.getTotalCost());
        
        assertNotNull(calculatedBreakdown.getCalculationDate());
        verify(propertyRepository, times(1)).findById(1L);
        verify(costBreakdownRepository, times(1)).save(any(CostBreakdown.class));
    }

    @Test
    public void testCalculateCostBreakdownWithoutMortgage() {
        // Arrange
        when(propertyRepository.findById(2L)).thenReturn(Optional.of(property2));
        when(costBreakdownRepository.save(any(CostBreakdown.class))).thenAnswer(invocation -> {
            CostBreakdown savedBreakdown = invocation.getArgument(0);
            savedBreakdown.setId(4L); // Simulate auto-generated ID
            return savedBreakdown;
        });

        Double mortgageAmount = 0.0;
        Double lifeInsuranceCost = 0.0;
        Double maintenanceDeposit = 3000.0;
        Double utilityConnectionFees = 2000.0;
        Double movingCosts = 5000.0;

        // Act
        CostBreakdown calculatedBreakdown = costBreakdownService.calculateCostBreakdown(
                2L, mortgageAmount, lifeInsuranceCost, maintenanceDeposit, utilityConnectionFees, movingCosts);

        // Assert
        assertNotNull(calculatedBreakdown);
        assertEquals(4L, calculatedBreakdown.getId());
        assertEquals(property2, calculatedBreakdown.getProperty());
        assertEquals(60000.0, calculatedBreakdown.getDldFee()); // 4% of 1,500,000
        assertEquals(30000.0, calculatedBreakdown.getAgencyFee()); // 2% of 1,500,000
        assertEquals(4000.0, calculatedBreakdown.getRegistrationFee());
        assertEquals(0.0, calculatedBreakdown.getMortgageRegistrationFee()); // No mortgage
        assertEquals(0.0, calculatedBreakdown.getValuationFee()); // No mortgage
        assertEquals(0.0, calculatedBreakdown.getMortgageProcessingFee()); // No mortgage
        assertEquals(0.0, calculatedBreakdown.getLifeInsuranceCost()); // No life insurance
        assertEquals(3750.0, calculatedBreakdown.getPropertyInsuranceCost()); // 0.25% of 1,500,000
        assertEquals(3000.0, calculatedBreakdown.getMaintenanceDeposit());
        assertEquals(2000.0, calculatedBreakdown.getUtilityConnectionFees());
        assertEquals(5000.0, calculatedBreakdown.getMovingCosts());
        
        // Total cost should be property price + all fees
        double expectedTotalCost = 1500000.0 + 60000.0 + 30000.0 + 4000.0 + 0.0 + 0.0 + 
                                  0.0 + 0.0 + 3750.0 + 3000.0 + 2000.0 + 5000.0;
        assertEquals(expectedTotalCost, calculatedBreakdown.getTotalCost());
        
        assertNotNull(calculatedBreakdown.getCalculationDate());
        verify(propertyRepository, times(1)).findById(2L);
        verify(costBreakdownRepository, times(1)).save(any(CostBreakdown.class));
    }

    @Test
    public void testCalculateCostBreakdownPropertyNotFound() {
        // Arrange
        when(propertyRepository.findById(3L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            costBreakdownService.calculateCostBreakdown(3L, 0.0, 0.0, 0.0, 0.0, 0.0);
        });
        verify(propertyRepository, times(1)).findById(3L);
        verify(costBreakdownRepository, never()).save(any());
    }

    @Test
    public void testDeleteCostBreakdown() {
        // Arrange
        doNothing().when(costBreakdownRepository).deleteById(1L);

        // Act
        costBreakdownService.deleteCostBreakdown(1L);

        // Assert
        verify(costBreakdownRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteAllCostBreakdownsForProperty() {
        // Arrange
        when(propertyRepository.findById(1L)).thenReturn(Optional.of(property1));
        doNothing().when(costBreakdownRepository).deleteByProperty(property1);

        // Act
        costBreakdownService.deleteAllCostBreakdownsForProperty(1L);

        // Assert
        verify(propertyRepository, times(1)).findById(1L);
        verify(costBreakdownRepository, times(1)).deleteByProperty(property1);
    }

    @Test
    public void testDeleteAllCostBreakdownsForPropertyNotFound() {
        // Arrange
        when(propertyRepository.findById(3L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            costBreakdownService.deleteAllCostBreakdownsForProperty(3L);
        });
        verify(propertyRepository, times(1)).findById(3L);
        verify(costBreakdownRepository, never()).deleteByProperty(any());
    }
}