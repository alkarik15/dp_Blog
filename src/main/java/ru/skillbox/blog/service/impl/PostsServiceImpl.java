package ru.skillbox.blog.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.skillbox.blog.model.ModerationStatus;
import ru.skillbox.blog.model.Posts;
import ru.skillbox.blog.repository.PostsRepository;
import ru.skillbox.blog.service.PostsService;

/**
 * @author alkarik
 * @link http://alkarik
 */
@Service
public class PostsServiceImpl implements PostsService {

    @Autowired
    private PostsRepository postsRepository;

    @Override
    public List<Posts> findAll() {
        List<Posts> posts = (List<Posts>) postsRepository.findAll();
        return posts;
    }

    @Override
    public void save(final Posts posts) {
        postsRepository.save(posts);
    }

    @Override
    public Posts getPostByIdModerationStatusActiveTime(
        final Integer id,
        final Byte isActive,
        final ModerationStatus moderationStatus,
        final LocalDateTime ldt) {
        return postsRepository.findByIdAndIsActiveAndModerationStatusAndTimeIsBefore(id, isActive, moderationStatus, ldt);
    }
}
