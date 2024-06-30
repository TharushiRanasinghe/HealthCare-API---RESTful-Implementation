/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.coursework.model;

import java.util.Date;

/**
 *
 * @author Tharushi
 */
public class Appointment {
    
    private String appointmentId;
    private Date appointmentDate;
    private String appointmentTime;
    private Patient patient;
    private Doctor doctor;
    
    // Constructors
    // Default constructor
    public Appointment(){
        
    }
    
    //Parameterized constructor to initialize an Appointment object with values.
    public Appointment(String appointmentId, Date appointmentDate, String appointmentTime, Patient patient, Doctor doctor) {
        this.appointmentId = appointmentId;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.patient = patient;
        this.doctor = doctor;
    }

    // Retrieves the appointment ID.
    public String getAppointmentId() {
        return appointmentId;
    }

    //Sets the appointment ID.
    public void setAppointmentId(String appointmentId){
        this.appointmentId=appointmentId;
    }

    //Retrieves the appointment date.
    public Date getAppointmentDate() {
        return appointmentDate;
    }

    //Sets the appointment date.
    public void setAppontmentDate(Date appointmentDate) {
        this.appointmentDate = appointmentDate;
    }
    
    //Retrieves the appointment time.
    public String getAppointmentTime() {
        return appointmentTime;
    }

    //Sets the appointment time.
    public void setAppintmentTime(String appointmentTime) {
        this.appointmentTime = appointmentTime;
    }
    
    //Retrieves the patient associated with the appointment.
    public Patient getPatient() {
        return patient;
    }

    //Sets the patient associated with the appointment.
    public void setPatinet(Patient patient) {
        this.patient = patient;
    }
    
    //Retrieves the doctor associated with the appointment.
    public Doctor getDoctor() {
        return doctor;
    }

    //Sets the doctor associated with the appointment.
    public void setDoctor(Doctor doctor) {
        this.doctor=doctor;
    }
    
}
