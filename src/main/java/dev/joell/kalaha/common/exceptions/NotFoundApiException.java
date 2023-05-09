package dev.joell.kalaha.common.exceptions;

/**
 * Exception to throw when resource is not found. Causes to return a 404 Not
 * Found.
 */
public class NotFoundApiException extends ApiException {
    public NotFoundApiException(String message) {
        super(message);
    }
}