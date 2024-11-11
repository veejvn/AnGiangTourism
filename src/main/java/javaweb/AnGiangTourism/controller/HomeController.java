package javaweb.AnGiangTourism.controller;

import javaweb.AnGiangTourism.entity.Category;
import javaweb.AnGiangTourism.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {
    @Autowired
    private CategoryRepository categoryRepository;
    @GetMapping({"/", "/home"})
    public String home(Model model){
        List<Category> categories = categoryRepository.findAll();
        model.addAttribute("categories", categories);
        return "home";
    }
}
