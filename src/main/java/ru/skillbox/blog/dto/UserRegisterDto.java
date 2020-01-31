package ru.skillbox.blog.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author alkarik
 * @link http://alkarik
 */
public class UserRegisterDto {
    @JsonProperty("e_mail")
    private String email;

    private String name;

    private String password;

    private String captcha;

    @JsonProperty("captcha_secret")
    private String captchaSecret;

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(final String captcha) {
        this.captcha = captcha;
    }

    public String getCaptchaSecret() {
        return captchaSecret;
    }

    public void setCaptchaSecret(final String captchaSecret) {
        this.captchaSecret = captchaSecret;
    }
}
