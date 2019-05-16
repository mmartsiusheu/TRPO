package com.epam.course.cp.rest_app.handler;

import com.epam.course.cp.response.ExceptionResponse;
import com.epam.course.cp.dao.exception.DaoRuntimeException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * The Rest error handler catches all errors that occur in the {@code com.epam.course.cp.rest_app} package.
 *
 * @see ResponseEntity
 * @see ResponseEntityExceptionHandler
 */
@ControllerAdvice("com.epam.course.cp.rest_app")
public class RestErrorHandler extends ResponseEntityExceptionHandler {

    /**
     * Handle Dao Runtime Exception
     *
     * @param ex is {@code DaoRuntimeException}.
     * @return the response entity with message of exception.
     */
    @ExceptionHandler(value = DaoRuntimeException.class)
    public final ResponseEntity<ExceptionResponse> handleDaoRuntimeException(DaoRuntimeException ex) {

        ExceptionResponse response = new ExceptionResponse(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handle empty result data access exception.
     *
     * @param ex is {EmptyResultDataAccessException}.
     * @return the response entity with message of exception.
     */
    @ExceptionHandler(value = EmptyResultDataAccessException.class)
    public static ResponseEntity<ExceptionResponse> handleEmptyResultException(EmptyResultDataAccessException ex) {

        ExceptionResponse response = new ExceptionResponse(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    /**
     * Handle duplicate key exception.
     *
     * @param ex is {DuplicateKeyException}.
     * @return the response entity with message of exception.
     */
    @ExceptionHandler(value = DuplicateKeyException.class)
    public static ResponseEntity<ExceptionResponse> handleDuplicateException(DuplicateKeyException ex) {

        ExceptionResponse response = new ExceptionResponse("This instance is already exist");
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
