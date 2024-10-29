package javaweb.AnGiangTourism.restcontroller;

import javaweb.AnGiangTourism.dto.GeoJSON.FeatureCollection;
import javaweb.AnGiangTourism.service.PlaceService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://127.0.0.1:5500/")
@RestController
@RequestMapping("/api/data")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PlaceController {

    PlaceService placeService;

    @GetMapping
    public FeatureCollection getData(){
        return placeService.getData();
    }



}
