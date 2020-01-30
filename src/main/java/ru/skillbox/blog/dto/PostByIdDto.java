package ru.skillbox.blog.dto;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author alkarik
 * @link http://alkarik
 */

public class PostByIdDto {
    private int id;

    @JsonProperty("user")
    private UserDto userId;

    private String time;

    private String title;

    private String text;

    private String announce;

    private int viewCount;

    private int likeCount;

    private int dislikeCount;

    private List<CommentsDto> comments;

    private Integer commentCount;

    private String[] tags;

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(final Integer commentCount) {
        this.commentCount = commentCount;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(final String[] tags) {
        this.tags = tags;
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public UserDto getUserId() {
        return userId;
    }

    public void setUserId(final UserDto userId) {
        this.userId = userId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(final String time) {
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

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(final int likeCount) {
        this.likeCount = likeCount;
    }

    public int getDislikeCount() {
        return dislikeCount;
    }

    public void setDislikeCount(final int dislikeCount) {
        this.dislikeCount = dislikeCount;
    }

    public List<CommentsDto> getComments() {
        return comments;
    }

    public void setComments(final List<CommentsDto> comments) {
        this.comments = comments;
    }

    public String getAnnounce() {
        return announce;
    }

    public void setAnnounce(final String announce) {
        this.announce = announce;
    }

}
