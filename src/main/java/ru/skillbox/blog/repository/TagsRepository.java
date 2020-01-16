package ru.skillbox.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skillbox.blog.model.Tags;

/**
 * @author alkarik
 * @link http://alkarik
 */
@Repository
public interface TagsRepository extends JpaRepository<Tags,Integer> {
    Tags findTagsByName(String tagName);
}
