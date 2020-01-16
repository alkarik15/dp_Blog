package ru.skillbox.blog.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.skillbox.blog.model.Posts;

/**
 * @author alkarik
 * @link http://alkarik
 */
@Repository
public interface PostsRepository extends CrudRepository<Posts, Integer> {
}
