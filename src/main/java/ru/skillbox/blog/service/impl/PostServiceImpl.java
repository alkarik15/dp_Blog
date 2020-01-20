package ru.skillbox.blog.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skillbox.blog.dto.AddPostDto;
import ru.skillbox.blog.dto.Param;
import ru.skillbox.blog.dto.PostByIdDto;
import ru.skillbox.blog.dto.PostDto;
import ru.skillbox.blog.dto.PostsDto;
import ru.skillbox.blog.model.PostEntity;
import ru.skillbox.blog.model.TagEntity;
import ru.skillbox.blog.model.enums.ModerationStatus;
import ru.skillbox.blog.repository.PostsRepository;
import ru.skillbox.blog.service.PostService;
import ru.skillbox.blog.service.TagService;

/**
 * @author alkarik
 * @link http://alkarik
 */
@Service
@Transactional(readOnly = true)
public class PostServiceImpl implements PostService {

    @Autowired
    private PostsRepository postsRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private TagService tagService;

    @Override
    public List<PostEntity> findAllWithParam(Param param) {
        final int offset = param.getOffset();
        int limit = param.getLimit();
        if(limit<1){limit=1;}

        Sort.Direction sortDir = Sort.Direction.ASC;
        if(param.getMode().toString().toLowerCase().equals("recent")){ String sortName="time";}
        if(param.getMode().toString().toLowerCase().equals("early")){ String sortName="time";sortDir = Sort.Direction.DESC;}
        if(param.getMode().toString().toLowerCase().equals("best")){
//            TODO Сортировка по убыванию лайков
            sortDir = Sort.Direction.DESC;
        }
        if(param.getMode().toString().toLowerCase().equals("popular")){
//            TODO Сортировка по убыванию комментариев
            sortDir = Sort.Direction.DESC;
        }


        final PageRequest pag = PageRequest.of(offset, limit, Sort.by(sortDir, "id"));
        final Page<PostEntity> all = postsRepository.findAllByIsActiveAndModerationStatusAndTimeIsBefore(
                                                    (byte) 1, ModerationStatus.ACCEPT, LocalDateTime.now(), pag);
        List<PostEntity> posts = new ArrayList<>();
        all.forEach(postEntity -> posts.add(postEntity));
        return posts;
    }

    @Override
    public Integer countAll() {
        return (int) postsRepository.count();
    }

    @Override
    @Transactional(readOnly = false)
    public void save(final PostEntity posts) {
        postsRepository.save(posts);
    }

    @Override
    public PostByIdDto getPostByIdModerationStatusActiveTime(
        final Integer id,
        final Byte isActive,
        final ModerationStatus moderationStatus,
        final LocalDateTime ldt) {

        final PostEntity postById = postsRepository.findByIdAndIsActiveAndModerationStatusAndTimeIsBefore(id, isActive, moderationStatus, ldt);
        if (postById == null) {
            return null;
        }
        PostByIdDto postByDto = modelMapper.map(postById, PostByIdDto.class);

        return postByDto;
    }

    @Override
    @Transactional(readOnly = false)
    public void createPostFromDto(
        final AddPostDto postDto,
        final ModerationStatus modStatus,
        final LocalDateTime ldt) {

        PostEntity post = modelMapper.map(postDto, PostEntity.class);
        post.setModerationStatus(modStatus);
        if (post.getTime().isBefore(ldt)) {
            post.setTime(ldt);
        }

        final Set<TagEntity> tagEntities = tagService.collectTags(postDto.getTags());
        tagEntities.forEach(tagEntity -> post.addTag(tagEntity));
        save(post);
    }

    @Override
    @Transactional(readOnly = false)
    public PostsDto apiPost(Param param, final Map<Integer, String> mapStatLDC) {

        final Integer count = countAll();
        final List<PostEntity> allPosts = findAllWithParam(param);

        List<PostDto> postDtos = new ArrayList<>();
        for (PostEntity post : allPosts) {
            PostDto postDto = modelMapper.map(post, PostDto.class);
            if (mapStatLDC.containsKey(postDto.getId())) {
                final String[] splitLikes = mapStatLDC.get(postDto.getId()).split(":");
                postDto.setLikes(Integer.parseInt(splitLikes[1]));
                postDto.setDislikes(Integer.parseInt(splitLikes[2]));
                postDto.setComments(Integer.parseInt(splitLikes[3]));
            }
            postDtos.add(postDto);
        }

        PostsDto postsDto = new PostsDto(count, postDtos);
        return postsDto;
    }

}
