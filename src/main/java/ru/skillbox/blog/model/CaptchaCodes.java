package ru.skillbox.blog.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

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
    private LocalDate time;

    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private String secretCode;

    public CaptchaCodes() {
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public LocalDate getTime() {
        return time;
    }

    public void setTime(final LocalDate time) {
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

