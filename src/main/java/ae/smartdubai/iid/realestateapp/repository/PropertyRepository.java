package ae.smartdubai.iid.realestateapp.repository;

import ae.smartdubai.iid.realestateapp.model.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for Property entity.
 */
@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {

    /**
     * Find properties by location.
     *
     * @param location the location to search for
     * @return list of properties in the specified location
     */
    List<Property> findByLocationContainingIgnoreCase(String location);

    /**
     * Find properties by price range.
     *
     * @param minPrice the minimum price
     * @param maxPrice the maximum price
     * @return list of properties within the specified price range
     */
    List<Property> findByPriceBetween(Double minPrice, Double maxPrice);

    /**
     * Find properties by property type.
     *
     * @param propertyType the property type to search for
     * @return list of properties of the specified type
     */
    List<Property> findByPropertyTypeIgnoreCase(String propertyType);

    /**
     * Find properties by community name.
     *
     * @param communityName the community name to search for
     * @return list of properties in the specified community
     */
    List<Property> findByCommunityNameContainingIgnoreCase(String communityName);

    /**
     * Find properties by number of bedrooms.
     *
     * @param bedrooms the number of bedrooms to search for
     * @return list of properties with the specified number of bedrooms
     */
    List<Property> findByBedrooms(Integer bedrooms);

    /**
     * Find properties by size range.
     *
     * @param minSize the minimum size in square feet
     * @param maxSize the maximum size in square feet
     * @return list of properties within the specified size range
     */
    List<Property> findBySizeBetween(Double minSize, Double maxSize);
}