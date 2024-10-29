package javaweb.AnGiangTourism.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class PlacesController {
    @GetMapping("/admin/place/create")
    public String create(){
        return "/admin/place/create";
    }
    @GetMapping("/admin/place/list")
    public String getList(){
        return "/admin/place/list-place";
    }

    @GetMapping("/admin/place/update")
    public String update(){
        return "/admin/place/update-place";
    }

}
