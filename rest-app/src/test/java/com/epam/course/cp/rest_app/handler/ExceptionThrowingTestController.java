package com.epam.course.cp.rest_app.handler;

import com.epam.course.cp.dao.exception.DaoRuntimeException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exceptions")
public class ExceptionThrowingTestController {

    @GetMapping(value = "/daoRuntimeException")
    void getDaoRuntimeException() {
        throw new DaoRuntimeException("Dao runtime exception");
    }

    @GetMapping(value = "/emptyResultDataAccessException")
    void getEmptyResultDataAccessException() {
        throw new EmptyResultDataAccessException(0);
    }

    @GetMapping(value = "/duplicateKeyException")
    void handleDuplicateKeyException() {
        throw new DuplicateKeyException("This instance is already exist");
    }
}
