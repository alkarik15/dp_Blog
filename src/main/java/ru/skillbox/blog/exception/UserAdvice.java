package ru.skillbox.blog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.skillbox.blog.dto.ResultsDto;

/**
 * @author alkarik
 * @link http://alkarik
 */
@ControllerAdvice
public class UserAdvice {
    @ResponseBody
    @ExceptionHandler(UserUnauthorizedException.class)
    public final ResponseEntity<ResultsDto> userNotAuthorized(UserUnauthorizedException ex) {
        ResultsDto result = new ResultsDto();
        result.setResult(false);
        return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
    }
}
