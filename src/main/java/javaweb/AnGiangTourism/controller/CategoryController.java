package javaweb.AnGiangTourism.controller;

import javaweb.AnGiangTourism.dto.category.AddCategoryRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class CategoryController {

    @GetMapping("/admin/category/create")
    @PreAuthorize("hasRole('ADMIN')")
    public String categoryPage(Model model){
        model.addAttribute("addCategoryRequest", new AddCategoryRequest());
        return "/admin/category/create";
    }
}
