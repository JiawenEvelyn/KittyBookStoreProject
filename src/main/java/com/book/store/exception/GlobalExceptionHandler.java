package com.book.store.exception;

import com.book.store.common.ErrorCode;
import com.book.store.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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
}

