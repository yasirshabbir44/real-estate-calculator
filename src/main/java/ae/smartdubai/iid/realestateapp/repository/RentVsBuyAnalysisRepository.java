package ae.smartdubai.iid.realestateapp.repository;

import ae.smartdubai.iid.realestateapp.model.Property;
import ae.smartdubai.iid.realestateapp.model.RentVsBuyAnalysis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for the RentVsBuyAnalysis entity.
 */
@Repository
public interface RentVsBuyAnalysisRepository extends JpaRepository<RentVsBuyAnalysis, Long> {
    
    /**
     * Find all analyses for a specific property.
     *
     * @param property the property
     * @return list of rent vs buy analyses for the property
     */
    List<RentVsBuyAnalysis> findByProperty(Property property);
    
    /**
     * Delete all analyses for a specific property.
     *
     * @param property the property
     */
    void deleteByProperty(Property property);
}