package com.book.store.exception;

import com.book.store.common.ErrorCode;
import com.book.store.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BizException.class)
    public ResponseEntity<Result<Void>> handleBiz(BizException e) {
        log.warn("BizException occurred, ", e);
        return ResponseEntity.status(e.getErrorCode().getHttpStatus()).body(Result.fail(e.getErrorCode()));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Result<Void>> handleUnexpected(RuntimeException e) {
        log.error("unexpected exception while handling request, ", e);
        return ResponseEntity.status(ErrorCode.UNEXPECTED.getHttpStatus()).body(Result.fail(ErrorCode.UNEXPECTED));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Result<Void>> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.warn("HttpMessageNotReadableException exception while handling request, ", e);
        return ResponseEntity.status(ErrorCode.BAD_MESSAGE.getHttpStatus()).body(Result.fail(ErrorCode.BAD_MESSAGE));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Result<Map<String, String>>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.warn("MethodArgumentNotValidException exception while handling request, ", e);
        return ResponseEntity.status(ErrorCode.BAD_REQUEST.getHttpStatus()).body(Result.fail(ErrorCode.BAD_REQUEST, getErrorFields(e.getBindingResult().getFieldErrors())));
    }

    private static Map<String, String> getErrorFields(List<FieldError> fieldErrors) {
        Map<String, String> fieldMap = new HashMap<>();
        fieldErrors.forEach(fieldError -> fieldMap.put(fieldError.getField(), fieldError.getDefaultMessage()));
        return fieldMap;
    }
}

