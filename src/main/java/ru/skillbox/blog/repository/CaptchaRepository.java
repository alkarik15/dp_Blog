package ru.skillbox.blog.repository;

import java.time.LocalDateTime;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.skillbox.blog.model.CaptchaCodeEntity;

/**
 * @author alkarik
 * @link http://alkarik
 */
@Repository
public interface CaptchaRepository extends CrudRepository<CaptchaCodeEntity, Integer> {
    void deleteAllByTimeBefore(LocalDateTime ldt);
    CaptchaCodeEntity findByCodeAndSecretCode(String code, String secretCode);

}
