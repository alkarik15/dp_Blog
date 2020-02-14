package ru.skillbox.blog.service.impl;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
import ru.skillbox.blog.controller.ApiAuthController;
import ru.skillbox.blog.dto.AddPostDto;
import ru.skillbox.blog.dto.CommentsDto;
import ru.skillbox.blog.dto.OffsetLimitQueryDto;
import ru.skillbox.blog.dto.PostByIdDto;
import ru.skillbox.blog.dto.PostDto;
import ru.skillbox.blog.dto.PostModeration;
import ru.skillbox.blog.dto.PostsDto;
import ru.skillbox.blog.dto.ResultsDto;
import ru.skillbox.blog.dto.StatsPostDto;
import ru.skillbox.blog.dto.UserDto;
import ru.skillbox.blog.dto.enums.ParametrMode;
import ru.skillbox.blog.dto.enums.ParametrStatus;
import ru.skillbox.blog.dto.projection.StatPostsShow;
import ru.skillbox.blog.dto.projection.SumsVotes;
import ru.skillbox.blog.dto.projection.SumsVotesComment;
import ru.skillbox.blog.exception.PostException;
import ru.skillbox.blog.model.PostEntity;
import ru.skillbox.blog.model.TagEntity;
import ru.skillbox.blog.model.UserEntity;
import ru.skillbox.blog.model.enums.ModerationStatus;
import ru.skillbox.blog.repository.PostVotesRepository;
import ru.skillbox.blog.repository.PostsRepository;
import ru.skillbox.blog.repository.UsersRepository;
import ru.skillbox.blog.service.PostCommentService;
import ru.skillbox.blog.service.PostService;
import ru.skillbox.blog.service.PostVoteService;
import ru.skillbox.blog.service.TagService;
import ru.skillbox.blog.service.UserService;

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

    @Autowired
    private UserService userService;

    @Autowired
    private ApiAuthController apiAuthController;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PostVoteService postVoteService;

    @Autowired
    private PostCommentService postCommentService;

    @Override
    public List<PostEntity> findAllWithParam(OffsetLimitQueryDto param, ParametrMode mode, UserEntity user) {
        final int offset = param.getOffset();
        int limit = param.getLimit() < 1 ? 10 : param.getLimit();

        Sort.Direction sortDir = Sort.Direction.ASC;
        String sortName = "id";

        if (mode == ParametrMode.RECENT) {
            sortName = "time";
        }
        if (mode == ParametrMode.EARLY) {
            sortName = "time";
            sortDir = Sort.Direction.DESC;
        }
        if (mode == ParametrMode.BEST) {
//            TODO Сортировка по убыванию лайков
            sortDir = Sort.Direction.DESC;
        }
        if (mode == ParametrMode.POPULAR) {
//            TODO Сортировка по убыванию комментариев
            sortDir = Sort.Direction.DESC;
        }

        final PageRequest pag = PageRequest.of(offset, limit, Sort.by(sortDir, sortName));
        Page<PostEntity> all;
        if (user == null) {
            all = postsRepository.findAllByIsActiveAndModerationStatusAndTimeIsBefore(
                true, ModerationStatus.ACCEPTED, LocalDateTime.now(), pag);
        } else {
            all = postsRepository.findAllByUserIdAndIsActiveAndModerationStatusAndTimeIsBefore(
                user, true, ModerationStatus.ACCEPTED, LocalDateTime.now(), pag);
        }
        List<PostEntity> posts = new ArrayList<>();
        all.forEach(postEntity -> posts.add(postEntity));
        return posts;
    }

    @Override
    public List<PostEntity> findAllWithParam(OffsetLimitQueryDto param, ParametrStatus status, UserEntity user) {
        final int offset = param.getOffset();
        int limit = param.getLimit() < 1 ? 10 : param.getLimit();

        Sort.Direction sortDir = Sort.Direction.ASC;
        String sortName = "id";
        final PageRequest pag = PageRequest.of(offset, limit, Sort.by(sortDir, sortName));

        Boolean isActive = false;

        ModerationStatus moderationStatus = ModerationStatus.NEW;
        if (status == ParametrStatus.INACTIVE) {
            isActive = false;
        }
        if (status == ParametrStatus.PENDING) {
            isActive = true;
            moderationStatus = ModerationStatus.NEW;
        }
        if (status == ParametrStatus.DECLINED) {
            isActive = true;
            moderationStatus = ModerationStatus.DECLINED;
        }
        if (status == ParametrStatus.PUBLISHED) {
            isActive = true;
            moderationStatus = ModerationStatus.ACCEPTED;
        }

        Page<PostEntity> all;
        if (isActive) {
            if (user == null) {
                all = postsRepository.findAllByIsActiveAndModerationStatusAndTimeIsBefore(isActive, moderationStatus, LocalDateTime.now(), pag);
            } else {
                all = postsRepository.findAllByUserIdAndIsActiveAndModerationStatusAndTimeIsBefore(user, isActive, moderationStatus, LocalDateTime.now(), pag);
            }
        } else {
            if (user == null) {
                all = postsRepository.findAllByIsActiveAndTimeIsBefore(isActive, LocalDateTime.now(), pag);
            } else {
                all = postsRepository.findAllByUserIdAndIsActiveAndTimeIsBefore(user, isActive, LocalDateTime.now(), pag);
            }
        }
        List<PostEntity> posts = new ArrayList<>();
        all.forEach(postEntity -> posts.add(postEntity));
        return posts;
    }

    @Override
    public Integer countAll() {
        return (int) postsRepository.count();
    }

    @Override
