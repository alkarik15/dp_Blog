package ru.skillbox.blog.repository;

import java.time.LocalDateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.skillbox.blog.dto.projection.StatPostsShow;
import ru.skillbox.blog.model.PostEntity;
import ru.skillbox.blog.model.UserEntity;
import ru.skillbox.blog.model.enums.ModerationStatus;

/**
 * @author alkarik
 * @link http://alkarik
 */
@Repository
public interface PostsRepository extends JpaRepository<PostEntity, Integer> {
    @Query(nativeQuery = true, value = "SELECT * FROM posts p WHERE p.id = ? and p.time <= ? and ( is_active = ? or moderation_status = ?)")
    PostEntity findByIdAndIsActiveORModerationStatusAndTimeIsBeforeNative(Integer id, LocalDateTime ldt, Boolean isActive, ModerationStatus moderationStatus);

    long count();

    Page<PostEntity> findAllByIsActiveAndModerationStatusAndTimeIsBefore(Boolean isActive, ModerationStatus moderationStatus, LocalDateTime ldt, Pageable pageable);

    Page<PostEntity> findAllByUserIdAndIsActiveAndModerationStatusAndTimeIsBefore(UserEntity user, Boolean isActive, ModerationStatus moderationStatus, LocalDateTime ldt, Pageable pageable);

    Page<PostEntity> findAllByIsActiveAndTimeIsBefore(Boolean isActive, LocalDateTime ldt, Pageable pageable);

    Page<PostEntity> findAllByUserIdAndIsActiveAndTimeIsBefore(UserEntity user, Boolean isActive, LocalDateTime ldt, Pageable pageable);

    Page<PostEntity> findAllByIsActiveAndModerationStatusAndTimeIsBeforeAndTextContains(Boolean isActive, ModerationStatus moderationStatus, LocalDateTime ldt, String query, Pageable pageable);

    Page<PostEntity> findAllByIsActiveAndModerationStatus(Boolean isActive, ModerationStatus moderationStatus, Pageable pageable);


    @Query(nativeQuery = true, value = "SELECT COUNT(DISTINCT p.id) as postCount, SUM(p.view_count) as showCount, MIN(p.time) as firstPubl " +
        "FROM posts p WHERE p.user_id=?")
    StatPostsShow statPostShowMy(Integer id);

    @Query(nativeQuery = true, value = "SELECT COUNT(DISTINCT p.id) as postCount, SUM(p.view_count) as showCount, MIN(p.time) as firstPubl " +
        "FROM posts p")
    StatPostsShow statPostShow();

    Integer countAllByIsActiveAndModerationStatus(Boolean isActive, ModerationStatus moderationStatus);

    Integer countAllByIsActiveAndModerationStatusAndTimeIsBefore(Boolean isActive, ModerationStatus moderationStatus, LocalDateTime ldt);

    Integer countAllByIsActiveAndModerationStatusAndTimeIsBeforeAndTextContains(Boolean isActive, ModerationStatus moderationStatus, LocalDateTime ldt, String query);

    PostEntity findAllById(Integer id);
//    @Query(nativeQuery = true, value = "SELECT p.id,p.time,p.user_id,p.title,p.text, p.view_count as viewCount, SUM(IF(pv.value=1,1,0)) AS likeCount, " +
//        " SUM(IF(pv.value=-1,1,0)) AS dislikCount, COUNT(DISTINCT(pc.id)) AS commentCount " +
//    " FROM posts p " +
//    " LEFT JOIN post_votes pv ON p.id = pv.post_id " +
//    " LEFT JOIN post_comments pc ON p.id = pc.post_id " +
//    " WHERE p.is_active=? AND  p.moderation_status=? AND  p.time<=? " +
//    " GROUP BY p.id LIMIT ?,?")
//   List<Object[]> fff(Byte isActive, String moderationStatus, LocalDateTime ldt, int offset,int limit);
}
