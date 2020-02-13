package ru.skillbox.blog.dto.projection;

import java.time.LocalDateTime;

/**
 * @author alkarik
 * @link http://alkarik
 */
public interface StatPostsShow {
    Integer getPostCount();
    Integer getShowCount();
    LocalDateTime getFirstPubl();
}
