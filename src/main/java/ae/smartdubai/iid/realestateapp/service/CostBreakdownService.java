package ae.smartdubai.iid.realestateapp.service;

import ae.smartdubai.iid.realestateapp.model.CostBreakdown;
import ae.smartdubai.iid.realestateapp.model.Property;
import ae.smartdubai.iid.realestateapp.repository.CostBreakdownRepository;
import ae.smartdubai.iid.realestateapp.repository.PropertyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Service for managing cost breakdowns.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class CostBreakdownService {

    private final CostBreakdownRepository costBreakdownRepository;
    private final PropertyRepository propertyRepository;

    // Constants for fee calculations
    private static final double DLD_FEE_PERCENTAGE = 0.04; // 4% of property price
    private static final double AGENCY_FEE_PERCENTAGE = 0.02; // 2% of property price
    private static final double REGISTRATION_FEE = 4000.0; // Fixed amount in AED
    private static final double MORTGAGE_REGISTRATION_FEE_PERCENTAGE = 0.0025; // 0.25% of mortgage amount
    private static final double VALUATION_FEE = 3000.0; // Fixed amount in AED
    private static final double MORTGAGE_PROCESSING_FEE_PERCENTAGE = 0.01; // 1% of mortgage amount
    private static final double PROPERTY_INSURANCE_PERCENTAGE = 0.0025; // 0.25% of property price per year

    /**
     * Get all cost breakdowns.
     *
     * @return list of all cost breakdowns
     */
    @Transactional(readOnly = true)
    public List<CostBreakdown> getAllCostBreakdowns() {
        return costBreakdownRepository.findAll();
    }

    /**
     * Get cost breakdown by ID.
     *
     * @param id the cost breakdown ID
     * @return the cost breakdown if found
     */
    @Transactional(readOnly = true)
    public Optional<CostBreakdown> getCostBreakdownById(Long id) {
        return costBreakdownRepository.findById(id);
    }

    /**
     * Get cost breakdowns by property.
     *
     * @param propertyId the property ID
     * @return list of cost breakdowns for the specified property
     */
    @Transactional(readOnly = true)
    public List<CostBreakdown> getCostBreakdownsByProperty(Long propertyId) {
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new RuntimeException("Property not found with id " + propertyId));
        return costBreakdownRepository.findByProperty(property);
    }

    /**
     * Calculate and save a cost breakdown for a property.
     *
     * @param propertyId the property ID
     * @param mortgageAmount the mortgage amount (0 if no mortgage)
     * @param lifeInsuranceCost the life insurance cost (0 if not applicable)
     * @param maintenanceDeposit the maintenance deposit (0 if not applicable)
     * @param utilityConnectionFees the utility connection fees
     * @param movingCosts the moving costs
     * @return the calculated cost breakdown
     */
    public CostBreakdown calculateCostBreakdown(Long propertyId, Double mortgageAmount, 
                                               Double lifeInsuranceCost, Double maintenanceDeposit,
                                               Double utilityConnectionFees, Double movingCosts) {
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new RuntimeException("Property not found with id " + propertyId));
        
        CostBreakdown costBreakdown = new CostBreakdown();
        costBreakdown.setProperty(property);
        
        // Calculate DLD fee (4% of property price)
        double dldFee = property.getPrice() * DLD_FEE_PERCENTAGE;
        costBreakdown.setDldFee(dldFee);
        
        // Calculate agency fee (2% of property price)
        double agencyFee = property.getPrice() * AGENCY_FEE_PERCENTAGE;
        costBreakdown.setAgencyFee(agencyFee);
        
        // Set fixed registration fee
        costBreakdown.setRegistrationFee(REGISTRATION_FEE);
        
        // Calculate mortgage-related fees if applicable
        if (mortgageAmount > 0) {
            double mortgageRegistrationFee = mortgageAmount * MORTGAGE_REGISTRATION_FEE_PERCENTAGE;
            costBreakdown.setMortgageRegistrationFee(mortgageRegistrationFee);
            
            costBreakdown.setValuationFee(VALUATION_FEE);
            
            double mortgageProcessingFee = mortgageAmount * MORTGAGE_PROCESSING_FEE_PERCENTAGE;
            costBreakdown.setMortgageProcessingFee(mortgageProcessingFee);
        } else {
            costBreakdown.setMortgageRegistrationFee(0.0);
            costBreakdown.setValuationFee(0.0);
            costBreakdown.setMortgageProcessingFee(0.0);
        }
        
        // Set life insurance cost
        costBreakdown.setLifeInsuranceCost(lifeInsuranceCost);
        
        // Calculate property insurance (0.25% of property price)
        double propertyInsuranceCost = property.getPrice() * PROPERTY_INSURANCE_PERCENTAGE;
        costBreakdown.setPropertyInsuranceCost(propertyInsuranceCost);
        
        // Set other costs
        costBreakdown.setMaintenanceDeposit(maintenanceDeposit);
        costBreakdown.setUtilityConnectionFees(utilityConnectionFees);
        costBreakdown.setMovingCosts(movingCosts);
        
        // Calculate total cost
        double totalCost = property.getPrice() + dldFee + agencyFee + REGISTRATION_FEE +
                (costBreakdown.getMortgageRegistrationFee() != null ? costBreakdown.getMortgageRegistrationFee() : 0.0) +
                (costBreakdown.getValuationFee() != null ? costBreakdown.getValuationFee() : 0.0) +
                (costBreakdown.getMortgageProcessingFee() != null ? costBreakdown.getMortgageProcessingFee() : 0.0) +
                lifeInsuranceCost + propertyInsuranceCost + maintenanceDeposit +
                utilityConnectionFees + movingCosts;
        
        costBreakdown.setTotalCost(totalCost);
        costBreakdown.setCalculationDate(LocalDate.now());
        
        return costBreakdownRepository.save(costBreakdown);
    }

    /**
     * Delete a cost breakdown.
     *
     * @param id the cost breakdown ID
     */
    public void deleteCostBreakdown(Long id) {
        costBreakdownRepository.deleteById(id);
    }

    /**
     * Delete all cost breakdowns for a property.
     *
     * @param propertyId the property ID
     */
    public void deleteAllCostBreakdownsForProperty(Long propertyId) {
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new RuntimeException("Property not found with id " + propertyId));
        costBreakdownRepository.deleteByProperty(property);
    }
}