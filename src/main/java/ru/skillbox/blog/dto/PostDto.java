package ru.skillbox.blog.dto;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonProperty;

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

    @JsonProperty("viewCount")
    private Integer viewCount;

    @JsonProperty("likeCount")
    private Integer likeCount;

    @JsonProperty("dislikeCount")
    private Integer dislikeCount;

    @JsonProperty("commentCount")
    private Integer commentCount;

    private String[] tags;

    public PostDto() {
    }

    public PostDto(final String title) {
        this.title = title;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(final String[] tags) {
        this.tags = tags;
    }

    public String getAnnonce() {
        return annonce;
    }

    public void setAnnonce(final String annonce) {
        this.annonce = annonce;
    }

    public Integer getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(final Integer likeCount) {
        this.likeCount = likeCount;
    }

    public Integer getDislikeCount() {
        return dislikeCount;
    }

    public void setDislikeCount(final Integer dislikeCount) {
        this.dislikeCount = dislikeCount;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(final Integer commentCount) {
        this.commentCount = commentCount;
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
