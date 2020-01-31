package ru.skillbox.blog.controller;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.skillbox.blog.dto.AddPostDto;
import ru.skillbox.blog.dto.OffsetLimitQueryDto;
import ru.skillbox.blog.dto.PostByIdDto;
import ru.skillbox.blog.dto.PostIdDto;
import ru.skillbox.blog.dto.PostsDto;
import ru.skillbox.blog.dto.ResultLikeDislikeDto;
import ru.skillbox.blog.dto.ResultsDto;
import ru.skillbox.blog.dto.enums.ParametrMode;
import ru.skillbox.blog.dto.enums.ParametrStatus;
import ru.skillbox.blog.model.enums.ModerationStatus;
import ru.skillbox.blog.service.PostService;
import ru.skillbox.blog.service.PostVoteService;

/**
 * @author alkarik
 * @link http://alkarik
 */
@RestController
@RequestMapping("/api/post")
public class ApiPostController {

    @Autowired
    private PostService postService;

    @Autowired
    private PostVoteService postVoteService;

    @GetMapping()
    @ResponseBody
    public ResponseEntity<PostsDto> apiPost(OffsetLimitQueryDto param, @RequestParam(name = "mode") String mode) {
        final Map<Integer, String> mapStatLDC = postVoteService.findStatistics(null);
        ParametrMode incomeMode = ParametrMode.valueOf(mode.toUpperCase());
        PostsDto postsDto = postService.apiPost(param, incomeMode, mapStatLDC);

        return new ResponseEntity<>(postsDto, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<PostsDto> apiPostSearch(@RequestBody OffsetLimitQueryDto param) {
        PostsDto allPostSearch;
        if (param.getQuery().equals("")) {
            //search all
            allPostSearch = postService.getAllPostSearch(param);
        } else {
            //search by query
            allPostSearch = postService.getAllPostSearchByQuery(param, param.getQuery());
        }
        return new ResponseEntity(allPostSearch, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostByIdDto> apiPostById(@PathVariable("id") Integer id) {
        PostByIdDto postByDto = postService.getPostByIdModerationStatusActiveTime(id, true, ModerationStatus.ACCEPTED, LocalDateTime.now());
        return new ResponseEntity<>(postByDto, HttpStatus.OK);
    }

    @GetMapping("/byDate")
    public String apiPostByDate(OffsetLimitQueryDto param) {
        return null;
    }

    @GetMapping("/byTag")
    public String apiPostByTag(OffsetLimitQueryDto param) {
        return null;
    }

    @GetMapping("/moderation")
    public ResponseEntity<PostsDto> apiPostModeration(HttpServletRequest request, OffsetLimitQueryDto param, @RequestParam(name = "status") String status) {
        if (request.getSession().getAttribute("user") != null && request.getSession().getAttribute("user").toString().length() > 0) {
            ModerationStatus moderationStatus = ModerationStatus.valueOf(status.toUpperCase());
            PostsDto postsDto = postService.apiPostModeration(param, moderationStatus);
            return new ResponseEntity<>(postsDto, HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/my")
    public ResponseEntity<PostsDto> apiPostMy(HttpServletRequest request, OffsetLimitQueryDto param, @RequestParam("status") String status) {
        if (request.getSession().getAttribute("user") != null && request.getSession().getAttribute("user").toString().length() > 0) {
            Integer userId = Integer.parseInt(request.getSession().getAttribute("user").toString());
            System.out.println(userId);
            final Map<Integer, String> mapStatLDC = postVoteService.findStatistics(userId);
            ParametrStatus incomeStatus = ParametrStatus.valueOf(status.toUpperCase());
            PostsDto postsDto = postService.apiPost(param, incomeStatus, mapStatLDC, userId);
            return new ResponseEntity<>(postsDto, HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultsDto> apiPostPost(HttpServletRequest request, @RequestBody AddPostDto addPost) {
        ResultsDto result = postService.createPost(request, addPost);
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @PostMapping("/like")
    public ResponseEntity apiPostLike(HttpServletRequest request, @RequestBody PostIdDto postIdDto) {
        if (request.getSession().getAttribute("user") != null && request.getSession().getAttribute("user").toString().length() > 0) {
            Integer userId = Integer.parseInt(request.getSession().getAttribute("user").toString());
            Integer postId = postIdDto.getPost_id();
            if (postId != null && postId > 0) {
                Boolean postLike = postVoteService.setLikeByPostIdAndUserId(postId, userId);
                ResultLikeDislikeDto resultLikeDislikeDto = new ResultLikeDislikeDto(postLike);
                return new ResponseEntity(resultLikeDislikeDto, HttpStatus.OK);
            } else {
                return new ResponseEntity(HttpStatus.OK);
            }
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/dislike")
    public ResponseEntity apiPostDislike(HttpServletRequest request, @RequestBody PostIdDto
        postIdDto) {
        if (request.getSession().getAttribute("user") != null && request.getSession().getAttribute("user").toString().length() > 0) {
            Integer userId = Integer.parseInt(request.getSession().getAttribute("user").toString());
            Integer postId = postIdDto.getPost_id();
            Boolean postDisLike = postVoteService.findDislikeByPostIdAndUserId(postId, userId);
            ResultLikeDislikeDto resultLikeDislikeDto = new ResultLikeDislikeDto(postDisLike);
            return new ResponseEntity(resultLikeDislikeDto, HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }
}
