package javaweb.AnGiangTourism.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import javaweb.AnGiangTourism.entity.Place;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PlaceRepositoryImpl implements PlaceRepositoryCustom{
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Place> fuzzySearchByName(String query) {
        // Tách query thành các từ khoá
        String[] keywords = query.trim().split("\\s+"); // Split theo khoảng trắng
        StringBuilder queryBuilder = new StringBuilder("SELECT p FROM Place p WHERE ");

        // Tạo câu truy vấn động
        for (int i = 0; i < keywords.length; i++) {
            queryBuilder.append("LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword").append(i).append(", '%'))");
            if (i < keywords.length - 1) {
                queryBuilder.append(" AND "); // Dùng AND để chắc chắn mỗi từ đều có trong kết quả
            }
        }

        TypedQuery<Place> jpaQuery = entityManager.createQuery(queryBuilder.toString(), Place.class);

        // Thiết lập tham số từ khoá
        for (int i = 0; i < keywords.length; i++) {
            jpaQuery.setParameter("keyword" + i, keywords[i]);
        }

        return jpaQuery.getResultList();
    }
}
