package ru.skillbox.blog.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.skillbox.blog.dto.AddPostDto;
import ru.skillbox.blog.dto.Param;
import ru.skillbox.blog.dto.PostDto;
import ru.skillbox.blog.dto.PostsDto;
import ru.skillbox.blog.dto.ResultsDto;
import ru.skillbox.blog.model.ModerationStatus;
import ru.skillbox.blog.model.Posts;
import ru.skillbox.blog.model.Tags;
import ru.skillbox.blog.service.PostVotesService;
import ru.skillbox.blog.service.PostsService;
import ru.skillbox.blog.service.TagsService;

/**
 * @author alkarik
 * @link http://alkarik
 */
@RestController
@RequestMapping("/api/post")
public class ApiPostController {

    @Autowired
    private PostsService postsService;

    @Autowired
    private PostVotesService postVotesService;

    @Autowired
    private TagsService tagsService;

    @GetMapping()
    @ResponseBody
    public String apiPost(Param param) {
        final List<Posts> allPosts = postsService.findAll();
        final Map<Integer, String> mapStatLDC = postVotesService.findStatistics();

        PostsDto postsDto = new PostsDto(allPosts.size(), getPost_dtos(allPosts, mapStatLDC));

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(postsDto);
    }

    private List<PostDto> getPost_dtos(final List<Posts> allPosts, final Map<Integer, String> mapStatLDC) {
        ModelMapper modelMapper = new ModelMapper();
        List<PostDto> postDtos = new ArrayList<>();
        for (Posts post : allPosts) {
            PostDto post_dto = modelMapper.map(post, PostDto.class);
            if (mapStatLDC.containsKey(post_dto.getId())) {
                final String[] splitLikes = mapStatLDC.get(post_dto.getId()).split(":");
                post_dto.setLikes(Integer.parseInt(splitLikes[1]));
                post_dto.setDislikes(Integer.parseInt(splitLikes[2]));
                post_dto.setComments(Integer.parseInt(splitLikes[3]));
            }
            postDtos.add(post_dto);
        }
        return postDtos;
    }

    @GetMapping("/search")
    public String apiPostSearch(Param param) {
        return null;
    }

    @GetMapping("/{id}")
    public String apiPostById(@PathVariable("id") Integer id) {
        return null;
    }

    @GetMapping("/byDate")
    public String apiPostByDate(Param param) {
        return null;
    }

    @GetMapping("/byTag")
    public String apiPostByTag(Param param) {
        return null;
    }

    @GetMapping("/moderation")
    public String apiPostModeration(Param param) {
        return null;
    }

    @GetMapping("/my")
    public String apiPostMy(Param param) {
        return null;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public String apiPostPost(@RequestBody AddPostDto addPost) {
        List<String> errors = new ArrayList<>();
        if (addPost.getTitle() == null || addPost.getTitle().length() == 0) {
            errors.add("Заголовок не установлен");
        } else if (addPost.getTitle().length() <= 10) {
            errors.add("Заголовок слишком короткий");
        }
        if (addPost.getText() == null || addPost.getText().length() == 0) {
            errors.add("Текст публикации не установлен");
        } else if (addPost.getText().length() <= 500) {
            errors.add("Текст публикации слишком короткий");
        }

        ResultsDto result = new ResultsDto();
        if (errors.size() == 0) {
            result.setResult(true);

            ModelMapper modelMapper = new ModelMapper();

            Posts post = modelMapper.map(addPost, Posts.class);
            post.setModerationStatus(ModerationStatus.NEW);
            final LocalDateTime nowDateTime = LocalDateTime.now();
            if (post.getTime().isBefore(nowDateTime)) {
                post.setTime(nowDateTime);
            }

            final String[] splitTags = addPost.getTags().split(",");
            for (String splitTag : splitTags) {
                Tags ta=tagsService.addTags(splitTag);
                post.addTag(ta);
            }
            postsService.save(post);

        } else {
            result.setResult(false);
            result.setErrors(errors);
        }

        Gson gson = new Gson();
        return gson.toJson(result);
    }

}
