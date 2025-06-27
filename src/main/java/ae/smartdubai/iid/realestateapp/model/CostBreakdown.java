package ae.smartdubai.iid.realestateapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity representing a cost breakdown for a property purchase.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CostBreakdown {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Property property;

    // Dubai Land Department (DLD) fee - typically 4% of property price
    private Double dldFee;

    // Real estate agency fee - typically 2% of property price
    private Double agencyFee;

    // Registration fee - fixed amount
    private Double registrationFee;

    // Mortgage registration fee if applicable
    private Double mortgageRegistrationFee;

    // Valuation fee for property assessment
    private Double valuationFee;

    // Mortgage processing fee if applicable
    private Double mortgageProcessingFee;

    // Life insurance cost if taking a mortgage
    private Double lifeInsuranceCost;

    // Property insurance cost
    private Double propertyInsuranceCost;

    // Maintenance deposit if applicable
    private Double maintenanceDeposit;

    // Utility connection fees
    private Double utilityConnectionFees;

    // Moving costs estimate
    private Double movingCosts;

    // Total cost (sum of all fees and property price)
    private Double totalCost;

    // Date of calculation
    private java.time.LocalDate calculationDate;
}