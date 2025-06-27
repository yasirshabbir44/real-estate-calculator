package ae.smartdubai.iid.realestateapp.service;

import ae.smartdubai.iid.realestateapp.model.Property;
import ae.smartdubai.iid.realestateapp.model.PropertyComparison;
import ae.smartdubai.iid.realestateapp.repository.PropertyComparisonRepository;
import ae.smartdubai.iid.realestateapp.repository.PropertyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Service for managing property comparisons.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class PropertyComparisonService {

    private final PropertyComparisonRepository propertyComparisonRepository;
    private final PropertyRepository propertyRepository;

    /**
     * Get all property comparisons.
     *
     * @return list of all property comparisons
     */
    @Transactional(readOnly = true)
    public List<PropertyComparison> getAllPropertyComparisons() {
        return propertyComparisonRepository.findAll();
    }

    /**
     * Get property comparison by ID.
     *
     * @param id the property comparison ID
     * @return the property comparison if found
     */
    @Transactional(readOnly = true)
    public Optional<PropertyComparison> getPropertyComparisonById(Long id) {
        return propertyComparisonRepository.findById(id);
    }

    /**
     * Get property comparisons by property.
     *
     * @param propertyId the property ID
     * @return list of property comparisons for the specified property
     */
    @Transactional(readOnly = true)
    public List<PropertyComparison> getPropertyComparisonsByProperty(Long propertyId) {
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new RuntimeException("Property not found with id " + propertyId));
        return propertyComparisonRepository.findByProperty1(property);
    }

    /**
     * Compare two properties.
     *
     * @param property1Id the first property ID
     * @param property2Id the second property ID
     * @param propertyAppreciationRate the expected annual property appreciation rate (in percentage)
     * @param holdingPeriodYears the expected holding period in years
     * @return the property comparison
     */
    public PropertyComparison compareProperties(Long property1Id, Long property2Id, 
                                               Double propertyAppreciationRate, Integer holdingPeriodYears) {
        Property property1 = propertyRepository.findById(property1Id)
                .orElseThrow(() -> new RuntimeException("Property 1 not found with id " + property1Id));

        Property property2 = propertyRepository.findById(property2Id)
                .orElseThrow(() -> new RuntimeException("Property 2 not found with id " + property2Id));

        if (property1.getPrice() == null || property1.getPrice() <= 0) {
            throw new IllegalArgumentException("Property 1 price must be positive");
        }

        if (property2.getPrice() == null || property2.getPrice() <= 0) {
            throw new IllegalArgumentException("Property 2 price must be positive");
        }

        if (propertyAppreciationRate == null || propertyAppreciationRate < 0) {
            throw new IllegalArgumentException("Property appreciation rate cannot be negative");
        }

        if (holdingPeriodYears == null || holdingPeriodYears <= 0) {
            throw new IllegalArgumentException("Holding period must be positive");
        }

        PropertyComparison comparison = new PropertyComparison();
        comparison.setProperty1(property1);
        comparison.setProperty2(property2);
        comparison.setIsRentVsBuy(false);
        comparison.setPropertyAppreciationRate(propertyAppreciationRate);
        comparison.setHoldingPeriodYears(holdingPeriodYears);
        comparison.setComparisonDate(LocalDate.now());

        // Calculate total cost of ownership for property 1
        double property1TotalCost = calculateTotalCostOfOwnership(property1, holdingPeriodYears);
        comparison.setProperty1TotalCost(property1TotalCost);

        // Calculate total cost of ownership for property 2
        double property2TotalCost = calculateTotalCostOfOwnership(property2, holdingPeriodYears);
        comparison.setProperty2TotalCost(property2TotalCost);

        // Calculate ROI for property 1
        double property1FutureValue = property1.getPrice() * Math.pow(1 + propertyAppreciationRate / 100, holdingPeriodYears);
        double property1Roi = ((property1FutureValue - property1.getPrice()) / property1.getPrice()) * 100;
        comparison.setProperty1Roi(property1Roi);

        // Calculate ROI for property 2
        double property2FutureValue = property2.getPrice() * Math.pow(1 + propertyAppreciationRate / 100, holdingPeriodYears);
        double property2Roi = ((property2FutureValue - property2.getPrice()) / property2.getPrice()) * 100;
        comparison.setProperty2Roi(property2Roi);

        return propertyComparisonRepository.save(comparison);
    }

    /**
     * Compare renting vs buying a property.
     *
     * @param propertyId the property ID (for buying)
     * @param monthlyRent the monthly rent amount
     * @param annualRentIncrease the annual rent increase percentage
     * @param investmentReturnRate the investment return rate for opportunity cost calculation
     * @param propertyAppreciationRate the expected annual property appreciation rate
     * @param holdingPeriodYears the expected holding period in years
     * @return the property comparison
     */
    public PropertyComparison compareRentVsBuy(Long propertyId, Double monthlyRent, Double annualRentIncrease,
                                              Double investmentReturnRate, Double propertyAppreciationRate,
                                              Integer holdingPeriodYears) {
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new RuntimeException("Property not found with id " + propertyId));

        if (property.getPrice() == null || property.getPrice() <= 0) {
            throw new IllegalArgumentException("Property price must be positive");
        }

        if (monthlyRent == null || monthlyRent <= 0) {
            throw new IllegalArgumentException("Monthly rent must be positive");
        }

        if (annualRentIncrease == null || annualRentIncrease < 0) {
            throw new IllegalArgumentException("Annual rent increase cannot be negative");
        }

        if (investmentReturnRate == null || investmentReturnRate < 0) {
            throw new IllegalArgumentException("Investment return rate cannot be negative");
        }

        if (propertyAppreciationRate == null || propertyAppreciationRate < 0) {
            throw new IllegalArgumentException("Property appreciation rate cannot be negative");
        }

        if (holdingPeriodYears == null || holdingPeriodYears <= 0) {
            throw new IllegalArgumentException("Holding period must be positive");
        }

        PropertyComparison comparison = new PropertyComparison();
        comparison.setProperty1(property);
        comparison.setProperty2(null);
        comparison.setIsRentVsBuy(true);
        comparison.setMonthlyRent(monthlyRent);
        comparison.setAnnualRentIncrease(annualRentIncrease);
        comparison.setInvestmentReturnRate(investmentReturnRate);
        comparison.setPropertyAppreciationRate(propertyAppreciationRate);
        comparison.setHoldingPeriodYears(holdingPeriodYears);
        comparison.setComparisonDate(LocalDate.now());

        // Calculate buying NPV
        double buyingNpv = calculateBuyingNpv(property, propertyAppreciationRate, holdingPeriodYears, investmentReturnRate);
        comparison.setBuyingNpv(buyingNpv);

        // Calculate renting NPV
        double rentingNpv = calculateRentingNpv(monthlyRent, annualRentIncrease, holdingPeriodYears, investmentReturnRate, property.getPrice());
        comparison.setRentingNpv(rentingNpv);

        // Calculate break-even point
        double breakEvenYears = calculateBreakEvenPoint(property, monthlyRent, annualRentIncrease, propertyAppreciationRate);
        comparison.setBreakEvenYears(breakEvenYears);

        // Set total cost of ownership for property
        double propertyTotalCost = calculateTotalCostOfOwnership(property, holdingPeriodYears);
        comparison.setProperty1TotalCost(propertyTotalCost);

        return propertyComparisonRepository.save(comparison);
    }

    /**
     * Calculate total cost of ownership for a property.
     *
     * @param property the property
     * @param holdingPeriodYears the holding period in years
     * @return the total cost of ownership
     */
    private double calculateTotalCostOfOwnership(Property property, Integer holdingPeriodYears) {
        // This is a simplified calculation. In a real application, you would include:
        // - Purchase costs (closing costs, taxes, etc.)
        // - Mortgage costs (if applicable)
        // - Property taxes
        // - Insurance
        // - Maintenance
        // - HOA fees
        // - Utilities
        // - Opportunity cost of down payment

        // For simplicity, we'll use a rough estimate of 30% of the purchase price over the holding period
        return property.getPrice() * 1.3;
    }

    /**
     * Calculate NPV of buying a property.
     *
     * @param property the property
     * @param propertyAppreciationRate the annual property appreciation rate
     * @param holdingPeriodYears the holding period in years
     * @param discountRate the discount rate (investment return rate)
     * @return the NPV of buying
     */
    private double calculateBuyingNpv(Property property, Double propertyAppreciationRate, 
                                     Integer holdingPeriodYears, Double discountRate) {
        // Initial investment (negative cash flow)
        double initialInvestment = -property.getPrice();

        // Future value of the property
        double futureValue = property.getPrice() * Math.pow(1 + propertyAppreciationRate / 100, holdingPeriodYears);

        // Discount the future value to present value
        double presentValueOfFutureValue = futureValue / Math.pow(1 + discountRate / 100, holdingPeriodYears);

        // NPV = Initial investment + PV of future value
        return initialInvestment + presentValueOfFutureValue;
    }

    /**
     * Calculate NPV of renting.
     *
     * @param monthlyRent the monthly rent
     * @param annualRentIncrease the annual rent increase percentage
     * @param holdingPeriodYears the holding period in years
     * @param discountRate the discount rate (investment return rate)
     * @param propertyPrice the property price (for opportunity cost calculation)
     * @return the NPV of renting
     */
    private double calculateRentingNpv(Double monthlyRent, Double annualRentIncrease, 
                                      Integer holdingPeriodYears, Double discountRate, Double propertyPrice) {
        double npv = 0;

        // Calculate the present value of all rent payments
        for (int year = 0; year < holdingPeriodYears; year++) {
            double yearlyRent = monthlyRent * 12 * Math.pow(1 + annualRentIncrease / 100, year);
            npv -= yearlyRent / Math.pow(1 + discountRate / 100, year + 1);
        }

        // Add the opportunity cost benefit of investing the property price
        double futureValueOfInvestment = propertyPrice * Math.pow(1 + discountRate / 100, holdingPeriodYears);
        npv += futureValueOfInvestment - propertyPrice;

        return npv;
    }

    /**
     * Calculate break-even point for rent vs buy.
     *
     * @param property the property
     * @param monthlyRent the monthly rent
     * @param annualRentIncrease the annual rent increase percentage
     * @param propertyAppreciationRate the annual property appreciation rate
     * @return the break-even point in years
     */
    private double calculateBreakEvenPoint(Property property, Double monthlyRent, 
                                          Double annualRentIncrease, Double propertyAppreciationRate) {
        // This is a simplified calculation. In a real application, you would use a more sophisticated approach.
        // For simplicity, we'll estimate the break-even point as the point where:
        // Total rent paid = Property price - Property appreciation

        double yearlyRent = monthlyRent * 12;
        double breakEvenYears = 0;
        double totalRentPaid = 0;
        double propertyAppreciation = 0;

        while (totalRentPaid < (property.getPrice() - propertyAppreciation) && breakEvenYears < 50) {
            breakEvenYears += 1;
            totalRentPaid += yearlyRent * Math.pow(1 + annualRentIncrease / 100, breakEvenYears - 1);
            propertyAppreciation = property.getPrice() * (Math.pow(1 + propertyAppreciationRate / 100, breakEvenYears) - 1);
        }

        return breakEvenYears;
    }

    /**
     * Delete a property comparison.
     *
     * @param id the property comparison ID
     */
    public void deletePropertyComparison(Long id) {
        propertyComparisonRepository.deleteById(id);
    }

    /**
     * Delete all property comparisons for a property.
     *
     * @param propertyId the property ID
     */
    public void deleteAllPropertyComparisonsForProperty(Long propertyId) {
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new RuntimeException("Property not found with id " + propertyId));
        propertyComparisonRepository.deleteByProperty1OrProperty2(property, property);
    }
}
