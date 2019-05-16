package com.epam.course.cp.dao.exception;

public class DaoRuntimeException extends RuntimeException {

    public DaoRuntimeException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public DaoRuntimeException(String s) {
        super(s);
    }
}
