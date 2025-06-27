package ae.smartdubai.iid.realestateapp.repository;

import ae.smartdubai.iid.realestateapp.model.Property;
import ae.smartdubai.iid.realestateapp.model.ServiceChargeEstimate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Repository for ServiceChargeEstimate entity.
 */
@Repository
public interface ServiceChargeEstimateRepository extends JpaRepository<ServiceChargeEstimate, Long> {

    /**
     * Find service charge estimates by property.
     *
     * @param property the property to search for
     * @return list of service charge estimates for the specified property
     */
    List<ServiceChargeEstimate> findByProperty(Property property);

    /**
     * Find the latest service charge estimate for a property.
     *
     * @param property the property to search for
     * @return the latest service charge estimate for the specified property
     */
    Optional<ServiceChargeEstimate> findFirstByPropertyOrderByEstimateDateDesc(Property property);

    /**
     * Find service charge estimates by community name.
     *
     * @param communityName the community name to search for
     * @return list of service charge estimates for the specified community
     */
    List<ServiceChargeEstimate> findByCommunityNameIgnoreCase(String communityName);

    /**
     * Find service charge estimates by property type.
     *
     * @param propertyType the property type to search for
     * @return list of service charge estimates for the specified property type
     */
    List<ServiceChargeEstimate> findByPropertyTypeIgnoreCase(String propertyType);

    /**
     * Find service charge estimates by property size range.
     *
     * @param minSize the minimum property size
     * @param maxSize the maximum property size
     * @return list of service charge estimates within the specified property size range
     */
    List<ServiceChargeEstimate> findByPropertySizeBetween(Double minSize, Double maxSize);

    /**
     * Find service charge estimates by annual service charge range.
     *
     * @param minCharge the minimum annual service charge
     * @param maxCharge the maximum annual service charge
     * @return list of service charge estimates within the specified annual service charge range
     */
    List<ServiceChargeEstimate> findByAnnualServiceChargeBetween(Double minCharge, Double maxCharge);

    /**
     * Find service charge estimates by estimate date range.
     *
     * @param startDate the start date
     * @param endDate the end date
     * @return list of service charge estimates within the specified date range
     */
    List<ServiceChargeEstimate> findByEstimateDateBetween(LocalDate startDate, LocalDate endDate);

    /**
     * Find pre-filled service charge estimates.
     *
     * @return list of pre-filled service charge estimates
     */
    List<ServiceChargeEstimate> findByIsPreFilledTrue();

    /**
     * Find pre-filled service charge estimates by community name.
     *
     * @param communityName the community name to search for
     * @return list of pre-filled service charge estimates for the specified community
     */
    List<ServiceChargeEstimate> findByIsPreFilledTrueAndCommunityNameIgnoreCase(String communityName);

    /**
     * Delete all service charge estimates for a property.
     *
     * @param property the property to delete service charge estimates for
     */
    void deleteByProperty(Property property);
}