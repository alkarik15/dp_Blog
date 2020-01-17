package ru.skillbox.blog.service;

import java.util.List;
import ru.skillbox.blog.model.PostComments;
import ru.skillbox.blog.model.Posts;

/**
 * @author alkarik
 * @link http://alkarik
 */
public interface PostCommentsService {
    List<PostComments> findByPostId(Posts id);
}
