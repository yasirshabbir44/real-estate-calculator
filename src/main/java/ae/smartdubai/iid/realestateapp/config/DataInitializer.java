package ae.smartdubai.iid.realestateapp.config;

import ae.smartdubai.iid.realestateapp.model.Property;
import ae.smartdubai.iid.realestateapp.model.DocumentChecklist;
import ae.smartdubai.iid.realestateapp.model.ServiceChargeEstimate;
import ae.smartdubai.iid.realestateapp.repository.PropertyRepository;
import ae.smartdubai.iid.realestateapp.repository.DocumentChecklistRepository;
import ae.smartdubai.iid.realestateapp.repository.ServiceChargeEstimateRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Configuration class for initializing default data in the database.
 */
@Configuration
public class DataInitializer {

    /**
     * Creates a CommandLineRunner bean to initialize default data in the database.
     *
     * @param propertyRepository the property repository
     * @param documentChecklistRepository the document checklist repository
     * @param serviceChargeEstimateRepository the service charge estimate repository
     * @return a CommandLineRunner bean
     */
    @Bean
    public CommandLineRunner initData(
            PropertyRepository propertyRepository,
            DocumentChecklistRepository documentChecklistRepository,
            ServiceChargeEstimateRepository serviceChargeEstimateRepository) {

        return args -> {
            // Initialize default properties
            initializeProperties(propertyRepository);

            // Initialize default document checklists
            initializeDocumentChecklists(documentChecklistRepository, propertyRepository);

            // Initialize default service charge estimates
            initializeServiceChargeEstimates(serviceChargeEstimateRepository, propertyRepository);
        };
    }

    /**
     * Initializes default properties in the database.
     *
     * @param propertyRepository the property repository
     */
    private void initializeProperties(PropertyRepository propertyRepository) {
        // Check if properties already exist
        if (propertyRepository.count() > 0) {
            return;
        }

        // Create default properties
        List<Property> defaultProperties = Arrays.asList(
            new Property(
                null, 
                "Luxury Apartment in Downtown", 
                "Downtown Dubai", 
                1500000.0, 
                1200.0, 
                2, 
                2, 
                "Apartment", 
                "Downtown Dubai", 
                true, 
                2020,
                    Collections.singletonList("https://example.com/images/downtown-apartment.jpg")
            ),
            new Property(
                null, 
                "Spacious Villa in Arabian Ranches", 
                "Arabian Ranches", 
                3500000.0, 
                3500.0, 
                4, 
                4, 
                "Villa", 
                "Arabian Ranches", 
                false, 
                2018,
                    Collections.singletonList("https://example.com/images/arabian-ranches-villa.jpg")
            ),
            new Property(
                null, 
                "Modern Townhouse in Dubai Hills", 
                "Dubai Hills Estate", 
                2800000.0, 
                2200.0, 
                3, 
                3, 
                "Townhouse", 
                "Dubai Hills Estate", 
                false, 
                2021,
                    Collections.singletonList("https://example.com/images/dubai-hills-townhouse.jpg")
            ),
            new Property(
                null, 
                "Beachfront Apartment in JBR", 
                "Jumeirah Beach Residence", 
                2200000.0, 
                1500.0, 
                2, 
                3, 
                "Apartment", 
                "Jumeirah Beach Residence", 
                true, 
                2010,
                    Collections.singletonList("https://example.com/images/jbr-apartment.jpg")
            ),
            new Property(
                null, 
                "Penthouse in Marina", 
                "Dubai Marina", 
                5000000.0, 
                3000.0, 
                4, 
                5, 
                "Penthouse", 
                "Dubai Marina", 
                true, 
                2015,
                    Collections.singletonList("https://example.com/images/marina-penthouse.jpg")
            )
        );

        // Save default properties
        propertyRepository.saveAll(defaultProperties);
    }

