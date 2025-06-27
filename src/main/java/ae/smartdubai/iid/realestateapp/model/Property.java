package ae.smartdubai.iid.realestateapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity representing a real estate property.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Property name is required")
    private String name;

    @NotBlank(message = "Location is required")
    private String location;

    @NotNull(message = "Property price is required")
    @Positive(message = "Property price must be positive")
    private Double price;

    @Positive(message = "Property size must be positive")
    private Double size;

    private Integer bedrooms;

    private Integer bathrooms;

    private String propertyType; // Apartment, Villa, Townhouse, etc.

    private String communityName;

    private Boolean isFurnished;

    private Integer yearBuilt;
}