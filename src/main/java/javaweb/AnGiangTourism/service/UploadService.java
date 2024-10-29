package javaweb.AnGiangTourism.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class UploadService {
    @Autowired
    private Cloudinary cloudinary;
    @Value("${app.cloudinary.folder}")
    private String CLOUDINARY_FOLDER;

    // Upload 1 image
    // Hàm để xác định thư mục lưu tệp
    private String getFolder(MultipartFile file) {
        String typeFolder = file.getContentType().startsWith("image/") ? "/images" : "/videos";
        return CLOUDINARY_FOLDER + "/" + typeFolder;
    }

    public String uploadFile(MultipartFile image) throws IOException {
        String folder = getFolder(image); // Gọi hàm getFolder
        Map<String, Object> options = ObjectUtils.asMap("folder", folder);
        Map<String, Object> result = cloudinary.uploader().upload(image.getBytes(), options);
        return (String) result.get("secure_url"); // Trả về URL an toàn của file đã upload
    }

    public void deleteFile(String url) throws IOException {
        String[] urlParts = url.split("/");
        if (urlParts.length < 5) {
            throw new IllegalArgumentException("Invalid URL format");
        }
        String fileName = urlParts[urlParts.length - 1]; // Lấy tên tệp
        String type = urlParts[urlParts.length - 2]; // Lấy thư mục loại tệp (video hoặc images)
        String publicId;
        if ("images".equals(type)) {
            publicId = CLOUDINARY_FOLDER + "/" + type + "/" + fileName.replaceFirst("\\.[^\\.]+$", ""); // Bỏ phần mở rộng
            System.out.println("Public ID to delete: " + publicId);
            Map<String, Object> result = cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
            System.out.println("Delete result: " + result); // Ghi lại kết quả
        } else if ("videos".equals(type)) {
            publicId = CLOUDINARY_FOLDER + "/" + type + "/" + fileName.replaceFirst("\\.[^\\.]+$", ""); // Bỏ phần mở rộng
            System.out.println("Public ID to delete: " + publicId);
            Map<String, Object> options = ObjectUtils.asMap("resource_type", "video");
            Map<String, Object> result = cloudinary.uploader().destroy(publicId, options);
            System.out.println("Delete result: " + result); // Ghi lại kết quả
        } else {
            throw new IllegalArgumentException("Invalid file type:  " + type);
        }
    }

}
