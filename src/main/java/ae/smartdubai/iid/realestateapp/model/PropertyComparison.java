package ae.smartdubai.iid.realestateapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity representing a comparison between two properties or rent vs buy analysis.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PropertyComparison {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // First property for comparison
    @ManyToOne
    private Property property1;

    // Second property for comparison (null if doing rent vs buy)
    @ManyToOne
    private Property property2;

    // Flag to indicate if this is a rent vs buy comparison
    private Boolean isRentVsBuy;

    // Monthly rent amount (for rent vs buy comparison)
    private Double monthlyRent;

    // Annual rent increase percentage (for rent vs buy comparison)
    private Double annualRentIncrease;

    // Investment return rate (for opportunity cost calculation)
    private Double investmentReturnRate;

    // Expected property appreciation rate
    private Double propertyAppreciationRate;

    // Holding period in years
    private Integer holdingPeriodYears;

    // Date of comparison
    private java.time.LocalDate comparisonDate;

    // Break-even point in years (for rent vs buy)
    private Double breakEvenYears;

    // Net present value of buying
    private Double buyingNpv;

    // Net present value of renting
    private Double rentingNpv;

    // Total cost of ownership for property1
    private Double property1TotalCost;

    // Total cost of ownership for property2
    private Double property2TotalCost;

    // Cost difference between properties
    @Transient
    public Double getCostDifference() {
        if (!isRentVsBuy && property1TotalCost != null && property2TotalCost != null) {
            return property1TotalCost - property2TotalCost;
        }
        return null;
    }

    // Return on investment for property1
    private Double property1Roi;

    // Return on investment for property2
    private Double property2Roi;
}