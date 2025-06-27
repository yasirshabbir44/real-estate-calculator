package ae.smartdubai.iid.realestateapp.controller;

import ae.smartdubai.iid.realestateapp.model.DocumentChecklist;
import ae.smartdubai.iid.realestateapp.service.DocumentChecklistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing document checklists.
 */
@RestController
@RequestMapping("/api/document-checklists")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class DocumentChecklistController {

    private final DocumentChecklistService documentChecklistService;

    /**
     * GET /api/document-checklists : Get all document checklists.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of document checklists in body
     */
    @GetMapping
    public ResponseEntity<List<DocumentChecklist>> getAllDocumentChecklists() {
        List<DocumentChecklist> documentChecklists = documentChecklistService.getAllDocumentChecklists();
        return ResponseEntity.ok(documentChecklists);
    }

    /**
     * GET /api/document-checklists/:id : Get the "id" document checklist.
     *
     * @param id the id of the document checklist to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the document checklist, or with status 404 (Not Found)
     */
    @GetMapping("/{id}")
    public ResponseEntity<DocumentChecklist> getDocumentChecklist(@PathVariable Long id) {
        return documentChecklistService.getDocumentChecklistById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * GET /api/document-checklists/property/:propertyId : Get document checklists by property.
     *
     * @param propertyId the id of the property
     * @return the ResponseEntity with status 200 (OK) and the list of document checklists in body
     */
    @GetMapping("/property/{propertyId}")
    public ResponseEntity<List<DocumentChecklist>> getDocumentChecklistsByProperty(@PathVariable Long propertyId) {
        List<DocumentChecklist> documentChecklists = documentChecklistService.getDocumentChecklistsByProperty(propertyId);
        return ResponseEntity.ok(documentChecklists);
    }

    /**
     * POST /api/document-checklists/generate : Generate a document checklist for a property.
     *
     * @param propertyId the property ID
     * @param buyerType the type of buyer (SALARIED, SELF_EMPLOYED, INVESTOR, etc.)
     * @param selectedBank the selected bank for mortgage (if applicable)
     * @param nationality the nationality of the buyer
     * @param residenceStatus the residence status of the buyer (UAE_RESIDENT, NON_RESIDENT)
     * @param isMortgageRequired whether a mortgage is required
     * @param isOffPlan whether the property is off-plan
     * @param isReady whether the property is ready
     * @return the ResponseEntity with status 200 (OK) and with body the generated document checklist
     */
    @PostMapping("/generate")
    public ResponseEntity<DocumentChecklist> generateDocumentChecklist(
            @RequestParam Long propertyId,
            @RequestParam String buyerType,
            @RequestParam(required = false) String selectedBank,
            @RequestParam String nationality,
            @RequestParam String residenceStatus,
            @RequestParam Boolean isMortgageRequired,
            @RequestParam Boolean isOffPlan,
            @RequestParam Boolean isReady) {
        
        DocumentChecklist documentChecklist = documentChecklistService.generateDocumentChecklist(
                propertyId, buyerType, selectedBank, nationality, residenceStatus, 
                isMortgageRequired, isOffPlan, isReady);
        
        return ResponseEntity.ok(documentChecklist);
    }

    /**
     * DELETE /api/document-checklists/:id : Delete the "id" document checklist.
     *
     * @param id the id of the document checklist to delete
     * @return the ResponseEntity with status 204 (NO_CONTENT)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDocumentChecklist(@PathVariable Long id) {
        documentChecklistService.deleteDocumentChecklist(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * DELETE /api/document-checklists/property/:propertyId : Delete all document checklists for a property.
     *
     * @param propertyId the id of the property
     * @return the ResponseEntity with status 204 (NO_CONTENT)
     */
    @DeleteMapping("/property/{propertyId}")
    public ResponseEntity<Void> deleteAllDocumentChecklistsForProperty(@PathVariable Long propertyId) {
        documentChecklistService.deleteAllDocumentChecklistsForProperty(propertyId);
        return ResponseEntity.noContent().build();
    }
}