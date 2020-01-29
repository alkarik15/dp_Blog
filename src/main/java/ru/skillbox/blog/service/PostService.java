package ru.skillbox.blog.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import ru.skillbox.blog.dto.AddPostDto;
import ru.skillbox.blog.dto.OffsetLimitQueryDto;
import ru.skillbox.blog.dto.PostByIdDto;
import ru.skillbox.blog.dto.PostModeration;
import ru.skillbox.blog.dto.PostsDto;
import ru.skillbox.blog.dto.enums.ParametrMode;
import ru.skillbox.blog.model.PostEntity;
import ru.skillbox.blog.model.enums.ModerationStatus;

/**
 * @author alkarik
 * @link http://alkarik
 */
public interface PostService {
    Integer countAll();

    List<PostEntity> findAllWithParam(OffsetLimitQueryDto param, ParametrMode mode);

    PostsDto apiPost(OffsetLimitQueryDto param, ParametrMode mode, Map<Integer, String> mapStatLDC);

    void save(PostEntity posts);

    PostByIdDto getPostByIdModerationStatusActiveTime(
        Integer id,
        Boolean isActive,
        ModerationStatus moderationStatus,
        LocalDateTime ldt);

    void createPostFromDto(
        final AddPostDto postDto,
        final ModerationStatus modStatus,
        final LocalDateTime ldt,
        final Integer userId);


    Map<String, String> statMy(Integer id);

    Map<String, String> statAll();

    void setModeration(PostModeration postModeration, Integer moderatorId);

    PostsDto apiPostModeration(OffsetLimitQueryDto param, ModerationStatus status);

    PostsDto getAllPostSearch(OffsetLimitQueryDto param);

    PostsDto getAllPostSearchByQuery(OffsetLimitQueryDto param, String query);
}
