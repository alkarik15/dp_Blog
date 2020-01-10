package ru.skillbox.blog.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.time.LocalDate;
import java.util.List;

/**
 * @author alkarik
 * @link http://alkarik
 */
@Entity
public class Posts {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(nullable = false)
    private byte isActive;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ModerationStatus moderationStatus;

    @ManyToOne
    @JoinColumn(name = "moderator_id")
    private Users moderatorId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users userId;

    @Column(nullable = false, columnDefinition = "DATETIME")
    private LocalDate time;

    @Column(nullable = false)
    private String title;

    @Column(length = 65535, columnDefinition = "Text", nullable = false)
    private String text;

    @Column(nullable = false)
    private int viewCount;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
        name = "tag2post",
        joinColumns = {@JoinColumn(name = "post_id")},
        inverseJoinColumns = {@JoinColumn(name = "tag_id")}
    )
    private List<Tags> tags;

    public List<Tags> getTags() {
        return tags;
    }

    public void setTags(final List<Tags> tags) {
        this.tags = tags;
    }

    public Posts() {
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public byte getIsActive() {
        return isActive;
    }

    public void setIsActive(final byte isActive) {
        this.isActive = isActive;
    }

    public ModerationStatus getModerationStatus() {
        return moderationStatus;
    }

    public void setModerationStatus(final ModerationStatus moderationStatus) {
        this.moderationStatus = moderationStatus;
    }

    public Users getModeratorId() {
        return moderatorId;
    }

    public void setModeratorId(final Users moderatorId) {
        this.moderatorId = moderatorId;
    }

    public Users getUserId() {
        return userId;
    }

    public void setUserId(final Users userId) {
        this.userId = userId;
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
}
