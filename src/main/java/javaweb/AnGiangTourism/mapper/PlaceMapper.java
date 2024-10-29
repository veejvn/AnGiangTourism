package javaweb.AnGiangTourism.mapper;

import javaweb.AnGiangTourism.dto.place.PlaceRequest;
import javaweb.AnGiangTourism.entity.Place;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PlaceMapper {
    Place toPlace(PlaceRequest placeRequest);

    PlaceRequest toPlaceRequest(Place place);

    void updatePlace(@MappingTarget Place place, PlaceRequest placeRequest);
}
