package ru.skillbox.blog.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

/**
 * @author alkarik
 * @link http://alkarik
 */
@Entity
public class CaptchaCodes {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(nullable = false,columnDefinition="DATETIME")
    private LocalDateTime time;

    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private String secretCode;

    public CaptchaCodes() {
    }

    public CaptchaCodes(final LocalDateTime time, final String code, final String secretCode) {
        this.time = time;
        this.code = code;
        this.secretCode = secretCode;
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(final LocalDateTime time) {
        this.time = time;
    }

    public String getCode() {
        return code;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    public String getSecretCode() {
        return secretCode;
    }

    public void setSecretCode(final String secretCode) {
        this.secretCode = secretCode;
    }
}

