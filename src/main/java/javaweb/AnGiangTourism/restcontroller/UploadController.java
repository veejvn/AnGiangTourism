package javaweb.AnGiangTourism.restcontroller;

import javaweb.AnGiangTourism.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/upload")
public class UploadController {
    @Autowired
    private UploadService uploadService;

    //upload 1 image
    @PostMapping("/image")
    public String uploadFile(@RequestParam("image") MultipartFile image) {
        try {
            String imageUrl = uploadService.uploadFile(image);
            return imageUrl;
        } catch (IOException e) {
            return "Upload Không Thành Công";
        }
    }
}
