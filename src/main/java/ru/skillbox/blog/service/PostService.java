package ru.skillbox.blog.service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import ru.skillbox.blog.dto.AddPostDto;
import ru.skillbox.blog.dto.OffsetLimitQueryDto;
import ru.skillbox.blog.dto.PostByIdDto;
import ru.skillbox.blog.dto.PostModeration;
import ru.skillbox.blog.dto.PostsDto;
import ru.skillbox.blog.dto.ResultsDto;
import ru.skillbox.blog.dto.StatsPostDto;
import ru.skillbox.blog.dto.enums.ParametrMode;
import ru.skillbox.blog.dto.enums.ParametrStatus;
import ru.skillbox.blog.model.PostEntity;
import ru.skillbox.blog.model.UserEntity;
import ru.skillbox.blog.model.enums.ModerationStatus;

/**
 * @author alkarik
 * @link http://alkarik
 */
public interface PostService {
    Integer countAll();

    //    List<PostEntity> findAllWithParam(OffsetLimitQueryDto param, ParametrMode mode);
    List<PostEntity> findAllWithParam(OffsetLimitQueryDto param, ParametrMode mod, UserEntity user);

    //    List<PostEntity> findAllWithParam(OffsetLimitQueryDto param, ParametrStatus status);
    List<PostEntity> findAllWithParam(OffsetLimitQueryDto param, ParametrStatus status, UserEntity userId);

    PostsDto apiPost(OffsetLimitQueryDto param, ParametrMode mode, Map<Integer, StatsPostDto> mapStatLDC);

    PostsDto apiPost(OffsetLimitQueryDto param, ParametrStatus status, Map<Integer, StatsPostDto> mapStatLDC, Integer userId);

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
        final Integer userId,
        final Integer postId);


    Map<String, String> statMy(Integer id);

    Map<String, String> statAll();

    void setModeration(PostModeration postModeration, Integer moderatorId);

    PostsDto apiPostModeration(OffsetLimitQueryDto param, ModerationStatus status);

    PostsDto getAllPostSearch(OffsetLimitQueryDto param);

    PostsDto getAllPostSearchByQuery(OffsetLimitQueryDto param, String query);

    PostEntity getPostById(Integer id);

    ResultsDto createPost(HttpServletRequest request, AddPostDto addPost);

    ResultsDto updatePost(Integer id, AddPostDto postDto, HttpServletRequest request);
}
