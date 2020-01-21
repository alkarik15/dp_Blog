package ru.skillbox.blog.controller;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.skillbox.blog.dto.CaptchaDto;
import ru.skillbox.blog.dto.EmailDto;
import ru.skillbox.blog.dto.LoginDto;
import ru.skillbox.blog.dto.ResultLoginDto;
import ru.skillbox.blog.dto.ResultsDto;
import ru.skillbox.blog.dto.UserRegisterDto;
import ru.skillbox.blog.model.CaptchaCodeEntity;
import ru.skillbox.blog.model.UserEntity;
import ru.skillbox.blog.service.CaptchaService;
import ru.skillbox.blog.service.UserService;
import ru.skillbox.blog.utils.Captcha;
import ru.skillbox.blog.utils.MailConstructor;

/**
 * @author alkarik
 * @link http://alkarik
 */
@RestController
@RequestMapping("/api/auth")
public class ApiAuthController {
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private CaptchaService captchaService;

    @Autowired
    private UserService userService;

    @Autowired
    private MailConstructor mailConstructor;

    @PostMapping(value = "/register")
    public ResponseEntity apiAuthRegister(@RequestBody UserRegisterDto userRegister) {
        Map<String, String> errors = new HashMap<>();
        if (userRegister.getName() == null || userRegister.getName().length() == 0) {
            errors.put("name", "Имя указано не верно");
        }
        if (userRegister.getPassword().length() < 6) {
            errors.put("password", "Пароль короче 6-ти символов");
        }
        final CaptchaCodeEntity captcha = captchaService.findCaptcha(userRegister.getCaptcha(), userRegister.getCaptchaSecret());
        if (captcha == null) {
            errors.put("captcha", "Код с картинки введен не верно");
        }
        if (userService.existEmail(userRegister.getEmail())) {
            errors.put("email", "Этот e-mail уже зарегистрирован");
        }
        ResultsDto result = new ResultsDto();
        if (errors.size() == 0) {
            userService.addUser(new UserEntity(LocalDateTime.now(), userRegister.getName(), userRegister.getEmail(), userRegister.getPassword()));
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
    public ResponseEntity apiAuthCaptcha() {

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
            CaptchaCodeEntity captcha = new CaptchaCodeEntity(LocalDateTime.now(), strCode, strSecret);
            captchaService.saveCaptcha(captcha);
        } catch (IOException e) {
            e.printStackTrace();
        }
        CaptchaDto captchaDto = new CaptchaDto(strSecret, encoded);
        Gson gson = new Gson();
        return new ResponseEntity(gson.toJson(captchaDto), HttpStatus.OK);
    }

    @PostMapping(value = "/restore")
    public ResponseEntity apiPostRestore(HttpServletRequest request, @RequestBody EmailDto email) {
        final UserEntity user = userService.findEmail(email.getEmail());
        ResultsDto result = new ResultsDto();
        if (user != null) {
            String token = UUID.randomUUID().toString();
            String appUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
            SimpleMailMessage newEmail = mailConstructor.constructResetPassword(appUrl, user.getEmail(), token);
            mailSender.send(newEmail);

            result.setResult(true);
        } else {
            result.setResult(false);

        }
        Gson gson = new Gson();
        return new ResponseEntity(gson.toJson(result), HttpStatus.OK);
    }

    @PostMapping(value = "/login")
    public ResponseEntity apiPostLogin(HttpServletRequest request, @RequestBody LoginDto loginDto) {
        ResultLoginDto resultLogin = userService.findUserByEmailAndPassword(loginDto);
        if (resultLogin.getUserLoginDto() != null) {
            resultLogin.setResult(true);
            request.getSession().setAttribute("user", resultLogin.getUserLoginDto().getId());
        } else {
            resultLogin.setResult(false);
        }
        Gson gson = new Gson();
        return new ResponseEntity(gson.toJson(resultLogin), HttpStatus.OK);
    }

    @GetMapping(value = "/logout")
    public ResponseEntity apiGetLogout(HttpServletRequest request) {
        ResultLoginDto resultLoginDto = new ResultLoginDto();
        if (request.getSession().getAttribute("user") != null) {
            request.getSession().setAttribute("user", "");
        }
        resultLoginDto.setResult(true);
        Gson gson = new Gson();
        return new ResponseEntity(gson.toJson(resultLoginDto), HttpStatus.OK);
    }

    @GetMapping(value = "/check")
    public ResponseEntity apiGetCheck(HttpServletRequest request) {
        ResultLoginDto resultLoginDto = new ResultLoginDto();
        if (request.getSession().getAttribute("user") != null && request.getSession().getAttribute("user").toString().length() > 0) {
            resultLoginDto.setResult(true);
        } else {
            resultLoginDto.setResult(false);
        }
        Gson gson = new Gson();
        return new ResponseEntity(gson.toJson(resultLoginDto), HttpStatus.OK);
    }
}
