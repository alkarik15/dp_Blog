package ru.skillbox.blog.service.impl;

import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skillbox.blog.model.TagEntity;
import ru.skillbox.blog.repository.TagsRepository;
import ru.skillbox.blog.service.TagService;

/**
 * @author alkarik
 * @link http://alkarik
 */
@Service
@Transactional(readOnly = true)
public class TagServiceImpl implements TagService {

    @Autowired
    private TagsRepository tagsRepository;

    @Override
    @Transactional(readOnly = false)
    public TagEntity saveOrGetTag(final String tagName) {
        TagEntity tagsByName = tagsRepository.findTagsByName(tagName);
        if (tagsByName == null) {
            tagsByName = tagsRepository.save(new TagEntity(tagName));
        }
        return tagsByName;
    }

    @Override
    public Set<TagEntity> collectTags(String tags) {
        Set<TagEntity> setTags = new HashSet<>();
        final String[] splitTags = tags.split(",");
        for (String splitTag : splitTags) {
            TagEntity ta = saveOrGetTag(splitTag);
            setTags.add(ta);
        }
        return setTags;
    }
}
