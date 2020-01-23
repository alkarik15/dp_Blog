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

    Page<PostEntity> findAllByIsActiveAndModerationStatusAndTimeIsBeforeAndTextContains(Byte isActive, ModerationStatus moderationStatus, LocalDateTime ldt, String query, Pageable pageable);

    Page<PostEntity> findAllByIsActiveAndModerationStatus(Byte isActive, ModerationStatus moderationStatus, Pageable pageable);

    @Query(nativeQuery = true, value = "SELECT COUNT(DISTINCT p.id) as postCount, SUM(p.view_count) as showCount, MIN(p.time) as firstPubl " +
        "FROM posts p WHERE p.user_id=?")
    List<Object[]> statPostShowMy(Integer id);

    @Query(nativeQuery = true, value = "SELECT COUNT(DISTINCT p.id) as postCount, SUM(p.view_count) as showCount, MIN(p.time) as firstPubl " +
        "FROM posts p")
    List<Object[]> statPostShow();

    Integer countAllByIsActiveAndModerationStatusAndTimeIsBefore(Byte isActive, ModerationStatus moderationStatus, LocalDateTime ldt);

    Integer countAllByIsActiveAndModerationStatusAndTimeIsBeforeAndTextContains(Byte isActive, ModerationStatus moderationStatus, LocalDateTime ldt, String query);

//    @Query(nativeQuery = true, value = "SELECT p.id,p.time,p.user_id,p.title,p.text, p.view_count as viewCount, SUM(IF(pv.value=1,1,0)) AS likeCount, " +
//        " SUM(IF(pv.value=-1,1,0)) AS dislikCount, COUNT(DISTINCT(pc.id)) AS commentCount " +
//    " FROM posts p " +
//    " LEFT JOIN post_votes pv ON p.id = pv.post_id " +
//    " LEFT JOIN post_comments pc ON p.id = pc.post_id " +
//    " WHERE p.is_active=? AND  p.moderation_status=? AND  p.time<=? " +
//    " GROUP BY p.id LIMIT ?,?")
//   List<Object[]> fff(Byte isActive, String moderationStatus, LocalDateTime ldt, int offset,int limit);
}
