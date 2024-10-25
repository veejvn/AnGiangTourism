package javaweb.AnGiangTourism.mapper;

import javaweb.AnGiangTourism.dto.GeoJSON.Properties;
import javaweb.AnGiangTourism.entity.Place;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PropertiesMapper {
    Properties toProperties(Place place);
}