    /**
     * Initializes default document checklists in the database.
     *
     * @param documentChecklistRepository the document checklist repository
     * @param propertyRepository the property repository
     */
    private void initializeDocumentChecklists(
            DocumentChecklistRepository documentChecklistRepository,
            PropertyRepository propertyRepository) {
        // Check if document checklists already exist
        if (documentChecklistRepository.count() > 0) {
            return;
        }

        // Get first property for reference
        Property property = propertyRepository.findAll().get(0);

        // Create default document checklists
        List<DocumentChecklist> defaultChecklists = Arrays.asList(
            new DocumentChecklist(
                null,
                property,
                "SALARIED",
                "Emirates NBD",
                "UAE National",
                "UAE Resident",
                Arrays.asList("Emirates ID", "Passport", "Visa"),
                Arrays.asList("Salary Certificate", "Bank Statements (6 months)", "Employment Contract"),
                Arrays.asList("Title Deed", "RERA Valuation", "Floor Plan"),
                Arrays.asList("Loan Application Form", "Credit Report", "Cheque Book"),
                Arrays.asList("Residence Visa", "Entry Permit"),
                Arrays.asList("NOC from Current Employer"),
                "Standard documents required for UAE Nationals who are salaried employees.",
                LocalDate.now(),
                true,
                false,
                true
            ),
            new DocumentChecklist(
                null,
                property,
                "SELF_EMPLOYED",
                "Dubai Islamic Bank",
                "Expat",
                "UAE Resident",
                Arrays.asList("Emirates ID", "Passport", "Visa"),
                Arrays.asList("Trade License", "Bank Statements (12 months)", "Audited Financial Statements"),
                Arrays.asList("Title Deed", "RERA Valuation", "Floor Plan"),
                Arrays.asList("Loan Application Form", "Credit Report", "Cheque Book"),
                Arrays.asList("Residence Visa", "Entry Permit"),
                Arrays.asList("Company Memorandum", "Share Certificates"),
                "Standard documents required for Expats who are self-employed.",
                LocalDate.now(),
                true,
                false,
                true
            ),
            new DocumentChecklist(
                null,
                property,
                "INVESTOR",
                "ADCB",
                "Expat",
                "Non-Resident",
                Arrays.asList("Passport", "Foreign ID"),
                Arrays.asList("Bank Statements (12 months)", "Investment Portfolio"),
                Arrays.asList("Title Deed", "RERA Valuation", "Floor Plan"),
                Arrays.asList("Loan Application Form", "Credit Report", "International Bank Reference"),
                Arrays.asList(),
                Arrays.asList("Proof of Address in Home Country", "Tax Residency Certificate"),
                "Standard documents required for Non-Resident Investors.",
                LocalDate.now(),
                false,
                true,
                false
            )
        );

        // Save default document checklists
        documentChecklistRepository.saveAll(defaultChecklists);
    }

    /**
     * Initializes default service charge estimates in the database.
     *
     * @param serviceChargeEstimateRepository the service charge estimate repository
     * @param propertyRepository the property repository
     */
    private void initializeServiceChargeEstimates(
            ServiceChargeEstimateRepository serviceChargeEstimateRepository,
            PropertyRepository propertyRepository) {
        // Check if service charge estimates already exist
        if (serviceChargeEstimateRepository.count() > 0) {
            return;
        }

        // Create default service charge estimates for common communities and property types
        List<ServiceChargeEstimate> defaultEstimates = Arrays.asList(
            // Downtown Dubai - Apartment
            new ServiceChargeEstimate(
                null,
                null, // No specific property
                "Downtown Dubai",
                "Apartment",
                1000.0,
                15.0,
                15000.0,
                5000.0,
                3000.0,
                2000.0,
                1500.0,
                1000.0,
                2000.0,
                500.0,
                30000.0,
                2500.0,
                2023,
                LocalDate.now(),
                true
            ),
            // Dubai Marina - Apartment
            new ServiceChargeEstimate(
                null,
                null, // No specific property
                "Dubai Marina",
                "Apartment",
                1200.0,
                12.0,
                14400.0,
                4800.0,
                2800.0,
                1800.0,
                1400.0,
                900.0,
                1800.0,
                500.0,
                28400.0,
                2366.67,
                2023,
                LocalDate.now(),
                true
            ),
            // Arabian Ranches - Villa
            new ServiceChargeEstimate(
                null,
                null, // No specific property
                "Arabian Ranches",
                "Villa",
                3500.0,
                8.0,
                28000.0,
                0.0, // No district cooling
                5000.0,
                3000.0,
                2500.0,
                0.0, // No parking fee
                3000.0,
                1500.0,
                43000.0,
                3583.33,
                2023,
                LocalDate.now(),
                true
            ),
            // Dubai Hills Estate - Townhouse
            new ServiceChargeEstimate(
                null,
                null, // No specific property
                "Dubai Hills Estate",
                "Townhouse",
                2200.0,
                10.0,
                22000.0,
                0.0, // No district cooling
                4000.0,
                2500.0,
                2000.0,
                0.0, // No parking fee
                2500.0,
                1000.0,
                34000.0,
                2833.33,
                2023,
                LocalDate.now(),
                true
            ),
            // Jumeirah Beach Residence - Apartment
            new ServiceChargeEstimate(
                null,
                null, // No specific property
                "Jumeirah Beach Residence",
                "Apartment",
                1500.0,
                14.0,
                21000.0,
                5500.0,
                3500.0,
                2200.0,
                1800.0,
                1200.0,
                2500.0,
                800.0,
                38500.0,
                3208.33,
                2023,
                LocalDate.now(),
                true
            )
        );

        // Save default service charge estimates
        serviceChargeEstimateRepository.saveAll(defaultEstimates);

        // Create property-specific service charge estimates for the first property
        Property firstProperty = propertyRepository.findAll().get(0);
        ServiceChargeEstimate propertyEstimate = new ServiceChargeEstimate(
            null,
            firstProperty,
            firstProperty.getCommunityName(),
            firstProperty.getPropertyType(),
            firstProperty.getSize(),
            15.0,
            firstProperty.getSize() * 15.0,
            5000.0,
            3000.0,
            2000.0,
            1500.0,
            1000.0,
            2000.0,
            500.0,
            firstProperty.getSize() * 15.0 + 15000.0,
            (firstProperty.getSize() * 15.0 + 15000.0) / 12,
            2023,
            LocalDate.now(),
            false
        );

        // Save property-specific service charge estimate
        serviceChargeEstimateRepository.save(propertyEstimate);
    }
}
