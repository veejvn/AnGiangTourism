package javaweb.AnGiangTourism.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CategoryController {
    @GetMapping("/admin/category/create")
    public String create(){
        return "/admin/category/create";
    }
    @GetMapping("/admin/category/list")
    public String getList(){
        return "/admin/category/list";
    }
}
