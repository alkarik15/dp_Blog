package ru.skillbox.blog.dto;

import java.time.LocalDateTime;

/**
 * @author alkarik
 * @link http://alkarik
 */
public class AddPostDto {
    private String time;

    private LocalDateTime ldt;

    private Boolean active;

    private String title;

    private String Text;

    private String[] tags;

    public LocalDateTime getLdt() {
        return ldt;
    }

    public void setLdt(final LocalDateTime ldt) {
        this.ldt = ldt;
    }

    public String getTime() {
        return time;
    }

    public void setTime(final String time) {
        this.time = time;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(final Boolean active) {
        this.active = active;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getText() {
        return Text;
    }

    public void setText(final String text) {
        Text = text;
    }

    public void setTime(final LocalDateTime time) {
        this.ldt = time;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(final String[] tags) {
        this.tags = tags;
    }
}
