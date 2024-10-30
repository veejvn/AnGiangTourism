package javaweb.AnGiangTourism.repository;

import javaweb.AnGiangTourism.dto.place.PlaceProjection;
import javaweb.AnGiangTourism.entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaceRepository extends JpaRepository<Place, String> {
    boolean existsByName(String name);

    @Query("SELECT p.id AS id, p.name AS name, p.address AS address, " +
            "p.hotLine AS hotLine, p.image AS image, p.minPrice AS minPrice, " +
            "p.maxPrice AS maxPrice, p.description AS description, " +
            "p.lon AS lon, p.lat AS lat FROM Place p")
    List<PlaceProjection> findAllWithoutGeometry();

    List<Place> findByNameContaining(String name);
}
