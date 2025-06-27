package ae.smartdubai.iid.realestateapp.service;

import ae.smartdubai.iid.realestateapp.model.Property;
import ae.smartdubai.iid.realestateapp.model.RentVsBuyAnalysis;
import ae.smartdubai.iid.realestateapp.repository.PropertyRepository;
import ae.smartdubai.iid.realestateapp.repository.RentVsBuyAnalysisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Service for managing rent vs buy analyses.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class RentVsBuyAnalysisService {

    private final RentVsBuyAnalysisRepository rentVsBuyAnalysisRepository;
    private final PropertyRepository propertyRepository;

    /**
     * Get all rent vs buy analyses.
     *
     * @return list of all rent vs buy analyses
     */
    @Transactional(readOnly = true)
    public List<RentVsBuyAnalysis> getAllRentVsBuyAnalyses() {
        return rentVsBuyAnalysisRepository.findAll();
    }

    /**
     * Get rent vs buy analysis by ID.
     *
     * @param id the rent vs buy analysis ID
     * @return the rent vs buy analysis if found
     */
    @Transactional(readOnly = true)
    public Optional<RentVsBuyAnalysis> getRentVsBuyAnalysisById(Long id) {
        return rentVsBuyAnalysisRepository.findById(id);
    }

    /**
     * Get rent vs buy analyses by property.
     *
     * @param propertyId the property ID
     * @return list of rent vs buy analyses for the specified property
     */
    @Transactional(readOnly = true)
    public List<RentVsBuyAnalysis> getRentVsBuyAnalysesByProperty(Long propertyId) {
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new RuntimeException("Property not found with id " + propertyId));
        return rentVsBuyAnalysisRepository.findByProperty(property);
    }

    /**
     * Calculate and save a rent vs buy analysis for a property.
     *
     * @param propertyId the property ID
     * @param downPayment the down payment amount
     * @param interestRate the annual interest rate (in percentage)
     * @param loanTenureYears the loan tenure in years
     * @param propertyAppreciationRate the annual property appreciation rate (in percentage)
     * @param annualMaintenanceCost the annual maintenance cost
     * @param annualPropertyTax the annual property tax
     * @param monthlyRent the monthly rent amount
     * @param annualRentIncreaseRate the annual rent increase rate (in percentage)
     * @param securityDeposit the security deposit amount
     * @param investmentReturnRate the annual investment return rate (in percentage)
     * @param analysisPeriodYears the analysis period in years
     * @return the calculated rent vs buy analysis
     */
    public RentVsBuyAnalysis calculateRentVsBuy(
            Long propertyId,
            Double downPayment,
            Double interestRate,
            Integer loanTenureYears,
            Double propertyAppreciationRate,
            Double annualMaintenanceCost,
            Double annualPropertyTax,
            Double monthlyRent,
            Double annualRentIncreaseRate,
            Double securityDeposit,
            Double investmentReturnRate,
            Integer analysisPeriodYears) {
        
        // Validate property
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new RuntimeException("Property not found with id " + propertyId));
        
        if (property.getPrice() == null || property.getPrice() <= 0) {
            throw new IllegalArgumentException("Property price must be positive");
        }
        
        // Validate input parameters
        validateInputParameters(
                property.getPrice(),
                downPayment,
                interestRate,
                loanTenureYears,
                propertyAppreciationRate,
                annualMaintenanceCost,
                annualPropertyTax,
                monthlyRent,
                annualRentIncreaseRate,
                securityDeposit,
                investmentReturnRate,
                analysisPeriodYears
        );
        
        // Create and populate the analysis object
        RentVsBuyAnalysis analysis = new RentVsBuyAnalysis();
        analysis.setProperty(property);
        analysis.setDownPayment(downPayment);
        analysis.setInterestRate(interestRate);
        analysis.setLoanTenureYears(loanTenureYears);
        analysis.setPropertyAppreciationRate(propertyAppreciationRate);
        analysis.setAnnualMaintenanceCost(annualMaintenanceCost);
        analysis.setAnnualPropertyTax(annualPropertyTax);
        analysis.setMonthlyRent(monthlyRent);
        analysis.setAnnualRentIncreaseRate(annualRentIncreaseRate);
        analysis.setSecurityDeposit(securityDeposit);
        analysis.setInvestmentReturnRate(investmentReturnRate);
        analysis.setAnalysisPeriodYears(analysisPeriodYears);
        analysis.setAnalysisDate(LocalDate.now());
        
        // Calculate buying scenario
        double loanAmount = property.getPrice() - downPayment;
        double monthlyInterestRate = (interestRate / 100) / 12;
        int totalPayments = loanTenureYears * 12;
        
        // Calculate monthly mortgage payment
        double monthlyMortgagePayment = calculateMonthlyMortgagePayment(loanAmount, monthlyInterestRate, totalPayments);
        
        // Calculate total cost of buying and net worth after buying
        double totalCostOfBuying = calculateTotalCostOfBuying(
                property.getPrice(),
                downPayment,
                monthlyMortgagePayment,
                loanTenureYears,
                annualMaintenanceCost,
                annualPropertyTax,
                analysisPeriodYears
        );
        
        double propertyValueAtEnd = calculatePropertyValueAtEnd(
                property.getPrice(),
                propertyAppreciationRate,
                analysisPeriodYears
        );
        
        double remainingMortgageBalance = calculateRemainingMortgageBalance(
                loanAmount,
                monthlyInterestRate,
                totalPayments,
                Math.min(analysisPeriodYears * 12, totalPayments)
        );
        
        double netWorthAfterBuying = propertyValueAtEnd - remainingMortgageBalance;
        
        // Calculate renting scenario
        double totalCostOfRenting = calculateTotalCostOfRenting(
                monthlyRent,
                annualRentIncreaseRate,
                securityDeposit,
                analysisPeriodYears
        );
        
        double investmentValue = calculateInvestmentValue(
                downPayment,
                monthlyRent,
                monthlyMortgagePayment,
                annualMaintenanceCost / 12,
                annualPropertyTax / 12,
                investmentReturnRate,
                annualRentIncreaseRate,
                analysisPeriodYears
        );
        
        double netWorthAfterRenting = investmentValue;
        
        // Determine if buying is better
        boolean isBuyingBetter = netWorthAfterBuying > netWorthAfterRenting;
        
        // Calculate break-even point
        double breakEvenYears = calculateBreakEvenYears(
                property.getPrice(),
                downPayment,
                monthlyMortgagePayment,
                annualMaintenanceCost,
                annualPropertyTax,
                monthlyRent,
                annualRentIncreaseRate,
                investmentReturnRate,
                propertyAppreciationRate
        );
        
        // Set the results
        analysis.setTotalCostOfBuying(totalCostOfBuying);
        analysis.setTotalCostOfRenting(totalCostOfRenting);
        analysis.setNetWorthAfterBuying(netWorthAfterBuying);
        analysis.setNetWorthAfterRenting(netWorthAfterRenting);
        analysis.setIsBuyingBetter(isBuyingBetter);
        analysis.setBreakEvenYears(breakEvenYears);
        
        // Save and return the analysis
        return rentVsBuyAnalysisRepository.save(analysis);
    }

    /**
     * Delete a rent vs buy analysis.
     *
     * @param id the rent vs buy analysis ID
     */
    public void deleteRentVsBuyAnalysis(Long id) {
        rentVsBuyAnalysisRepository.deleteById(id);
    }

    /**
     * Delete all rent vs buy analyses for a property.
     *
     * @param propertyId the property ID
     */
    public void deleteAllRentVsBuyAnalysesForProperty(Long propertyId) {
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new RuntimeException("Property not found with id " + propertyId));
        rentVsBuyAnalysisRepository.deleteByProperty(property);
    }

    /**
     * Validate input parameters for the rent vs buy analysis.
     */
    private void validateInputParameters(
            Double propertyPrice,
            Double downPayment,
            Double interestRate,
            Integer loanTenureYears,
            Double propertyAppreciationRate,
            Double annualMaintenanceCost,
            Double annualPropertyTax,
            Double monthlyRent,
            Double annualRentIncreaseRate,
            Double securityDeposit,
            Double investmentReturnRate,
            Integer analysisPeriodYears) {
        
        if (downPayment == null || downPayment < 0) {
            throw new IllegalArgumentException("Down payment cannot be negative");
        }
        
        if (downPayment >= propertyPrice) {
            throw new IllegalArgumentException("Down payment cannot be greater than or equal to property price");
        }
        
        if (interestRate == null || interestRate <= 0 || interestRate > 100) {
            throw new IllegalArgumentException("Interest rate must be between 0 and 100");
        }
        
        if (loanTenureYears == null || loanTenureYears <= 0 || loanTenureYears > 35) {
            throw new IllegalArgumentException("Loan tenure must be between 1 and 35 years");
        }
        
        if (propertyAppreciationRate == null || propertyAppreciationRate < -20 || propertyAppreciationRate > 20) {
            throw new IllegalArgumentException("Property appreciation rate must be between -20 and 20");
        }
        
        if (annualMaintenanceCost == null || annualMaintenanceCost < 0) {
            throw new IllegalArgumentException("Annual maintenance cost cannot be negative");
        }
        
        if (annualPropertyTax == null || annualPropertyTax < 0) {
            throw new IllegalArgumentException("Annual property tax cannot be negative");
        }
        
        if (monthlyRent == null || monthlyRent <= 0) {
            throw new IllegalArgumentException("Monthly rent must be positive");
        }
        
        if (annualRentIncreaseRate == null || annualRentIncreaseRate < 0 || annualRentIncreaseRate > 20) {
            throw new IllegalArgumentException("Annual rent increase rate must be between 0 and 20");
        }
        
        if (securityDeposit == null || securityDeposit < 0) {
            throw new IllegalArgumentException("Security deposit cannot be negative");
        }
        
        if (investmentReturnRate == null || investmentReturnRate < 0 || investmentReturnRate > 30) {
            throw new IllegalArgumentException("Investment return rate must be between 0 and 30");
        }
        
        if (analysisPeriodYears == null || analysisPeriodYears < 1 || analysisPeriodYears > 30) {
            throw new IllegalArgumentException("Analysis period must be between 1 and 30 years");
        }
    }

    /**
     * Calculate monthly mortgage payment.
     */
    private double calculateMonthlyMortgagePayment(double loanAmount, double monthlyInterestRate, int totalPayments) {
        if (monthlyInterestRate == 0) {
            return loanAmount / totalPayments;
        }
        
        return loanAmount * monthlyInterestRate * Math.pow(1 + monthlyInterestRate, totalPayments) 
                / (Math.pow(1 + monthlyInterestRate, totalPayments) - 1);
    }

    /**
     * Calculate total cost of buying over the analysis period.
     */
    private double calculateTotalCostOfBuying(
            double propertyPrice,
            double downPayment,
            double monthlyMortgagePayment,
            int loanTenureYears,
            double annualMaintenanceCost,
            double annualPropertyTax,
            int analysisPeriodYears) {
        
        double totalMortgagePayments = monthlyMortgagePayment * Math.min(analysisPeriodYears * 12, loanTenureYears * 12);
        double totalMaintenanceCost = annualMaintenanceCost * analysisPeriodYears;
        double totalPropertyTax = annualPropertyTax * analysisPeriodYears;
        
        return downPayment + totalMortgagePayments + totalMaintenanceCost + totalPropertyTax;
    }

    /**
     * Calculate property value at the end of the analysis period.
     */
    private double calculatePropertyValueAtEnd(double propertyPrice, double annualAppreciationRate, int years) {
        return propertyPrice * Math.pow(1 + annualAppreciationRate / 100, years);
    }

    /**
     * Calculate remaining mortgage balance after a certain number of payments.
     */
    private double calculateRemainingMortgageBalance(
            double loanAmount,
            double monthlyInterestRate,
            int totalPayments,
            int paymentsMade) {
        
        if (paymentsMade >= totalPayments) {
            return 0;
        }
        
        if (monthlyInterestRate == 0) {
            return loanAmount * (1 - (double) paymentsMade / totalPayments);
        }
        
        double monthlyPayment = calculateMonthlyMortgagePayment(loanAmount, monthlyInterestRate, totalPayments);
        
        return (monthlyPayment / monthlyInterestRate) * 
               (1 - Math.pow(1 + monthlyInterestRate, -(totalPayments - paymentsMade)));
    }

    /**
     * Calculate total cost of renting over the analysis period.
     */
    private double calculateTotalCostOfRenting(
            double initialMonthlyRent,
            double annualRentIncreaseRate,
            double securityDeposit,
            int years) {
        
        double totalRent = 0;
        double monthlyRent = initialMonthlyRent;
        
        for (int year = 0; year < years; year++) {
            totalRent += monthlyRent * 12;
            monthlyRent *= (1 + annualRentIncreaseRate / 100);
        }
        
        // Security deposit is typically returned, so not included in total cost
        return totalRent;
    }

    /**
     * Calculate investment value at the end of the analysis period.
     * This represents the value of investing the down payment and the monthly savings from renting vs buying.
     */
    private double calculateInvestmentValue(
            double initialInvestment,
            double initialMonthlyRent,
            double monthlyMortgagePayment,
            double monthlyMaintenanceCost,
            double monthlyPropertyTax,
            double annualInvestmentReturnRate,
            double annualRentIncreaseRate,
            int years) {
        
        double monthlyInvestmentReturnRate = annualInvestmentReturnRate / 100 / 12;
        double investmentValue = initialInvestment;
        double monthlyRent = initialMonthlyRent;
        
        for (int month = 0; month < years * 12; month++) {
            // Calculate monthly savings (or additional cost) from renting vs buying
            double monthlyCostOfOwning = monthlyMortgagePayment + monthlyMaintenanceCost + monthlyPropertyTax;
            double monthlySavings = monthlyCostOfOwning - monthlyRent;
            
            // If renting is cheaper, add the savings to the investment
            if (monthlySavings > 0) {
                investmentValue += monthlySavings;
            }
            
            // Apply monthly investment return
            investmentValue *= (1 + monthlyInvestmentReturnRate);
            
            // Increase rent annually
            if (month % 12 == 11) {
                monthlyRent *= (1 + annualRentIncreaseRate / 100);
            }
        }
        
        return investmentValue;
    }

    /**
     * Calculate the break-even point in years.
     * This is the point at which the net worth from buying equals the net worth from renting.
     */
    private double calculateBreakEvenYears(
            double propertyPrice,
            double downPayment,
            double monthlyMortgagePayment,
            double annualMaintenanceCost,
            double annualPropertyTax,
            double initialMonthlyRent,
            double annualRentIncreaseRate,
            double annualInvestmentReturnRate,
            double annualPropertyAppreciationRate) {
        
        // Simple approximation - in a real application, this would be more sophisticated
        double monthlyMaintenanceCost = annualMaintenanceCost / 12;
        double monthlyPropertyTax = annualPropertyTax / 12;
        
        for (int year = 1; year <= 30; year++) {
            // Calculate net worth if buying
            double propertyValue = calculatePropertyValueAtEnd(propertyPrice, annualPropertyAppreciationRate, year);
            double remainingMortgage = calculateRemainingMortgageBalance(
                    propertyPrice - downPayment,
                    (annualInvestmentReturnRate / 100) / 12,
                    30 * 12,
                    year * 12
            );
            double netWorthBuying = propertyValue - remainingMortgage;
            
            // Calculate net worth if renting
            double investmentValue = calculateInvestmentValue(
                    downPayment,
                    initialMonthlyRent,
                    monthlyMortgagePayment,
                    monthlyMaintenanceCost,
                    monthlyPropertyTax,
                    annualInvestmentReturnRate,
                    annualRentIncreaseRate,
                    year
            );
            
            // If buying net worth exceeds renting net worth, we've found the break-even point
            if (netWorthBuying >= investmentValue) {
                return year;
            }
        }
        
        // If no break-even point found within 30 years, return a value indicating this
        return Double.POSITIVE_INFINITY;
    }
}