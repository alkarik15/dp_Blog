package ru.skillbox.blog.service.impl;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skillbox.blog.dto.CaptchaDto;
import ru.skillbox.blog.model.CaptchaCodeEntity;
import ru.skillbox.blog.repository.CaptchaRepository;
import ru.skillbox.blog.service.CaptchaService;
import ru.skillbox.blog.utils.Captcha;

/**
 * @author alkarik
 * @link http://alkarik
 */
@Service
@Transactional(readOnly = true)
public class CaptchaServiceImpl implements CaptchaService {
    @Autowired
    private CaptchaRepository captchaRepository;

    @Value("${captcha.deleteAfterMin}")
    private int deleteAfterMin;

    @Override
    @Transactional(readOnly = false)
    public void saveCaptcha(final CaptchaCodeEntity captcha) {
        captchaRepository.save(captcha);
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteOldCaptсhas() {
        LocalDateTime ldt = LocalDateTime.now().minusMinutes(deleteAfterMin);
        captchaRepository.deleteAllByTimeBefore(ldt);
    }

    @Override
    public CaptchaCodeEntity findCaptcha(final String code, final String secretCode) {
        return captchaRepository.findByCodeAndSecretCode(code, secretCode);
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteCaptcha(final CaptchaCodeEntity captcha) {
        captchaRepository.delete(captcha);
    }

    @Override
    public CaptchaDto generateCaptchaDto() {
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
            saveCaptcha(captcha);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new CaptchaDto(strSecret, encoded);
    }
}
