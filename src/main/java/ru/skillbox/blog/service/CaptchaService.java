package ru.skillbox.blog.service;

import ru.skillbox.blog.dto.CaptchaDto;
import ru.skillbox.blog.model.CaptchaCodeEntity;

/**
 * @author alkarik
 * @link http://alkarik
 */
public interface CaptchaService {
    void saveCaptcha(CaptchaCodeEntity captcha);

    void deleteOldCaptсhas();

    CaptchaCodeEntity findCaptcha(String code, String secretCode);

    void deleteCaptcha(CaptchaCodeEntity captcha);

    CaptchaDto generateCaptchaDto();

}
