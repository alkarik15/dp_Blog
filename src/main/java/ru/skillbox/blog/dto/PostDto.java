package ru.skillbox.blog.dto;

import java.time.LocalDateTime;

/**
 * @author alkarik
 * @link http://alkarik
 */

public class PostDto {
    private int id;

    private UserDto user;

    private LocalDateTime time;

    private String title;

    private String text;

    private int viewCount;

    private int likes;

    private int dislikes;

    private int comments;

    public PostDto() {
    }

    public PostDto(final String title) {
        this.title = title;
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

    public int getComments() {
        return comments;
    }

    public void setComments(final int comments) {
        this.comments = comments;
    }

    public void setDislikes(final int dislikes) {
        this.dislikes = dislikes;
    }

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

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(final LocalDateTime time) {
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
}
