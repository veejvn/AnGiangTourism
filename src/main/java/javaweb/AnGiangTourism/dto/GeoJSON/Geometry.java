package javaweb.AnGiangTourism.dto.GeoJSON;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Geometry {
    private String type; // Loại hình học (Point, LineString, Polygon, ...)
    private Object coordinates; // Tọa độ có thể là List<Double> hoặc List<List<Double>>
}
