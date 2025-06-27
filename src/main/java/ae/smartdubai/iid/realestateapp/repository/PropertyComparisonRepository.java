package ae.smartdubai.iid.realestateapp.repository;

import ae.smartdubai.iid.realestateapp.model.Property;
import ae.smartdubai.iid.realestateapp.model.PropertyComparison;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Repository for PropertyComparison entity.
 */
@Repository
public interface PropertyComparisonRepository extends JpaRepository<PropertyComparison, Long> {

    /**
     * Find property comparisons by first property.
     *
     * @param property1 the first property to search for
     * @return list of property comparisons for the specified first property
     */
    List<PropertyComparison> findByProperty1(Property property1);

    /**
     * Find property comparisons by second property.
     *
     * @param property2 the second property to search for
     * @return list of property comparisons for the specified second property
     */
    List<PropertyComparison> findByProperty2(Property property2);

    /**
     * Find property comparisons by both properties.
     *
     * @param property1 the first property to search for
     * @param property2 the second property to search for
     * @return list of property comparisons for the specified properties
     */
    List<PropertyComparison> findByProperty1AndProperty2(Property property1, Property property2);

    /**
     * Find the latest property comparison for a property.
     *
     * @param property1 the property to search for
     * @return the latest property comparison for the specified property
     */
    Optional<PropertyComparison> findFirstByProperty1OrderByComparisonDateDesc(Property property1);

    /**
     * Find property comparisons by comparison date range.
     *
     * @param startDate the start date
     * @param endDate the end date
     * @return list of property comparisons within the specified date range
     */
    List<PropertyComparison> findByComparisonDateBetween(LocalDate startDate, LocalDate endDate);

    /**
     * Find rent vs buy comparisons.
     *
     * @return list of rent vs buy comparisons
     */
    List<PropertyComparison> findByIsRentVsBuyTrue();

    /**
     * Find property vs property comparisons.
     *
     * @return list of property vs property comparisons
     */
    List<PropertyComparison> findByIsRentVsBuyFalse();

    /**
     * Delete all property comparisons for a property.
     *
     * @param property1 the first property to delete property comparisons for
     * @param property2 the second property to delete property comparisons for
     */
    void deleteByProperty1OrProperty2(Property property1, Property property2);
}
