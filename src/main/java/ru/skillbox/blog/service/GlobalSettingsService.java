package ru.skillbox.blog.service;

import java.util.List;
import ru.skillbox.blog.model.GlobalSettings;

/**
 * @author alkarik
 * @link http://alkarik
 */
public interface GlobalSettingsService {
    void createSetting(final GlobalSettings globalSettings);
    List<GlobalSettings> findAll();
}
