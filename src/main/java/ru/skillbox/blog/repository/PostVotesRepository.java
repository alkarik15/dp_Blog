package ru.skillbox.blog.repository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.skillbox.blog.model.PostEntity;
import ru.skillbox.blog.model.PostVoteEntity;
import ru.skillbox.blog.model.UserEntity;

/**
 * @author alkarik
 * @link http://alkarik
 */
@Repository
public interface PostVotesRepository extends CrudRepository<PostVoteEntity, Integer> {
    //
    @Query(nativeQuery = true, value = "SELECT " +
        "p.post_id AS pid, SUM(IF(p.value=1,1,0)) AS likes, SUM(IF(p.value=-1,1,0)) AS dislikes, " +
        "COUNT(DISTINCT pc.id) " +
        "FROM Post_Votes p " +
        "  LEFT JOIN dp_blog.post_comments AS pc ON pc.post_id=p.post_id " +
        "GROUP BY p.post_id")
    List<Object[]> statLDC();

    @Query(nativeQuery = true, value = "SELECT " +
        "p.post_id AS pid, SUM(IF(p.value=1,1,0)) AS likes, SUM(IF(p.value=-1,1,0)) AS dislikes " +
        "FROM Post_Votes p  WHERE p.post_id=?")
    List<Object[]> statPost(Integer id);

    PostVoteEntity findAllByPostIdAndUserId(PostEntity postId, UserEntity userId);

    void deleteById(Integer id);


    @Query(nativeQuery = true, value = "SELECT SUM(IF(pv.value=1,1,0)) AS likes, SUM(IF(pv.value=-1,1,0)) AS dislikes  " +
        "FROM post_votes pv " +
        "WHERE pv.post_id IN (SELECT id FROM posts p WHERE p.user_id=?)")
    List<Object[]> statLikeDislikeMy(Integer id);

    @Query(nativeQuery = true, value = "SELECT SUM(IF(pv.value=1,1,0)) AS likes, SUM(IF(pv.value=-1,1,0)) AS dislikes  " +
        "FROM post_votes pv")
    List<Object[]> statLikeDislike();

    @Query(nativeQuery = true, value = "SELECT SUM(IF(pv.value=1,1,0)) AS likes, SUM(IF(pv.value=-1,1,0)) AS dislikes," +
        " COUNT(DISTINCT(pc.id)) as commentCount " +
        "  FROM post_votes pv " +
        "  LEFT JOIN post_comments pc ON pv.post_id = pc.post_id  " +
        "  WHERE pv.post_id=?")
    List<Object[]> statLikeDislikeCountCommentByPostId(Integer id);
}
