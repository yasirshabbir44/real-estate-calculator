package ae.smartdubai.iid.realestateapp.controller;

import ae.smartdubai.iid.realestateapp.model.RentVsBuyAnalysis;
import ae.smartdubai.iid.realestateapp.service.RentVsBuyAnalysisService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing rent vs buy analyses.
 */
@RestController
@RequestMapping("/api/rent-vs-buy-analyses")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class RentVsBuyAnalysisController {

    private final RentVsBuyAnalysisService rentVsBuyAnalysisService;

    /**
     * GET /api/rent-vs-buy-analyses : Get all rent vs buy analyses.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of rent vs buy analyses in body
     */
    @GetMapping
    public ResponseEntity<List<RentVsBuyAnalysis>> getAllRentVsBuyAnalyses() {
        List<RentVsBuyAnalysis> analyses = rentVsBuyAnalysisService.getAllRentVsBuyAnalyses();
        return ResponseEntity.ok(analyses);
    }

    /**
     * GET /api/rent-vs-buy-analyses/:id : Get the "id" rent vs buy analysis.
     *
     * @param id the id of the rent vs buy analysis to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the rent vs buy analysis, or with status 404 (Not Found)
     */
    @GetMapping("/{id}")
    public ResponseEntity<RentVsBuyAnalysis> getRentVsBuyAnalysis(@PathVariable Long id) {
        return rentVsBuyAnalysisService.getRentVsBuyAnalysisById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * GET /api/rent-vs-buy-analyses/property/:propertyId : Get rent vs buy analyses by property.
     *
     * @param propertyId the id of the property
     * @return the ResponseEntity with status 200 (OK) and the list of rent vs buy analyses in body
     */
    @GetMapping("/property/{propertyId}")
    public ResponseEntity<List<RentVsBuyAnalysis>> getRentVsBuyAnalysesByProperty(@PathVariable Long propertyId) {
        List<RentVsBuyAnalysis> analyses = rentVsBuyAnalysisService.getRentVsBuyAnalysesByProperty(propertyId);
        return ResponseEntity.ok(analyses);
    }

    /**
     * POST /api/rent-vs-buy-analyses/calculate : Calculate a rent vs buy analysis for a property.
     *
     * @param propertyId the id of the property
     * @param downPayment the down payment amount
     * @param interestRate the annual interest rate (in percentage)
     * @param loanTenureYears the loan tenure in years
     * @param propertyAppreciationRate the annual property appreciation rate (in percentage)
     * @param annualMaintenanceCost the annual maintenance cost
     * @param annualPropertyTax the annual property tax
     * @param monthlyRent the monthly rent amount
     * @param annualRentIncreaseRate the annual rent increase rate (in percentage)
     * @param securityDeposit the security deposit amount
     * @param investmentReturnRate the annual investment return rate (in percentage)
     * @param analysisPeriodYears the analysis period in years
     * @return the ResponseEntity with status 200 (OK) and with body the calculated rent vs buy analysis
     */
    @PostMapping("/calculate")
    public ResponseEntity<RentVsBuyAnalysis> calculateRentVsBuy(
            @RequestParam Long propertyId,
            @RequestParam Double downPayment,
            @RequestParam Double interestRate,
            @RequestParam Integer loanTenureYears,
            @RequestParam Double propertyAppreciationRate,
            @RequestParam Double annualMaintenanceCost,
            @RequestParam Double annualPropertyTax,
            @RequestParam Double monthlyRent,
            @RequestParam Double annualRentIncreaseRate,
            @RequestParam Double securityDeposit,
            @RequestParam Double investmentReturnRate,
            @RequestParam Integer analysisPeriodYears) {
        
        RentVsBuyAnalysis analysis = rentVsBuyAnalysisService.calculateRentVsBuy(
                propertyId,
                downPayment,
                interestRate,
                loanTenureYears,
                propertyAppreciationRate,
                annualMaintenanceCost,
                annualPropertyTax,
                monthlyRent,
                annualRentIncreaseRate,
                securityDeposit,
                investmentReturnRate,
                analysisPeriodYears);
        
        return ResponseEntity.ok(analysis);
    }

    /**
     * DELETE /api/rent-vs-buy-analyses/:id : Delete the "id" rent vs buy analysis.
     *
     * @param id the id of the rent vs buy analysis to delete
     * @return the ResponseEntity with status 204 (NO_CONTENT)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRentVsBuyAnalysis(@PathVariable Long id) {
        rentVsBuyAnalysisService.deleteRentVsBuyAnalysis(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * DELETE /api/rent-vs-buy-analyses/property/:propertyId : Delete all rent vs buy analyses for a property.
     *
     * @param propertyId the id of the property
     * @return the ResponseEntity with status 204 (NO_CONTENT)
     */
    @DeleteMapping("/property/{propertyId}")
    public ResponseEntity<Void> deleteAllRentVsBuyAnalysesForProperty(@PathVariable Long propertyId) {
        rentVsBuyAnalysisService.deleteAllRentVsBuyAnalysesForProperty(propertyId);
        return ResponseEntity.noContent().build();
    }
}