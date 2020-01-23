package ru.skillbox.blog.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import ru.skillbox.blog.dto.AddPostDto;
import ru.skillbox.blog.dto.Param;
import ru.skillbox.blog.dto.PostByIdDto;
import ru.skillbox.blog.dto.PostModeration;
import ru.skillbox.blog.dto.PostsDto;
import ru.skillbox.blog.model.PostEntity;
import ru.skillbox.blog.model.enums.ModerationStatus;

/**
 * @author alkarik
 * @link http://alkarik
 */
public interface PostService {
    Integer countAll();

    List<PostEntity> findAllWithParam(Param param);

    PostsDto apiPost(Param param, Map<Integer, String> mapStatLDC);

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


    Map<String, String> statMy(Integer id);

    Map<String, String> statAll();

    void setModeration(PostModeration postModeration, Integer moderatorId);

    PostsDto apiPostModeration(Param param);

    PostsDto getAllPostSearch(Param param);
    PostsDto getAllPostSearchByQuery(Param param,String query);
}
