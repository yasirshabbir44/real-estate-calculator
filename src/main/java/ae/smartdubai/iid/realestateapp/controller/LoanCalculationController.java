package ae.smartdubai.iid.realestateapp.controller;

import ae.smartdubai.iid.realestateapp.model.LoanCalculation;
import ae.smartdubai.iid.realestateapp.service.LoanCalculationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing loan calculations.
 */
@RestController
@RequestMapping("/api/loan-calculations")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class LoanCalculationController {

    private final LoanCalculationService loanCalculationService;

    /**
     * GET /api/loan-calculations : Get all loan calculations.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of loan calculations in body
     */
    @GetMapping
    public ResponseEntity<List<LoanCalculation>> getAllLoanCalculations() {
        List<LoanCalculation> loanCalculations = loanCalculationService.getAllLoanCalculations();
        return ResponseEntity.ok(loanCalculations);
    }

    /**
     * GET /api/loan-calculations/:id : Get the "id" loan calculation.
     *
     * @param id the id of the loan calculation to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the loan calculation, or with status 404 (Not Found)
     */
    @GetMapping("/{id}")
    public ResponseEntity<LoanCalculation> getLoanCalculation(@PathVariable Long id) {
        return loanCalculationService.getLoanCalculationById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * GET /api/loan-calculations/property/:propertyId : Get loan calculations by property.
     *
     * @param propertyId the id of the property
     * @return the ResponseEntity with status 200 (OK) and the list of loan calculations in body
     */
    @GetMapping("/property/{propertyId}")
    public ResponseEntity<List<LoanCalculation>> getLoanCalculationsByProperty(@PathVariable Long propertyId) {
        List<LoanCalculation> loanCalculations = loanCalculationService.getLoanCalculationsByProperty(propertyId);
        return ResponseEntity.ok(loanCalculations);
    }

    /**
     * POST /api/loan-calculations/calculate : Calculate a loan for a property.
     *
     * @param propertyId the id of the property
     * @param downPayment the down payment amount
     * @param interestRate the annual interest rate (in percentage)
     * @param tenureYears the loan tenure in years
     * @return the ResponseEntity with status 200 (OK) and with body the calculated loan calculation
     */
    @PostMapping("/calculate")
    public ResponseEntity<LoanCalculation> calculateLoan(
            @RequestParam Long propertyId,
            @RequestParam Double downPayment,
            @RequestParam Double interestRate,
            @RequestParam Integer tenureYears) {
        
        LoanCalculation loanCalculation = loanCalculationService.calculateLoan(
                propertyId, downPayment, interestRate, tenureYears);
        
        return ResponseEntity.ok(loanCalculation);
    }

    /**
     * DELETE /api/loan-calculations/:id : Delete the "id" loan calculation.
     *
     * @param id the id of the loan calculation to delete
     * @return the ResponseEntity with status 204 (NO_CONTENT)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLoanCalculation(@PathVariable Long id) {
        loanCalculationService.deleteLoanCalculation(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * DELETE /api/loan-calculations/property/:propertyId : Delete all loan calculations for a property.
     *
     * @param propertyId the id of the property
     * @return the ResponseEntity with status 204 (NO_CONTENT)
     */
    @DeleteMapping("/property/{propertyId}")
    public ResponseEntity<Void> deleteAllLoanCalculationsForProperty(@PathVariable Long propertyId) {
        loanCalculationService.deleteAllLoanCalculationsForProperty(propertyId);
        return ResponseEntity.noContent().build();
    }
}