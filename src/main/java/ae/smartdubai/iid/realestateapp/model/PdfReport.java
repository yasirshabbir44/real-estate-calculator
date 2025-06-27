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
 * Entity representing a generated PDF report.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PdfReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Property associated with the report
    @ManyToOne
    private Property property;

    // Report title
    private String title;

    // Report type (COST_BREAKDOWN, LOAN_CALCULATION, COMPARISON, etc.)
    private String reportType;

    // Path to the stored PDF file
    private String filePath;

    // File size in KB
    private Long fileSize;

    // Date of report generation
    private java.time.LocalDate generationDate;

    // Email address where the report was shared (if applicable)
    private String sharedToEmail;

    // Date when the report was shared (if applicable)
    private java.time.LocalDate sharedDate;

    // Flag to indicate if the report includes cost breakdown
    private Boolean includesCostBreakdown;

    // Flag to indicate if the report includes loan calculation
    private Boolean includesLoanCalculation;

    // Flag to indicate if the report includes property comparison
    private Boolean includesPropertyComparison;

    // Flag to indicate if the report includes document checklist
    private Boolean includesDocumentChecklist;

    // Flag to indicate if the report includes service charge estimate
    private Boolean includesServiceChargeEstimate;

    // Optional reference to cost breakdown if included
    @ManyToOne
    private CostBreakdown costBreakdown;

    // Optional reference to loan calculation if included
    @ManyToOne
    private LoanCalculation loanCalculation;

    // Optional reference to property comparison if included
    @ManyToOne
    private PropertyComparison propertyComparison;

    // Optional reference to document checklist if included
    @ManyToOne
    private DocumentChecklist documentChecklist;

    // Optional reference to service charge estimate if included
    @ManyToOne
    private ServiceChargeEstimate serviceChargeEstimate;
}