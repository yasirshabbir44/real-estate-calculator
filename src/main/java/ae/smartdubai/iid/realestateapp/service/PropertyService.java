package ae.smartdubai.iid.realestateapp.service;

import ae.smartdubai.iid.realestateapp.model.Property;
import ae.smartdubai.iid.realestateapp.repository.PropertyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service for managing properties.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class PropertyService {

    private final PropertyRepository propertyRepository;

    /**
     * Get all properties.
     *
     * @return list of all properties
     */
    @Transactional(readOnly = true)
    public List<Property> getAllProperties() {
        return propertyRepository.findAll();
    }

    /**
     * Get property by ID.
     *
     * @param id the property ID
     * @return the property if found
     */
    @Transactional(readOnly = true)
    public Optional<Property> getPropertyById(Long id) {
        return propertyRepository.findById(id);
    }

    /**
     * Create a new property.
     *
     * @param property the property to create
     * @return the created property
     */
    public Property createProperty(Property property) {
        return propertyRepository.save(property);
    }

    /**
     * Update an existing property.
     *
     * @param id the property ID
     * @param propertyDetails the updated property details
     * @return the updated property
     * @throws RuntimeException if the property is not found
     */
    public Property updateProperty(Long id, Property propertyDetails) {
        return propertyRepository.findById(id)
                .map(existingProperty -> {
                    if (propertyDetails.getName() != null) {
                        existingProperty.setName(propertyDetails.getName());
                    }
                    if (propertyDetails.getLocation() != null) {
                        existingProperty.setLocation(propertyDetails.getLocation());
                    }
                    if (propertyDetails.getPrice() != null) {
                        existingProperty.setPrice(propertyDetails.getPrice());
                    }
                    if (propertyDetails.getSize() != null) {
                        existingProperty.setSize(propertyDetails.getSize());
                    }
                    if (propertyDetails.getBedrooms() != null) {
                        existingProperty.setBedrooms(propertyDetails.getBedrooms());
                    }
                    if (propertyDetails.getBathrooms() != null) {
                        existingProperty.setBathrooms(propertyDetails.getBathrooms());
                    }
                    if (propertyDetails.getPropertyType() != null) {
                        existingProperty.setPropertyType(propertyDetails.getPropertyType());
                    }
                    if (propertyDetails.getCommunityName() != null) {
                        existingProperty.setCommunityName(propertyDetails.getCommunityName());
                    }
                    if (propertyDetails.getIsFurnished() != null) {
                        existingProperty.setIsFurnished(propertyDetails.getIsFurnished());
                    }
                    if (propertyDetails.getYearBuilt() != null) {
                        existingProperty.setYearBuilt(propertyDetails.getYearBuilt());
                    }
                    return propertyRepository.save(existingProperty);
                })
                .orElseThrow(() -> new RuntimeException("Property not found with id " + id));
    }

    /**
     * Delete a property.
     *
     * @param id the property ID
     */
    public void deleteProperty(Long id) {
        propertyRepository.deleteById(id);
    }

    /**
     * Find properties by location.
     *
     * @param location the location to search for
     * @return list of properties in the specified location
     */
    @Transactional(readOnly = true)
    public List<Property> findPropertiesByLocation(String location) {
        return propertyRepository.findByLocationContainingIgnoreCase(location);
    }

    /**
     * Find properties by price range.
     *
     * @param minPrice the minimum price
     * @param maxPrice the maximum price
     * @return list of properties within the specified price range
     */
    @Transactional(readOnly = true)
    public List<Property> findPropertiesByPriceRange(Double minPrice, Double maxPrice) {
        return propertyRepository.findByPriceBetween(minPrice, maxPrice);
    }

    /**
     * Find properties by property type.
     *
     * @param propertyType the property type to search for
     * @return list of properties of the specified type
     */
    @Transactional(readOnly = true)
    public List<Property> findPropertiesByType(String propertyType) {
        return propertyRepository.findByPropertyTypeIgnoreCase(propertyType);
    }

    /**
     * Find properties by community name.
     *
     * @param communityName the community name to search for
     * @return list of properties in the specified community
     */
    @Transactional(readOnly = true)
    public List<Property> findPropertiesByCommunity(String communityName) {
        return propertyRepository.findByCommunityNameContainingIgnoreCase(communityName);
    }
}