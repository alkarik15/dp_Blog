package ru.skillbox.blog.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.skillbox.blog.model.PostComments;
import ru.skillbox.blog.model.Posts;

/**
 * @author alkarik
 * @link http://alkarik
 */
@Repository
public interface PostCommentsRepository extends CrudRepository<PostComments, Integer> {
    List<PostComments> findAllByPostId(Posts postsId);
}
