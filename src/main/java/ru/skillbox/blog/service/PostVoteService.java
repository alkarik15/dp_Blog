package ru.skillbox.blog.service;

import java.util.Map;

/**
 * @author alkarik
 * @link http://alkarik
 */
public interface PostVoteService {
    Map<Integer, String> findStatistics();

    String findStatPost(Integer id);
}
