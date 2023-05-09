package dev.joell.kalaha.common.exceptions;

/**
 * Generic exception for the API. Causes to return a 400 Bad Request.
 */
public class ApiException extends Exception {
    public ApiException(String message) {
        super(message);
    }
}
