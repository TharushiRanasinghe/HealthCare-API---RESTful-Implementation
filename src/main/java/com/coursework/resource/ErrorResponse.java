/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.coursework.resource;

/**
 *
 * @author Tharushi
 */

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Produces(MediaType.APPLICATION_JSON)
public class ErrorResponse {
    private String error; // Type of error
    private String message; // Error message

    // Constructor to initialize error and message
    public ErrorResponse(String error, String message) {
        this.error = error;
        this.message = message;
    }

    // Getter method for error type
    public String getError() {
        return error;
    }

    // Getter method for error message
    public String getMessage() {
        return message;
    }
}
