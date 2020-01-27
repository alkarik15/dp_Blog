package ru.skillbox.blog.service.impl;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skillbox.blog.model.PostEntity;
import ru.skillbox.blog.model.PostVoteEntity;
import ru.skillbox.blog.model.UserEntity;
import ru.skillbox.blog.repository.PostVotesRepository;
import ru.skillbox.blog.repository.PostsRepository;
import ru.skillbox.blog.service.PostVoteService;

/**
 * @author alkarik
 * @link http://alkarik
 */
@Service
@Transactional(readOnly = true)
public class PostVoteServiceImpl implements PostVoteService {

    @Autowired
    private PostVotesRepository postVotesRepository;

    @Autowired
    private PostsRepository postsRepository;

    @Override
    public Map<Integer, String> findStatistics() {
        final List<Object[]> statLDCs = postVotesRepository.statLDC();
        Map<Integer, String> mapLDC = new HashMap<>();
        for (Object[] statLDC : statLDCs) {
            String sss = String.join(":", Arrays.stream(statLDC).map(Object::toString).toArray(String[]::new));
            mapLDC.put(Integer.parseInt(statLDC[0].toString()), sss);
        }
        return mapLDC;
    }

    @Override
    public String findStatPost(Integer id) {
        final List<Object[]> statPost = postVotesRepository.statPost(id);
        String strStat = "";
        for (Object[] stat : statPost) {
            strStat = String.join(":", Arrays.stream(stat).map(Object::toString).toArray(String[]::new));
        }
        return strStat;
    }

    @Override
    @Transactional(readOnly = false)
    public Boolean findLikeByPostIdAndUserId(final Integer postId, final Integer userId) {
        PostEntity postEntity = postsRepository.findAllById(postId);
        if (postEntity != null) {
            PostVoteEntity postLike = postVotesRepository.findAllByPostIdAndUserId(postEntity, postEntity.getUserId());
            if (postLike != null) {
                final byte value = postLike.getValue();
                if (value == 1) {
                    return false;
                } else if (value == -1) {
                    deleteLike(postLike.getId());
                }
            } else {
                setLike(new PostVoteEntity(postEntity.getUserId(), postEntity, LocalDateTime.now(), (byte) 1));
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    @Transactional(readOnly = false)
    public Boolean findDislikeByPostIdAndUserId(final Integer postId, final Integer userId) {
        PostEntity postEntity = new PostEntity();
        postEntity.setId(postId);
        UserEntity userEntity = new UserEntity();
        userEntity.setId(userId);
        final PostVoteEntity postLike = postVotesRepository.findAllByPostIdAndUserId(postEntity, userEntity);
        if (postLike != null) {
            final byte value = postLike.getValue();
            if (value == -1) {
                return false;
            } else if (value == 1) {
                deleteLike(postLike.getId());
            }
        } else {
            setLike(new PostVoteEntity(userEntity, postEntity, LocalDateTime.now(), (byte) -1));
        }
        return true;
    }

    @Override
    @Transactional(readOnly = false)
    public void setLike(PostVoteEntity postVoteEntity) {
        postVotesRepository.save(postVoteEntity);
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteLike(final Integer postId) {
        postVotesRepository.deleteById(postId);
    }
}
