package ae.smartdubai.iid.realestateapp.service;

import ae.smartdubai.iid.realestateapp.model.Property;
import ae.smartdubai.iid.realestateapp.model.ServiceChargeEstimate;
import ae.smartdubai.iid.realestateapp.repository.PropertyRepository;
import ae.smartdubai.iid.realestateapp.repository.ServiceChargeEstimateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Service for managing service charge estimates.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class ServiceChargeEstimateService {

    private final ServiceChargeEstimateRepository serviceChargeEstimateRepository;
    private final PropertyRepository propertyRepository;

    // Pre-filled service charge rates per square foot for different communities
    private static final Map<String, Double> COMMUNITY_SERVICE_CHARGE_RATES = new HashMap<>();

    static {
        // Initialize with some common Dubai communities and their approximate service charge rates
        COMMUNITY_SERVICE_CHARGE_RATES.put("DUBAI_MARINA", 15.0);
        COMMUNITY_SERVICE_CHARGE_RATES.put("DOWNTOWN_DUBAI", 18.0);
        COMMUNITY_SERVICE_CHARGE_RATES.put("PALM_JUMEIRAH", 20.0);
        COMMUNITY_SERVICE_CHARGE_RATES.put("JUMEIRAH_LAKE_TOWERS", 14.0);
        COMMUNITY_SERVICE_CHARGE_RATES.put("BUSINESS_BAY", 16.0);
        COMMUNITY_SERVICE_CHARGE_RATES.put("JUMEIRAH_VILLAGE_CIRCLE", 12.0);
        COMMUNITY_SERVICE_CHARGE_RATES.put("DUBAI_SPORTS_CITY", 10.0);
        COMMUNITY_SERVICE_CHARGE_RATES.put("INTERNATIONAL_CITY", 8.0);
        COMMUNITY_SERVICE_CHARGE_RATES.put("DUBAI_SILICON_OASIS", 9.0);
        COMMUNITY_SERVICE_CHARGE_RATES.put("ARABIAN_RANCHES", 12.0);
        COMMUNITY_SERVICE_CHARGE_RATES.put("EMIRATES_HILLS", 22.0);
        COMMUNITY_SERVICE_CHARGE_RATES.put("THE_SPRINGS", 11.0);
        COMMUNITY_SERVICE_CHARGE_RATES.put("THE_GREENS", 13.0);
        COMMUNITY_SERVICE_CHARGE_RATES.put("DUBAI_HILLS_ESTATE", 15.0);
        COMMUNITY_SERVICE_CHARGE_RATES.put("BLUEWATERS_ISLAND", 22.0);
    }

    // Cooling charge rates per square foot for different communities
    private static final Map<String, Double> COMMUNITY_COOLING_RATES = new HashMap<>();

    static {
        // Initialize with some common Dubai communities and their approximate cooling charge rates
        COMMUNITY_COOLING_RATES.put("DUBAI_MARINA", 6.0);
        COMMUNITY_COOLING_RATES.put("DOWNTOWN_DUBAI", 7.0);
        COMMUNITY_COOLING_RATES.put("PALM_JUMEIRAH", 7.5);
        COMMUNITY_COOLING_RATES.put("JUMEIRAH_LAKE_TOWERS", 6.0);
        COMMUNITY_COOLING_RATES.put("BUSINESS_BAY", 6.5);
        COMMUNITY_COOLING_RATES.put("JUMEIRAH_VILLAGE_CIRCLE", 5.0);
        COMMUNITY_COOLING_RATES.put("DUBAI_SPORTS_CITY", 4.5);
        COMMUNITY_COOLING_RATES.put("INTERNATIONAL_CITY", 4.0);
        COMMUNITY_COOLING_RATES.put("DUBAI_SILICON_OASIS", 4.5);
        COMMUNITY_COOLING_RATES.put("ARABIAN_RANCHES", 0.0); // Individual cooling
        COMMUNITY_COOLING_RATES.put("EMIRATES_HILLS", 0.0); // Individual cooling
        COMMUNITY_COOLING_RATES.put("THE_SPRINGS", 0.0); // Individual cooling
        COMMUNITY_COOLING_RATES.put("THE_GREENS", 5.5);
        COMMUNITY_COOLING_RATES.put("DUBAI_HILLS_ESTATE", 6.0);
        COMMUNITY_COOLING_RATES.put("BLUEWATERS_ISLAND", 7.5);
    }

    /**
     * Get all service charge estimates.
     *
     * @return list of all service charge estimates
     */
    @Transactional(readOnly = true)
    public List<ServiceChargeEstimate> getAllServiceChargeEstimates() {
        return serviceChargeEstimateRepository.findAll();
    }

    /**
     * Get service charge estimate by ID.
     *
     * @param id the service charge estimate ID
     * @return the service charge estimate if found
     */
    @Transactional(readOnly = true)
    public Optional<ServiceChargeEstimate> getServiceChargeEstimateById(Long id) {
        return serviceChargeEstimateRepository.findById(id);
    }

    /**
     * Get service charge estimates by property.
     *
     * @param propertyId the property ID
     * @return list of service charge estimates for the specified property
     */
    @Transactional(readOnly = true)
    public List<ServiceChargeEstimate> getServiceChargeEstimatesByProperty(Long propertyId) {
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new RuntimeException("Property not found with id " + propertyId));
        return serviceChargeEstimateRepository.findByProperty(property);
    }

    /**
     * Get pre-filled service charge estimates by community.
     *
     * @param communityName the community name
     * @return list of pre-filled service charge estimates for the specified community
     */
    @Transactional(readOnly = true)
    public List<ServiceChargeEstimate> getPreFilledEstimatesByCommunity(String communityName) {
        return serviceChargeEstimateRepository.findByIsPreFilledTrueAndCommunityNameIgnoreCase(communityName);
    }

    /**
     * Calculate and save a service charge estimate for a property.
     *
     * @param propertyId the property ID
     * @param communityName the community name
     * @param propertyType the property type
     * @param propertySize the property size in square feet
     * @return the calculated service charge estimate
     */
    public ServiceChargeEstimate calculateServiceCharges(Long propertyId, String communityName, 
                                                       String propertyType, Double propertySize) {
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new RuntimeException("Property not found with id " + propertyId));

        if (propertySize == null || propertySize <= 0) {
            throw new IllegalArgumentException("Property size must be positive");
        }

        ServiceChargeEstimate estimate = new ServiceChargeEstimate();
        estimate.setProperty(property);
        estimate.setCommunityName(communityName);
        estimate.setPropertyType(propertyType);
        estimate.setPropertySize(propertySize);
        estimate.setEstimateYear(LocalDate.now().getYear());
        estimate.setEstimateDate(LocalDate.now());
        estimate.setIsPreFilled(false);

        // Get service charge rate for the community
        Double serviceChargeRate = COMMUNITY_SERVICE_CHARGE_RATES.getOrDefault(
                communityName.toUpperCase().replace(" ", "_"), 12.0); // Default rate if community not found
        estimate.setServiceChargeRatePerSqFt(serviceChargeRate);

        // Calculate annual service charge
        double annualServiceCharge = propertySize * serviceChargeRate;
        estimate.setAnnualServiceCharge(annualServiceCharge);

        // Calculate cooling charges if applicable
        Double coolingRate = COMMUNITY_COOLING_RATES.getOrDefault(
                communityName.toUpperCase().replace(" ", "_"), 0.0);
        double coolingCharges = propertySize * coolingRate;
        estimate.setCoolingCharges(coolingCharges);

        // Calculate other fees based on property size and type
        calculateAdditionalFees(estimate, propertySize, propertyType);

        // Calculate total annual charges
        double totalAnnualCharges = annualServiceCharge + coolingCharges +
                estimate.getBuildingMaintenanceFee() + estimate.getSecurityFee() +
                estimate.getCleaningFee() + estimate.getParkingFee() +
                estimate.getGymAndPoolFee() + estimate.getMiscCharges();
        estimate.setTotalAnnualCharges(totalAnnualCharges);

        // Calculate monthly charges
        double monthlyCharges = totalAnnualCharges / 12;
        estimate.setMonthlyCharges(monthlyCharges);

        return serviceChargeEstimateRepository.save(estimate);
    }

    /**
     * Calculate additional fees based on property size and type.
     *
     * @param estimate the service charge estimate
     * @param propertySize the property size in square feet
     * @param propertyType the property type
     */
    private void calculateAdditionalFees(ServiceChargeEstimate estimate, Double propertySize, String propertyType) {
        // Building maintenance fee (varies by property size)
        double buildingMaintenanceFee;
        if (propertySize < 800) {
            buildingMaintenanceFee = 2000;
        } else if (propertySize < 1500) {
            buildingMaintenanceFee = 3000;
        } else if (propertySize < 2500) {
            buildingMaintenanceFee = 4000;
        } else {
            buildingMaintenanceFee = 5000;
        }
        estimate.setBuildingMaintenanceFee(buildingMaintenanceFee);

        // Security fee (varies by property type)
        double securityFee;
        if ("APARTMENT".equalsIgnoreCase(propertyType)) {
            securityFee = 1500;
        } else if ("VILLA".equalsIgnoreCase(propertyType)) {
            securityFee = 2500;
        } else if ("TOWNHOUSE".equalsIgnoreCase(propertyType)) {
            securityFee = 2000;
        } else {
            securityFee = 1800;
        }
        estimate.setSecurityFee(securityFee);

        // Cleaning fee (varies by property size)
        double cleaningFee = propertySize * 1.5;
        estimate.setCleaningFee(cleaningFee);

        // Parking fee (varies by property type)
        double parkingFee;
        if ("APARTMENT".equalsIgnoreCase(propertyType)) {
            parkingFee = 1000;
        } else {
            parkingFee = 0; // Included in other fees for villas and townhouses
        }
        estimate.setParkingFee(parkingFee);

        // Gym and pool fee (varies by property type and community)
        double gymAndPoolFee;
        if ("APARTMENT".equalsIgnoreCase(propertyType)) {
            gymAndPoolFee = 2000;
        } else if ("VILLA".equalsIgnoreCase(propertyType)) {
            gymAndPoolFee = 3000;
        } else if ("TOWNHOUSE".equalsIgnoreCase(propertyType)) {
            gymAndPoolFee = 2500;
        } else {
            gymAndPoolFee = 2000;
        }
        estimate.setGymAndPoolFee(gymAndPoolFee);

        // Miscellaneous charges (fixed percentage of other charges)
        double miscCharges = (buildingMaintenanceFee + securityFee + cleaningFee + parkingFee + gymAndPoolFee) * 0.05;
        estimate.setMiscCharges(miscCharges);
    }

    /**
     * Create pre-filled service charge estimates for all communities.
     * This method can be used to initialize the database with pre-filled estimates.
     */
    public void createPreFilledEstimates() {
        // For each community
        for (Map.Entry<String, Double> entry : COMMUNITY_SERVICE_CHARGE_RATES.entrySet()) {
            String communityName = entry.getKey().replace("_", " ");
            Double serviceChargeRate = entry.getValue();
            Double coolingRate = COMMUNITY_COOLING_RATES.getOrDefault(entry.getKey(), 0.0);

            // For different property types
            for (String propertyType : Arrays.asList("APARTMENT", "VILLA", "TOWNHOUSE")) {
                // For different property sizes
                for (Double propertySize : Arrays.asList(800.0, 1500.0, 2500.0, 3500.0)) {
                    ServiceChargeEstimate estimate = new ServiceChargeEstimate();
                    estimate.setProperty(null); // No specific property
                    estimate.setCommunityName(communityName);
                    estimate.setPropertyType(propertyType);
                    estimate.setPropertySize(propertySize);
                    estimate.setServiceChargeRatePerSqFt(serviceChargeRate);
                    estimate.setEstimateYear(LocalDate.now().getYear());
                    estimate.setEstimateDate(LocalDate.now());
                    estimate.setIsPreFilled(true);

                    // Calculate annual service charge
                    double annualServiceCharge = propertySize * serviceChargeRate;
                    estimate.setAnnualServiceCharge(annualServiceCharge);

                    // Calculate cooling charges if applicable
                    double coolingCharges = propertySize * coolingRate;
                    estimate.setCoolingCharges(coolingCharges);

                    // Calculate other fees
                    calculateAdditionalFees(estimate, propertySize, propertyType);

                    // Calculate total annual charges
                    double totalAnnualCharges = annualServiceCharge + coolingCharges +
                            estimate.getBuildingMaintenanceFee() + estimate.getSecurityFee() +
                            estimate.getCleaningFee() + estimate.getParkingFee() +
                            estimate.getGymAndPoolFee() + estimate.getMiscCharges();
                    estimate.setTotalAnnualCharges(totalAnnualCharges);

                    // Calculate monthly charges
                    double monthlyCharges = totalAnnualCharges / 12;
                    estimate.setMonthlyCharges(monthlyCharges);

                    serviceChargeEstimateRepository.save(estimate);
                }
            }
        }
    }

    /**
     * Delete a service charge estimate.
     *
     * @param id the service charge estimate ID
     */
    public void deleteServiceChargeEstimate(Long id) {
        serviceChargeEstimateRepository.deleteById(id);
    }

    /**
     * Delete all service charge estimates for a property.
     *
     * @param propertyId the property ID
     */
    public void deleteAllServiceChargeEstimatesForProperty(Long propertyId) {
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new RuntimeException("Property not found with id " + propertyId));
        serviceChargeEstimateRepository.deleteByProperty(property);
    }
}
