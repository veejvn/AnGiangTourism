package javaweb.AnGiangTourism.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class CloudinaryConfig {


    private String CLOUDINARY_NAME = "ds0gpqe3p";

    private String CLOUDINARY_API_KEY = "615477669223833";

    private String CLOUDINARY_API_SECRET = "D_KvZRYuNYekLatK0-I88NpkmwE";

    @Bean
    public Cloudinary cloudinary(){
        return new Cloudinary(ObjectUtils.asMap(
           "cloud_name", CLOUDINARY_NAME,
                "api_key",CLOUDINARY_API_KEY,
                "api_secret", CLOUDINARY_API_SECRET
        ));
    }
}
