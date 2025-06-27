package ae.smartdubai.iid.realestateapp.controller;

import ae.smartdubai.iid.realestateapp.model.Property;
import ae.smartdubai.iid.realestateapp.service.PropertyService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.mockito.Mock;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PropertyController.class)
@ExtendWith(SpringExtension.class)
public class PropertyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private PropertyService propertyService;

    @Autowired
    private ObjectMapper objectMapper;

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
    public void testGetAllProperties() throws Exception {
        // Mock service method
        when(propertyService.getAllProperties()).thenReturn(properties);

        // Perform GET request and validate response
        mockMvc.perform(get("/api/properties"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Test Property 1")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("Test Property 2")));

        // Verify service method was called
        verify(propertyService, times(1)).getAllProperties();
    }

    @Test
    public void testGetProperty() throws Exception {
        // Mock service method
        when(propertyService.getPropertyById(1L)).thenReturn(Optional.of(property1));
        when(propertyService.getPropertyById(3L)).thenReturn(Optional.empty());

        // Perform GET request for existing property and validate response
        mockMvc.perform(get("/api/properties/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Test Property 1")));

        // Perform GET request for non-existing property and validate response
        mockMvc.perform(get("/api/properties/3"))
                .andExpect(status().isNotFound());

        // Verify service method was called
        verify(propertyService, times(1)).getPropertyById(1L);
        verify(propertyService, times(1)).getPropertyById(3L);
    }

    @Test
    public void testCreateProperty() throws Exception {
        // Create a new property for the request
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

        // Create a response property with ID
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

        // Mock service method
        when(propertyService.createProperty(any(Property.class))).thenReturn(savedProperty);

        // Perform POST request and validate response
        mockMvc.perform(post("/api/properties")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newProperty)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(3)))
                .andExpect(jsonPath("$.name", is("New Property")));

        // Verify service method was called
        verify(propertyService, times(1)).createProperty(any(Property.class));
    }

    @Test
    public void testUpdateProperty() throws Exception {
        // Create an updated property
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
        updatedProperty.setIsFurnished(true);
        updatedProperty.setYearBuilt(2020);

        // Mock service method
        when(propertyService.updateProperty(eq(1L), any(Property.class))).thenReturn(updatedProperty);

        // Perform PUT request and validate response
        mockMvc.perform(put("/api/properties/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedProperty)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Updated Property")))
                .andExpect(jsonPath("$.price", is(1100000.0)));

        // Verify service method was called
        verify(propertyService, times(1)).updateProperty(eq(1L), any(Property.class));
    }

    @Test
    public void testDeleteProperty() throws Exception {
        // Mock service method (void method, so no return value to mock)
        doNothing().when(propertyService).deleteProperty(1L);

        // Perform DELETE request and validate response
        mockMvc.perform(delete("/api/properties/1"))
                .andExpect(status().isNoContent());

        // Verify service method was called
        verify(propertyService, times(1)).deleteProperty(1L);
    }

    @Test
    public void testSearchPropertiesByLocation() throws Exception {
        // Mock service method
        when(propertyService.findPropertiesByLocation("Dubai Marina")).thenReturn(Arrays.asList(property1));

        // Perform GET request and validate response
        mockMvc.perform(get("/api/properties/search/location")
                .param("location", "Dubai Marina"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].location", is("Dubai Marina")));

        // Verify service method was called
        verify(propertyService, times(1)).findPropertiesByLocation("Dubai Marina");
    }

    @Test
    public void testSearchPropertiesByPriceRange() throws Exception {
        // Mock service method
        when(propertyService.findPropertiesByPriceRange(900000.0, 1200000.0)).thenReturn(Arrays.asList(property1));

        // Perform GET request and validate response
        mockMvc.perform(get("/api/properties/search/price")
                .param("minPrice", "900000.0")
                .param("maxPrice", "1200000.0"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].price", is(1000000.0)));

        // Verify service method was called
        verify(propertyService, times(1)).findPropertiesByPriceRange(900000.0, 1200000.0);
    }

    @Test
    public void testSearchPropertiesByType() throws Exception {
        // Mock service method
        when(propertyService.findPropertiesByType("Apartment")).thenReturn(Arrays.asList(property1));

        // Perform GET request and validate response
        mockMvc.perform(get("/api/properties/search/type")
                .param("propertyType", "Apartment"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].propertyType", is("Apartment")));

        // Verify service method was called
        verify(propertyService, times(1)).findPropertiesByType("Apartment");
    }

    @Test
    public void testSearchPropertiesByCommunity() throws Exception {
        // Mock service method
        when(propertyService.findPropertiesByCommunity("Dubai Marina")).thenReturn(Arrays.asList(property1));

        // Perform GET request and validate response
        mockMvc.perform(get("/api/properties/search/community")
                .param("communityName", "Dubai Marina"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].communityName", is("Dubai Marina")));

        // Verify service method was called
        verify(propertyService, times(1)).findPropertiesByCommunity("Dubai Marina");
    }
}
