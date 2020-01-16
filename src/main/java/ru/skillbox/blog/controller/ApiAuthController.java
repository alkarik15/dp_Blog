package ru.skillbox.blog.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Base64;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.skillbox.blog.dto.CaptchaDto;
import ru.skillbox.blog.model.CaptchaCodes;
import ru.skillbox.blog.service.CaptchaService;
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


    @PostMapping(value = "/register")
    public ResponseEntity apiAuthRegister() throws HttpMessageNotReadableException {

        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping(value = "/captcha")
    @Transactional
    public String apiAuthCaptcha() {

        captchaService.deleteOldCapthas();

        String chars="abdefhknrstyz23456789";
        int length=(int)Math.round(Math.random()*2)+4;
        int numChars=chars.length();
        String strCode="";
        for(int i=0;i<length;i++){
            final int round = (int) Math.round(Math.random() * numChars-1);
            strCode+=chars.charAt(round);
        }
        String strSecret="";
        for(int i=0;i<20;i++){
            final int round = (int) Math.round(Math.random() * numChars-1);
            strSecret+=chars.charAt(round);
        }
        String encoded="";
        try {
            final byte[] captchaPng = Captcha.getCaptcha(strCode, "png");
            encoded = Base64.getEncoder().encodeToString(captchaPng);
            CaptchaCodes captcha=new CaptchaCodes(LocalDateTime.now(),strCode,strSecret);
            captchaService.saveCaptcha(captcha);
        } catch (IOException e) {
            e.printStackTrace();
        }
        CaptchaDto captchaDto = new CaptchaDto(strSecret,encoded);
        Gson gson = new Gson();
        return gson.toJson(captchaDto);
    }

}
