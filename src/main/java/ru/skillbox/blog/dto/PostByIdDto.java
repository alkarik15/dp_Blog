package ru.skillbox.blog.dto;

import java.time.LocalDate;
import java.util.List;

/**
 * @author alkarik
 * @link http://alkarik
 */

public class PostByIdDto {
    private int id;

    private UserDto user;

    private LocalDate time;

    private String title;

    private String text;

    private int viewCount;

    private int likes;

    private int dislikes;

    private List<CommentsDto> comments;

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(final UserDto user) {
        this.user = user;
    }

    public LocalDate getTime() {
        return time;
    }

    public void setTime(final LocalDate time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(final String text) {
        this.text = text;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(final int viewCount) {
        this.viewCount = viewCount;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(final int likes) {
        this.likes = likes;
    }

    public int getDislikes() {
        return dislikes;
    }

    public void setDislikes(final int dislikes) {
        this.dislikes = dislikes;
    }

    public List<CommentsDto> getComments() {
        return comments;
    }

    public void setComments(final List<CommentsDto> comments) {
        this.comments = comments;
    }
}
