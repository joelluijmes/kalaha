package dev.joell.kalaha.common;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import dev.joell.kalaha.common.exceptions.ApiException;
import dev.joell.kalaha.common.exceptions.NotFoundApiException;

@ResponseBody
@ControllerAdvice
public class GlobalControllerAdvice {

    /**
     * Triggers on validation errors (@Validated annotation in the DTOs) -> 400 Bad
     * Request.
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    /**
     * Triggers on NotFoundApiException -> 404 Not Found.
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundApiException.class)
    public Map<String, String> handleValidationExceptions(NotFoundApiException ex) {
        return Map.of("error", ex.getMessage());
    }

    /**
     * Triggers on ApiException -> 400 Bad Request.
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ApiException.class)
    public Map<String, String> handleValidationExceptions(ApiException ex) {
        return Map.of("error", ex.getMessage());
    }
}
