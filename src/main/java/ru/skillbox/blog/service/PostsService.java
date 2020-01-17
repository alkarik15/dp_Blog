package ru.skillbox.blog.service;

import java.time.LocalDateTime;
import java.util.List;
import ru.skillbox.blog.model.ModerationStatus;
import ru.skillbox.blog.model.Posts;

/**
 * @author alkarik
 * @link http://alkarik
 */
public interface PostsService {
    List<Posts> findAll();

    void save(Posts posts);

    Posts getPostByIdModerationStatusActiveTime(
        Integer id,
        Byte isActive,
        ModerationStatus moderationStatus,
        LocalDateTime ldt);
}
