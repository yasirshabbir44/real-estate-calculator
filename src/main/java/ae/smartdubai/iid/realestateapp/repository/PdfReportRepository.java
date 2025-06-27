package ae.smartdubai.iid.realestateapp.repository;

import ae.smartdubai.iid.realestateapp.model.PdfReport;
import ae.smartdubai.iid.realestateapp.model.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Repository for PdfReport entity.
 */
@Repository
public interface PdfReportRepository extends JpaRepository<PdfReport, Long> {

    /**
     * Find PDF reports by property.
     *
     * @param property the property to search for
     * @return list of PDF reports for the specified property
     */
    List<PdfReport> findByProperty(Property property);

    /**
     * Find the latest PDF report for a property.
     *
     * @param property the property to search for
     * @return the latest PDF report for the specified property
     */
    Optional<PdfReport> findFirstByPropertyOrderByGenerationDateDesc(Property property);

    /**
     * Find PDF reports by report type.
     *
     * @param reportType the report type to search for
     * @return list of PDF reports of the specified type
     */
    List<PdfReport> findByReportType(String reportType);

    /**
     * Find PDF reports by generation date range.
     *
     * @param startDate the start date
     * @param endDate the end date
     * @return list of PDF reports within the specified generation date range
     */
    List<PdfReport> findByGenerationDateBetween(LocalDate startDate, LocalDate endDate);

    /**
     * Find PDF reports by shared email.
     *
     * @param sharedToEmail the email address to search for
     * @return list of PDF reports shared to the specified email address
     */
    List<PdfReport> findBySharedToEmail(String sharedToEmail);

    /**
     * Find PDF reports by shared date range.
     *
     * @param startDate the start date
     * @param endDate the end date
     * @return list of PDF reports within the specified shared date range
     */
    List<PdfReport> findBySharedDateBetween(LocalDate startDate, LocalDate endDate);

    /**
     * Find PDF reports that include cost breakdown.
     *
     * @return list of PDF reports that include cost breakdown
     */
    List<PdfReport> findByIncludesCostBreakdownTrue();

    /**
     * Find PDF reports that include loan calculation.
     *
     * @return list of PDF reports that include loan calculation
     */
    List<PdfReport> findByIncludesLoanCalculationTrue();

    /**
     * Find PDF reports that include property comparison.
     *
     * @return list of PDF reports that include property comparison
     */
    List<PdfReport> findByIncludesPropertyComparisonTrue();

    /**
     * Find PDF reports that include document checklist.
     *
     * @return list of PDF reports that include document checklist
     */
    List<PdfReport> findByIncludesDocumentChecklistTrue();

    /**
     * Find PDF reports that include service charge estimate.
     *
     * @return list of PDF reports that include service charge estimate
     */
    List<PdfReport> findByIncludesServiceChargeEstimateTrue();

    /**
     * Delete all PDF reports for a property.
     *
     * @param property the property to delete PDF reports for
     */
    void deleteByProperty(Property property);
}