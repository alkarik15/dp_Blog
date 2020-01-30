package ru.skillbox.blog.controller;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
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
    public ResponseEntity<ResultsDto> apiAuthRegister(@RequestBody UserRegisterDto userRegister) {
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
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @GetMapping(value = "/captcha")
    @Transactional
    public ResponseEntity<CaptchaDto> apiAuthCaptcha() {

        captchaService.deleteOldCaptсhas();

        CaptchaDto captchaDto = captchaService.generateCaptchaDto();
        return new ResponseEntity(captchaDto, HttpStatus.OK);
    }


    @PostMapping(value = "/restore")
    public ResponseEntity<ResultsDto> apiPostRestore(HttpServletRequest request, @RequestBody EmailDto email) {
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
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @PostMapping(value = "/login")
    public ResponseEntity<ResultLoginDto> apiPostLogin(HttpServletRequest request, @RequestBody LoginDto loginDto) {
        ResultLoginDto resultLogin = userService.findUserByEmailAndPassword(loginDto);
        if (resultLogin.getUserLoginDto() != null) {
            resultLogin.setResult(true);
            request.getSession().setAttribute("user", resultLogin.getUserLoginDto().getId());
        } else {
            resultLogin.setResult(false);
        }
        return new ResponseEntity(resultLogin, HttpStatus.OK);
    }

    @GetMapping(value = "/logout")
    public ResponseEntity<ResultLoginDto> apiGetLogout(HttpServletRequest request) {
        ResultLoginDto resultLoginDto = new ResultLoginDto();
        if (request.getSession().getAttribute("user") != null) {
            request.getSession().setAttribute("user", "");
        }
        resultLoginDto.setResult(true);
        return new ResponseEntity(resultLoginDto, HttpStatus.OK);
    }

    @GetMapping(value = "/check")
    public ResponseEntity<ResultLoginDto> apiGetCheck(HttpServletRequest request) {
        ResultLoginDto resultLoginDto = new ResultLoginDto();
        if (request.getSession().getAttribute("user") != null && request.getSession().getAttribute("user").toString().length() > 0) {
            Integer userId = Integer.parseInt(request.getSession().getAttribute("user").toString());
            resultLoginDto.setResult(true);
            resultLoginDto.setUserLoginDto(userService.getUserLoginDto(userId));
        } else {
            resultLoginDto.setResult(false);
        }
        return new ResponseEntity(resultLoginDto, HttpStatus.OK);
    }
}
