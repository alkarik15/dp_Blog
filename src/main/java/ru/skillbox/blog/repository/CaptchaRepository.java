package ru.skillbox.blog.repository;

import java.time.LocalDateTime;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.skillbox.blog.model.CaptchaCodes;

/**
 * @author alkarik
 * @link http://alkarik
 */
@Repository
public interface CaptchaRepository extends CrudRepository<CaptchaCodes, Integer> {
    void deleteAllByTimeBefore(LocalDateTime ldt);
    CaptchaCodes findByCodeAndSecretCode(String code, String secretCode);

}
