package com.bernhard.personal_blog.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ResponseStatusException.class)
    public String handleResponseStatusException(
            ResponseStatusException ex,
            HttpServletRequest request,
            Model model
    ) {
        logger.error("ResponseStatusException: {} at {}", ex.getReason(), request.getRequestURI());

        if (ex.getStatusCode().value() == 404) {
            model.addAttribute("message", "Couldn't find resource.");
            return "error/404";
        }

        model.addAttribute("message", "An unexpected error occurred.");
        return "error/error";
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleIllegalArgumentException(
            IllegalArgumentException ex,
            RedirectAttributes redirectAttributes
    ) {
        logger.warn("IllegalArgumentException: {}", ex.getMessage());
        redirectAttributes.addFlashAttribute("error", ex.getMessage());
        return "redirect:/admin/addArticle";
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleGenericException(
            Exception ex,
            HttpServletRequest request,
            Model model
    ) {
        logger.error("Unerwarteter Fehler bei {}: {}", request.getRequestURI(), ex.getMessage(), ex);
        model.addAttribute("message", "An unexpected error occurred, please try again!");
        return "error/500";
    }
}

