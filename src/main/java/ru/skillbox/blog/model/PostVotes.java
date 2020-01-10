package ru.skillbox.blog.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

/**
 * @author alkarik
 * @link http://alkarik
 */
@Entity
public class PostVotes {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users userId;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Posts postId;

    @Column(nullable = false, columnDefinition = "DATETIME")
    private LocalDate time;

    @Column(nullable = false)
    private byte value;

    public PostVotes() {
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
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

    public LocalDate getTime() {
        return time;
    }

    public void setTime(final LocalDate time) {
        this.time = time;
    }

    public byte getValue() {
        return value;
    }

    public void setValue(final byte value) {
        this.value = value;
    }
}
