package ru.skillbox.blog.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.skillbox.blog.model.PostCommentEntity;
import ru.skillbox.blog.model.PostEntity;

/**
 * @author alkarik
 * @link http://alkarik
 */
@Repository
public interface PostCommentsRepository extends CrudRepository<PostCommentEntity, Integer> {
    List<PostCommentEntity> findAllByPostId(PostEntity postId);
    PostCommentEntity findAllById(Integer id);
}
