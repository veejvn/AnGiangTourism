package javaweb.AnGiangTourism.dto.place;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PlaceRequest {

    @NotBlank(message = "Place name is required")
    String name;

    @NotBlank(message = "Place address is required")
    String address;

    @NotBlank(message = "Hotline address is required")
    String hotLine;

    @NotBlank(message = "Place image is required")
    String image;

    @NotNull(message = "Min Price is required")
    Integer minPrice;

    @NotNull(message = "Max Price is required")
    Integer maxPrice;

    @NotBlank(message = "Description is required")
    String description;

    @NotBlank(message = "Category is required")
    String categoryId;

    @NotBlank(message = "Longitude is required")
    String longitude;

    @NotBlank(message = "Latitude is required")
    String latitude;


    public PlaceRequest() {
    }
}
