package javaweb.AnGiangTourism.controller;

import jakarta.validation.Valid;
import javaweb.AnGiangTourism.dto.category.CategoryRequest;
import javaweb.AnGiangTourism.entity.Category;
import javaweb.AnGiangTourism.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping("/admin/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public String showAddCategoryPage(Model model){
        model.addAttribute("categoryRequest", new CategoryRequest());
        return "/admin/category/create";
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public  String addCategory(@ModelAttribute("categoryRequest") @Valid CategoryRequest request,
                               BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()){
            String errorMessage = Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage();
            redirectAttributes.addFlashAttribute("errorMessage", errorMessage);
            return "redirect:/admin/category/create";
        }
        categoryService.saveCategory(request);
        redirectAttributes.addFlashAttribute("successMessage", "Thêm danh mục thành công!");
        return "redirect:/admin/category/create";
    }

    @GetMapping("/list")
    @PreAuthorize("hasRole('ADMIN')")
    public String showListCategoryPage(Model model){
        List<Category> categories = categoryService.findAll();
        model.addAttribute("listCategory", categories);
        return "admin/category/list-category";
    }

    @GetMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String showUpdateCategoryPage(@PathVariable String id, Model model, RedirectAttributes redirectAttributes){
        Optional<Category> categoryOptional = categoryService.findById(id);
        if (categoryOptional.isEmpty()){
            redirectAttributes.addFlashAttribute("errorMessage", "Danh mục không tồn tại");
            return "redirect:/admin/place/list-category";
        }
        Category category = categoryOptional.get();
        CategoryRequest categoryRequest = categoryService.mapperToCategoryRequest(category);
        model.addAttribute("categoryRequest", categoryRequest);
        model.addAttribute("id", id);
        return "admin/category/update";
    }

    @PostMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String updateCategory(@PathVariable String id, @ModelAttribute("categoryRequest") @Valid CategoryRequest request,
                                 BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if (bindingResult.hasErrors()){
            String errorMessage = Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage();
            redirectAttributes.addFlashAttribute("errorMessage", errorMessage);
            return "redirect:/admin/category/update";
        }
        categoryService.updateCategory(id, request);
        redirectAttributes.addFlashAttribute("successMessage", "Cập nhật danh mục thành công!");
        return "redirect:/admin/category/update/" + id;
    }

    @GetMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteCategoryPage(@PathVariable String id, RedirectAttributes redirectAttributes){
        if(categoryService.existsById(id)){
            categoryService.deleteCategory(id);
            redirectAttributes.addFlashAttribute("successMessage", "Danh mục đã được xóa thành công!");
        }else {
            redirectAttributes.addFlashAttribute("errorMessage", "Danh mục không tồn tại!");
        }
        return "redirect:/admin/category/list";
    }
}
