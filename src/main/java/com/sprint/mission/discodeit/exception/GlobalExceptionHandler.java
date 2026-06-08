package com.sprint.mission.discodeit.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        log.error("예상치 못한 오류 발생: {}", e.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(e, 500);
        return ResponseEntity.status(errorResponse.getStatus()).body(errorResponse);
    }

    @ExceptionHandler(DiscodeitException.class)
    public ResponseEntity<ErrorResponse> handleException(DiscodeitException e) {
        log.error("커스텀 예외 발생: code={}, message={}, detail={}", e.getErrorCode(), e.getMessage(), e.getDetails());
        HttpStatus httpStatus = parseHttpStatus(e);
        ErrorResponse errorResponse = new ErrorResponse(e, httpStatus.value());
        return ResponseEntity.status(httpStatus).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException e) {
        log.error("요청 유효성 검사 실패: {}", e.getMessage());

        Map<String, Object> validationErrors = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError)error).getField();
            String errorMessage = error.getDefaultMessage();
            validationErrors.put(fieldName, errorMessage);
        });

        ErrorResponse response = new ErrorResponse(
                Instant.now(),
                "VALIDATION_ERROR",
                "요청 데이터 유효성 검사에 실패하였습니다.",
                validationErrors,
                e.getClass().getSimpleName(),
                HttpStatus.BAD_REQUEST.value()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    private HttpStatus parseHttpStatus(DiscodeitException e) {
        ErrorCode errorCode = e.getErrorCode();
        return switch (errorCode) {
            case USER_NOT_FOUND, USER_STATUS_NOT_FOUND, READ_STATUS_NOT_FOUND, CHANNEL_NOT_FOUND, MESSAGE_NOT_FOUND,
                 BINARY_NOT_FOUND -> HttpStatus.NOT_FOUND;
            case DUPLICATE_USER, DUPLICATE_USER_STATUS, DUPLICATE_READ_STATUS -> HttpStatus.CONFLICT;
            case INVALID_USER_CREDENTIALS -> HttpStatus.UNAUTHORIZED;
            case PRIVATE_CHANNEL_UPDATE -> HttpStatus.FORBIDDEN;
            case INVALID_REQUEST -> HttpStatus.BAD_REQUEST;
            case INTERNAL_SERVER_ERROR -> HttpStatus.INTERNAL_SERVER_ERROR;
        };
    }
}