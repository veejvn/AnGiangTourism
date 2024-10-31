package javaweb.AnGiangTourism.service;

import jakarta.persistence.EntityNotFoundException;
import javaweb.AnGiangTourism.dto.category.CategoryRequest;
import javaweb.AnGiangTourism.entity.Category;
import javaweb.AnGiangTourism.mapper.CategoryMapper;
import javaweb.AnGiangTourism.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public void saveCategory(CategoryRequest request){
        Category category = categoryMapper.toCategory(request);
        categoryRepository.save(category);
    }

    public List<Category> findAll(){
        return categoryRepository.findAll();
    }

    public Optional<Category> findById(String id){
        return categoryRepository.findById(id);
    }

    public CategoryRequest mapperToCategoryRequest(Category category){
        return categoryMapper.toCategoryRequest(category);
    }

    public void updateCategory(String id, CategoryRequest request){
        Category category = categoryRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Danh mục không tồn tại")
        );
        categoryMapper.updateCategory(category, request);
        categoryRepository.save(category);
    }

    public boolean existsById(String id){
        return categoryRepository.existsById(id);
    }

    public void deleteCategory(String id){
        categoryRepository.deleteById(id);
    }
}
