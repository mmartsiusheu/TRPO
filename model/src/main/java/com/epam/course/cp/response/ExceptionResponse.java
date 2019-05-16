package com.epam.course.cp.response;

/**
 * The {@code ExceptionResponse} is a wrapper class
 * for result of rest incorrect work
 */
public class ExceptionResponse {

    /**
     * Message describing a reason of incorrect work
     */
    private String message;

    /**
     * Constructing an empty new object
     */
    public ExceptionResponse() {
    }

    /**
     * Constructing new object with a message
     *
     * @param message describing a reason of incorrect work
     */
    public ExceptionResponse(String message) {
        this.message = message;
    }

    /**
     * Getting message, describing {@code ExceptionResponse}
     *
     * @return String representing a message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Setting a message to {@code ExceptionResponse}
     * @param message describing a reason of incorrect work
     */
    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ExceptionResponse{" +
                "message='" + message + '\'' +
                '}';
    }
}
