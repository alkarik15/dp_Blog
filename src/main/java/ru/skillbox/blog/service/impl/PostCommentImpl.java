package ru.skillbox.blog.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skillbox.blog.dto.CommentsDto;
import ru.skillbox.blog.model.PostCommentEntity;
import ru.skillbox.blog.model.PostEntity;
import ru.skillbox.blog.repository.PostCommentsRepository;
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
    private ModelMapper modelMapper;

    @Override
    public List<CommentsDto> findByPostId(PostEntity postId) {
        List<PostCommentEntity> commentsByPostId = postCommentsRepository.findAllByPostId(postId);
        List<CommentsDto> listCommentsDto = commentsByPostId
            .stream()
            .map(postComment -> modelMapper.map(postComment, CommentsDto.class))
            .collect(Collectors.toList());

        return listCommentsDto;
    }
}
