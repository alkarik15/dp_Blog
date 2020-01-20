package ru.skillbox.blog.service.impl;

import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skillbox.blog.model.CaptchaCodeEntity;
import ru.skillbox.blog.repository.CaptchaRepository;
import ru.skillbox.blog.service.CaptchaService;

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
}
