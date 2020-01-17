package ru.skillbox.blog.repository;

import java.time.LocalDateTime;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.skillbox.blog.model.ModerationStatus;
import ru.skillbox.blog.model.Posts;

/**
 * @author alkarik
 * @link http://alkarik
 */
@Repository
public interface PostsRepository extends CrudRepository<Posts, Integer> {
    Posts findByIdAndIsActiveAndModerationStatusAndTimeIsBefore(Integer id, Byte isActive, ModerationStatus moderationStatus, LocalDateTime ldt);
}
