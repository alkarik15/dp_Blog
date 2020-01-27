package ru.skillbox.blog.dto;

/**
 * @author alkarik
 * @link http://alkarik
 */
public class ResultLikeDislikeDto {
    private Boolean result;

    public Boolean getResult() {
        return result;
    }

    public void setResult(final Boolean result) {
        this.result = result;
    }

    public ResultLikeDislikeDto(final Boolean result) {
        this.result = result;
    }
}
