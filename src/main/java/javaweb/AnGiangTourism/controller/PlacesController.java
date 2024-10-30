package javaweb.AnGiangTourism.controller;

import jakarta.validation.Valid;
import javaweb.AnGiangTourism.dto.place.PlaceProjection;
import javaweb.AnGiangTourism.dto.place.PlaceRequest;
import javaweb.AnGiangTourism.entity.Category;
import javaweb.AnGiangTourism.entity.Place;
import javaweb.AnGiangTourism.repository.CategoryRepository;
import javaweb.AnGiangTourism.service.PlaceService;
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
@RequestMapping("/admin/place")
@RequiredArgsConstructor
public class PlacesController {

    private final PlaceService placeService;
    private final CategoryRepository categoryRepository;
    @GetMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public String showAddPlace(Model model) {
        List<Category> categories = categoryRepository.findAll();
        model.addAttribute("categories", categories);
        model.addAttribute("placeRequest", new PlaceRequest());
        return "admin/place/create"; // Trả về view tạo mới Place
    }


    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public String addPlace(@ModelAttribute("placeRequest") @Valid PlaceRequest request,
                           BindingResult bindingResult, RedirectAttributes redirectAttributes
                           ){
        if(bindingResult.hasErrors()){
            String errorMessage = Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage();
            redirectAttributes.addFlashAttribute("errorMessage", errorMessage);
            return "redirect:/admin/place/create";
        }
        if(placeService.existsByName(request.getName())){
            redirectAttributes.addFlashAttribute("errorMessage", "Tên địa điểm đã tồn tại");
            return "redirect:/admin/place/create";
        }
        placeService.savePlace(request);
        redirectAttributes.addFlashAttribute("successMessage", "Thêm địa điểm thành công!");
        return "redirect:/admin/place/create";
    }

    @GetMapping("/list")
    @PreAuthorize("hasRole('ADMIN')")
    public String showListPlace(Model model){
        List<PlaceProjection> places = placeService.findAll();
        model.addAttribute("listPlace", places);
        return "admin/place/list-place";
    }

    @GetMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String showUpdatePlace(@PathVariable("id") String id, Model model, RedirectAttributes redirectAttributes){
        Optional<Place> placeOptional =  placeService.findById(id);
        if(placeOptional.isEmpty()){
            redirectAttributes.addFlashAttribute("errorMessage", "Địa điểm không tồn tại");
            return "redirect:/admin/place/list-place";
        }
        Place place = placeOptional.get();
        PlaceRequest placeRequest = placeService.mapperToPlaceRequest(place);
        List<Category> categories = categoryRepository.findAll();
        model.addAttribute("categories", categories);
        model.addAttribute("placeRequest", placeRequest);
        model.addAttribute("id", id);
        return "admin/place/update";
    }

    @PostMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String updatePlace(@PathVariable("id") String id, @ModelAttribute("placeRequest") @Valid PlaceRequest request,
                              BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if (bindingResult.hasErrors()){
            String errorMessage = Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage();
            redirectAttributes.addFlashAttribute("errorMessage", errorMessage);
            return "/admin/place/update";
        }
        if(placeService.existsByName(request.getName())){
            redirectAttributes.addFlashAttribute("errorMessage", "Tên địa điểm đã tồn tại");
            return "redirect:/admin/place/update/" + id;
        }
        placeService.updatePlace(id, request);
        redirectAttributes.addFlashAttribute("successMessage", "Cập nhật địa điểm thành công!");
        return "redirect:/admin/place/update/" + id;
    }

    @GetMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deletePlace(@PathVariable("id") String id, RedirectAttributes redirectAttributes){
        if(placeService.existsById(id)) {
            placeService.deletePlace(id);
            redirectAttributes.addFlashAttribute("successMessage", "Địa điểm đã được xóa thành công!");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Địa điểm không tồn tại!");
        }
        return "redirect:/admin/place/list";
    }
}