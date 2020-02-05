package ru.skillbox.blog.controller;

import javax.servlet.http.HttpServletRequest;
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
import ru.skillbox.blog.dto.LoginDto;
import ru.skillbox.blog.dto.ResultLoginDto;
import ru.skillbox.blog.dto.ResultsDto;
import ru.skillbox.blog.dto.UserRegisterDto;
import ru.skillbox.blog.service.CaptchaService;
import ru.skillbox.blog.service.MailService;
import ru.skillbox.blog.service.UserService;

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
    private UserService userService;

    @Autowired
    private MailService mailService;

    @PostMapping(value = "/register")
    public ResponseEntity<ResultsDto> apiAuthRegister(@RequestBody UserRegisterDto userRegister) {
        ResultsDto result = userService.createUser(userRegister);
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @GetMapping(value = "/captcha")
    @Transactional
    public ResponseEntity<CaptchaDto> apiAuthCaptcha() {
        CaptchaDto captchaDto = captchaService.generateCaptchaDto();
        return new ResponseEntity(captchaDto, HttpStatus.OK);
    }


    @PostMapping(value = "/restore")
    public ResponseEntity<ResultsDto> apiPostRestore(HttpServletRequest request, @RequestBody String email) {
        ResultsDto result = new ResultsDto(mailService.sendEmail(request, email));
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
        Integer userId = userService.getUserIdFromSession(request);
        resultLoginDto.setResult(true);
        resultLoginDto.setUserLoginDto(userService.getUserLoginDto(userId));
        return new ResponseEntity(resultLoginDto, HttpStatus.OK);
    }
}
