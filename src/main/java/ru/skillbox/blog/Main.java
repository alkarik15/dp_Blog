package ru.skillbox.blog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.skillbox.blog.component.InitProperties;
import ru.skillbox.blog.model.GlobalSettings;
import ru.skillbox.blog.service.GlobalSettingsService;

@SpringBootApplication
public class Main implements CommandLineRunner {
    @Autowired
    private GlobalSettingsService globalSettingsService;

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
            GlobalSettings gl1 = new GlobalSettings(men.getCode(), men.getName(), men.getValue());
            globalSettingsService.createSetting(gl1);
        }
    }
}
