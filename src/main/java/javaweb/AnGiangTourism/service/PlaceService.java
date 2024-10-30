package javaweb.AnGiangTourism.service;

import jakarta.persistence.EntityNotFoundException;
import javaweb.AnGiangTourism.dto.GeoJSON.Feature;
import javaweb.AnGiangTourism.dto.GeoJSON.FeatureCollection;
import javaweb.AnGiangTourism.dto.GeoJSON.Properties;
import javaweb.AnGiangTourism.dto.place.PlaceProjection;
import javaweb.AnGiangTourism.dto.place.PlaceRequest;
import javaweb.AnGiangTourism.entity.Category;
import javaweb.AnGiangTourism.entity.Place;
import javaweb.AnGiangTourism.mapper.PlaceMapper;
import javaweb.AnGiangTourism.mapper.PropertiesMapper;
import javaweb.AnGiangTourism.repository.CategoryRepository;
import javaweb.AnGiangTourism.repository.PlaceRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.geotools.geojson.geom.GeometryJSON;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;

import java.io.StringWriter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PlaceService {
    PlaceRepository placeRepository;
    PropertiesMapper propertiesMapper;
    PlaceMapper placeMapper;
    CategoryRepository categoryRepository;

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

    public boolean existsByName(String name){
        return placeRepository.existsByName(name);
    }

    public Optional<Place> findById(String id){
        return placeRepository.findById(id);
    }

    public PlaceRequest mapperToPlaceRequest(Place place){
        return placeMapper.toPlaceRequest(place);
    }

    public void savePlace(PlaceRequest request){
        Place place = placeMapper.toPlace(request);

        Category category;

        Optional<Category> categoryOptional = categoryRepository.findById(request.getCategoryId());
        if(categoryOptional.isPresent()){
            category = categoryOptional.get();
        }else {
            throw new EntityNotFoundException("Danh mục không tồn tại");
        }

        GeometryFactory geometryFactory = new GeometryFactory();

        double lon = Double.parseDouble(request.getLongitude());
        double lat = Double.parseDouble(request.getLatitude());

        Point point = geometryFactory.createPoint(new Coordinate(lon, lat));

        place.setCategory(category);
        place.setThe_geom(point);
        place.setLon(lon);
        place.setLat(lat);

        placeRepository.save(place);
    }

    public List<PlaceProjection> findAll(){
        return placeRepository.findAllWithoutGeometry();
    }

    public void updatePlace(String id, PlaceRequest request){

        Place place = placeRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Địa điểm không tồn tại")
        );

        placeMapper.updatePlace(place, request);

        Category category;

        Optional<Category> categoryOptional = categoryRepository.findById(request.getCategoryId());
        if(categoryOptional.isPresent()){
            category = categoryOptional.get();
        }else {
            throw new EntityNotFoundException("Danh mục không tồn tại");
        }

        GeometryFactory geometryFactory = new GeometryFactory();

        double lon = Double.parseDouble(request.getLongitude());
        double lat = Double.parseDouble(request.getLatitude());

        Point point = geometryFactory.createPoint(new Coordinate(lon, lat));

        place.setCategory(category);
        place.setThe_geom(point);
        place.setLon(lon);
        place.setLat(lat);

        placeRepository.save(place);
    }

    public void deletePlace(String id){
        placeRepository.deleteById(id);
    }

    public boolean existsById(String id) {
        return placeRepository.existsById(id);
    }

    public List<Place> findByNameContaining(String name) {
        return placeRepository.findByNameContaining(name);
    }
}
