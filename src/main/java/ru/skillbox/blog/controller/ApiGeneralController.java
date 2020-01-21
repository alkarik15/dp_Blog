package ru.skillbox.blog.controller;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.skillbox.blog.component.HeaderProperties;
import ru.skillbox.blog.model.GlobalSettingEntity;
import ru.skillbox.blog.service.GlobalSettingService;
import ru.skillbox.blog.service.PostService;

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

    @GetMapping("/init/")
    public ResponseEntity initHeader() {
        StringBuilder sb = new StringBuilder("{");
        headerProperties.getHeader().forEach((s, s2) -> sb.append("'").append(s).append("': '").append(s2).append("',"));
        sb.append("}");
        return new ResponseEntity(sb, HttpStatus.OK);
    }

    @GetMapping("/settings/")
    public ResponseEntity apiGetSettings() {
        //TODO наличие Авторизация и модератор
        return new ResponseEntity(getSettings().toString(), HttpStatus.OK);
    }

    @PutMapping(value = "/settings/")
    public ResponseEntity apiPutSettings(@RequestBody Map<String, Boolean> globalSettings) throws HttpMessageNotReadableException {
        //TODO наличие Авторизация и модератор
        //TODO Валидация входных данных ?
        for (Map.Entry<String, Boolean> entry : globalSettings.entrySet()) {
            globalSettingService.createSetting(
                new GlobalSettingEntity(entry.getKey(), entry.getValue().equals(true) ? "YES" : "NO")
            );
        }
        return new ResponseEntity(getSettings().toString(), HttpStatus.OK);
    }

    private Map<String, Boolean> getSettings() {
        Map<String, Boolean> glSett = new HashMap<>();
        for (GlobalSettingEntity glSettings : globalSettingService.findAll()) {
            glSett.put(glSettings.getCode(), glSettings.getValue().equals("YES") ? true : false);
        }
        return glSett;
    }

    @GetMapping("/statistics/my/")
    public ResponseEntity apiGetStatMy(HttpServletRequest request) {
        if (request.getSession().getAttribute("user") != null && request.getSession().getAttribute("user").toString().length() > 0) {
            Integer userId = Integer.parseInt(request.getSession().getAttribute("user").toString());
            Map<String, String> mapStatMy = postService.statMy(userId);
            Gson gson = new Gson();

            return new ResponseEntity(gson.toJson(mapStatMy), HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/statistics/all/")
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
}
