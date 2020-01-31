package ru.skillbox.blog.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skillbox.blog.service.FileService;

/**
 * @author alkarik
 * @link http://alkarik
 */
@Service
public class FileServiceImpl implements FileService {

    @Value("${upload.path}")
    private String uploadPath;

    @Value("${upload.dir}")
    private String uploadDir;

    @Override
    public String saveImage(MultipartFile uploadFile) throws IOException {
        if (uploadFile.isEmpty()) {
            return "please select a file!";
        }
        String uuidFile = UUID.randomUUID().toString();
        final String dirPath = uploadDir + "/" + uuidFile.substring(0, 2) + "/"
            + uuidFile.substring(2, 4) + "/" + uuidFile.substring(4, 6);
        if (uploadFile != null) {
            File uploadDir = new File(uploadPath + "/" + dirPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }
        }
        final String fileName = "/" + uuidFile.substring(uuidFile.length() - 5)
            + uploadFile.getOriginalFilename().substring(uploadFile.getOriginalFilename().lastIndexOf("."));
        uploadFile.transferTo(new File(uploadPath + "/" + dirPath + fileName).getAbsoluteFile());

        return dirPath + fileName;
    }
}
