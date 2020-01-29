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
import ru.skillbox.blog.dto.OffsetLimitQueryDto;
import ru.skillbox.blog.dto.PostByIdDto;
import ru.skillbox.blog.dto.PostDto;
import ru.skillbox.blog.dto.PostModeration;
import ru.skillbox.blog.dto.PostsDto;
import ru.skillbox.blog.dto.UserDto;
import ru.skillbox.blog.dto.enums.ParametrMode;
import ru.skillbox.blog.model.PostEntity;
import ru.skillbox.blog.model.TagEntity;
import ru.skillbox.blog.model.UserEntity;
import ru.skillbox.blog.model.enums.ModerationStatus;
import ru.skillbox.blog.repository.PostVotesRepository;
import ru.skillbox.blog.repository.PostsRepository;
import ru.skillbox.blog.repository.UsersRepository;
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

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public List<PostEntity> findAllWithParam(OffsetLimitQueryDto param, ParametrMode mode) {
        final int offset = param.getOffset();
        int limit = param.getLimit() < 1 ? 1 : param.getLimit();

        Sort.Direction sortDir = Sort.Direction.ASC;
        String sortName = "id";

        if (mode == ParametrMode.recent) {
            sortName = "time";
        }
        if (mode == ParametrMode.early) {
            sortName = "time";
            sortDir = Sort.Direction.DESC;
        }
        if (mode == ParametrMode.best) {
//            TODO Сортировка по убыванию лайков
            sortDir = Sort.Direction.DESC;
        }
        if (mode == ParametrMode.popular) {
//            TODO Сортировка по убыванию комментариев
            sortDir = Sort.Direction.DESC;
        }


        final PageRequest pag = PageRequest.of(offset, limit, Sort.by(sortDir, sortName));
        final Page<PostEntity> all = postsRepository.findAllByIsActiveAndModerationStatusAndTimeIsBefore(
            true, ModerationStatus.ACCEPTED, LocalDateTime.now(), pag);
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
        final Boolean isActive,
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
        final LocalDateTime ldt,
        final Integer userId) {

        PostEntity post = modelMapper.map(postDto, PostEntity.class);
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
    public PostsDto apiPost(OffsetLimitQueryDto param, ParametrMode mode, final Map<Integer, String> mapStatLDC) {

        final Integer count = countAll();
        final List<PostEntity> allPosts = findAllWithParam(param, mode);

        List<PostDto> postDtos = new ArrayList<>();
        for (PostEntity post : allPosts) {
            PostDto postDto = modelMapper.map(post, PostDto.class);
            UserDto userDto = modelMapper.map(post.getUserId(), UserDto.class);


            final Set<TagEntity> tags = post.getTags();
            String[] tagsString = new String[tags.size()];
            int i = 0;
            for (TagEntity tag : tags) {
                tagsString[i++] = tag.getName();
            }
            postDto.setAnnonce(Jsoup.parse(postDto.getText()).text());
//            postDto.setText(null);
            postDto.setUser(userDto);
            postDto.setTags(tagsString);
            if (mapStatLDC.containsKey(postDto.getId())) {
                final String[] splitLikes = mapStatLDC.get(postDto.getId()).split(":");
                postDto.setLikeCount(Integer.parseInt(splitLikes[1]));
                postDto.setDislikeCount(Integer.parseInt(splitLikes[2]));
                postDto.setCommentCount(Integer.parseInt(splitLikes[3]));
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
            mapStatMy.put("postsCount", stat[0].toString());
            mapStatMy.put("viewsCount", stat[1].toString());
            final String dateTime = stat[2].toString();
            mapStatMy.put("firstPublication", dateTime.substring(0, dateTime.lastIndexOf(":")));
        }
        statMy = postVotesRepository.statLikeDislikeMy(id);
        for (Object[] stat : statMy) {
            mapStatMy.put("likesCount", stat[0].toString());
            mapStatMy.put("dislikesCount", stat[1].toString());
        }
        return mapStatMy;
    }

    public Map<String, String> statAll() {
        List<Object[]> statMy = postsRepository.statPostShow();
        HashMap<String, String> mapStatAll = new HashMap<>();
        for (Object[] stat : statMy) {
            mapStatAll.put("postsCount", stat[0].toString());
            mapStatAll.put("viewsCount", stat[1].toString());
            final String dateTime = stat[2].toString();
            mapStatAll.put("firstPublication", dateTime.substring(0, dateTime.lastIndexOf(":")));
        }
        statMy = postVotesRepository.statLikeDislike();
        for (Object[] stat : statMy) {
            mapStatAll.put("likesCount", stat[0].toString());
            mapStatAll.put("dislikesCount", stat[1].toString());
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
        int limit = param.getLimit() < 1 ? 1 : param.getLimit();

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
        int limit = param.getLimit() < 1 ? 1 : param.getLimit();
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
            postDto.setAnnonce(Jsoup.parse(postDto.getText()).text());
            postDto.setText(null);
            if (needStat) {
                List<Object[]> statLD = postVotesRepository.statLikeDislikeCountCommentByPostId(post.getId());
                for (Object[] stat : statLD) {
                    postDto.setLikeCount(Integer.parseInt(stat[0].toString()));
                    postDto.setDislikeCount(Integer.parseInt(stat[1].toString()));
                    postDto.setCommentCount(Integer.parseInt(stat[2].toString()));
                }
            } else {
                postDto.setViewCount(null);
            }
            postDtos.add(postDto);
        }
        return new PostsDto(count, postDtos);
    }
}
