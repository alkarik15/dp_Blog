package ru.skillbox.blog.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.skillbox.blog.dto.projection.TagType;
import ru.skillbox.blog.model.TagEntity;

/**
 * @author alkarik
 * @link http://alkarik
 */
@Repository
public interface TagsRepository extends JpaRepository<TagEntity,Integer> {
    TagEntity findTagsByName(String tagName);

    @Query(nativeQuery = true, value = "SELECT t1.id,t1.name, COUNT(t.tag_id) as cnt " +
        "FROM tag2post t " +
        "LEFT JOIN tags t1 ON t.tag_id = t1.id " +
        "GROUP BY t.tag_id ORDER BY cnt DESC")
    List<TagType> tagsCountInPost();


}
