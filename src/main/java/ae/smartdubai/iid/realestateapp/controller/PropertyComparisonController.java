package ae.smartdubai.iid.realestateapp.controller;

import ae.smartdubai.iid.realestateapp.model.PropertyComparison;
import ae.smartdubai.iid.realestateapp.service.PropertyComparisonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing property comparisons.
 */
@RestController
@RequestMapping("/api/property-comparisons")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PropertyComparisonController {

    private final PropertyComparisonService propertyComparisonService;

    /**
     * GET /api/property-comparisons : Get all property comparisons.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of property comparisons in body
     */
    @GetMapping
    public ResponseEntity<List<PropertyComparison>> getAllPropertyComparisons() {
        List<PropertyComparison> propertyComparisons = propertyComparisonService.getAllPropertyComparisons();
        return ResponseEntity.ok(propertyComparisons);
    }

    /**
     * GET /api/property-comparisons/:id : Get the "id" property comparison.
     *
     * @param id the id of the property comparison to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the property comparison, or with status 404 (Not Found)
     */
    @GetMapping("/{id}")
    public ResponseEntity<PropertyComparison> getPropertyComparison(@PathVariable Long id) {
        return propertyComparisonService.getPropertyComparisonById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * GET /api/property-comparisons/property/:propertyId : Get property comparisons by property.
     *
     * @param propertyId the id of the property
     * @return the ResponseEntity with status 200 (OK) and the list of property comparisons in body
     */
    @GetMapping("/property/{propertyId}")
    public ResponseEntity<List<PropertyComparison>> getPropertyComparisonsByProperty(@PathVariable Long propertyId) {
        List<PropertyComparison> propertyComparisons = propertyComparisonService.getPropertyComparisonsByProperty(propertyId);
        return ResponseEntity.ok(propertyComparisons);
    }

    /**
     * POST /api/property-comparisons/compare-properties : Compare two properties.
     *
     * @param property1Id the first property ID
     * @param property2Id the second property ID
     * @param propertyAppreciationRate the expected annual property appreciation rate (in percentage)
     * @param holdingPeriodYears the expected holding period in years
     * @return the ResponseEntity with status 200 (OK) and with body the property comparison
     */
    @PostMapping("/compare-properties")
    public ResponseEntity<PropertyComparison> compareProperties(
            @RequestParam Long property1Id,
            @RequestParam Long property2Id,
            @RequestParam Double propertyAppreciationRate,
            @RequestParam Integer holdingPeriodYears) {
        
        PropertyComparison comparison = propertyComparisonService.compareProperties(
                property1Id, property2Id, propertyAppreciationRate, holdingPeriodYears);
        
        return ResponseEntity.ok(comparison);
    }

    /**
     * POST /api/property-comparisons/compare-rent-vs-buy : Compare renting vs buying a property.
     *
     * @param propertyId the property ID (for buying)
     * @param monthlyRent the monthly rent amount
     * @param annualRentIncrease the annual rent increase percentage
     * @param investmentReturnRate the investment return rate for opportunity cost calculation
     * @param propertyAppreciationRate the expected annual property appreciation rate
     * @param holdingPeriodYears the expected holding period in years
     * @return the ResponseEntity with status 200 (OK) and with body the property comparison
     */
    @PostMapping("/compare-rent-vs-buy")
    public ResponseEntity<PropertyComparison> compareRentVsBuy(
            @RequestParam Long propertyId,
            @RequestParam Double monthlyRent,
            @RequestParam Double annualRentIncrease,
            @RequestParam Double investmentReturnRate,
            @RequestParam Double propertyAppreciationRate,
            @RequestParam Integer holdingPeriodYears) {
        
        PropertyComparison comparison = propertyComparisonService.compareRentVsBuy(
                propertyId, monthlyRent, annualRentIncrease, investmentReturnRate, 
                propertyAppreciationRate, holdingPeriodYears);
        
        return ResponseEntity.ok(comparison);
    }

    /**
     * DELETE /api/property-comparisons/:id : Delete the "id" property comparison.
     *
     * @param id the id of the property comparison to delete
     * @return the ResponseEntity with status 204 (NO_CONTENT)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePropertyComparison(@PathVariable Long id) {
        propertyComparisonService.deletePropertyComparison(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * DELETE /api/property-comparisons/property/:propertyId : Delete all property comparisons for a property.
     *
     * @param propertyId the id of the property
     * @return the ResponseEntity with status 204 (NO_CONTENT)
     */
    @DeleteMapping("/property/{propertyId}")
    public ResponseEntity<Void> deleteAllPropertyComparisonsForProperty(@PathVariable Long propertyId) {
        propertyComparisonService.deleteAllPropertyComparisonsForProperty(propertyId);
        return ResponseEntity.noContent().build();
    }
}