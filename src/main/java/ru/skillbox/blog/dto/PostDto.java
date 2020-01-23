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

    private String annonce;

    private Integer viewCount;

    private Integer likes;

    private Integer dislikes;

    private Integer comments;

    public PostDto() {
    }

    public PostDto(final String title) {
        this.title = title;
    }

    public String getAnnonce() {
        return annonce;
    }

    public void setAnnonce(final String annonce) {
        this.annonce = annonce;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(final Integer likes) {
        this.likes = likes;
    }

    public Integer getDislikes() {
        return dislikes;
    }

    public void setDislikes(final Integer dislikes) {
        this.dislikes = dislikes;
    }

    public Integer getComments() {
        return comments;
    }

    public void setComments(final Integer comments) {
        this.comments = comments;
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

    public Integer getViewCount() {
        return viewCount;
    }

    public void setViewCount(final Integer viewCount) {
        this.viewCount = viewCount;
    }
}
