package ru.skillbox.blog.service;

import java.util.Map;
import ru.skillbox.blog.dto.StatsPostDto;
import ru.skillbox.blog.model.PostVoteEntity;

/**
 * @author alkarik
 * @link http://alkarik
 */
public interface PostVoteService {
    Map<Integer, StatsPostDto> findStatistics(Integer userId);

    StatsPostDto findStatPost(Integer id);

    Boolean setLikeByPostIdAndUserId(Integer postId, Integer userId);

    Boolean findDislikeByPostIdAndUserId(Integer postId, Integer userId);

    void setLike(PostVoteEntity postVoteEntity);

    void deleteLike(Integer postId);
}
