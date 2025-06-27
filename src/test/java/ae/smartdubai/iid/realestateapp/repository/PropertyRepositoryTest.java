package ae.smartdubai.iid.realestateapp.repository;

import ae.smartdubai.iid.realestateapp.model.Property;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@ActiveProfiles("test")
public class PropertyRepositoryTest {

    @Autowired
    private PropertyRepository propertyRepository;

    @Test
    public void testSaveAndFindProperty() {
        // Create a new property
        Property property = new Property();
        property.setName("Test Property");
        property.setLocation("Test Location");
        property.setPrice(1000000.0);
        property.setSize(200.0);
        property.setBedrooms(3);
        property.setBathrooms(2);
        property.setPropertyType("Apartment");
        property.setCommunityName("Test Community");
        property.setIsFurnished(false);
        property.setYearBuilt(2020);

        // Save the property
        Property savedProperty = propertyRepository.save(property);

        // Verify the property was saved
        assertNotNull(savedProperty.getId());
        
        // Find the property by ID
        Property foundProperty = propertyRepository.findById(savedProperty.getId()).orElse(null);
        
        // Verify the property was found
        assertNotNull(foundProperty);
        assertEquals("Test Property", foundProperty.getName());
        assertEquals("Test Location", foundProperty.getLocation());
        assertEquals(1000000.0, foundProperty.getPrice());
        assertEquals(200.0, foundProperty.getSize());
        assertEquals(3, foundProperty.getBedrooms());
        assertEquals(2, foundProperty.getBathrooms());
        assertEquals("Apartment", foundProperty.getPropertyType());
        assertEquals("Test Community", foundProperty.getCommunityName());
        assertEquals(false, foundProperty.getIsFurnished());
        assertEquals(2020, foundProperty.getYearBuilt());
    }
}