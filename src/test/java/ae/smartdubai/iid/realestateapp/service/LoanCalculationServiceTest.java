package ae.smartdubai.iid.realestateapp.service;

import ae.smartdubai.iid.realestateapp.model.LoanCalculation;
import ae.smartdubai.iid.realestateapp.model.Property;
import ae.smartdubai.iid.realestateapp.repository.LoanCalculationRepository;
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
public class LoanCalculationServiceTest {

    @Mock
    private LoanCalculationRepository loanCalculationRepository;

    @Mock
    private PropertyRepository propertyRepository;

    @InjectMocks
    private LoanCalculationService loanCalculationService;

    private Property property1;
    private Property property2;
    private LoanCalculation loanCalculation1;
    private LoanCalculation loanCalculation2;

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

        // Create test loan calculations
        loanCalculation1 = new LoanCalculation();
        loanCalculation1.setId(1L);
        loanCalculation1.setProperty(property1);
        loanCalculation1.setDownPayment(200000.0); // 20% down payment
        loanCalculation1.setLoanAmount(800000.0); // 80% loan
        loanCalculation1.setInterestRate(4.5); // 4.5% interest rate
        loanCalculation1.setTenureYears(25); // 25 years
        loanCalculation1.setLoanToValueRatio(80.0); // 80% LTV
        loanCalculation1.setMonthlyEmi(4466.61); // Calculated EMI
        loanCalculation1.setTotalPayable(1339983.0); // Total amount payable
        loanCalculation1.setTotalInterest(539983.0); // Total interest
        loanCalculation1.setFirstEmiPrincipal(1966.61); // First EMI principal
        loanCalculation1.setFirstEmiInterest(2500.0); // First EMI interest
        loanCalculation1.setLastEmiPrincipal(4452.53); // Last EMI principal
        loanCalculation1.setLastEmiInterest(14.08); // Last EMI interest
        loanCalculation1.setCalculationDate(LocalDate.of(2023, 1, 15));

