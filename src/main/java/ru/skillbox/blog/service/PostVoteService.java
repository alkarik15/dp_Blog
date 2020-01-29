package ru.skillbox.blog.service;

import java.util.Map;
import ru.skillbox.blog.model.PostVoteEntity;

/**
 * @author alkarik
 * @link http://alkarik
 */
public interface PostVoteService {
    Map<Integer, String> findStatistics();

    String findStatPost(Integer id);

    Boolean setLikeByPostIdAndUserId(Integer postId, Integer userId);

    Boolean findDislikeByPostIdAndUserId(Integer postId, Integer userId);

    void setLike(PostVoteEntity postVoteEntity);

    void deleteLike(Integer postId);
}
