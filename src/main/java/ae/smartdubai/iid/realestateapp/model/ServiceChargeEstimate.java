package ae.smartdubai.iid.realestateapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity representing a service charge estimate for a property.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceChargeEstimate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Property property;

    // Community name (e.g., JVC, Dubai Marina, etc.)
    private String communityName;

    // Property type (Apartment, Villa, Townhouse, etc.)
    private String propertyType;

    // Property size in square feet
    @NotNull(message = "Property size is required")
    @Positive(message = "Property size must be positive")
    private Double propertySize;

    // Service charge rate per square foot
    @Positive(message = "Service charge rate must be positive")
    private Double serviceChargeRatePerSqFt;

    // Annual service charge amount
    private Double annualServiceCharge;

    // Cooling charges (district cooling) if applicable
    private Double coolingCharges;

    // Building maintenance fee
    private Double buildingMaintenanceFee;

    // Security fee
    private Double securityFee;

    // Cleaning and waste management fee
    private Double cleaningFee;

    // Parking fee if applicable
    private Double parkingFee;

    // Gym and pool maintenance if applicable
    private Double gymAndPoolFee;

    // Other miscellaneous charges
    private Double miscCharges;

    // Total annual charges (sum of all charges)
    private Double totalAnnualCharges;

    // Monthly charges (total annual charges / 12)
    private Double monthlyCharges;

    // Year of estimate
    private Integer estimateYear;

    // Date of estimate
    private java.time.LocalDate estimateDate;
    
    // Flag to indicate if this is a pre-filled estimate from database
    private Boolean isPreFilled;
}