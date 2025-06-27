package ae.smartdubai.iid.realestateapp.controller;

import ae.smartdubai.iid.realestateapp.model.CostBreakdown;
import ae.smartdubai.iid.realestateapp.service.CostBreakdownService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing cost breakdowns.
 */
@RestController
@RequestMapping("/api/cost-breakdowns")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CostBreakdownController {

    private final CostBreakdownService costBreakdownService;

    /**
     * GET /api/cost-breakdowns : Get all cost breakdowns.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of cost breakdowns in body
     */
    @GetMapping
    public ResponseEntity<List<CostBreakdown>> getAllCostBreakdowns() {
        List<CostBreakdown> costBreakdowns = costBreakdownService.getAllCostBreakdowns();
        return ResponseEntity.ok(costBreakdowns);
    }

    /**
     * GET /api/cost-breakdowns/:id : Get the "id" cost breakdown.
     *
     * @param id the id of the cost breakdown to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the cost breakdown, or with status 404 (Not Found)
     */
    @GetMapping("/{id}")
    public ResponseEntity<CostBreakdown> getCostBreakdown(@PathVariable Long id) {
        return costBreakdownService.getCostBreakdownById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * GET /api/cost-breakdowns/property/:propertyId : Get cost breakdowns by property.
     *
     * @param propertyId the id of the property
     * @return the ResponseEntity with status 200 (OK) and the list of cost breakdowns in body
     */
    @GetMapping("/property/{propertyId}")
    public ResponseEntity<List<CostBreakdown>> getCostBreakdownsByProperty(@PathVariable Long propertyId) {
        List<CostBreakdown> costBreakdowns = costBreakdownService.getCostBreakdownsByProperty(propertyId);
        return ResponseEntity.ok(costBreakdowns);
    }

    /**
     * POST /api/cost-breakdowns/calculate : Calculate a cost breakdown for a property.
     *
     * @param propertyId the id of the property
     * @param mortgageAmount the mortgage amount (0 if no mortgage)
     * @param lifeInsuranceCost the life insurance cost (0 if not applicable)
     * @param maintenanceDeposit the maintenance deposit (0 if not applicable)
     * @param utilityConnectionFees the utility connection fees
     * @param movingCosts the moving costs
     * @return the ResponseEntity with status 200 (OK) and with body the calculated cost breakdown
     */
    @PostMapping("/calculate")
    public ResponseEntity<CostBreakdown> calculateCostBreakdown(
            @RequestParam Long propertyId,
            @RequestParam(defaultValue = "0") Double mortgageAmount,
            @RequestParam(defaultValue = "0") Double lifeInsuranceCost,
            @RequestParam(defaultValue = "0") Double maintenanceDeposit,
            @RequestParam(defaultValue = "0") Double utilityConnectionFees,
            @RequestParam(defaultValue = "0") Double movingCosts) {
        
        CostBreakdown costBreakdown = costBreakdownService.calculateCostBreakdown(
                propertyId, mortgageAmount, lifeInsuranceCost, maintenanceDeposit, utilityConnectionFees, movingCosts);
        
        return ResponseEntity.ok(costBreakdown);
    }

    /**
     * DELETE /api/cost-breakdowns/:id : Delete the "id" cost breakdown.
     *
     * @param id the id of the cost breakdown to delete
     * @return the ResponseEntity with status 204 (NO_CONTENT)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCostBreakdown(@PathVariable Long id) {
        costBreakdownService.deleteCostBreakdown(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * DELETE /api/cost-breakdowns/property/:propertyId : Delete all cost breakdowns for a property.
     *
     * @param propertyId the id of the property
     * @return the ResponseEntity with status 204 (NO_CONTENT)
     */
    @DeleteMapping("/property/{propertyId}")
    public ResponseEntity<Void> deleteAllCostBreakdownsForProperty(@PathVariable Long propertyId) {
        costBreakdownService.deleteAllCostBreakdownsForProperty(propertyId);
        return ResponseEntity.noContent().build();
    }
}