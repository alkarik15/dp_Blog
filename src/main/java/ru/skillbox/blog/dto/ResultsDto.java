package ru.skillbox.blog.dto;

import java.util.Map;

/**
 * @author alkarik
 * @link http://alkarik
 */
public class ResultsDto {
    private Boolean result;

    private Map<String, String> errors;

    public Boolean getResult() {
        return result;
    }

    public void setResult(final Boolean result) {
        this.result = result;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public void setErrors(final Map<String, String> errors) {
        this.errors = errors;
    }
}
