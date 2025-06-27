package ae.smartdubai.iid.realestateapp.repository;

import ae.smartdubai.iid.realestateapp.model.CostBreakdown;
import ae.smartdubai.iid.realestateapp.model.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Repository for CostBreakdown entity.
 */
@Repository
public interface CostBreakdownRepository extends JpaRepository<CostBreakdown, Long> {

    /**
     * Find cost breakdowns by property.
     *
     * @param property the property to search for
     * @return list of cost breakdowns for the specified property
     */
    List<CostBreakdown> findByProperty(Property property);

    /**
     * Find the latest cost breakdown for a property.
     *
     * @param property the property to search for
     * @return the latest cost breakdown for the specified property
     */
    Optional<CostBreakdown> findFirstByPropertyOrderByCalculationDateDesc(Property property);

    /**
     * Find cost breakdowns by calculation date range.
     *
     * @param startDate the start date
     * @param endDate the end date
     * @return list of cost breakdowns within the specified date range
     */
    List<CostBreakdown> findByCalculationDateBetween(LocalDate startDate, LocalDate endDate);

    /**
     * Find cost breakdowns by total cost range.
     *
     * @param minTotalCost the minimum total cost
     * @param maxTotalCost the maximum total cost
     * @return list of cost breakdowns within the specified total cost range
     */
    List<CostBreakdown> findByTotalCostBetween(Double minTotalCost, Double maxTotalCost);

    /**
     * Delete all cost breakdowns for a property.
     *
     * @param property the property to delete cost breakdowns for
     */
    void deleteByProperty(Property property);
}