package com.sama.portfolio.exception;

import com.sama.portfolio.config.DebugConfig;
import com.sama.portfolio.dto.ErrorResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final DebugConfig debugConfig;

    /**
     * Handle DataIntegrityViolationException
     * Thrown when database constraints are violated (unique, not null, etc.)
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolation(
            DataIntegrityViolationException ex,
            WebRequest request) {

        log.error("Data Integrity Violation: {}", ex.getMessage(), ex);

        String message = "Database constraint violation. Unable to save/update project.";
        String details = null;

        if (debugConfig.isDebugLevel()) {
            details = ex.getRootCause() != null ? ex.getRootCause().getMessage() : ex.getMessage();
            log.debug("Violation details: {}", details);
        }

        ErrorResponse response = ErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(message)
                .error("DATA_INTEGRITY_VIOLATION")
                .timestamp(LocalDateTime.now())
                .path(request.getDescription(false).replace("uri=", ""))
                .details(details)
                .build();

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle DataAccessException
     * General database access exceptions
     */
    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ErrorResponse> handleDataAccessException(
            DataAccessException ex,
            WebRequest request) {

        log.error("Database Access Error: {}", ex.getMessage(), ex);

        String message = "Database operation failed. Please try again later.";
        String details = null;

        if (debugConfig.isDebugLevel()) {
            details = ex.getMessage();
            log.debug("Access error details: {}", details);
        }

        ErrorResponse response = ErrorResponse.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(message)
                .error("DATABASE_ERROR")
                .timestamp(LocalDateTime.now())
                .path(request.getDescription(false).replace("uri=", ""))
                .details(details)
                .build();

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handle IllegalArgumentException
     * Thrown when invalid arguments are passed
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(
            IllegalArgumentException ex,
            WebRequest request) {

        log.warn("Illegal Argument: {}", ex.getMessage());

        ErrorResponse response = ErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message("Invalid input: " + ex.getMessage())
                .error("INVALID_ARGUMENT")
                .timestamp(LocalDateTime.now())
                .path(request.getDescription(false).replace("uri=", ""))
                .details(ex.getMessage())
                .build();

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle NullPointerException
     * Thrown when null value is encountered unexpectedly
     */
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ErrorResponse> handleNullPointerException(
            NullPointerException ex,
            WebRequest request) {

        log.error("Null Pointer Exception: {}", ex.getMessage(), ex);

        String message = "An unexpected error occurred. Please try again.";
        String details = null;

        if (debugConfig.isDebugLevel()) {
            details = "Null value encountered at: " + ex.getStackTrace()[0].toString();
            log.debug("Null pointer details: {}", details);
        }

        ErrorResponse response = ErrorResponse.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(message)
                .error("NULL_POINTER")
                .timestamp(LocalDateTime.now())
                .path(request.getDescription(false).replace("uri=", ""))
                .details(details)
                .build();

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handle generic Exception
     * Catch-all for any other exceptions
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(
            Exception ex,
            WebRequest request) {

        log.error("Unexpected Exception: {}", ex.getMessage(), ex);

        String message = "An unexpected error occurred. Please contact support.";
        String details = null;

        if (debugConfig.isDebugLevel()) {
            details = ex.getMessage();
            log.debug("Exception details: {}", details);
        }

        ErrorResponse response = ErrorResponse.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(message)
                .error("INTERNAL_ERROR")
                .timestamp(LocalDateTime.now())
                .path(request.getDescription(false).replace("uri=", ""))
                .details(details)
                .build();

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
