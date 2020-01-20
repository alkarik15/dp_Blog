package ru.skillbox.blog.service;

import java.util.List;
import ru.skillbox.blog.dto.CommentsDto;

/**
 * @author alkarik
 * @link http://alkarik
 */
public interface PostCommentService {
    List<CommentsDto> findByPostId(Integer id);
}