//    @Transactional(readOnly = false)
    public void save(final PostEntity posts) {
        postsRepository.save(posts);
    }

    @Override
    @Transactional(readOnly = false)
    public PostByIdDto getPostByIdModerationStatusActiveTime(
        final Integer id,
        final Boolean isActive,
        final ModerationStatus moderationStatus,
        final LocalDateTime ldt) {

        final PostEntity postById = postsRepository.findByIdAndIsActiveORModerationStatusAndTimeIsBeforeNative(id, ldt, isActive, moderationStatus);
        if (postById == null) {
            throw new PostException("Post Id:" + id + " not found");
        }

        postById.setViewCount(postById.getViewCount() + 1);
        save(postById);

        PostByIdDto postDto = modelMapper.map(postById, PostByIdDto.class);

        StatsPostDto stats = postVoteService.findStatPost(id);

        postDto.setLikeCount(stats.getLikes());
        postDto.setDislikeCount(stats.getDislikes());

        List<CommentsDto> listCommentsDto = postCommentService.findByPostId(getPostById(id));

        postDto.setComments(listCommentsDto);
        postDto.setCommentCount(listCommentsDto.size());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(postDto.getTime());
        postDto.setTime(dateTime.format(formatter));

        postDto.setTags(TagToString(postById));

        postDto.setAnnounce(Jsoup.parse(postDto.getText()).text());
//      postDto.setText(null);
//      postDto.setUser(userDto);
        return postDto;
    }

    private String[] TagToString(final PostEntity postById) {
        final Set<TagEntity> tags = postById.getTags();
        String[] tagsString = new String[tags.size()];
        int i = 0;
        for (TagEntity tag : tags) {
            tagsString[i++] = tag.getName();
        }
        return tagsString;
    }

    @Override
