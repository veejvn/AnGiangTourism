package javaweb.AnGiangTourism.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.Point;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Place {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Column(name = "the_geom", columnDefinition = "geometry")
    @JsonIgnore
    Geometry the_geom;
    String name;
    String address;
    String hotLine;
    String image;
    Integer minPrice;
    Integer maxPrice;
    @Column(columnDefinition = "TEXT")
    String description;
    double lon;
    double lat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    @JsonBackReference
    Category category;

    public Place(String id, String name, String address, String image, double lat, double lon) {
        this.id=id;
        this.name=name;
        this.address=address;
        this.lat=lat;
        this.lon=lon;
        this.image=image;
    }
}
