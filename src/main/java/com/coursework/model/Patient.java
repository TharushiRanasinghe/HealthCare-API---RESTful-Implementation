/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.coursework.model;

/**
 *
 * @author Tharushi
 */
public class Patient extends Person{

    private String patientMedicalHistory;
    private String currentHealthStatus;
    
    //constructors
    
    //default constructor
    public Patient(){
        
    }
    
    //Parameterized constructor to initialize a Patient object with values.
    public Patient(String personId, String personName, String personContactInfo, String personAddress, String patientMedicalHistory, String currentHealthStatus) {
        super(personId, personName, personContactInfo, personAddress);
        this.patientMedicalHistory = patientMedicalHistory;
        this.currentHealthStatus = currentHealthStatus;
        
    }
    
    //Retrieves the medical history of the patient
    public String getPatientMedicalHistory() {
        return patientMedicalHistory;
    }
    
    //Sets the medical history of the patient.
    public void setPatientMedicalHistory(String patientMedicalHistory) {
        this.patientMedicalHistory = patientMedicalHistory;
    }
    
    //Retrieves the current health status of the patient.
    public String getCurrentHealthStatus() {
        return currentHealthStatus;
    }
    
    //Sets the current health status of the patient.
    public void setCurrentHealthStatus(String currentHealthStatus){
        this.currentHealthStatus=currentHealthStatus;
    }

}