//    @Transactional(readOnly = false)
    public void createPostFromDto(
        final AddPostDto postDto,
        final ModerationStatus modStatus,
        final LocalDateTime ldt,
        final Integer userId,
        final Integer postId) {

        PostEntity post = modelMapper.map(postDto, PostEntity.class);
        if (postId != null) {
            post.setId(postId);
        }
        post.setIsActive(postDto.getActive());
        post.setModerationStatus(modStatus);
        post.setTime(postDto.getLdt());
        if (post.getTime().isBefore(ldt)) {
            post.setTime(ldt);
        }
        post.setTags(null);
        UserEntity userEntity = usersRepository.findAllById(userId);
        post.setUserId(userEntity);
        final Set<TagEntity> tagEntities = tagService.collectTags(postDto.getTags());
        tagEntities.forEach(tagEntity -> post.addTag(tagEntity));
        save(post);
    }

    @Override
    @Transactional(readOnly = false)
    public PostsDto apiPost(OffsetLimitQueryDto param, ParametrMode mode, final Map<Integer, StatsPostDto> mapStatLDC) {

        final Integer count = countAll();
        final List<PostEntity> allPosts = findAllWithParam(param, mode, null);
        List<PostDto> postDtos = getPostDtos(mapStatLDC, allPosts);
        PostsDto postsDto = new PostsDto(count, postDtos);
        return postsDto;
    }

    @Override
    @Transactional(readOnly = false)
    public PostsDto apiPost(OffsetLimitQueryDto param, ParametrStatus status, final Map<Integer, StatsPostDto> mapStatLDC, Integer userId) {

        final Integer count = countAll();

        final UserEntity userById = usersRepository.findAllById(userId);

        final List<PostEntity> allPostsByUserId = findAllWithParam(param, status, userById);
        List<PostDto> postDtos = getPostDtos(mapStatLDC, allPostsByUserId);
        PostsDto postsDto = new PostsDto(count, postDtos);
        return postsDto;
    }

    private List<PostDto> getPostDtos(final Map<Integer, StatsPostDto> mapStatLDC, final List<PostEntity> allPosts) {
        List<PostDto> postDtos = new ArrayList<>();
        for (PostEntity post : allPosts) {
            PostDto postDto = modelMapper.map(post, PostDto.class);
            UserDto userDto = modelMapper.map(post.getUserId(), UserDto.class);

            postDto.setTags(TagToString(post));

            postDto.setAnnounce(Jsoup.parse(postDto.getText()).text());
//            postDto.setText(null);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime dateTime = LocalDateTime.parse(postDto.getTime());
            postDto.setTime(dateTime.format(formatter));

            postDto.setUser(userDto);
            if (mapStatLDC.containsKey(postDto.getId())) {
                final StatsPostDto statsPostDto = mapStatLDC.get(postDto.getId());
                postDto.setLikeCount(statsPostDto.getLikes());
                postDto.setDislikeCount(statsPostDto.getDislikes());
                postDto.setCommentCount(statsPostDto.getCommentCount());
            } else {
                postDto.setLikeCount(0);
                postDto.setDislikeCount(0);
                postDto.setCommentCount(0);
            }
            postDtos.add(postDto);
        }
        return postDtos;
    }

    public Map<String, String> statMy(Integer id) {
        StatPostsShow statMy = postsRepository.statPostShowMy(id);
        return getStringMap(statMy, postVotesRepository.statLikeDislikeMy(id));
    }

    public Map<String, String> statAll() {
        StatPostsShow statMy = postsRepository.statPostShow();
        return getStringMap(statMy, postVotesRepository.statLikeDislike());
    }

    private Map<String, String> getStringMap(StatPostsShow statMy, final List<SumsVotes> sumsVotes) {
        HashMap<String, String> mapStatAll = new HashMap<>();
        mapStatAll.put("postsCount", statMy.getPostCount().toString());
        mapStatAll.put("viewsCount", statMy.getShowCount().toString());
        mapStatAll.put("firstPublication", statMy.getFirstPubl().toString());
        for (SumsVotes vote : sumsVotes) {
            mapStatAll.put("likesCount", vote.getLikes().toString());
            mapStatAll.put("dislikesCount", vote.getDislikes().toString());
        }
        return mapStatAll;
    }

    @Override
    @Transactional(readOnly = false)
    public PostsDto apiPostModeration(OffsetLimitQueryDto param, ModerationStatus status) {

        final Integer count = countAll();
        final int offset = param.getOffset();
        int limit = param.getLimit() < 1 ? 1 : param.getLimit();

        final PageRequest pag = PageRequest.of(offset, limit, Sort.by(Sort.Direction.ASC, "id"));
        Page<PostEntity> all = postsRepository.findAllByIsActiveAndModerationStatus(true, status, pag);

        PostsDto postsDto = getPostsDto(count, all, false);
        return postsDto;
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
        UserEntity moderator = usersRepository.findAllById(moderatorId);
        post.setModeratorId(moderator);
    }

    @Override
    public PostsDto getAllPostSearch(final OffsetLimitQueryDto param) {
        final Integer count = postsRepository.countAllByIsActiveAndModerationStatusAndTimeIsBefore(true,
            ModerationStatus.ACCEPTED, LocalDateTime.now());
        final int offset = param.getOffset();
        int limit = param.getLimit() < 1 ? 10 : param.getLimit();

        final PageRequest pag = PageRequest.of(offset, limit, Sort.by(Sort.Direction.ASC, "time"));
        final Page<PostEntity> allPosts = postsRepository.findAllByIsActiveAndModerationStatusAndTimeIsBefore(
            true, ModerationStatus.ACCEPTED, LocalDateTime.now(), pag);
        return getPostsDto(count, allPosts, true);
    }

    @Override
    public PostsDto getAllPostSearchByQuery(final OffsetLimitQueryDto param, final String query) {
        final Integer count = postsRepository.countAllByIsActiveAndModerationStatusAndTimeIsBeforeAndTextContains(true,
            ModerationStatus.ACCEPTED, LocalDateTime.now(), query);
        final int offset = param.getOffset();
        int limit = param.getLimit() < 1 ? 10 : param.getLimit();
        final PageRequest pag = PageRequest.of(offset, limit, Sort.by(Sort.Direction.ASC, "time"));
        final Page<PostEntity> allPosts = postsRepository.findAllByIsActiveAndModerationStatusAndTimeIsBeforeAndTextContains(
            true, ModerationStatus.ACCEPTED, LocalDateTime.now(), query, pag);
        return getPostsDto(count, allPosts, true);
    }


    private PostsDto getPostsDto(final Integer count, final Page<PostEntity> allPosts, Boolean needStat) {
        List<PostDto> postDtos = new ArrayList<>();
        for (PostEntity post : allPosts) {
            PostDto postDto = modelMapper.map(post, PostDto.class);
            UserDto userDto = modelMapper.map(post.getUserId(), UserDto.class);
            postDto.setUser(userDto);
            postDto.setAnnounce(Jsoup.parse(postDto.getText()).text());
//            postDto.setText(null);

            postDto.setTags(TagToString(post));

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime dateTime = LocalDateTime.parse(postDto.getTime());
            postDto.setTime(dateTime.format(formatter));

            if (needStat) {
                List<SumsVotesComment> statLD = postVotesRepository.statLikeDislikeCountCommentByPostId(post.getId());
                for (SumsVotesComment stat : statLD) {
                    postDto.setLikeCount(stat.getLikes());
                    postDto.setDislikeCount(stat.getDislikes());
                    postDto.setCommentCount(stat.getCommentCount());
                }
            } else {
                postDto.setViewCount(null);
            }
            postDtos.add(postDto);
        }
        return new PostsDto(count, postDtos);
    }

    @Override
    public PostEntity getPostById(final Integer id) {
        return postsRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = false)
    public ResultsDto createPost(HttpServletRequest request, AddPostDto addPost) {
        Integer userId = apiAuthController.getUserIdFromSession(request);

        Map<String, String> errors = verifyPost(addPost);

        ResultsDto result = new ResultsDto();
        if (errors.size() == 0) {
            result.setResult(true);
            createPostFromDto(addPost, ModerationStatus.NEW, LocalDateTime.now(), userId, null);
        } else {
            result.setResult(false);
            result.setErrors(errors);
        }
        return result;
    }

    public Map<String, String> verifyPost(final AddPostDto addPost) {
        Map<String, String> errors = new HashMap<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(addPost.getTime(), formatter);

        addPost.setLdt(dateTime);

        if (addPost.getTitle() == null || addPost.getTitle().length() == 0) {
            errors.put("title", "Заголовок не установлен");
        } else if (addPost.getTitle().length() <= 10) {
            errors.put("title", "Заголовок слишком короткий");
        }
        if (addPost.getText() == null || addPost.getText().length() == 0) {
            errors.put("text", "Текст публикации не установлен");
        } else if (addPost.getText().length() <= 500) {
            errors.put("text", "Текст публикации слишком короткий");
        }
        return errors;
    }

    @Override
    @Transactional(readOnly = false)
    public ResultsDto updatePost(final Integer id, final AddPostDto postDto, HttpServletRequest request) {
        Integer userId = apiAuthController.getUserIdFromSession(request);

        Map<String, String> errors = verifyPost(postDto);

        ResultsDto result = new ResultsDto();
        if (errors.size() == 0) {
            result.setResult(true);
            createPostFromDto(postDto, ModerationStatus.NEW, LocalDateTime.now(), userId, id);
        } else {
            result.setResult(false);
            result.setErrors(errors);
        }
        return result;
    }
}
