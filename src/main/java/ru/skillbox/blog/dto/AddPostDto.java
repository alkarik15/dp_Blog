package ru.skillbox.blog.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author alkarik
 * @link http://alkarik
 */
public class AddPostDto {
    private LocalDateTime time;

    private Boolean active;

    private String title;

    private String Text;

    private String tags;

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(String time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(time, formatter);
        this.time = dateTime;
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

    public String getTags() {
        return tags;
    }

    public void setTags(final String tags) {
        this.tags = tags;
    }
}
