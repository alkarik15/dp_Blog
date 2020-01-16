package ru.skillbox.blog.service;

import ru.skillbox.blog.model.CaptchaCodes;

/**
 * @author alkarik
 * @link http://alkarik
 */
public interface CaptchaService {
    void saveCaptcha(CaptchaCodes captcha);

    void deleteOldCapthas();
}
