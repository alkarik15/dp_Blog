package ru.skillbox.blog.service;

import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author alkarik
 * @link http://alkarik
 */
public interface FileService {
    String saveImage(MultipartFile uploadFile) throws IOException;
}
