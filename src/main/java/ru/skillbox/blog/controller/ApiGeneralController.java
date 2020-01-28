package ru.skillbox.blog.controller;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.skillbox.blog.component.HeaderProperties;
import ru.skillbox.blog.dto.PostModeration;
import ru.skillbox.blog.dto.TagDto;
import ru.skillbox.blog.model.GlobalSettingEntity;
import ru.skillbox.blog.service.GlobalSettingService;
import ru.skillbox.blog.service.PostService;
import ru.skillbox.blog.service.TagService;
import ru.skillbox.blog.service.UserService;

/**
 * @author alkarik
 * @link http://alkarik
 */
@RestController
@RequestMapping("/api")
public class ApiGeneralController {
    @Autowired
    private HeaderProperties headerProperties;

    @Autowired
    private GlobalSettingService globalSettingService;

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @Autowired
    private TagService tagService;

    @Value("${upload.path}")
    private String uploadPath;

    @Value("${upload.dir}")
    private String uploadDir;

    @GetMapping("/init")
    public ResponseEntity<Map<String, String>> initHeader() {
        return new ResponseEntity(headerProperties.getHeader(), HttpStatus.OK);
    }

    @GetMapping("/settings")
    public ResponseEntity<Map<String, Boolean>> apiGetSettings(HttpServletRequest request) {
        if (request.getSession().getAttribute("user") != null && request.getSession().getAttribute("user").toString().length() > 0) {
            Integer userId = Integer.parseInt(request.getSession().getAttribute("user").toString());
            if (userService.isModerator(userId)) {
                return new ResponseEntity(getSettings(), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PutMapping(value = "/settings")
    public ResponseEntity<Map<String, Boolean>> apiPutSettings(HttpServletRequest request, @RequestBody Map<String, Boolean> globalSettings) throws HttpMessageNotReadableException {
        if (request.getSession().getAttribute("user") != null && request.getSession().getAttribute("user").toString().length() > 0) {
            Integer userId = Integer.parseInt(request.getSession().getAttribute("user").toString());
            if (userService.isModerator(userId)) {
                //TODO Валидация входных данных ?
                for (Map.Entry<String, Boolean> entry : globalSettings.entrySet()) {
                    globalSettingService.createSetting(
                        new GlobalSettingEntity(entry.getKey(), entry.getValue().equals(true) ? "YES" : "NO")
                    );
                }
                return new ResponseEntity(getSettings(), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    private Map<String, Boolean> getSettings() {
        Map<String, Boolean> glSett = new HashMap<>();
        for (GlobalSettingEntity glSettings : globalSettingService.findAll()) {
            glSett.put(glSettings.getCode(), glSettings.getValue().equals("YES") ? true : false);
        }
        return glSett;
    }

    @GetMapping("/statistics/my")
    public ResponseEntity<Map<String, String>> apiGetStatMy(HttpServletRequest request) {
        if (request.getSession().getAttribute("user") != null && request.getSession().getAttribute("user").toString().length() > 0) {
            Integer userId = Integer.parseInt(request.getSession().getAttribute("user").toString());
            Map<String, String> mapStatMy = postService.statMy(userId);
            return new ResponseEntity(mapStatMy, HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/statistics/all")
    public ResponseEntity apiGetStatAll(HttpServletRequest request) {
        Map<String, Boolean> sett = getSettings();
        Boolean isPublic = sett.get("STATISTICS_IS_PUBLIC");
        if (request.getSession().getAttribute("user") != null
            && request.getSession().getAttribute("user").toString().length() > 0
            && isPublic
        ) {
            Map<String, String> mapStatMy = postService.statAll();
            Gson gson = new Gson();

            return new ResponseEntity(gson.toJson(mapStatMy), HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/image")
    public String apiPostImage(@RequestParam("image") MultipartFile uploadFile) throws IOException {
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

    @PostMapping("/moderation")
    public ResponseEntity apiPostImage(HttpServletRequest request, @RequestBody PostModeration postModeration) {
        if (request.getSession().getAttribute("user") != null
            && request.getSession().getAttribute("user").toString().length() > 0) {
            Integer moderatorId = Integer.parseInt(request.getSession().getAttribute("user").toString());
            postService.setModeration(postModeration, moderatorId);
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/tag")
    public ResponseEntity<List<TagDto>> apiGetTag(@RequestParam(value = "query",defaultValue = "") String query) {
        final List<TagDto> tagDtos = tagService.GetAllTags(query);
        return new ResponseEntity(tagDtos,HttpStatus.OK);
    }
}