        loanCalculation2 = new LoanCalculation();
        loanCalculation2.setId(2L);
        loanCalculation2.setProperty(property2);
        loanCalculation2.setDownPayment(450000.0); // 30% down payment
        loanCalculation2.setLoanAmount(1050000.0); // 70% loan
        loanCalculation2.setInterestRate(5.0); // 5.0% interest rate
        loanCalculation2.setTenureYears(20); // 20 years
        loanCalculation2.setLoanToValueRatio(70.0); // 70% LTV
        loanCalculation2.setMonthlyEmi(6930.71); // Calculated EMI
        loanCalculation2.setTotalPayable(1663370.4); // Total amount payable
        loanCalculation2.setTotalInterest(613370.4); // Total interest
        loanCalculation2.setFirstEmiPrincipal(2680.71); // First EMI principal
        loanCalculation2.setFirstEmiInterest(4250.0); // First EMI interest
        loanCalculation2.setLastEmiPrincipal(6902.0); // Last EMI principal
        loanCalculation2.setLastEmiInterest(28.71); // Last EMI interest
        loanCalculation2.setCalculationDate(LocalDate.of(2023, 2, 20));
    }

    @Test
    public void testGetAllLoanCalculations() {
        // Arrange
        List<LoanCalculation> expectedCalculations = Arrays.asList(loanCalculation1, loanCalculation2);
        when(loanCalculationRepository.findAll()).thenReturn(expectedCalculations);

        // Act
        List<LoanCalculation> actualCalculations = loanCalculationService.getAllLoanCalculations();

        // Assert
        assertEquals(expectedCalculations.size(), actualCalculations.size());
        assertEquals(expectedCalculations, actualCalculations);
        verify(loanCalculationRepository, times(1)).findAll();
    }

    @Test
    public void testGetLoanCalculationById() {
        // Arrange
        when(loanCalculationRepository.findById(1L)).thenReturn(Optional.of(loanCalculation1));
        when(loanCalculationRepository.findById(3L)).thenReturn(Optional.empty());

        // Act
        Optional<LoanCalculation> foundCalculation = loanCalculationService.getLoanCalculationById(1L);
        Optional<LoanCalculation> notFoundCalculation = loanCalculationService.getLoanCalculationById(3L);

        // Assert
        assertTrue(foundCalculation.isPresent());
        assertEquals(loanCalculation1, foundCalculation.get());
        assertFalse(notFoundCalculation.isPresent());
        verify(loanCalculationRepository, times(1)).findById(1L);
        verify(loanCalculationRepository, times(1)).findById(3L);
    }

    @Test
    public void testGetLoanCalculationsByProperty() {
        // Arrange
        List<LoanCalculation> property1Calculations = Arrays.asList(loanCalculation1);
        when(propertyRepository.findById(1L)).thenReturn(Optional.of(property1));
        when(loanCalculationRepository.findByProperty(property1)).thenReturn(property1Calculations);

        // Act
        List<LoanCalculation> actualCalculations = loanCalculationService.getLoanCalculationsByProperty(1L);

        // Assert
        assertEquals(property1Calculations.size(), actualCalculations.size());
        assertEquals(property1Calculations, actualCalculations);
        verify(propertyRepository, times(1)).findById(1L);
        verify(loanCalculationRepository, times(1)).findByProperty(property1);
    }

    @Test
    public void testGetLoanCalculationsByPropertyNotFound() {
        // Arrange
        when(propertyRepository.findById(3L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            loanCalculationService.getLoanCalculationsByProperty(3L);
        });
        verify(propertyRepository, times(1)).findById(3L);
        verify(loanCalculationRepository, never()).findByProperty(any());
    }

    @Test
    public void testCalculateLoan() {
        // Arrange
        when(propertyRepository.findById(1L)).thenReturn(Optional.of(property1));
        when(loanCalculationRepository.save(any(LoanCalculation.class))).thenAnswer(invocation -> {
            LoanCalculation savedCalculation = invocation.getArgument(0);
            savedCalculation.setId(3L); // Simulate auto-generated ID
            return savedCalculation;
        });

        Double downPayment = 200000.0; // 20% down payment
        Double interestRate = 4.5; // 4.5% interest rate
        Integer tenureYears = 25; // 25 years

        // Act
        LoanCalculation calculatedLoan = loanCalculationService.calculateLoan(
                1L, downPayment, interestRate, tenureYears);

        // Assert
        assertNotNull(calculatedLoan);
        assertEquals(3L, calculatedLoan.getId());
        assertEquals(property1, calculatedLoan.getProperty());
        assertEquals(downPayment, calculatedLoan.getDownPayment());
        assertEquals(interestRate, calculatedLoan.getInterestRate());
        assertEquals(tenureYears, calculatedLoan.getTenureYears());

        // Loan amount should be property price - down payment
        assertEquals(800000.0, calculatedLoan.getLoanAmount());

        // LTV should be (loan amount / property price) * 100
        assertEquals(80.0, calculatedLoan.getLoanToValueRatio());

        // Check that monthly EMI is calculated correctly
        // For a loan of 800,000 at 4.5% for 25 years, EMI should be around 4,446.66
        assertEquals(4446.66, calculatedLoan.getMonthlyEmi(), 1.0);

        // Total payable should be EMI * number of months
        assertEquals(calculatedLoan.getMonthlyEmi() * tenureYears * 12, calculatedLoan.getTotalPayable(), 1.0);

        // Total interest should be total payable - loan amount
        assertEquals(calculatedLoan.getTotalPayable() - calculatedLoan.getLoanAmount(), calculatedLoan.getTotalInterest(), 1.0);

        // First EMI interest should be loan amount * monthly interest rate
        double monthlyInterestRate = (interestRate / 100) / 12;
        assertEquals(calculatedLoan.getLoanAmount() * monthlyInterestRate, calculatedLoan.getFirstEmiInterest(), 0.5);

        // First EMI principal should be EMI - first EMI interest
        assertEquals(calculatedLoan.getMonthlyEmi() - calculatedLoan.getFirstEmiInterest(), calculatedLoan.getFirstEmiPrincipal(), 0.5);

        assertNotNull(calculatedLoan.getCalculationDate());
        verify(propertyRepository, times(1)).findById(1L);
        verify(loanCalculationRepository, times(1)).save(any(LoanCalculation.class));
    }

    @Test
    public void testCalculateLoanPropertyNotFound() {
        // Arrange
        when(propertyRepository.findById(3L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            loanCalculationService.calculateLoan(3L, 200000.0, 4.5, 25);
        });
        verify(propertyRepository, times(1)).findById(3L);
        verify(loanCalculationRepository, never()).save(any());
    }

    @Test
    public void testCalculateLoanInvalidDownPayment() {
        // Arrange
        when(propertyRepository.findById(1L)).thenReturn(Optional.of(property1));

        // Act & Assert - Negative down payment
        assertThrows(IllegalArgumentException.class, () -> {
            loanCalculationService.calculateLoan(1L, -100000.0, 4.5, 25);
        });

        // Act & Assert - Down payment greater than property price
        assertThrows(IllegalArgumentException.class, () -> {
            loanCalculationService.calculateLoan(1L, 1100000.0, 4.5, 25);
        });

        verify(propertyRepository, times(2)).findById(1L);
        verify(loanCalculationRepository, never()).save(any());
    }

    @Test
    public void testCalculateLoanInvalidInterestRate() {
        // Arrange
        when(propertyRepository.findById(1L)).thenReturn(Optional.of(property1));

        // Act & Assert - Negative interest rate
        assertThrows(IllegalArgumentException.class, () -> {
            loanCalculationService.calculateLoan(1L, 200000.0, -1.0, 25);
        });

        // Act & Assert - Zero interest rate
        assertThrows(IllegalArgumentException.class, () -> {
            loanCalculationService.calculateLoan(1L, 200000.0, 0.0, 25);
        });

        // Act & Assert - Interest rate > 100
        assertThrows(IllegalArgumentException.class, () -> {
            loanCalculationService.calculateLoan(1L, 200000.0, 101.0, 25);
        });

        verify(propertyRepository, times(3)).findById(1L);
        verify(loanCalculationRepository, never()).save(any());
    }

    @Test
    public void testCalculateLoanInvalidTenure() {
        // Arrange
        when(propertyRepository.findById(1L)).thenReturn(Optional.of(property1));

        // Act & Assert - Negative tenure
        assertThrows(IllegalArgumentException.class, () -> {
            loanCalculationService.calculateLoan(1L, 200000.0, 4.5, -5);
        });

        // Act & Assert - Zero tenure
        assertThrows(IllegalArgumentException.class, () -> {
            loanCalculationService.calculateLoan(1L, 200000.0, 4.5, 0);
        });

        // Act & Assert - Tenure > 35 years
        assertThrows(IllegalArgumentException.class, () -> {
            loanCalculationService.calculateLoan(1L, 200000.0, 4.5, 36);
        });

        verify(propertyRepository, times(3)).findById(1L);
        verify(loanCalculationRepository, never()).save(any());
    }

    @Test
    public void testDeleteLoanCalculation() {
        // Arrange
        doNothing().when(loanCalculationRepository).deleteById(1L);

        // Act
        loanCalculationService.deleteLoanCalculation(1L);

        // Assert
        verify(loanCalculationRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteAllLoanCalculationsForProperty() {
        // Arrange
        when(propertyRepository.findById(1L)).thenReturn(Optional.of(property1));
        doNothing().when(loanCalculationRepository).deleteByProperty(property1);

        // Act
        loanCalculationService.deleteAllLoanCalculationsForProperty(1L);

        // Assert
        verify(propertyRepository, times(1)).findById(1L);
        verify(loanCalculationRepository, times(1)).deleteByProperty(property1);
    }

    @Test
    public void testDeleteAllLoanCalculationsForPropertyNotFound() {
        // Arrange
        when(propertyRepository.findById(3L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            loanCalculationService.deleteAllLoanCalculationsForProperty(3L);
        });
        verify(propertyRepository, times(1)).findById(3L);
        verify(loanCalculationRepository, never()).deleteByProperty(any());
    }
}
