package ae.smartdubai.iid.realestateapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Entity representing a rent vs buy analysis for a property.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RentVsBuyAnalysis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Property property;

    // Purchase parameters
    @NotNull(message = "Down payment is required")
    @Positive(message = "Down payment must be positive")
    private Double downPayment;

    @NotNull(message = "Interest rate is required")
    @Positive(message = "Interest rate must be positive")
    @Max(value = 100, message = "Interest rate cannot exceed 100%")
    private Double interestRate;

    @NotNull(message = "Loan tenure is required")
    @Positive(message = "Loan tenure must be positive")
    @Max(value = 35, message = "Loan tenure cannot exceed 35 years")
    private Integer loanTenureYears;

    @NotNull(message = "Property appreciation rate is required")
    @Min(value = -20, message = "Property appreciation rate cannot be less than -20%")
    @Max(value = 20, message = "Property appreciation rate cannot exceed 20%")
    private Double propertyAppreciationRate;

    @NotNull(message = "Annual maintenance cost is required")
    @Min(value = 0, message = "Annual maintenance cost cannot be negative")
    private Double annualMaintenanceCost;

    @NotNull(message = "Annual property tax is required")
    @Min(value = 0, message = "Annual property tax cannot be negative")
    private Double annualPropertyTax;

    // Rental parameters
    @NotNull(message = "Monthly rent is required")
    @Positive(message = "Monthly rent must be positive")
    private Double monthlyRent;

    @NotNull(message = "Annual rent increase rate is required")
    @Min(value = 0, message = "Annual rent increase rate cannot be negative")
    @Max(value = 20, message = "Annual rent increase rate cannot exceed 20%")
    private Double annualRentIncreaseRate;

    @NotNull(message = "Security deposit is required")
    @Min(value = 0, message = "Security deposit cannot be negative")
    private Double securityDeposit;

    // Investment parameters
    @NotNull(message = "Investment return rate is required")
    @Min(value = 0, message = "Investment return rate cannot be negative")
    @Max(value = 30, message = "Investment return rate cannot exceed 30%")
    private Double investmentReturnRate;

    // Analysis period
    @NotNull(message = "Analysis period is required")
    @Min(value = 1, message = "Analysis period must be at least 1 year")
    @Max(value = 30, message = "Analysis period cannot exceed 30 years")
    private Integer analysisPeriodYears;

    // Results
    private Double totalCostOfBuying;
    private Double totalCostOfRenting;
    private Double netWorthAfterBuying;
    private Double netWorthAfterRenting;
    private Boolean isBuyingBetter;
    private Double breakEvenYears;

    // Date of analysis
    private LocalDate analysisDate;
}