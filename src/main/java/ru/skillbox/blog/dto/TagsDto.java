package ru.skillbox.blog.dto;

import java.util.List;

/**
 * @author alkarik
 * @link http://alkarik
 */
public class TagsDto {

    private List<TagDto> tags;

    public List<TagDto> getTags() {
        return tags;
    }

    public void setTags(final List<TagDto> tags) {
        this.tags = tags;
    }

    public TagsDto(final List<TagDto> tags) {
        this.tags = tags;
    }

    public TagsDto() {
    }
}
