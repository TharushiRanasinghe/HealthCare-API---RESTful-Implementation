/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.coursework.exception;

/**
 *
 * @author Tharushi
 */
// This class represents a custom runtime exception for indicating resource not found errors.
public class CWNotFoundException extends RuntimeException{
    public CWNotFoundException(String message) {
        super(message);
    }
}
