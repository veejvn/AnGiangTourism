package javaweb.AnGiangTourism.service;

import javaweb.AnGiangTourism.dto.GeoJSON.Feature;
import javaweb.AnGiangTourism.dto.GeoJSON.FeatureCollection;
import javaweb.AnGiangTourism.dto.GeoJSON.Properties;
import javaweb.AnGiangTourism.entity.Place;
import javaweb.AnGiangTourism.mapper.PropertiesMapper;
import javaweb.AnGiangTourism.repository.PlaceRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.geotools.geojson.geom.GeometryJSON;
import org.locationtech.jts.geom.Geometry;
import org.springframework.stereotype.Service;

import java.io.StringWriter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PlaceService {
    PlaceRepository placeRepository;
    PropertiesMapper propertiesMapper;

    public FeatureCollection getData(){
        List<Place> dataList = placeRepository.findAll();
        List<Feature> features = dataList.stream()
                .map(this::convertDataToFeature)
                .collect(Collectors.toList());
        FeatureCollection featureCollection = new FeatureCollection();
        featureCollection.setFeatures(features);

        return featureCollection;
    }

    private Feature convertDataToFeature(Place place) {


        Properties properties = propertiesMapper.toProperties(place);

        // Chuyển đổi Geometry sang GeoJSON (chuỗi GeoJSON)
        String geoJson = convertGeometryToGeoJson(place.getThe_geom());

        return new Feature(properties, geoJson);
    }


    // Hàm chuyển Geometry thành chuỗi GeoJSON
    private String convertGeometryToGeoJson(Geometry geometry) {
        if (geometry == null) {
            return null;
        }

        GeometryJSON gjson = new GeometryJSON();
        StringWriter writer = new StringWriter();
        try {
            gjson.write(geometry, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return writer.toString();
    }


}
