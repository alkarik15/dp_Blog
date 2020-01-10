package ru.skillbox.blog.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.skillbox.blog.model.GlobalSettings;
import ru.skillbox.blog.repository.GlobalSettingsRepository;
import ru.skillbox.blog.service.GlobalSettingsService;

/**
 * @author alkarik
 * @link http://alkarik
 */
@Service
public class GlobalSettingsServiceImpl implements GlobalSettingsService {

    @Autowired
    private GlobalSettingsRepository globalSettingsRepository;

    public void createSetting(final GlobalSettings globalSettings) {
        final GlobalSettings byCode = globalSettingsRepository.findByCode(globalSettings.getCode());
        if (byCode != null) {
            globalSettings.setId(byCode.getId());
        }
        globalSettingsRepository.save(globalSettings);
    }
}
