package ru.skillbox.blog.service;

import java.util.Map;

/**
 * @author alkarik
 * @link http://alkarik
 */
public interface PostVotesService {
    Map<Integer,String> findStatistics();
}
