package javaweb.AnGiangTourism.service;

import javaweb.AnGiangTourism.dto.DataDto;
import javaweb.AnGiangTourism.dto.GeoJSON.Feature;
import javaweb.AnGiangTourism.dto.GeoJSON.FeatureCollection;
import javaweb.AnGiangTourism.dto.GeoJSON.Properties;
import javaweb.AnGiangTourism.entity.Data;
import javaweb.AnGiangTourism.repository.DataRepository;
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
public class DataService {
    DataRepository dataRepository;

    public List<DataDto> getAllDataAsGeoJson() {
        // Lấy danh sách Data từ cơ sở dữ liệu
        List<Data> dataList = dataRepository.findAll();
        // Chuyển đổi danh sách Data thành DTO với GeoJSON
        return dataList.stream()
                .map(this::convertDataToGeoJson)
                .collect(Collectors.toList());
    }

    public FeatureCollection getData(){
        List<Data> dataList = dataRepository.findAll();
        List<Feature> features = dataList.stream()
                .map(this::convertDataToFeature)
                .collect(Collectors.toList());
        FeatureCollection featureCollection = new FeatureCollection();
        featureCollection.setFeatures(features);

        return featureCollection;
    }

    private Feature convertDataToFeature(Data data) {
        // Tạo properties cho Feature
        Properties properties = new Properties(data.getName(), data.getName());

        // Chuyển đổi Geometry sang GeoJSON (chuỗi GeoJSON)
        String geoJson = convertGeometryToGeoJson(data.getThe_geom());

        return new Feature(properties, geoJson);
    }

    // Chuyển đối tượng Data thành DataDto có chứa GeoJSON
    private DataDto convertDataToGeoJson(Data data) {
        Geometry geometry = data.getThe_geom();
        String geoJson = convertGeometryToGeoJson(geometry);
        return new DataDto(data.getId(), geoJson, data.getName());
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
