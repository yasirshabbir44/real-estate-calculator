package ae.smartdubai.iid.realestateapp.model;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Entity representing a document checklist for property purchase.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentChecklist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Property property;

    // Type of buyer: SALARIED, SELF_EMPLOYED, INVESTOR, etc.
    private String buyerType;

    // Selected bank for mortgage (if applicable)
    private String selectedBank;

    // Nationality of buyer (affects document requirements)
    private String nationality;

    // Residence status (UAE Resident, Non-Resident)
    private String residenceStatus;

    // List of required identity documents
    @ElementCollection
    private List<String> identityDocuments;

    // List of required income proof documents
    @ElementCollection
    private List<String> incomeProofDocuments;

    // List of required property documents
    @ElementCollection
    private List<String> propertyDocuments;

    // List of required bank documents
    @ElementCollection
    private List<String> bankDocuments;

    // List of required visa/residency documents
    @ElementCollection
    private List<String> visaDocuments;

    // Additional documents based on specific requirements
    @ElementCollection
    private List<String> additionalDocuments;

    // Notes or special instructions
    @Column(length = 1000)
    private String notes;

    // Date of checklist creation
    private java.time.LocalDate creationDate;
    
    // Flag to indicate if mortgage is required
    private Boolean isMortgageRequired;
    
    // Flag to indicate if property is off-plan
    private Boolean isOffPlan;
    
    // Flag to indicate if property is ready
    private Boolean isReady;
}