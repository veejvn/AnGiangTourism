package javaweb.AnGiangTourism.dto.GeoJSON;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Feature {
    private String type = "Feature";
    private Properties properties;
    private String geometry;

    public Feature(Properties properties, String geoJson) {
        this.properties = properties;
        this.geometry = geoJson;
    }
}
