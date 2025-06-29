package com.kabirit.cards_service.exceptions;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author nasimkabir
 * @date 7/4/25
 */

@Data
@Builder
public class Response {
    @JsonInclude(JsonInclude.Include.ALWAYS)
    private final LocalDateTime timestamp = LocalDateTime.now();
    @JsonInclude(JsonInclude.Include.ALWAYS)
    private int statusCode;
    @JsonInclude(JsonInclude.Include.ALWAYS)
    private String status;
    @JsonInclude(JsonInclude.Include.ALWAYS)
    private String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object content;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<ResponseError> errors;
}
