package ru.skillbox.blog.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skillbox.blog.dto.CommentIdDto;
import ru.skillbox.blog.dto.CommentsDto;
import ru.skillbox.blog.dto.CommentsParamDto;
import ru.skillbox.blog.dto.ResultsDto;
import ru.skillbox.blog.dto.UserDto;
import ru.skillbox.blog.exception.PostException;
import ru.skillbox.blog.model.PostCommentEntity;
import ru.skillbox.blog.model.PostEntity;
import ru.skillbox.blog.model.UserEntity;
import ru.skillbox.blog.repository.PostCommentsRepository;
import ru.skillbox.blog.repository.PostsRepository;
import ru.skillbox.blog.service.PostCommentService;

/**
 * @author alkarik
 * @link http://alkarik
 */
@Service
@Transactional(readOnly = true)
public class PostCommentImpl implements PostCommentService {
    @Autowired
    private PostCommentsRepository postCommentsRepository;

    @Autowired
    private PostsRepository postsRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<CommentsDto> findByPostId(PostEntity postId) {
        List<PostCommentEntity> commentsByPostId = postCommentsRepository.findAllByPostId(postId);
        List<CommentsDto> listCommentsDto = new ArrayList<>();
        for (PostCommentEntity postComment : commentsByPostId) {
            CommentsDto map = modelMapper.map(postComment, CommentsDto.class);
            UserDto userDto = modelMapper.map(postComment.getUserId(), UserDto.class);
            map.setUser(userDto);
            listCommentsDto.add(map);
        }
        return listCommentsDto;
    }

    @Override
    @Transactional(readOnly = false)
    public Object saveComment(final CommentsParamDto commentsParamDto, final Integer userId) {
        Map<String, String> errors = verifyComment(commentsParamDto);
        if (errors.size() == 0) {
            CommentIdDto commentIdDto = new CommentIdDto(createCommentFromDto(commentsParamDto, userId));
            return commentIdDto;
        } else {
            ResultsDto result = new ResultsDto();
            result.setResult(false);
            result.setErrors(errors);
            return result;
        }
    }

    private Integer createCommentFromDto(final CommentsParamDto commentsParamDto, final Integer userId) {
        PostCommentEntity comment = modelMapper.map(commentsParamDto, PostCommentEntity.class);
        UserEntity user = new UserEntity();
        user.setId(userId);
        comment.setUserId(user);
        comment.setTime(LocalDateTime.now());
        final Optional<PostEntity> postId = postsRepository.findById(commentsParamDto.getPostId());
        comment.setPostId(postId.orElseThrow(() -> new PostException("post not found")));
        final PostCommentEntity commentByParentId = postCommentsRepository.findAllById(commentsParamDto.getParentId());
        if (commentByParentId != null) {
            comment.setParentId(commentByParentId);
        }
        postCommentsRepository.save(comment);
        return comment.getId();
    }

    public Map<String, String> verifyComment(final CommentsParamDto addComment) {
        Map<String, String> errors = new HashMap<>();
        if (addComment.getText() == null || addComment.getText().length() < 10) {
            errors.put("text", "Текст комментария не задан, либо слишком короткий");
        }
        return errors;
    }
}
