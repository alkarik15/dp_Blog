package ru.skillbox.blog.controller;

import java.util.HashMap;
import java.util.Map;
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
import ru.skillbox.blog.model.GlobalSettings;
import ru.skillbox.blog.service.GlobalSettingsService;

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
    private GlobalSettingsService globalSettingsService;

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
            globalSettingsService.createSetting(
                new GlobalSettings(entry.getKey(), entry.getValue().equals(true) ? "YES" : "NO")
            );
        }
        return new ResponseEntity(getSettings().toString(), HttpStatus.OK);
    }

    private Map<String, Boolean> getSettings() {
        Map<String, Boolean> glSett = new HashMap<>();
        for (GlobalSettings glSettings : globalSettingsService.findAll()) {
            glSett.put(glSettings.getCode(), glSettings.getValue().equals("YES") ? true : false);
        }
        return glSett;
    }
}
