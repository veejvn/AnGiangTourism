package javaweb.AnGiangTourism.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DataDto {
    private Integer id;
    private String geoJson;
    private String name;

    public DataDto(Integer id, String geoJson, String name) {
        this.id = id;
        this.geoJson = geoJson;
        this.name = name;
    }
}

