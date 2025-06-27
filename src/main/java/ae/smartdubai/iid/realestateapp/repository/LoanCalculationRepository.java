package ae.smartdubai.iid.realestateapp.repository;

import ae.smartdubai.iid.realestateapp.model.LoanCalculation;
import ae.smartdubai.iid.realestateapp.model.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Repository for LoanCalculation entity.
 */
@Repository
public interface LoanCalculationRepository extends JpaRepository<LoanCalculation, Long> {

    /**
     * Find loan calculations by property.
     *
     * @param property the property to search for
     * @return list of loan calculations for the specified property
     */
    List<LoanCalculation> findByProperty(Property property);

    /**
     * Find the latest loan calculation for a property.
     *
     * @param property the property to search for
     * @return the latest loan calculation for the specified property
     */
    Optional<LoanCalculation> findFirstByPropertyOrderByCalculationDateDesc(Property property);

    /**
     * Find loan calculations by calculation date range.
     *
     * @param startDate the start date
     * @param endDate the end date
     * @return list of loan calculations within the specified date range
     */
    List<LoanCalculation> findByCalculationDateBetween(LocalDate startDate, LocalDate endDate);

    /**
     * Find loan calculations by loan amount range.
     *
     * @param minLoanAmount the minimum loan amount
     * @param maxLoanAmount the maximum loan amount
     * @return list of loan calculations within the specified loan amount range
     */
    List<LoanCalculation> findByLoanAmountBetween(Double minLoanAmount, Double maxLoanAmount);

    /**
     * Find loan calculations by interest rate range.
     *
     * @param minInterestRate the minimum interest rate
     * @param maxInterestRate the maximum interest rate
     * @return list of loan calculations within the specified interest rate range
     */
    List<LoanCalculation> findByInterestRateBetween(Double minInterestRate, Double maxInterestRate);

    /**
     * Find loan calculations by tenure years.
     *
     * @param tenureYears the tenure years to search for
     * @return list of loan calculations with the specified tenure years
     */
    List<LoanCalculation> findByTenureYears(Integer tenureYears);

    /**
     * Delete all loan calculations for a property.
     *
     * @param property the property to delete loan calculations for
     */
    void deleteByProperty(Property property);
}