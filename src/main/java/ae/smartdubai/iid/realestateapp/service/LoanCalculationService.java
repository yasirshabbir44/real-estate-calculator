package ae.smartdubai.iid.realestateapp.service;

import ae.smartdubai.iid.realestateapp.model.LoanCalculation;
import ae.smartdubai.iid.realestateapp.model.Property;
import ae.smartdubai.iid.realestateapp.repository.LoanCalculationRepository;
import ae.smartdubai.iid.realestateapp.repository.PropertyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Service for managing loan calculations.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class LoanCalculationService {

    private final LoanCalculationRepository loanCalculationRepository;
    private final PropertyRepository propertyRepository;

    /**
     * Get all loan calculations.
     *
     * @return list of all loan calculations
     */
    @Transactional(readOnly = true)
    public List<LoanCalculation> getAllLoanCalculations() {
        return loanCalculationRepository.findAll();
    }

    /**
     * Get loan calculation by ID.
     *
     * @param id the loan calculation ID
     * @return the loan calculation if found
     */
    @Transactional(readOnly = true)
    public Optional<LoanCalculation> getLoanCalculationById(Long id) {
        return loanCalculationRepository.findById(id);
    }

    /**
     * Get loan calculations by property.
     *
     * @param propertyId the property ID
     * @return list of loan calculations for the specified property
     */
    @Transactional(readOnly = true)
    public List<LoanCalculation> getLoanCalculationsByProperty(Long propertyId) {
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new RuntimeException("Property not found with id " + propertyId));
        return loanCalculationRepository.findByProperty(property);
    }

    /**
     * Calculate and save a loan calculation for a property.
     *
     * @param propertyId the property ID
     * @param downPayment the down payment amount
     * @param interestRate the annual interest rate (in percentage)
     * @param tenureYears the loan tenure in years
     * @return the calculated loan calculation
     */
    public LoanCalculation calculateLoan(Long propertyId, Double downPayment, Double interestRate, Integer tenureYears) {
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new RuntimeException("Property not found with id " + propertyId));
        
        if (property.getPrice() == null || property.getPrice() <= 0) {
            throw new IllegalArgumentException("Property price must be positive");
        }
        
        if (downPayment == null || downPayment < 0) {
            throw new IllegalArgumentException("Down payment cannot be negative");
        }
        
        if (downPayment >= property.getPrice()) {
            throw new IllegalArgumentException("Down payment cannot be greater than or equal to property price");
        }
        
        if (interestRate == null || interestRate <= 0 || interestRate > 100) {
            throw new IllegalArgumentException("Interest rate must be between 0 and 100");
        }
        
        if (tenureYears == null || tenureYears <= 0 || tenureYears > 35) {
            throw new IllegalArgumentException("Tenure must be between 1 and 35 years");
        }
        
        LoanCalculation loanCalculation = new LoanCalculation();
        loanCalculation.setProperty(property);
        loanCalculation.setDownPayment(downPayment);
        loanCalculation.setInterestRate(interestRate);
        loanCalculation.setTenureYears(tenureYears);
        
        // Calculate loan amount
        double loanAmount = property.getPrice() - downPayment;
        loanCalculation.setLoanAmount(loanAmount);
        
        // Calculate loan to value ratio (LTV)
        double ltv = (loanAmount / property.getPrice()) * 100;
        loanCalculation.setLoanToValueRatio(ltv);
        
        // Calculate monthly EMI
        // Formula: EMI = P * r * (1 + r)^n / ((1 + r)^n - 1)
        // where P = principal (loan amount), r = monthly interest rate, n = number of monthly installments
        double monthlyInterestRate = (interestRate / 100) / 12;
        int numberOfInstallments = tenureYears * 12;
        
        double emi = loanAmount * monthlyInterestRate * Math.pow(1 + monthlyInterestRate, numberOfInstallments) 
                / (Math.pow(1 + monthlyInterestRate, numberOfInstallments) - 1);
        loanCalculation.setMonthlyEmi(emi);
        
        // Calculate total amount payable
        double totalPayable = emi * numberOfInstallments;
        loanCalculation.setTotalPayable(totalPayable);
        
        // Calculate total interest payable
        double totalInterest = totalPayable - loanAmount;
        loanCalculation.setTotalInterest(totalInterest);
        
        // Calculate first EMI split (principal and interest)
        double firstEmiInterest = loanAmount * monthlyInterestRate;
        double firstEmiPrincipal = emi - firstEmiInterest;
        loanCalculation.setFirstEmiInterest(firstEmiInterest);
        loanCalculation.setFirstEmiPrincipal(firstEmiPrincipal);
        
        // Calculate last EMI split (principal and interest)
        // For this, we need to simulate the entire amortization schedule
        double remainingLoan = loanAmount;
        double lastEmiPrincipal = 0;
        double lastEmiInterest = 0;
        
        for (int i = 0; i < numberOfInstallments; i++) {
            double monthlyInterest = remainingLoan * monthlyInterestRate;
            double monthlyPrincipal = emi - monthlyInterest;
            
            if (i == numberOfInstallments - 1) {
                lastEmiPrincipal = monthlyPrincipal;
                lastEmiInterest = monthlyInterest;
            }
            
            remainingLoan -= monthlyPrincipal;
        }
        
        loanCalculation.setLastEmiPrincipal(lastEmiPrincipal);
        loanCalculation.setLastEmiInterest(lastEmiInterest);
        
        loanCalculation.setCalculationDate(LocalDate.now());
        
        return loanCalculationRepository.save(loanCalculation);
    }

    /**
     * Delete a loan calculation.
     *
     * @param id the loan calculation ID
     */
    public void deleteLoanCalculation(Long id) {
        loanCalculationRepository.deleteById(id);
    }

    /**
     * Delete all loan calculations for a property.
     *
     * @param propertyId the property ID
     */
    public void deleteAllLoanCalculationsForProperty(Long propertyId) {
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new RuntimeException("Property not found with id " + propertyId));
        loanCalculationRepository.deleteByProperty(property);
    }
}