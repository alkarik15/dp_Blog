package ru.skillbox.blog.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skillbox.blog.model.GlobalSettingEntity;
import ru.skillbox.blog.repository.GlobalSettingsRepository;
import ru.skillbox.blog.service.GlobalSettingService;

/**
 * @author alkarik
 * @link http://alkarik
 */
@Service
@Transactional(readOnly=true)
public class GlobalSettingServiceImpl implements GlobalSettingService {

    @Autowired
    private GlobalSettingsRepository globalSettingsRepository;

    @Override
    @Transactional(readOnly = false)
    public void createSetting(final GlobalSettingEntity globalSettings) {
        final GlobalSettingEntity byCode = globalSettingsRepository.findByCode(globalSettings.getCode());
        if (byCode != null) {
            globalSettings.setId(byCode.getId());
            globalSettings.setName(byCode.getName());
        }
        globalSettingsRepository.save(globalSettings);
    }

    @Override
    public List<GlobalSettingEntity> findAll() {
        return (List<GlobalSettingEntity>) globalSettingsRepository.findAll();
    }

}
