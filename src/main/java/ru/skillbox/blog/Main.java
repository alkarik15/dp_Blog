package ru.skillbox.blog;

import static org.modelmapper.config.Configuration.AccessLevel.PRIVATE;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ru.skillbox.blog.component.InitProperties;
import ru.skillbox.blog.model.GlobalSettingEntity;
import ru.skillbox.blog.service.GlobalSettingService;

@SpringBootApplication
public class Main implements CommandLineRunner {
    @Autowired
    private GlobalSettingService globalSettingService;

    @Autowired
    private InitProperties app;


    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Override
    public void run(final String... args) {
        initSettings();
    }

    private void initSettings() {
        for (InitProperties.GlSettings men : app.getSettings()) {
            GlobalSettingEntity gl1 = new GlobalSettingEntity(men.getCode(), men.getName(), men.getValue());
            globalSettingService.createSetting(gl1);
        }
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration()
            .setMatchingStrategy(MatchingStrategies.STRICT)
            .setFieldMatchingEnabled(true)
            .setSkipNullEnabled(true)
            .setFieldAccessLevel(PRIVATE);
        return mapper;
    }
}
