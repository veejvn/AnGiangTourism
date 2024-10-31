package javaweb.AnGiangTourism.mapper;

import javaweb.AnGiangTourism.dto.category.CategoryRequest;
import javaweb.AnGiangTourism.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category toCategory(CategoryRequest request);

    CategoryRequest toCategoryRequest(Category category);

    void updateCategory(@MappingTarget Category category, CategoryRequest request);
}
