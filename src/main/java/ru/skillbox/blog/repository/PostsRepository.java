package ru.skillbox.blog.repository;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.skillbox.blog.model.PostEntity;
import ru.skillbox.blog.model.enums.ModerationStatus;

/**
 * @author alkarik
 * @link http://alkarik
 */
@Repository
public interface PostsRepository extends JpaRepository<PostEntity, Integer> {
    PostEntity findByIdAndIsActiveAndModerationStatusAndTimeIsBefore(Integer id, Byte isActive, ModerationStatus moderationStatus, LocalDateTime ldt);

    long count();

    Page<PostEntity> findAllByIsActiveAndModerationStatusAndTimeIsBefore(Byte isActive, ModerationStatus moderationStatus, LocalDateTime ldt, Pageable pageable);

    @Query(nativeQuery = true, value = "SELECT COUNT(DISTINCT p.id) as postCount, SUM(p.view_count) as showCount, MIN(p.time) as firstPubl " +
        "FROM posts p WHERE p.user_id=?")
    List<Object[]> statPostShowMy(Integer id);

    @Query(nativeQuery = true, value = "SELECT COUNT(DISTINCT p.id) as postCount, SUM(p.view_count) as showCount, MIN(p.time) as firstPubl " +
        "FROM posts p")
    List<Object[]> statPostShow();

}
