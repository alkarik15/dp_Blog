package ru.skillbox.blog.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import ru.skillbox.blog.dto.AddPostDto;
import ru.skillbox.blog.dto.Param;
import ru.skillbox.blog.dto.PostByIdDto;
import ru.skillbox.blog.dto.PostsDto;
import ru.skillbox.blog.model.PostEntity;
import ru.skillbox.blog.model.enums.ModerationStatus;

/**
 * @author alkarik
 * @link http://alkarik
 */
public interface PostService {
    List<PostEntity> findAllWithParam(Param param);

    Integer countAll();

    void save(PostEntity posts);

    PostByIdDto getPostByIdModerationStatusActiveTime(
        Integer id,
        Byte isActive,
        ModerationStatus moderationStatus,
        LocalDateTime ldt);

    void createPostFromDto(
        final AddPostDto postDto,
        final ModerationStatus modStatus,
        final LocalDateTime ldt);

    PostsDto apiPost(Param param, Map<Integer, String> mapStatLDC);
}
