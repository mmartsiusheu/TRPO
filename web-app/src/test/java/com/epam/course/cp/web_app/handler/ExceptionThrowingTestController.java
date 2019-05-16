package com.epam.course.cp.web_app.handler;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;

@Controller
public class ExceptionThrowingTestController {

    @GetMapping(value = "/httpClientException")
    void getHttpClientExcepttion() {
        throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Bad request");
    }

    @GetMapping(value = "/httpServerException")
    void getHttpServerExcepttion() {
        throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error");
    }

    @GetMapping(value = "/resourceAccessException")
    void getResourceAccessException() {
        throw new ResourceAccessException("Can't find object");
    }

}
