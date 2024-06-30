/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.coursework.resource;

import com.coursework.exception.CWBadRequestException;
import com.coursework.exception.CWNotFoundException;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.core.Response;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Tharushi
 */

@Provider
public class GlobalExceptionMapper implements ExceptionMapper<Throwable>{
    // Method to map exceptions to appropriate HTTP responses
    @Override
    public Response toResponse(Throwable ex) {
        Response.Status status;

        if (ex instanceof WebApplicationException) {
            return ((WebApplicationException) ex).getResponse();
        } else if (ex instanceof CWNotFoundException) { // Custom exception for not found
            status = Response.Status.NOT_FOUND;
        } else if (ex instanceof CWBadRequestException) { // Custom exception for bad requests
            status = Response.Status.BAD_REQUEST;
        } else {
            status = Response.Status.INTERNAL_SERVER_ERROR; // Other exceptions are considered server errors
        }

        // Building response with appropriate status and custom error message
        return Response.status(status)
                       .entity(new ErrorMessage(ex.getMessage())) // Custom error message class
                       .type(MediaType.APPLICATION_JSON)
                       .build();
    }

    // Inner class representing a custom error message format
    @Produces(MediaType.APPLICATION_JSON)
    public static class ErrorMessage {
        private String message;

        // Constructor to initialize the error message
        public ErrorMessage(String message) {
            this.message = message;
        }

        // Getter method for the error message
        public String getMessage() {
            return message;
        }
    }
}
