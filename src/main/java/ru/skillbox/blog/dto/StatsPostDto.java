package ru.skillbox.blog.dto;

/**
 * @author alkarik
 * @link http://alkarik
 */

public class StatsPostDto {

    private Integer likes;

    private Integer dislikes;

    private Integer commentCount;

    public StatsPostDto(final Integer likes, final Integer dislikes, final Integer commentCount) {
        this.likes = likes;
        this.dislikes = dislikes;
        this.commentCount = commentCount;
    }

    public StatsPostDto(final Integer likes, final Integer dislikes) {
        this.likes = likes;
        this.dislikes = dislikes;
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

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(final Integer commentCount) {
        this.commentCount = commentCount;
    }
}
