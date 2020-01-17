package ru.skillbox.blog.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.skillbox.blog.model.PostComments;
import ru.skillbox.blog.model.Posts;
import ru.skillbox.blog.repository.PostCommentsRepository;
import ru.skillbox.blog.service.PostCommentsService;

/**
 * @author alkarik
 * @link http://alkarik
 */
@Service
public class PostCommentsImpl implements PostCommentsService {
    @Autowired
    private PostCommentsRepository postCommentsRepository;

    @Override
    public List<PostComments> findByPostId(final Posts id) {
        List<PostComments> commentsByPostId = postCommentsRepository.findAllByPostId(id);
        return commentsByPostId;
    }
}
