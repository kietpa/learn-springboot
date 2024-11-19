package com.kiet.exception;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.ZonedDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record ApiError(
        String path,
        String message,
        int statusCode,
        ZonedDateTime zonedDateTime,
        List<String> errors
) {
}
