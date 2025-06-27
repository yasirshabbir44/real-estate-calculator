package ae.smartdubai.iid.realestateapp.repository;

import ae.smartdubai.iid.realestateapp.model.DocumentChecklist;
import ae.smartdubai.iid.realestateapp.model.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Repository for DocumentChecklist entity.
 */
@Repository
public interface DocumentChecklistRepository extends JpaRepository<DocumentChecklist, Long> {

    /**
     * Find document checklists by property.
     *
     * @param property the property to search for
     * @return list of document checklists for the specified property
     */
    List<DocumentChecklist> findByProperty(Property property);

    /**
     * Find the latest document checklist for a property.
     *
     * @param property the property to search for
     * @return the latest document checklist for the specified property
     */
    Optional<DocumentChecklist> findFirstByPropertyOrderByCreationDateDesc(Property property);

    /**
     * Find document checklists by buyer type.
     *
     * @param buyerType the buyer type to search for
     * @return list of document checklists for the specified buyer type
     */
    List<DocumentChecklist> findByBuyerType(String buyerType);

    /**
     * Find document checklists by selected bank.
     *
     * @param selectedBank the selected bank to search for
     * @return list of document checklists for the specified selected bank
     */
    List<DocumentChecklist> findBySelectedBank(String selectedBank);

    /**
     * Find document checklists by nationality.
     *
     * @param nationality the nationality to search for
     * @return list of document checklists for the specified nationality
     */
    List<DocumentChecklist> findByNationality(String nationality);

    /**
     * Find document checklists by residence status.
     *
     * @param residenceStatus the residence status to search for
     * @return list of document checklists for the specified residence status
     */
    List<DocumentChecklist> findByResidenceStatus(String residenceStatus);

    /**
     * Find document checklists by creation date range.
     *
     * @param startDate the start date
     * @param endDate the end date
     * @return list of document checklists within the specified date range
     */
    List<DocumentChecklist> findByCreationDateBetween(LocalDate startDate, LocalDate endDate);

    /**
     * Find document checklists by mortgage requirement.
     *
     * @param isMortgageRequired the mortgage requirement flag
     * @return list of document checklists with the specified mortgage requirement
     */
    List<DocumentChecklist> findByIsMortgageRequired(Boolean isMortgageRequired);

    /**
     * Delete all document checklists for a property.
     *
     * @param property the property to delete document checklists for
     */
    void deleteByProperty(Property property);
}