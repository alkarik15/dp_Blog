package ru.skillbox.blog.dto;

import java.time.LocalDateTime;

/**
 * @author alkarik
 * @link http://alkarik
 */
public class CommentsDto {
    private Integer id;

    private LocalDateTime time;

    private UserDto user;

    private String text;

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(final LocalDateTime time) {
        this.time = time;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(final UserDto user) {
        this.user = user;
    }

    public String getText() {
        return text;
    }

    public void setText(final String text) {
        this.text = text;
    }
}
