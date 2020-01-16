package ru.skillbox.blog.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.skillbox.blog.model.Tags;
import ru.skillbox.blog.repository.TagsRepository;
import ru.skillbox.blog.service.TagsService;

/**
 * @author alkarik
 * @link http://alkarik
 */
@Service
public class TagsServiceImpl implements TagsService {

    @Autowired
    private TagsRepository tagsRepository;

    @Override
    public Tags addTags(final String tagName) {
        Tags tagsByName = tagsRepository.findTagsByName(tagName);
        if(tagsByName==null){
            tagsByName=tagsRepository.save(new Tags(tagName));
        }
        return tagsByName;
    }
}
