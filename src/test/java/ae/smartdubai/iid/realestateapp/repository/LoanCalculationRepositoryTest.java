package ae.smartdubai.iid.realestateapp.repository;

import ae.smartdubai.iid.realestateapp.model.LoanCalculation;
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
public class LoanCalculationRepositoryTest {

    @Autowired
    private LoanCalculationRepository loanCalculationRepository;

    @Autowired
    private PropertyRepository propertyRepository;

    private Property property1;
    private Property property2;
    private LoanCalculation loanCalculation1;
    private LoanCalculation loanCalculation2;
    private LoanCalculation loanCalculation3;

    @BeforeEach
    public void setup() {
        // Clear any existing data
        loanCalculationRepository.deleteAll();
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

        // Create test loan calculations
        loanCalculation1 = new LoanCalculation();
        loanCalculation1.setProperty(property1);
        loanCalculation1.setLoanAmount(800000.0); // 80% of property price
        loanCalculation1.setDownPayment(200000.0); // 20% of property price
        loanCalculation1.setInterestRate(3.5);
        loanCalculation1.setTenureYears(25);
        loanCalculation1.setMonthlyEmi(4003.73); // Calculated EMI
        loanCalculation1.setTotalInterest(400119.0); // Total interest over 25 years
        loanCalculation1.setTotalPayable(1200119.0); // Loan amount + total interest
        loanCalculation1.setLoanToValueRatio(80.0); // 80% LTV
        loanCalculation1.setCalculationDate(LocalDate.of(2023, 1, 15));
        loanCalculation1.setFirstEmiPrincipal(1837.06);
        loanCalculation1.setFirstEmiInterest(2166.67);
        loanCalculation1.setLastEmiPrincipal(3992.76);
        loanCalculation1.setLastEmiInterest(10.97);
        loanCalculation1 = loanCalculationRepository.save(loanCalculation1);

        loanCalculation2 = new LoanCalculation();
        loanCalculation2.setProperty(property1);
        loanCalculation2.setLoanAmount(750000.0); // 75% of property price
        loanCalculation2.setDownPayment(250000.0); // 25% of property price
        loanCalculation2.setInterestRate(3.75);
        loanCalculation2.setTenureYears(20);
        loanCalculation2.setMonthlyEmi(4456.90); // Calculated EMI
        loanCalculation2.setTotalInterest(319656.0); // Total interest over 20 years
        loanCalculation2.setTotalPayable(1069656.0); // Loan amount + total interest
        loanCalculation2.setLoanToValueRatio(75.0); // 75% LTV
        loanCalculation2.setCalculationDate(LocalDate.of(2023, 2, 20)); // More recent date
        loanCalculation2.setFirstEmiPrincipal(2331.90);
        loanCalculation2.setFirstEmiInterest(2125.0);
        loanCalculation2.setLastEmiPrincipal(4440.44);
        loanCalculation2.setLastEmiInterest(16.46);
        loanCalculation2 = loanCalculationRepository.save(loanCalculation2);

        loanCalculation3 = new LoanCalculation();
        loanCalculation3.setProperty(property2);
        loanCalculation3.setLoanAmount(1200000.0); // 80% of property price
        loanCalculation3.setDownPayment(300000.0); // 20% of property price
        loanCalculation3.setInterestRate(4.0);
        loanCalculation3.setTenureYears(30);
        loanCalculation3.setMonthlyEmi(5728.28); // Calculated EMI
        loanCalculation3.setTotalInterest(862180.8); // Total interest over 30 years
        loanCalculation3.setTotalPayable(2062180.8); // Loan amount + total interest
        loanCalculation3.setLoanToValueRatio(80.0); // 80% LTV
        loanCalculation3.setCalculationDate(LocalDate.of(2023, 3, 10));
        loanCalculation3.setFirstEmiPrincipal(1728.28);
        loanCalculation3.setFirstEmiInterest(4000.0);
        loanCalculation3.setLastEmiPrincipal(5709.42);
        loanCalculation3.setLastEmiInterest(18.86);
        loanCalculation3 = loanCalculationRepository.save(loanCalculation3);
    }

    @Test
    public void testFindByProperty() {
        // Test finding loan calculations by property
        List<LoanCalculation> results = loanCalculationRepository.findByProperty(property1);
        
        // Verify results
        assertEquals(2, results.size());
        assertTrue(results.contains(loanCalculation1));
        assertTrue(results.contains(loanCalculation2));
        assertFalse(results.contains(loanCalculation3));
    }

    @Test
    public void testFindFirstByPropertyOrderByCalculationDateDesc() {
        // Test finding the latest loan calculation for a property
        Optional<LoanCalculation> result = loanCalculationRepository.findFirstByPropertyOrderByCalculationDateDesc(property1);
        
        // Verify result
        assertTrue(result.isPresent());
        assertEquals(loanCalculation2, result.get()); // loanCalculation2 has a more recent date
    }

    @Test
    public void testFindByCalculationDateBetween() {
        // Test finding loan calculations by date range
        LocalDate startDate = LocalDate.of(2023, 2, 1);
        LocalDate endDate = LocalDate.of(2023, 3, 15);
        List<LoanCalculation> results = loanCalculationRepository.findByCalculationDateBetween(startDate, endDate);
        
        // Verify results
        assertEquals(2, results.size());
        assertTrue(results.contains(loanCalculation2));
        assertTrue(results.contains(loanCalculation3));
        assertFalse(results.contains(loanCalculation1));
    }

    @Test
    public void testFindByLoanAmountBetween() {
        // Test finding loan calculations by loan amount range
        Double minLoanAmount = 700000.0;
        Double maxLoanAmount = 900000.0;
        List<LoanCalculation> results = loanCalculationRepository.findByLoanAmountBetween(minLoanAmount, maxLoanAmount);
        
        // Verify results
        assertEquals(2, results.size());
        assertTrue(results.contains(loanCalculation1));
        assertTrue(results.contains(loanCalculation2));
        assertFalse(results.contains(loanCalculation3));
    }

    @Test
    public void testFindByInterestRateBetween() {
        // Test finding loan calculations by interest rate range
        Double minInterestRate = 3.6;
        Double maxInterestRate = 4.5;
        List<LoanCalculation> results = loanCalculationRepository.findByInterestRateBetween(minInterestRate, maxInterestRate);
        
        // Verify results
        assertEquals(2, results.size());
        assertTrue(results.contains(loanCalculation2));
        assertTrue(results.contains(loanCalculation3));
        assertFalse(results.contains(loanCalculation1));
    }

    @Test
    public void testFindByTenureYears() {
        // Test finding loan calculations by tenure years
        List<LoanCalculation> results = loanCalculationRepository.findByTenureYears(25);
        
        // Verify results
        assertEquals(1, results.size());
        assertTrue(results.contains(loanCalculation1));
        assertFalse(results.contains(loanCalculation2));
        assertFalse(results.contains(loanCalculation3));
    }

    @Test
    public void testDeleteByProperty() {
        // Test deleting loan calculations by property
        loanCalculationRepository.deleteByProperty(property1);
        
        // Verify deletion
        List<LoanCalculation> remainingCalculations = loanCalculationRepository.findAll();
        assertEquals(1, remainingCalculations.size());
        assertTrue(remainingCalculations.contains(loanCalculation3));
        assertFalse(remainingCalculations.contains(loanCalculation1));
        assertFalse(remainingCalculations.contains(loanCalculation2));
    }
}