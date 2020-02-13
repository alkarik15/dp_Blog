package ru.skillbox.blog.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skillbox.blog.dto.TagDto;
import ru.skillbox.blog.dto.projection.TagType;
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
    public Set<TagEntity> collectTags(String[] tags) {
        Set<TagEntity> setTags = new HashSet<>();
        for (String splitTag : tags) {
            TagEntity ta = saveOrGetTag(splitTag);
            setTags.add(ta);
        }
        return setTags;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TagDto> GetAllTags(final String tagName) {
        final List<TagType> tags = tagsRepository.tagsCountInPost();
        Boolean fistRow = true;
        Integer maxCount = 1;
        List<TagDto> tagsDto = new ArrayList<>();

        for (TagType tag : tags) {
            if (fistRow) {
                fistRow = false;
                maxCount = tag.getCount();
            }
            Float normWeight = (float) tag.getCount() / maxCount;
            final String nameTag = tag.getName();
            final Integer tagId = tag.getId();
            if (tagName.equals("")) {
                tagsDto.add(new TagDto(tagId, nameTag, normWeight));
            } else {
                if (nameTag.startsWith(tagName)) {
                    tagsDto.add(new TagDto(tagId, nameTag, normWeight));
                    break;
                }
            }
        }
        return tagsDto;
    }
}
