package ru.skillbox.blog.component;

import java.util.ArrayList;
import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author alkarik
 * @link http://alkarik
 */
@Component
@ConfigurationProperties("global")
public class InitProperties {
    private List<GlSettings> settings = new ArrayList<>();

    public List<GlSettings> getSettings() {
        return settings;
    }

    public void setSettings(final List<GlSettings> settings) {
        this.settings = settings;
    }

    public static class GlSettings {
        private String code;

        private String name;

        private String value;

        public String getCode() {
            return code;
        }

        public void setCode(final String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(final String name) {
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public void setValue(final String value) {
            this.value = value;
        }
    }
}
