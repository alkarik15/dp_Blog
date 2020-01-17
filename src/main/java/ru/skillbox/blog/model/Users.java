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
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(nullable = false)
    private byte isModerator;

    @Column(nullable = false, columnDefinition = "DATETIME")
    private LocalDateTime regTime;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    private String code;

    @Column(length = 65535, columnDefinition = "Text")
    private String photo;

    public Users() {
    }

    public Users(final LocalDateTime regTime, final String name, final String email, final String password) {
        this.regTime = regTime;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public byte getIsModerator() {
        return isModerator;
    }

    public void setIsModerator(final byte isModerator) {
        this.isModerator = isModerator;
    }

    public LocalDateTime getRegTime() {
        return regTime;
    }

    public void setRegTime(final LocalDateTime regTime) {
        this.regTime = regTime;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public String getCode() {
        return code;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(final String photo) {
        this.photo = photo;
    }
}
