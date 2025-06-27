package ae.smartdubai.iid.realestateapp.controller;

import ae.smartdubai.iid.realestateapp.model.ServiceChargeEstimate;
import ae.smartdubai.iid.realestateapp.service.ServiceChargeEstimateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing service charge estimates.
 */
@RestController
@RequestMapping("/api/service-charge-estimates")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ServiceChargeEstimateController {

    private final ServiceChargeEstimateService serviceChargeEstimateService;

    /**
     * GET /api/service-charge-estimates : Get all service charge estimates.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of service charge estimates in body
     */
    @GetMapping
    public ResponseEntity<List<ServiceChargeEstimate>> getAllServiceChargeEstimates() {
        List<ServiceChargeEstimate> serviceChargeEstimates = serviceChargeEstimateService.getAllServiceChargeEstimates();
        return ResponseEntity.ok(serviceChargeEstimates);
    }

    /**
     * GET /api/service-charge-estimates/:id : Get the "id" service charge estimate.
     *
     * @param id the id of the service charge estimate to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the service charge estimate, or with status 404 (Not Found)
     */
    @GetMapping("/{id}")
    public ResponseEntity<ServiceChargeEstimate> getServiceChargeEstimate(@PathVariable Long id) {
        return serviceChargeEstimateService.getServiceChargeEstimateById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * GET /api/service-charge-estimates/property/:propertyId : Get service charge estimates by property.
     *
     * @param propertyId the id of the property
     * @return the ResponseEntity with status 200 (OK) and the list of service charge estimates in body
     */
    @GetMapping("/property/{propertyId}")
    public ResponseEntity<List<ServiceChargeEstimate>> getServiceChargeEstimatesByProperty(@PathVariable Long propertyId) {
        List<ServiceChargeEstimate> serviceChargeEstimates = serviceChargeEstimateService.getServiceChargeEstimatesByProperty(propertyId);
        return ResponseEntity.ok(serviceChargeEstimates);
    }

    /**
     * GET /api/service-charge-estimates/community/:communityName : Get pre-filled service charge estimates by community.
     *
     * @param communityName the community name
     * @return the ResponseEntity with status 200 (OK) and the list of pre-filled service charge estimates in body
     */
    @GetMapping("/community/{communityName}")
    public ResponseEntity<List<ServiceChargeEstimate>> getPreFilledEstimatesByCommunity(@PathVariable String communityName) {
        List<ServiceChargeEstimate> serviceChargeEstimates = serviceChargeEstimateService.getPreFilledEstimatesByCommunity(communityName);
        return ResponseEntity.ok(serviceChargeEstimates);
    }

    /**
     * POST /api/service-charge-estimates/calculate : Calculate a service charge estimate for a property.
     *
     * @param propertyId the property ID
     * @param communityName the community name
     * @param propertyType the property type
     * @param propertySize the property size in square feet
     * @return the ResponseEntity with status 200 (OK) and with body the calculated service charge estimate
     */
    @PostMapping("/calculate")
    public ResponseEntity<ServiceChargeEstimate> calculateServiceCharges(
            @RequestParam Long propertyId,
            @RequestParam String communityName,
            @RequestParam String propertyType,
            @RequestParam Double propertySize) {
        
        ServiceChargeEstimate serviceChargeEstimate = serviceChargeEstimateService.calculateServiceCharges(
                propertyId, communityName, propertyType, propertySize);
        
        return ResponseEntity.ok(serviceChargeEstimate);
    }

    /**
     * POST /api/service-charge-estimates/create-pre-filled : Create pre-filled service charge estimates for all communities.
     * This endpoint is for administrative use to initialize the database with pre-filled estimates.
     *
     * @return the ResponseEntity with status 200 (OK)
     */
    @PostMapping("/create-pre-filled")
    public ResponseEntity<Void> createPreFilledEstimates() {
        serviceChargeEstimateService.createPreFilledEstimates();
        return ResponseEntity.ok().build();
    }

    /**
     * DELETE /api/service-charge-estimates/:id : Delete the "id" service charge estimate.
     *
     * @param id the id of the service charge estimate to delete
     * @return the ResponseEntity with status 204 (NO_CONTENT)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteServiceChargeEstimate(@PathVariable Long id) {
        serviceChargeEstimateService.deleteServiceChargeEstimate(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * DELETE /api/service-charge-estimates/property/:propertyId : Delete all service charge estimates for a property.
     *
     * @param propertyId the id of the property
     * @return the ResponseEntity with status 204 (NO_CONTENT)
     */
    @DeleteMapping("/property/{propertyId}")
    public ResponseEntity<Void> deleteAllServiceChargeEstimatesForProperty(@PathVariable Long propertyId) {
        serviceChargeEstimateService.deleteAllServiceChargeEstimatesForProperty(propertyId);
        return ResponseEntity.noContent().build();
    }
}