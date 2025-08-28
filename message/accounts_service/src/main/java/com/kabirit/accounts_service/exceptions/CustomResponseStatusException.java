package com.kabirit.accounts_service.exceptions;

/**
 * @author nasimkabir
 * @date 7/4/25
 */

public class CustomResponseStatusException extends RuntimeException {
    public CustomResponseStatusException(String message) {
        super(message);
    }
}
