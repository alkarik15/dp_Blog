package ru.skillbox.blog.controller;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.skillbox.blog.component.HeaderProperties;
import ru.skillbox.blog.dto.CommentsParamDto;
import ru.skillbox.blog.dto.PostModeration;
import ru.skillbox.blog.dto.TagsDto;
import ru.skillbox.blog.model.GlobalSettingEntity;
import ru.skillbox.blog.service.FileService;
import ru.skillbox.blog.service.GlobalSettingService;
import ru.skillbox.blog.service.PostCommentService;
import ru.skillbox.blog.service.PostService;
import ru.skillbox.blog.service.TagService;
import ru.skillbox.blog.service.UserService;

/**
 * @author alkarik
 * @link http://alkarik
 */
@CrossOrigin
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
    private PostCommentService postCommentService;

    @Autowired
    private UserService userService;

    @Autowired
    private TagService tagService;

    @Autowired
    private FileService fileService;

    @Autowired
    ApiAuthController apiAuthController;

    @GetMapping("/init")
    public ResponseEntity<Map<String, String>> initHeader() {
        return new ResponseEntity(headerProperties.getHeader(), HttpStatus.OK);
    }

    @GetMapping("/settings")
    public ResponseEntity<Map<String, Boolean>> apiGetSettings(HttpServletRequest request) {
        Integer userId = apiAuthController.getUserIdFromSession(request);
        if (userService.isModerator(userId)) {
            return new ResponseEntity(getSettings(), HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }

    @PutMapping(value = "/settings")
    public ResponseEntity<Map<String, Boolean>> apiPutSettings(HttpServletRequest request, @RequestBody Map<String, Boolean> globalSettings) throws HttpMessageNotReadableException {
        Integer userId = apiAuthController.getUserIdFromSession(request);
        if (userService.isModerator(userId)) {
            //TODO Валидация входных данных ?
            for (Map.Entry<String, Boolean> entry : globalSettings.entrySet()) {
                globalSettingService.createSetting(
                    new GlobalSettingEntity(entry.getKey(), entry.getValue().equals(true) ? "YES" : "NO")
                );
            }
            return new ResponseEntity(getSettings(), HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
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
        Integer userId = apiAuthController.getUserIdFromSession(request);
        Map<String, String> mapStatMy = postService.statMy(userId);
        return new ResponseEntity(mapStatMy, HttpStatus.OK);
    }

    @GetMapping("/statistics/all")
    public ResponseEntity apiGetStatAll(HttpServletRequest request) {
        Map<String, Boolean> sett = getSettings();
        Boolean isPublic = sett.get("STATISTICS_IS_PUBLIC");
        Integer userId =apiAuthController.getUserIdFromSession(request);
        if (isPublic) {
            Map<String, String> mapStatMy = postService.statAll();
            Gson gson = new Gson();
            return new ResponseEntity(gson.toJson(mapStatMy), HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/image")
    public String apiPostImage(@RequestParam("image") MultipartFile uploadFile) throws IOException {
        return fileService.saveImage(uploadFile);
    }

    @PostMapping("/moderation")
    public ResponseEntity apiPostImage(HttpServletRequest request, @RequestBody PostModeration postModeration) {
        Integer moderatorId = apiAuthController.getUserIdFromSession(request);
        postService.setModeration(postModeration, moderatorId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/tag")
    public ResponseEntity apiGetTag(@RequestParam(value = "query", defaultValue = "") String query) {
        TagsDto tagsDto = new TagsDto(tagService.getAllTags(query));
        Gson gson = new Gson();
        return new ResponseEntity(gson.toJson(tagsDto), HttpStatus.OK);
    }
    @PostMapping("/comment")
    public ResponseEntity apiPostComment(HttpServletRequest request, @RequestBody CommentsParamDto commentsParamDto) {
        Integer userId = apiAuthController.getUserIdFromSession(request);
        Object result = postCommentService.saveComment(commentsParamDto, userId);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }
}
