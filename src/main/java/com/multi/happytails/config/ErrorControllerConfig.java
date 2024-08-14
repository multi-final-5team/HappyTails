package com.multi.happytails.config;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * packageName    : com.multi.happytails.config
 * fileName       : ErrorControllerConfig
 * author         : ehdtka
 * date           : 2024-08-14
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-08-14        ehdtka       최초 생성
 */
@ControllerAdvice
public class ErrorControllerConfig {

    @ExceptionHandler
    public String handleBadRequestException(Exception e) {

        return "/error/error";
    }
}
