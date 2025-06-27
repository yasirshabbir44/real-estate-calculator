package ae.smartdubai.iid.realestateapp.controller;

import ae.smartdubai.iid.realestateapp.model.PdfReport;
import ae.smartdubai.iid.realestateapp.service.PdfReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.List;

/**
 * REST controller for managing PDF reports.
 */
@RestController
@RequestMapping("/api/pdf-reports")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PdfReportController {

    private final PdfReportService pdfReportService;

    /**
     * GET /api/pdf-reports : Get all PDF reports.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of PDF reports in body
     */
    @GetMapping
    public ResponseEntity<List<PdfReport>> getAllPdfReports() {
        List<PdfReport> pdfReports = pdfReportService.getAllPdfReports();
        return ResponseEntity.ok(pdfReports);
    }

    /**
     * GET /api/pdf-reports/:id : Get the "id" PDF report.
     *
     * @param id the id of the PDF report to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the PDF report, or with status 404 (Not Found)
     */
    @GetMapping("/{id}")
    public ResponseEntity<PdfReport> getPdfReport(@PathVariable Long id) {
        return pdfReportService.getPdfReportById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * GET /api/pdf-reports/property/:propertyId : Get PDF reports by property.
     *
     * @param propertyId the id of the property
     * @return the ResponseEntity with status 200 (OK) and the list of PDF reports in body
     */
    @GetMapping("/property/{propertyId}")
    public ResponseEntity<List<PdfReport>> getPdfReportsByProperty(@PathVariable Long propertyId) {
        List<PdfReport> pdfReports = pdfReportService.getPdfReportsByProperty(propertyId);
        return ResponseEntity.ok(pdfReports);
    }

    /**
     * GET /api/pdf-reports/:id/download : Download the PDF file for the "id" PDF report.
     *
     * @param id the id of the PDF report
     * @return the ResponseEntity with status 200 (OK) and with body the PDF file, or with status 404 (Not Found)
     */
    @GetMapping("/{id}/download")
    public ResponseEntity<Resource> downloadPdfReport(@PathVariable Long id) {
        return pdfReportService.getPdfReportById(id)
                .map(pdfReport -> {
                    File file = new File(pdfReport.getFilePath());
                    Resource resource = new FileSystemResource(file);
                    
                    return ResponseEntity.ok()
                            .contentType(MediaType.APPLICATION_PDF)
                            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
                            .body(resource);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * POST /api/pdf-reports/generate : Generate a PDF report for a property with various calculations.
     *
     * @param propertyId the property ID
     * @param title the report title
     * @param includeCostBreakdown whether to include cost breakdown
     * @param costBreakdownId the cost breakdown ID (if including cost breakdown)
     * @param includeLoanCalculation whether to include loan calculation
     * @param loanCalculationId the loan calculation ID (if including loan calculation)
     * @param includePropertyComparison whether to include property comparison
     * @param propertyComparisonId the property comparison ID (if including property comparison)
     * @param includeDocumentChecklist whether to include document checklist
     * @param documentChecklistId the document checklist ID (if including document checklist)
     * @param includeServiceChargeEstimate whether to include service charge estimate
     * @param serviceChargeEstimateId the service charge estimate ID (if including service charge estimate)
     * @return the ResponseEntity with status 200 (OK) and with body the generated PDF report
     */
    @PostMapping("/generate")
    public ResponseEntity<PdfReport> generatePdfReport(
            @RequestParam Long propertyId,
            @RequestParam String title,
            @RequestParam(defaultValue = "false") Boolean includeCostBreakdown,
            @RequestParam(required = false) Long costBreakdownId,
            @RequestParam(defaultValue = "false") Boolean includeLoanCalculation,
            @RequestParam(required = false) Long loanCalculationId,
            @RequestParam(defaultValue = "false") Boolean includePropertyComparison,
            @RequestParam(required = false) Long propertyComparisonId,
            @RequestParam(defaultValue = "false") Boolean includeDocumentChecklist,
            @RequestParam(required = false) Long documentChecklistId,
            @RequestParam(defaultValue = "false") Boolean includeServiceChargeEstimate,
            @RequestParam(required = false) Long serviceChargeEstimateId) {
        
        PdfReport pdfReport = pdfReportService.generatePdfReport(
                propertyId, title, includeCostBreakdown, costBreakdownId,
                includeLoanCalculation, loanCalculationId, includePropertyComparison, propertyComparisonId,
                includeDocumentChecklist, documentChecklistId, includeServiceChargeEstimate, serviceChargeEstimateId);
        
        return ResponseEntity.ok(pdfReport);
    }

    /**
     * POST /api/pdf-reports/:id/share : Share a PDF report via email.
     *
     * @param id the id of the PDF report
     * @param email the email address to share with
     * @return the ResponseEntity with status 200 (OK) and with body the updated PDF report
     */
    @PostMapping("/{id}/share")
    public ResponseEntity<PdfReport> sharePdfReport(
            @PathVariable Long id,
            @RequestParam String email) {
        
        PdfReport pdfReport = pdfReportService.sharePdfReport(id, email);
        return ResponseEntity.ok(pdfReport);
    }

    /**
     * DELETE /api/pdf-reports/:id : Delete the "id" PDF report.
     *
     * @param id the id of the PDF report to delete
     * @return the ResponseEntity with status 204 (NO_CONTENT)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePdfReport(@PathVariable Long id) {
        pdfReportService.deletePdfReport(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * DELETE /api/pdf-reports/property/:propertyId : Delete all PDF reports for a property.
     *
     * @param propertyId the id of the property
     * @return the ResponseEntity with status 204 (NO_CONTENT)
     */
    @DeleteMapping("/property/{propertyId}")
    public ResponseEntity<Void> deleteAllPdfReportsForProperty(@PathVariable Long propertyId) {
        pdfReportService.deleteAllPdfReportsForProperty(propertyId);
        return ResponseEntity.noContent().build();
    }
}