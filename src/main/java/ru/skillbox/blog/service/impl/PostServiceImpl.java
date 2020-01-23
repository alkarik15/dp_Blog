package ru.skillbox.blog.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.jsoup.Jsoup;
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
import ru.skillbox.blog.dto.PostModeration;
import ru.skillbox.blog.dto.PostsDto;
import ru.skillbox.blog.dto.UserDto;
import ru.skillbox.blog.model.PostEntity;
import ru.skillbox.blog.model.TagEntity;
import ru.skillbox.blog.model.UserEntity;
import ru.skillbox.blog.model.enums.ModerationStatus;
import ru.skillbox.blog.repository.PostVotesRepository;
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
    private PostVotesRepository postVotesRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private TagService tagService;

    @Override
    public List<PostEntity> findAllWithParam(Param param) {
        final int offset = param.getOffset();
        int limit = param.getLimit() < 1 ? 1 : param.getLimit();

        Sort.Direction sortDir = Sort.Direction.ASC;
        String sortName = "id";
        if (param.getMode().toString().toLowerCase().equals("recent")) {
            sortName = "time";
        }
        if (param.getMode().toString().toLowerCase().equals("early")) {
            sortName = "time";
            sortDir = Sort.Direction.DESC;
        }
        if (param.getMode().toString().toLowerCase().equals("best")) {
//            TODO Сортировка по убыванию лайков
            sortDir = Sort.Direction.DESC;
        }
        if (param.getMode().toString().toLowerCase().equals("popular")) {
//            TODO Сортировка по убыванию комментариев
            sortDir = Sort.Direction.DESC;
        }


        final PageRequest pag = PageRequest.of(offset, limit, Sort.by(sortDir, sortName));
        final Page<PostEntity> all = postsRepository.findAllByIsActiveAndModerationStatusAndTimeIsBefore(
            (byte) 1, ModerationStatus.ACCEPTED, LocalDateTime.now(), pag);
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
            UserDto userDto = modelMapper.map(post.getUserId(), UserDto.class);
            postDto.setAnnonce(Jsoup.parse(postDto.getText()).text());
            postDto.setText(null);
            postDto.setUser(userDto);
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

    public Map<String, String> statMy(Integer id) {
        List<Object[]> statMy = postsRepository.statPostShowMy(id);
        HashMap<String, String> mapStatMy = new HashMap<>();
        for (Object[] stat : statMy) {
            mapStatMy.put("Постов", stat[0].toString());
            mapStatMy.put("Просмотров", stat[1].toString());
            final String dateTime = stat[2].toString();
            mapStatMy.put("Первая публикация", dateTime.substring(0, dateTime.lastIndexOf(":")));
        }
        statMy = postVotesRepository.statLikeDislikeMy(id);
        for (Object[] stat : statMy) {
            mapStatMy.put("Лайков", stat[0].toString());
            mapStatMy.put("Дизлайков", stat[1].toString());
        }
        return mapStatMy;
    }

    public Map<String, String> statAll() {
        List<Object[]> statMy = postsRepository.statPostShow();
        HashMap<String, String> mapStatAll = new HashMap<>();
        for (Object[] stat : statMy) {
            mapStatAll.put("Постов", stat[0].toString());
            mapStatAll.put("Просмотров", stat[1].toString());
            final String dateTime = stat[2].toString();
            mapStatAll.put("Первая публикация", dateTime.substring(0, dateTime.lastIndexOf(":")));
        }
        statMy = postVotesRepository.statLikeDislike();
        for (Object[] stat : statMy) {
            mapStatAll.put("Лайков", stat[0].toString());
            mapStatAll.put("Дизлайков", stat[1].toString());
        }
        return mapStatAll;
    }

    @Override
    @Transactional(readOnly = false)
    public PostsDto apiPostModeration(Param param) {

        final Integer count = countAll();
//        final List<PostEntity> allPosts = findAllWithParamStatus(param);
        final int offset = param.getOffset();
        int limit = param.getLimit() < 1 ? 1 : param.getLimit();

        final PageRequest pag = PageRequest.of(offset, limit, Sort.by(Sort.Direction.ASC, "id"));
        final Page<PostEntity> all = postsRepository.findAllByIsActiveAndModerationStatus(
            (byte) 1, param.getStatus(), pag);
//        List<PostEntity> posts = new ArrayList<>();
//        all.forEach(postEntity -> posts.add(postEntity));
        PostsDto postsDto = getPostsDto(count, all);
//        List<PostDto> postDtos = new ArrayList<>();
//        for (PostEntity post : all) {
//            PostDto postDto = modelMapper.map(post, PostDto.class);
//            UserDto userDto = modelMapper.map(post.getUserId(), UserDto.class);
//            postDto.setUser(userDto);
//            postDto.setAnnonce(Jsoup.parse(postDto.getText()).text());
//            postDto.setText(null);
//            postDtos.add(postDto);
//        }

        //  PostsDto postsDto = new PostsDto(count, postDtos);
        return postsDto;
    }

    public List<PostEntity> findAllWithParamStatus(Param param) {
        final int offset = param.getOffset();
        int limit = param.getLimit() < 1 ? 1 : param.getLimit();

        final PageRequest pag = PageRequest.of(offset, limit, Sort.by(Sort.Direction.ASC, "id"));
        final Page<PostEntity> all = postsRepository.findAllByIsActiveAndModerationStatus(
            (byte) 1, param.getStatus(), pag);
        List<PostEntity> posts = new ArrayList<>();
        all.forEach(postEntity -> posts.add(postEntity));
        return posts;
    }

    @Override
    @Transactional(readOnly = false)
    public void setModeration(final PostModeration postModeration, final Integer moderatorId) {
        PostEntity post = postsRepository.findById(postModeration.getPostId()).orElse(null);
        ModerationStatus ms = ModerationStatus.NEW;
        if (postModeration.getDecision().toLowerCase().equals("decline")) {
            ms = ModerationStatus.DECLINED;
        }
        if (postModeration.getDecision().toLowerCase().equals("accept")) {
            ms = ModerationStatus.ACCEPTED;
        }
        post.setModerationStatus(ms);
        UserEntity moderator = new UserEntity();
        moderator.setId(moderatorId);
        post.setModeratorId(moderator);
    }

    @Override
    public PostsDto getAllPostSearch(final Param param) {
        final Integer count = postsRepository.countAllByIsActiveAndModerationStatusAndTimeIsBefore((byte) 1,
            ModerationStatus.ACCEPTED, LocalDateTime.now());
        final int offset = param.getOffset();
        int limit = param.getLimit() < 1 ? 1 : param.getLimit();

        final PageRequest pag = PageRequest.of(offset, limit, Sort.by(Sort.Direction.ASC, "time"));
        final Page<PostEntity> allPosts = postsRepository.findAllByIsActiveAndModerationStatusAndTimeIsBefore(
            (byte) 1, ModerationStatus.ACCEPTED, LocalDateTime.now(), pag);
        return getPostsDto(count, allPosts);
    }

    @Override
    public PostsDto getAllPostSearchByQuery(final Param param, final String query) {
        final Integer count = postsRepository.countAllByIsActiveAndModerationStatusAndTimeIsBeforeAndTextContains((byte) 1,
            ModerationStatus.ACCEPTED, LocalDateTime.now(), query);
        final int offset = param.getOffset();
        int limit = param.getLimit() < 1 ? 1 : param.getLimit();

        final PageRequest pag = PageRequest.of(offset, limit, Sort.by(Sort.Direction.ASC, "time"));
        final Page<PostEntity> allPosts = postsRepository.findAllByIsActiveAndModerationStatusAndTimeIsBeforeAndTextContains(
            (byte) 1, ModerationStatus.ACCEPTED, LocalDateTime.now(), query, pag);
        return getPostsDto(count, allPosts);
    }

    private PostsDto getPostsDto(final Integer count, final Page<PostEntity> allPosts) {
        List<PostDto> postDtos = new ArrayList<>();
        for (PostEntity post : allPosts) {
            PostDto postDto = modelMapper.map(post, PostDto.class);
            UserDto userDto = modelMapper.map(post.getUserId(), UserDto.class);
            postDto.setUser(userDto);
            postDto.setAnnonce(Jsoup.parse(postDto.getText()).text());
            postDto.setText(null);
            postDtos.add(postDto);
        }
        return new PostsDto(count, postDtos);
    }
}
