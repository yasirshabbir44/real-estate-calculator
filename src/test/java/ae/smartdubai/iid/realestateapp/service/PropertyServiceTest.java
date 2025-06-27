package ae.smartdubai.iid.realestateapp.service;

import ae.smartdubai.iid.realestateapp.model.Property;
import ae.smartdubai.iid.realestateapp.repository.PropertyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PropertyServiceTest {

    @Mock
    private PropertyRepository propertyRepository;

    @InjectMocks
    private PropertyService propertyService;

    private Property property1;
    private Property property2;
    private List<Property> properties;

    @BeforeEach
    public void setup() {
        // Create test properties
        property1 = new Property();
        property1.setId(1L);
        property1.setName("Test Property 1");
        property1.setLocation("Dubai Marina");
        property1.setPrice(1000000.0);
        property1.setSize(200.0);
        property1.setBedrooms(3);
        property1.setBathrooms(2);
        property1.setPropertyType("Apartment");
        property1.setCommunityName("Dubai Marina");
        property1.setIsFurnished(false);
        property1.setYearBuilt(2020);

        property2 = new Property();
        property2.setId(2L);
        property2.setName("Test Property 2");
        property2.setLocation("Arabian Ranches");
        property2.setPrice(1500000.0);
        property2.setSize(300.0);
        property2.setBedrooms(4);
        property2.setBathrooms(3);
        property2.setPropertyType("Villa");
        property2.setCommunityName("Arabian Ranches");
        property2.setIsFurnished(true);
        property2.setYearBuilt(2019);

        properties = Arrays.asList(property1, property2);
    }

    @Test
    public void testGetAllProperties() {
        // Arrange
        when(propertyRepository.findAll()).thenReturn(properties);

        // Act
        List<Property> result = propertyService.getAllProperties();

        // Assert
        assertEquals(2, result.size());
        assertEquals(properties, result);
        verify(propertyRepository, times(1)).findAll();
    }

    @Test
    public void testGetPropertyById() {
        // Arrange
        when(propertyRepository.findById(1L)).thenReturn(Optional.of(property1));
        when(propertyRepository.findById(3L)).thenReturn(Optional.empty());

        // Act
        Optional<Property> foundProperty = propertyService.getPropertyById(1L);
        Optional<Property> notFoundProperty = propertyService.getPropertyById(3L);

        // Assert
        assertTrue(foundProperty.isPresent());
        assertEquals(property1, foundProperty.get());
        assertFalse(notFoundProperty.isPresent());
        verify(propertyRepository, times(1)).findById(1L);
        verify(propertyRepository, times(1)).findById(3L);
    }

    @Test
    public void testCreateProperty() {
        // Arrange
        Property newProperty = new Property();
        newProperty.setName("New Property");
        newProperty.setLocation("Palm Jumeirah");
        newProperty.setPrice(2000000.0);
        newProperty.setSize(250.0);
        newProperty.setBedrooms(3);
        newProperty.setBathrooms(2);
        newProperty.setPropertyType("Apartment");
        newProperty.setCommunityName("Palm Jumeirah");
        newProperty.setIsFurnished(true);
        newProperty.setYearBuilt(2021);

        Property savedProperty = new Property();
        savedProperty.setId(3L);
        savedProperty.setName("New Property");
        savedProperty.setLocation("Palm Jumeirah");
        savedProperty.setPrice(2000000.0);
        savedProperty.setSize(250.0);
        savedProperty.setBedrooms(3);
        savedProperty.setBathrooms(2);
        savedProperty.setPropertyType("Apartment");
        savedProperty.setCommunityName("Palm Jumeirah");
        savedProperty.setIsFurnished(true);
        savedProperty.setYearBuilt(2021);

        when(propertyRepository.save(any(Property.class))).thenReturn(savedProperty);

        // Act
        Property result = propertyService.createProperty(newProperty);

        // Assert
        assertNotNull(result);
        assertEquals(3L, result.getId());
        assertEquals("New Property", result.getName());
        assertEquals("Palm Jumeirah", result.getLocation());
        verify(propertyRepository, times(1)).save(any(Property.class));
    }

    @Test
    public void testUpdateProperty() {
        // Arrange
        Property updatedDetails = new Property();
        updatedDetails.setName("Updated Property");
        updatedDetails.setPrice(1100000.0);

        Property existingProperty = new Property();
        existingProperty.setId(1L);
        existingProperty.setName("Test Property 1");
        existingProperty.setLocation("Dubai Marina");
        existingProperty.setPrice(1000000.0);
        existingProperty.setSize(200.0);
        existingProperty.setBedrooms(3);
        existingProperty.setBathrooms(2);
        existingProperty.setPropertyType("Apartment");
        existingProperty.setCommunityName("Dubai Marina");
        existingProperty.setIsFurnished(false);
        existingProperty.setYearBuilt(2020);

        Property updatedProperty = new Property();
        updatedProperty.setId(1L);
        updatedProperty.setName("Updated Property");
        updatedProperty.setLocation("Dubai Marina");
        updatedProperty.setPrice(1100000.0);
        updatedProperty.setSize(200.0);
        updatedProperty.setBedrooms(3);
        updatedProperty.setBathrooms(2);
        updatedProperty.setPropertyType("Apartment");
        updatedProperty.setCommunityName("Dubai Marina");
        updatedProperty.setIsFurnished(false);
        updatedProperty.setYearBuilt(2020);

        when(propertyRepository.findById(1L)).thenReturn(Optional.of(existingProperty));
        when(propertyRepository.save(any(Property.class))).thenReturn(updatedProperty);
        when(propertyRepository.findById(3L)).thenReturn(Optional.empty());

        // Act
        Property result = propertyService.updateProperty(1L, updatedDetails);

        // Assert
        assertNotNull(result);
        assertEquals("Updated Property", result.getName());
        assertEquals(1100000.0, result.getPrice());
        assertEquals("Dubai Marina", result.getLocation()); // Unchanged field
        verify(propertyRepository, times(1)).findById(1L);
        verify(propertyRepository, times(1)).save(any(Property.class));

        // Test property not found
        assertThrows(RuntimeException.class, () -> {
            propertyService.updateProperty(3L, updatedDetails);
        });
        verify(propertyRepository, times(1)).findById(3L);
    }

    @Test
    public void testDeleteProperty() {
        // Arrange
        doNothing().when(propertyRepository).deleteById(1L);

        // Act
        propertyService.deleteProperty(1L);

        // Assert
        verify(propertyRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testFindPropertiesByLocation() {
        // Arrange
        when(propertyRepository.findByLocationContainingIgnoreCase("Dubai")).thenReturn(Arrays.asList(property1));

        // Act
        List<Property> result = propertyService.findPropertiesByLocation("Dubai");

        // Assert
        assertEquals(1, result.size());
        assertEquals(property1, result.get(0));
        verify(propertyRepository, times(1)).findByLocationContainingIgnoreCase("Dubai");
    }

    @Test
    public void testFindPropertiesByPriceRange() {
        // Arrange
        when(propertyRepository.findByPriceBetween(900000.0, 1200000.0)).thenReturn(Arrays.asList(property1));

        // Act
        List<Property> result = propertyService.findPropertiesByPriceRange(900000.0, 1200000.0);

        // Assert
        assertEquals(1, result.size());
        assertEquals(property1, result.get(0));
        verify(propertyRepository, times(1)).findByPriceBetween(900000.0, 1200000.0);
    }

    @Test
    public void testFindPropertiesByType() {
        // Arrange
        when(propertyRepository.findByPropertyTypeIgnoreCase("Apartment")).thenReturn(Arrays.asList(property1));

        // Act
        List<Property> result = propertyService.findPropertiesByType("Apartment");

        // Assert
        assertEquals(1, result.size());
        assertEquals(property1, result.get(0));
        verify(propertyRepository, times(1)).findByPropertyTypeIgnoreCase("Apartment");
    }

    @Test
    public void testFindPropertiesByCommunity() {
        // Arrange
        when(propertyRepository.findByCommunityNameContainingIgnoreCase("Dubai Marina")).thenReturn(Arrays.asList(property1));

        // Act
        List<Property> result = propertyService.findPropertiesByCommunity("Dubai Marina");

        // Assert
        assertEquals(1, result.size());
        assertEquals(property1, result.get(0));
        verify(propertyRepository, times(1)).findByCommunityNameContainingIgnoreCase("Dubai Marina");
    }
}