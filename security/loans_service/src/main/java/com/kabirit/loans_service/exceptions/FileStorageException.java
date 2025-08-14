package com.kabirit.loans_service.exceptions;

/**
 * @author nasimkabir
 * @date 7/4/25
 */

public class FileStorageException extends RuntimeException {

    public FileStorageException(String message) {
        super(message);
    }

    public FileStorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
