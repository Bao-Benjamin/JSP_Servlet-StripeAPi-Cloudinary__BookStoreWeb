package util;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import java.util.Map;
import java.util.HashMap;

public class CloudinaryUtil {
    private static Cloudinary cloudinary;
    
    static {
        // Tạo Map thay vì sử dụng ObjectUtils.asMap để tránh lỗi
        Map<String, String> config = new HashMap<>();
        config.put("cloud_name", "dfinfmcan");
        config.put("api_key", "416599144347955");
        config.put("api_secret", "Ef5bL8dK0gj4rrt4T4vhn0Bzkkk");
        
        cloudinary = new Cloudinary(config);
    }
    
    public static Cloudinary getCloudinary() {
        return cloudinary;
    }
}