package com.kabirit.cards_service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;

/**
 * @author nasimkabir
 * @date 7/4/25
 */

public class ResponseBuilder {
    private ResponseBuilder() {
    }

    private static List<ResponseError> getCustomError(BindingResult result) {
        List<ResponseError> dtoList = new ArrayList<>();
        result.getFieldErrors().forEach(fieldError -> {
            ResponseError dto = ResponseError.builder()
                    .field(fieldError.getField())
                    .message(fieldError.getDefaultMessage()).build();
            dtoList.add(dto);
        });
        return dtoList;
    }

    public static Response getFailureResponse(BindingResult result, String message) {
        return Response.builder()
                .message(message)
                .errors(getCustomError(result))
                .status(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .statusCode(HttpStatus.BAD_REQUEST.value()).build();
    }

    public static Response getFailureResponse(HttpStatus status, String message) {
        return Response.builder()
                .message(message)
                .status(status.getReasonPhrase())
                .statusCode(status.value()).build();
    }


    public static Response getFailureResponse(BindingResult result, String message, HttpStatus status) {
        return Response.builder()
                .message(message)
                .errors(getCustomError(result))
                .status(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .statusCode(HttpStatus.BAD_REQUEST.value()).build();
    }


    public static Response getSuccessResponse(HttpStatus status, String message, Object content) {
        return Response.builder()
                .message(message)
                .status(status.getReasonPhrase())
                .content(content)
                .statusCode(status.value()).build();
    }

    public static Response getSuccessResponse(HttpStatus status, String message) {
        return Response.builder()
                .message(message)
                .status(status.getReasonPhrase())
                .statusCode(status.value()).build();
    }

}
