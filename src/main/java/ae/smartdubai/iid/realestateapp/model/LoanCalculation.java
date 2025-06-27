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

/**
 * Entity representing a loan/mortgage calculation for a property.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoanCalculation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Property property;

    @NotNull(message = "Loan amount is required")
    @Positive(message = "Loan amount must be positive")
    private Double loanAmount;

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
    private Integer tenureYears;

    // Monthly EMI (Equated Monthly Installment)
    private Double monthlyEmi;

    // Total interest payable over the loan tenure
    private Double totalInterest;

    // Total amount payable (principal + interest)
    private Double totalPayable;

    // Loan to Value ratio (LTV)
    @Min(value = 0, message = "LTV cannot be negative")
    @Max(value = 100, message = "LTV cannot exceed 100%")
    private Double loanToValueRatio;

    // Date of calculation
    private java.time.LocalDate calculationDate;
    
    // Principal component in first EMI
    private Double firstEmiPrincipal;
    
    // Interest component in first EMI
    private Double firstEmiInterest;
    
    // Principal component in last EMI
    private Double lastEmiPrincipal;
    
    // Interest component in last EMI
    private Double lastEmiInterest;
}