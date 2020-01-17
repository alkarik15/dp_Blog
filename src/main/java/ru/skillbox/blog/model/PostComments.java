package ru.skillbox.blog.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

/**
 * @author alkarik
 * @link http://alkarik
 */
@Entity
public class PostComments {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private PostComments parentId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users userId;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Posts postId;

    @Column(nullable = false, columnDefinition = "DATETIME")
    private LocalDateTime time;

    @Column(length = 4096)
    private String text;

    public PostComments() {
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public PostComments getParentId() {
        return parentId;
    }

    public void setParentId(final PostComments parentId) {
        this.parentId = parentId;
    }

    public Users getUserId() {
        return userId;
    }

    public void setUserId(final Users userId) {
        this.userId = userId;
    }

    public Posts getPostId() {
        return postId;
    }

    public void setPostId(final Posts postId) {
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

