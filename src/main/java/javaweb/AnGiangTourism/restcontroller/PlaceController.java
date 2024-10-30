package javaweb.AnGiangTourism.restcontroller;

import javaweb.AnGiangTourism.dto.GeoJSON.FeatureCollection;
import javaweb.AnGiangTourism.dto.place.PlaceRequest;
import javaweb.AnGiangTourism.entity.Place;
import javaweb.AnGiangTourism.service.PlaceService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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

    @GetMapping("/search")
    public List<Place> searchPlaces(@RequestParam String name) {
        List<Place> places = placeService.findByNameContaining(name);
        return places.stream().map(place -> {
            return new Place(place.getId(), place.getName(), place.getAddress(), place.getImage() ,place.getLat(), place.getLon());
        }).collect(Collectors.toList());
    }


}
