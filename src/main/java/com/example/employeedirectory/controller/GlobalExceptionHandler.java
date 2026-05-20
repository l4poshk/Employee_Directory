package com.example.employeedirectory.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // Обработка наших RuntimeException (например, если сущность не найдена в БД)
    @ExceptionHandler(RuntimeException.class)
    public String handleRuntimeException(RuntimeException ex, Model model) {
        logger.error("Runtime Exception: ", ex);
        model.addAttribute("errorMessage", ex.getMessage());
        return "error/500";
    }

    // Обработка всех остальных непредвиденных ошибок
    @ExceptionHandler(Exception.class)
    public String handleException(Exception ex, Model model) {
        logger.error("Unexpected Exception: ", ex);
        model.addAttribute("errorMessage", "An unexpected error occurred on the server. Please contact support.");
        return "error/500";
    }
}
