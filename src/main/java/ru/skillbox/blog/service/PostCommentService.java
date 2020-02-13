package ru.skillbox.blog.service;

import java.util.List;
import ru.skillbox.blog.dto.CommentsDto;
import ru.skillbox.blog.dto.CommentsParamDto;
import ru.skillbox.blog.model.PostEntity;

/**
 * @author alkarik
 * @link http://alkarik
 */
public interface PostCommentService {
    List<CommentsDto> findByPostId(PostEntity postId);

    Object saveComment(CommentsParamDto commentsParamDto, Integer userId);
}
