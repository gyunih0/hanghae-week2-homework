package com.sparta.week02homework.exception;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class ExControllerAdvice {

    // 회원가입 예외처리(valid 여러개)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleMethodNotValidException(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getAllErrors()
                .forEach(c -> errors.put(((FieldError) c).getField(), c.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errors);
    }


    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleArgumentException(IllegalArgumentException e) {
        log.warn("handleAllException", e);
        return ResponseEntity.internalServerError().body(e.getMessage());
    }


    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<String> handleUsernameException(UsernameNotFoundException e) {
        log.warn("handleAllException", e);
        return ResponseEntity.internalServerError().body(e.getMessage());
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Object> handleAuthException(AuthenticationException e) {
        log.warn("handleAllException", e);
        return ResponseEntity.internalServerError().body(new ErrorResult("인증오류", "안돼 돌아가"));
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllException(Exception e) {
        log.warn("handleAllException", e);
        return ResponseEntity.internalServerError().body(e.getMessage());
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<Object> handleNullPointerException(NullPointerException e) {
        log.warn("handleAllException", e);
        return ResponseEntity.internalServerError().body(new ErrorResult("인증오류", "안돼 돌아가"));
    }

}
