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
@Table(name = "post_votes")
public class PostVoteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity userId;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private PostEntity postId;

    @Column(nullable = false, columnDefinition = "DATETIME")
    private LocalDateTime time;

    @Column(nullable = false)
    private byte value;

    public PostVoteEntity() {
    }

    public PostVoteEntity(final UserEntity userId, final PostEntity postId, final LocalDateTime time, final byte value) {
        this.userId = userId;
        this.postId = postId;
        this.time = time;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
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

    public byte getValue() {
        return value;
    }

    public void setValue(final byte value) {
        this.value = value;
    }
}
