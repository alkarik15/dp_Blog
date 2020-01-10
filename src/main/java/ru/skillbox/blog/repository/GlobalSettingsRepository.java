package ru.skillbox.blog.repository;

import org.springframework.data.repository.CrudRepository;
import ru.skillbox.blog.model.GlobalSettings;

/**
 * @author alkarik
 * @link http://alkarik
 */
public interface GlobalSettingsRepository extends CrudRepository<GlobalSettings, Integer> {
    GlobalSettings findByCode(String code);
}
