package ru.skillbox.blog.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.skillbox.blog.model.PostCommentEntity;

/**
 * @author alkarik
 * @link http://alkarik
 */
@Repository
public interface PostCommentsRepository extends CrudRepository<PostCommentEntity, Integer> {
    List<PostCommentEntity> findAllByPostId(Integer postsId);
}
