package ru.skillbox.blog.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * @author alkarik
 * @link http://alkarik
 */
@Entity
@Table(name = "post_comments")
public class PostCommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private PostCommentEntity parentId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity userId;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private PostEntity postId;

    @Column(nullable = false, columnDefinition = "DATETIME")
    private LocalDateTime time;

    @Column(length = 4096)
    private String text;

    public PostCommentEntity() {
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public PostCommentEntity getParentId() {
        return parentId;
    }

    public void setParentId(final PostCommentEntity parentId) {
        this.parentId = parentId;
    }

    public UserEntity getUserId() {
        return userId;
    }

    public void setUserId(final UserEntity userId) {
        this.userId = userId;
    }

    public PostEntity getPostId() {
        return postId;
    }

    public void setPostId(final PostEntity postId) {
        this.postId = postId;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(final LocalDateTime time) {
        this.time = time;
    }

    public String getText() {
        return text;
    }

    public void setText(final String text) {
        this.text = text;
    }
}

