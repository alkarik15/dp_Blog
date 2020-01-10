package ru.skillbox.blog.component;

import java.util.Map;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author alkarik
 * @link http://alkarik
 */
@Component
@ConfigurationProperties(prefix = "blog")
public class HeaderProperties {
    private Map<String, String> header;

    public Map<String, String> getHeader() {
        return header;
    }

    public void setHeader(final Map<String, String> header) {
        this.header = header;
    }
}
