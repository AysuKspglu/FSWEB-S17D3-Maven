package com.workintech.zoo.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ZooGlobalExceptionHandler {

    @ExceptionHandler(ZooException.class)
    public ResponseEntity<ZooErrorResponse> handleZooException(ZooException ex) {
        log.error("ZooException: {}", ex.getMessage(), ex);
        var body = new ZooErrorResponse(
                ex.getHttpStatus().value(),
                ex.getMessage(),
                System.currentTimeMillis()
        );
        return ResponseEntity.status(ex.getHttpStatus()).body(body);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ZooErrorResponse> handleException(Exception ex) {
        log.error("Unhandled Exception: {}", ex.getMessage(), ex);
        var body = new ZooErrorResponse(
                400, // generic için 400 bekleniyor
                ex.getMessage() != null ? ex.getMessage() : "Bad Request",
                System.currentTimeMillis()
        );
        return ResponseEntity.status(400).body(body);
    }
}
