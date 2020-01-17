package ru.skillbox.blog.dto;

/**
 * @author alkarik
 * @link http://alkarik
 */
public class UserRegisterDto {
    private String email;
    private String name;
    private String password;
    private String captcha;
    private String captcha_secret;

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

    public String getCaptcha_secret() {
        return captcha_secret;
    }

    public void setCaptcha_secret(final String captcha_secret) {
        this.captcha_secret = captcha_secret;
    }
}
