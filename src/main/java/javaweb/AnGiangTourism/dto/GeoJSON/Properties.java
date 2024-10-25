package javaweb.AnGiangTourism.dto.GeoJSON;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Properties {
    private String name;
    String address;
    String hotLine;
    String image;
    Integer minPrice;
    Integer maxPrice;
    String description;
}
