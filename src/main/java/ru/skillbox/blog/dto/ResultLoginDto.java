package ru.skillbox.blog.dto;

/**
 * @author alkarik
 * @link http://alkarik
 */
public class ResultLoginDto {
    private Boolean result;

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
