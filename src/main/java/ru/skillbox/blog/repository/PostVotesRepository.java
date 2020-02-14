package ru.skillbox.blog.repository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.skillbox.blog.dto.projection.PidSumsVotesComment;
import ru.skillbox.blog.dto.projection.SumsVotes;
import ru.skillbox.blog.dto.projection.SumsVotesComment;
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
        "p.id AS pid, COUNT(DISTINCT(IF(pv.value=1,pv.id,null))) AS likes, COUNT(DISTINCT(IF(pv.value=-1,pv.id,null))) AS dislikes, " +
        "COUNT(DISTINCT pc.id) as commentCount " +
        "FROM posts p " +
        "LEFT JOIN post_votes pv ON p.id = pv.post_id " +
        "LEFT JOIN post_comments AS pc ON pc.post_id=p.id " +
        "GROUP BY p.id")
    List<PidSumsVotesComment> statLDC();

    @Query(nativeQuery = true, value = "SELECT " +
        "p.id AS pid, COUNT(DISTINCT(IF(pv.value=1,pv.id,null))) AS likes, COUNT(DISTINCT(IF(pv.value=-1,pv.id,null))) AS dislikes, " +
        "COUNT(DISTINCT pc.id) as commentCount " +
        "FROM posts p " +
        "LEFT JOIN post_votes pv ON p.id = pv.post_id " +
        "LEFT JOIN post_comments AS pc ON pc.post_id=p.id " +
        "WHERE p.user_id = ? " +
        "GROUP BY p.id")
    List<PidSumsVotesComment> statLDCMy(Integer userId);

    @Query(nativeQuery = true, value = "SELECT SUM(IF(p.value=1,1,0)) AS likes, SUM(IF(p.value=-1,1,0)) AS dislikes " +
        "FROM post_votes p  WHERE p.post_id=?")
    SumsVotes statPost(Integer id);

    PostVoteEntity findAllByPostIdAndUserId(PostEntity postId, UserEntity userId);

    void deleteById(Integer id);


    @Query(nativeQuery = true, value = "SELECT SUM(IF(pv.value=1,1,0)) AS likes, SUM(IF(pv.value=-1,1,0)) AS dislikes  " +
        "FROM post_votes pv " +
        "WHERE pv.post_id IN (SELECT id FROM posts p WHERE p.user_id=?)")
    List<SumsVotes> statLikeDislikeMy(Integer id);

    @Query(nativeQuery = true, value = "SELECT SUM(IF(pv.value=1,1,0)) AS likes, SUM(IF(pv.value=-1,1,0)) AS dislikes  " +
        "FROM post_votes pv")
    List<SumsVotes> statLikeDislike();

    @Query(nativeQuery = true, value = "SELECT SUM(IF(pv.value=1,1,0)) AS likes, SUM(IF(pv.value=-1,1,0)) AS dislikes," +
        " COUNT(DISTINCT(pc.id)) as commentCount " +
        "  FROM post_votes pv " +
        "  LEFT JOIN post_comments pc ON pv.post_id = pc.post_id  " +
        "  WHERE pv.post_id=?")
    List<SumsVotesComment> statLikeDislikeCountCommentByPostId(Integer id);

}
