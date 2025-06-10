package com.joblessfriend.jobfinder.util;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    //전역예외 //
    @ExceptionHandler(IllegalArgumentException.class)
    public String handleIllegalArgument(IllegalArgumentException e, Model model){

        model.addAttribute("message", e.getMessage());
        return "error/error";

    }

    @ExceptionHandler(NullPointerException.class)
    public String handleNullPointerArgument(NullPointerException e, Model model){

        model.addAttribute("message", e.getMessage());
        return "error/error";

    }
    @ExceptionHandler(Exception.class)
    public String handleAllExceptions(Exception e, Model model) {
        model.addAttribute("message", e.getMessage());
        return "error/error"; // JSP 경로
    }
}
