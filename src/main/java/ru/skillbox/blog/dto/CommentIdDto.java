package ru.skillbox.blog.dto;

/**
 * @author alkarik
 * @link http://alkarik
 */
public class CommentIdDto {
    private Integer id;

    public CommentIdDto(final Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }
}
