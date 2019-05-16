package com.epam.course.cp.web_app.handler;

import com.epam.course.cp.response.ExceptionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;

import java.io.IOException;

/**
 * The Web error handler catches all errors that occur in the {@code com.epam.course.cp.web_app} package.
 */

@ControllerAdvice("com.epam.course.cp.web_app")
public class WebErrorHandler {

    private final MappingJackson2HttpMessageConverter converter;

    public WebErrorHandler(MappingJackson2HttpMessageConverter converter) {
        this.converter = converter;
    }

    /**
     * Handle http client error exception.
     *
     * @param ex    is {@code HttpClientErrorException}.
     * @param model model to storage information for view rendering
     * @return the response entity with message of exception.
     */
    @ExceptionHandler(HttpClientErrorException.class)
    public final String handleHttpClientException(HttpClientErrorException ex, Model model) {

        model.addAttribute("exception", ex.getMessage());
        model.addAttribute("response", getExceptionResponse(ex.getResponseBodyAsString()));
        return "exception";
    }

    /**
     * Handle http server error exception.
     *
     * @param ex    is {@code HttpServerErrorException}.
     * @param model model to storage information for view rendering
     * @return the response entity with message of exception.
     */
    @ExceptionHandler(HttpServerErrorException.class)
    public final String handleHttpServerErrorException(HttpServerErrorException ex, Model model){

        model.addAttribute("exception", ex.getMessage());
        model.addAttribute("response", getExceptionResponse(ex.getResponseBodyAsString()));
        return "exception";
    }

    /**
     * Handle resource access exception.
     *
     * @param ex    is {@code ResourceAccessException}.
     * @param model model to storage information for view rendering
     * @return the response entity with message of exception.
     */
    @ExceptionHandler(value = ResourceAccessException.class)
    public final String handleResourceAccessException(ResourceAccessException ex, Model model) {

        model.addAttribute("exception", ex.getMessage());
        return "no_source";
    }

    private ExceptionResponse getExceptionResponse(String json) {

        try {

            return converter.getObjectMapper().readValue(json, ExceptionResponse.class);

        } catch (IOException e) {

            return new ExceptionResponse("We can't interpret response");
        }
    }
}
