package ru.skillbox.blog.dto;

import java.util.List;

/**
 * @author alkarik
 * @link http://alkarik
 */
public class ResultsDto {
    private Boolean result;
    private List<String> errors;

    public Boolean getResult() {
        return result;
    }

    public void setResult(final Boolean result) {
        this.result = result;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(final List<String> errors) {
        this.errors = errors;
    }
}
