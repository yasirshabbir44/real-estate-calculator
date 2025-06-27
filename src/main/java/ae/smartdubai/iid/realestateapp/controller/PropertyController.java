package ae.smartdubai.iid.realestateapp.controller;

import ae.smartdubai.iid.realestateapp.model.Property;
import ae.smartdubai.iid.realestateapp.service.PropertyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
 * REST controller for managing properties.
 */
@RestController
@RequestMapping("/api/properties")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PropertyController {

    private final PropertyService propertyService;

    /**
     * GET /api/properties : Get all properties.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of properties in body
     */
    @GetMapping
    public ResponseEntity<List<Property>> getAllProperties() {
        List<Property> properties = propertyService.getAllProperties();
        return ResponseEntity.ok(properties);
    }

    /**
     * GET /api/properties/:id : Get the "id" property.
     *
     * @param id the id of the property to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the property, or with status 404 (Not Found)
     */
    @GetMapping("/{id}")
    public ResponseEntity<Property> getProperty(@PathVariable Long id) {
        return propertyService.getPropertyById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * POST /api/properties : Create a new property.
     *
     * @param property the property to create
     * @return the ResponseEntity with status 201 (Created) and with body the new property
     */
    @PostMapping
    public ResponseEntity<Property> createProperty(@Valid @RequestBody Property property) {
        Property result = propertyService.createProperty(property);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    /**
     * PUT /api/properties/:id : Updates an existing property.
     *
     * @param id the id of the property to update
     * @param property the property to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated property
     */
    @PutMapping("/{id}")
    public ResponseEntity<Property> updateProperty(@PathVariable Long id, @Valid @RequestBody Property property) {
        Property result = propertyService.updateProperty(id, property);
        return ResponseEntity.ok(result);
    }

    /**
     * DELETE /api/properties/:id : Delete the "id" property.
     *
     * @param id the id of the property to delete
     * @return the ResponseEntity with status 204 (NO_CONTENT)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProperty(@PathVariable Long id) {
        propertyService.deleteProperty(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * GET /api/properties/search/location : Search properties by location.
     *
     * @param location the location to search for
     * @return the ResponseEntity with status 200 (OK) and the list of properties in body
     */
    @GetMapping("/search/location")
    public ResponseEntity<List<Property>> searchPropertiesByLocation(@RequestParam String location) {
        List<Property> properties = propertyService.findPropertiesByLocation(location);
        return ResponseEntity.ok(properties);
    }

    /**
     * GET /api/properties/search/price : Search properties by price range.
     *
     * @param minPrice the minimum price
     * @param maxPrice the maximum price
     * @return the ResponseEntity with status 200 (OK) and the list of properties in body
     */
    @GetMapping("/search/price")
    public ResponseEntity<List<Property>> searchPropertiesByPriceRange(
            @RequestParam Double minPrice, @RequestParam Double maxPrice) {
        List<Property> properties = propertyService.findPropertiesByPriceRange(minPrice, maxPrice);
        return ResponseEntity.ok(properties);
    }

    /**
     * GET /api/properties/search/type : Search properties by type.
     *
     * @param propertyType the property type to search for
     * @return the ResponseEntity with status 200 (OK) and the list of properties in body
     */
    @GetMapping("/search/type")
    public ResponseEntity<List<Property>> searchPropertiesByType(@RequestParam String propertyType) {
        List<Property> properties = propertyService.findPropertiesByType(propertyType);
        return ResponseEntity.ok(properties);
    }

    /**
     * GET /api/properties/search/community : Search properties by community.
     *
     * @param communityName the community name to search for
     * @return the ResponseEntity with status 200 (OK) and the list of properties in body
     */
    @GetMapping("/search/community")
    public ResponseEntity<List<Property>> searchPropertiesByCommunity(@RequestParam String communityName) {
        List<Property> properties = propertyService.findPropertiesByCommunity(communityName);
        return ResponseEntity.ok(properties);
    }
}