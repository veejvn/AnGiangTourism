package javaweb.AnGiangTourism.repository;

import javaweb.AnGiangTourism.entity.Place;

import java.util.List;

public interface PlaceRepositoryCustom {
    List<Place> fuzzySearchByName(String query);
}
