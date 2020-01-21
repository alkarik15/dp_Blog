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

    PostVoteEntity findAllByPostIdAndUserId (PostEntity postId, UserEntity userId);
    void deleteById (Integer id);

}
