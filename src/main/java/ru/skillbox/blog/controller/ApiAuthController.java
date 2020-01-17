package ru.skillbox.blog.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.skillbox.blog.dto.CaptchaDto;
import ru.skillbox.blog.dto.ResultsDto;
import ru.skillbox.blog.dto.UserRegisterDto;
import ru.skillbox.blog.model.CaptchaCodes;
import ru.skillbox.blog.model.Users;
import ru.skillbox.blog.service.CaptchaService;
import ru.skillbox.blog.service.UsersService;
import ru.skillbox.blog.utils.Captcha;

/**
 * @author alkarik
 * @link http://alkarik
 */
@RestController
@RequestMapping("/api/auth")
public class ApiAuthController {
    @Autowired
    private CaptchaService captchaService;

    @Autowired
    private UsersService usersService;

    @PostMapping(value = "/register")
    public ResponseEntity apiAuthRegister(@RequestBody UserRegisterDto userRegister) {
        List<String> errors = new ArrayList<>();
        if (userRegister.getName() == null || userRegister.getName().length() == 0) {
            errors.add("Имя указано не верно");
        }
        if (userRegister.getPassword().length() < 6) {
            errors.add("Пароль короче 6-ти символов");
        }
        final CaptchaCodes captcha = captchaService.findCaptcha(userRegister.getCaptcha(), userRegister.getCaptcha_secret());
        if (captcha == null) {
            errors.add("Код с картинки введен не верно");
        }
        if (usersService.existEmail(userRegister.getEmail())) {
            errors.add("Этот e-mail уже зарегистрирован");
        }
        ResultsDto result = new ResultsDto();
        if (errors.size() == 0) {
            usersService.addUser(new Users(LocalDateTime.now(), userRegister.getName(), userRegister.getEmail(), userRegister.getPassword()));
            captchaService.deleteCaptcha(captcha);
            result.setResult(true);
        } else {
            result.setResult(false);
            result.setErrors(errors);
        }
        Gson gson = new Gson();
        return new ResponseEntity(gson.toJson(result), HttpStatus.OK);
    }

    @GetMapping(value = "/captcha")
    @Transactional
    public String apiAuthCaptcha() {

        captchaService.deleteOldCaptсhas();

        String chars = "abdefhknrstyz23456789";
        int length = (int) Math.round(Math.random() * 2) + 4;
        int numChars = chars.length() - 1;
        String strCode = "";
        for (int i = 0; i < length; i++) {
            final int round = (int) Math.round(Math.random() * numChars);
            strCode += chars.charAt(round);
        }
        String strSecret = "";
        for (int i = 0; i < 20; i++) {
            final int round = (int) Math.round(Math.random() * numChars);
            strSecret += chars.charAt(round);
        }
        String encoded = "";
        try {
            final byte[] captchaPng = Captcha.getCaptcha(strCode, "png");
            encoded = Base64.getEncoder().encodeToString(captchaPng);
            CaptchaCodes captcha = new CaptchaCodes(LocalDateTime.now(), strCode, strSecret);
            captchaService.saveCaptcha(captcha);
        } catch (IOException e) {
            e.printStackTrace();
        }
        CaptchaDto captchaDto = new CaptchaDto(strSecret, encoded);
        Gson gson = new Gson();
        return gson.toJson(captchaDto);
    }
}
