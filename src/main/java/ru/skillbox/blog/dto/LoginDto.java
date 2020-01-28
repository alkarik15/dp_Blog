package ru.skillbox.blog.dto;

/**
 * @author alkarik
 * @link http://alkarik
 */
public class LoginDto {
    private String e_mail;
    private String password;

    public String getE_mail() {
        return e_mail;
    }

    public void setE_mail(final String e_mail) {
        this.e_mail = e_mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }
}
