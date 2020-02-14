package ru.skillbox.blog.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.skillbox.blog.dto.CaptchaDto;
import ru.skillbox.blog.dto.LoginDto;
import ru.skillbox.blog.dto.ResultLoginDto;
import ru.skillbox.blog.dto.ResultsDto;
import ru.skillbox.blog.dto.UserRegisterDto;
import ru.skillbox.blog.exception.UserUnauthorizedException;
import ru.skillbox.blog.service.CaptchaService;
import ru.skillbox.blog.service.MailService;
import ru.skillbox.blog.service.UserService;

/**
 * @author alkarik
 * @link http://alkarik
 */
@CrossOrigin
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
    public ResultsDto apiAuthRegister(@RequestBody UserRegisterDto userRegister) {
        return userService.createUser(userRegister);
    }

    @GetMapping(value = "/captcha")
    @Transactional
    public ResponseEntity<CaptchaDto> apiAuthCaptcha() {
        CaptchaDto captchaDto = captchaService.generateCaptchaDto();
        return new ResponseEntity(captchaDto, HttpStatus.OK);
    }


    @PostMapping(value = "/restore")
    @ResponseStatus(HttpStatus.OK)
    public ResultsDto apiPostRestore(HttpServletRequest request, @RequestBody String email) {
        return new ResultsDto(mailService.sendEmail(request, email));
    }


    @PostMapping(value = "/login")
    public ResponseEntity<ResultLoginDto> apiPostLogin(HttpServletRequest request, @RequestBody LoginDto loginDto) {
        ResultLoginDto resultLogin = userService.findUserByEmailAndPassword(loginDto);
        if (resultLogin.getUserLoginDto() != null) {
            HttpSession newSession = request.getSession(true);
            newSession.setMaxInactiveInterval(10*60);
            resultLogin.setResult(true);
            newSession.setAttribute("user", resultLogin.getUserLoginDto().getId());
        } else {
            resultLogin.setResult(false);
        }
        return new ResponseEntity<>(resultLogin,HttpStatus.OK);
    }

    @GetMapping(value = "/logout")
    public ResultLoginDto apiGetLogout(HttpServletRequest request) {
        ResultLoginDto resultLoginDto = new ResultLoginDto();
        if (request.getSession().getAttribute("user") != null) {
            request.getSession().setAttribute("user", "");
        }
        resultLoginDto.setResult(true);
        return resultLoginDto;
    }

    @GetMapping(value = "/check")
    public ResultLoginDto apiGetCheck(HttpServletRequest request) {
        ResultLoginDto resultLoginDto = new ResultLoginDto();
        Integer userId = getUserIdFromSession(request);
        resultLoginDto.setResult(true);
        resultLoginDto.setUserLoginDto(userService.getUserLoginDto(userId));
        return resultLoginDto;
    }

    public Integer getUserIdFromSession(HttpServletRequest request) {
        if (request.getSession().getAttribute("user") != null && request.getSession().getAttribute("user").toString().length() > 0) {
            Integer userId = Integer.parseInt(request.getSession().getAttribute("user").toString());
            return userId;
        }
        throw new UserUnauthorizedException("User unauthorized");
    }
}
