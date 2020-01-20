package ru.skillbox.blog.service;

import java.util.Set;
import ru.skillbox.blog.model.TagEntity;

/**
 * @author alkarik
 * @link http://alkarik
 */
public interface TagService {
    TagEntity saveOrGetTag(String tagName);
    Set<TagEntity> collectTags(String tags);
}
