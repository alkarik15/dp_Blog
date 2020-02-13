package ru.skillbox.blog.service.impl;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skillbox.blog.dto.StatsPostDto;
import ru.skillbox.blog.dto.projection.PidSumsVotesComment;
import ru.skillbox.blog.dto.projection.SumsVotes;
import ru.skillbox.blog.model.PostEntity;
import ru.skillbox.blog.model.PostVoteEntity;
import ru.skillbox.blog.model.UserEntity;
import ru.skillbox.blog.repository.PostVotesRepository;
import ru.skillbox.blog.repository.PostsRepository;
import ru.skillbox.blog.repository.UsersRepository;
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
    private UsersRepository usersRepository;

    @Autowired
    private PostsRepository postsRepository;

    @Override
    public Map<Integer, StatsPostDto> findStatistics(Integer userId) {
        List<PidSumsVotesComment> statLDCs = (userId == null) ? postVotesRepository.statLDC() : postVotesRepository.statLDCMy(userId);
        Map<Integer, StatsPostDto> mapLDC = new HashMap<>();
        for (PidSumsVotesComment statLDC : statLDCs) {
            mapLDC.put(statLDC.getId(), new StatsPostDto(statLDC.getLikes(),statLDC.getDislikes(),statLDC.getCommentCount()));
        }
        return mapLDC;
    }

    @Override
    public StatsPostDto findStatPost(Integer id) {
        final SumsVotes sumsVotes = postVotesRepository.statPost(id);
        if(sumsVotes.getLikes()!=null) {
            return new StatsPostDto(sumsVotes.getLikes(), sumsVotes.getDislikes());
        }else{
            return  new StatsPostDto(0, 0);
        }
    }

    @Override
    @Transactional(readOnly = false)
    public Boolean setLikeByPostIdAndUserId(final Integer postId, final Integer userId) {
        PostEntity postEntity = postsRepository.findAllById(postId);
        if (postEntity != null) {
            UserEntity userEntity = usersRepository.findAllById(userId);
            PostVoteEntity postLike = postVotesRepository.findAllByPostIdAndUserId(postEntity, userEntity);
            if (postLike != null) {
                final byte value = postLike.getValue();
                if (value == 1) {
                    return false;
                } else if (value == -1) {
                    deleteLike(postLike.getId());
                }
            } else {
                setLike(new PostVoteEntity(userEntity, postEntity, LocalDateTime.now(), (byte) 1));
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
        UserEntity userEntity = usersRepository.findAllById(userId);
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
