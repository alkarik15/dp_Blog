package ru.skillbox.blog.service;

import java.util.List;
import ru.skillbox.blog.model.GlobalSettingEntity;

/**
 * @author alkarik
 * @link http://alkarik
 */
public interface GlobalSettingService {
    void createSetting(final GlobalSettingEntity globalSettings);
    List<GlobalSettingEntity> findAll();
}
