package ru.skillbox.blog.service.impl;

import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.skillbox.blog.model.CaptchaCodes;
import ru.skillbox.blog.repository.CaptchaRepository;
import ru.skillbox.blog.service.CaptchaService;

/**
 * @author alkarik
 * @link http://alkarik
 */
@Service
public class CaptchaServiceImpl implements CaptchaService {
    @Autowired
    private CaptchaRepository captchaRepository;

    @Value("${captcha.deleteAfterMin}")
    private int deleteAfterMin;

    @Override
    public void saveCaptcha(final CaptchaCodes captcha) {
        captchaRepository.save(captcha);
    }

    @Override
    public void deleteOldCaptсhas() {
        LocalDateTime ldt = LocalDateTime.now().minusMinutes(deleteAfterMin);
        captchaRepository.deleteAllByTimeBefore(ldt);
    }

    @Override
    public CaptchaCodes findCaptcha(final String code, final String secretCode) {
        return captchaRepository.findByCodeAndSecretCode(code, secretCode);
    }

    @Override
    public void deleteCaptcha(final CaptchaCodes captcha) {
        captchaRepository.delete(captcha);
    }
}
