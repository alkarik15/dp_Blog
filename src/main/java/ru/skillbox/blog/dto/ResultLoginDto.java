package ru.skillbox.blog.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author alkarik
 * @link http://alkarik
 */
public class ResultLoginDto {
    private Boolean result;

    @JsonProperty("user")
    private UserLoginDto user;

    public Boolean getResult() {
        return result;
    }

    public void setResult(final Boolean result) {
        this.result = result;
    }

    public UserLoginDto getUserLoginDto() {
        return user;
    }

    public void setUserLoginDto(final UserLoginDto userLoginDto) {
        this.user = userLoginDto;
    }
}
