package javaweb.AnGiangTourism.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import javaweb.AnGiangTourism.dto.place.PlaceProjection;
import javaweb.AnGiangTourism.entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaceRepository extends JpaRepository<Place, String>, PlaceRepositoryCustom {
    boolean existsByName(String name);
    @Query("SELECT p.id AS id, p.name AS name, p.address AS address, " +
            "p.hotLine AS hotLine, p.image AS image, p.minPrice AS minPrice, " +
            "p.maxPrice AS maxPrice, p.description AS description, " +
            "p.lon AS lon, p.lat AS lat FROM Place p")
    List<PlaceProjection> findAllWithoutGeometry();

    List<Place> findByNameContaining(String name);

    @Query("SELECT p FROM Place p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Place> searchByName(@Param("name") String name);

    @Query("SELECT p FROM Place p WHERE p.category.id = :categoryId")
    List<Place> findByCategoryId(@Param("categoryId") String categoryId);



}
