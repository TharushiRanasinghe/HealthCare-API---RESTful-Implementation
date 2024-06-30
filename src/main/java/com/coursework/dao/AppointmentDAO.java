/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.coursework.dao;

import com.coursework.model.Appointment;
import com.coursework.exception.CWNotFoundException;
import com.coursework.exception.CWBadRequestException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tharushi
 */

public class AppointmentDAO {
    // Logger for logging messages related to AppointmentDAO operations
    private static final Logger logerTool = Logger.getLogger(AppointmentDAO.class.getName());
    // List to store appointments
    private List<Appointment> appointmentsList = new ArrayList<>(); 
    // instance of AppointmentDAO
    private static AppointmentDAO instance; 

    // Private constructor to prevent instantiation from outside
    private AppointmentDAO() {}

    // Method to get a instance of AppointmentDAO
    public static synchronized AppointmentDAO getInstance() {
        if (instance == null) {
            instance = new AppointmentDAO();
        }
        return instance;
    }

    // Method to add an appointment
    public void addAppointment(Appointment appointment){
        // Check if the appointment is null
        if (appointment == null) {
            logerTool.log(Level.SEVERE, "Attempt to add a null appointment");
            throw new CWBadRequestException("Sorry, Appointment provided is invalid.");
        }
        // Check for duplicate appointment ID
        if (findingAppointment(appointment.getAppointmentId()) != null) {
            logerTool.log(Level.SEVERE, "Attempt to add duplicate appointment ID: {0}", appointment.getAppointmentId());
            throw new CWBadRequestException("A prior appointment using this ID has been made: " + appointment.getAppointmentId());
        }
        // Add the appointment to the list
        appointmentsList.add(appointment);
        logerTool.log(Level.INFO, "Appointment added with ID: {0}", appointment.getAppointmentId());
    }

    // Method to get an appointment by ID
    public Appointment getAppointmentById(String id) {
        Appointment appointment = findingAppointment(id);
        // Check if appointment is found
        if (appointment == null) {
            logerTool.log(Level.SEVERE, "No appointment found with ID: {0}", id);
            throw new CWNotFoundException("Cannot find any appointment with ID: " + id);
        }
        return appointment;
    }

    // Method to update an appointment
    public void updateAppointment(Appointment appointment) {
        // Check if the appointment is null
        if (appointment == null) {
            logerTool.log(Level.SEVERE, "Attempt to update a null appointment");
            throw new CWBadRequestException("Sorry, provided appointment is invalid. ");
        }
        Appointment existingAppointment = findingAppointment(appointment.getAppointmentId());
        // Check if existing appointment is found
        if (existingAppointment == null) {
            logerTool.log(Level.SEVERE, "No appointment found for update with ID: {0}", appointment.getAppointmentId());
            throw new CWNotFoundException("Sorry, cannot find appointment with ID: " + appointment.getAppointmentId());
        }
        // Update the appointment in the list
        appointmentsList.set(appointmentsList.indexOf(existingAppointment), appointment);
        logerTool.log(Level.INFO, "Appointment updated with ID: {0}", appointment.getAppointmentId());
    }


    // Method to delete an appointment
    public void deleteAppointment(String id) {
        Appointment appointment = getAppointmentById(id);
        // Remove the appointment from the list
        appointmentsList.remove(appointment);
        logerTool.log(Level.INFO, "Appointment deleted with ID: {0}", id);
    }

    // Method to get all appointments
    public List<Appointment> getAllAppointments() {
        logerTool.log(Level.INFO, "Fetching all appointments");
        return new ArrayList<>(appointmentsList);
    }

    // Helper method to find an appointment by ID
    private Appointment findingAppointment(String id) {
        for (Appointment appointment : appointmentsList) {
            if (appointment.getAppointmentId().equals(id)) {
                return appointment;
            }
        }
        return null;
    } 
}
