package ru.skillbox.blog.repository;

import org.springframework.data.repository.CrudRepository;
import ru.skillbox.blog.model.GlobalSettingEntity;

/**
 * @author alkarik
 * @link http://alkarik
 */
public interface GlobalSettingsRepository extends CrudRepository<GlobalSettingEntity, Integer> {
    GlobalSettingEntity findByCode(String code);
}
