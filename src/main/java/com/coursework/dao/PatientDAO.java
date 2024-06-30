/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.coursework.dao;

import com.coursework.model.Patient;
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

public class PatientDAO {
    // Logger for logging messages
    private static final Logger logerTool = Logger.getLogger(PatientDAO.class.getName());
    // List to store patient entities
    private List<Patient> patientsList = new ArrayList<>(); 
    //instance of PatientDAO
    private static PatientDAO instance; 

    // Private constructor to prevent external instantiation.
    private PatientDAO() {}

    // Method to get a singleton instance of PatientDAO
    public static synchronized PatientDAO getInstance() {
        if (instance == null) {
            instance = new PatientDAO();
        }
        return instance;
    }

    // Method to add a patient
    public void addPatient(Patient patient){
        // Check if the patient is null
        if (patient == null){
            logerTool.log(Level.SEVERE, "Attempt to add a null patient");
            throw new CWBadRequestException("Sorry, patient provided is invalid.");
        }

        // Check for duplicate patient ID
        if (findPatientUsingId(patient.getPersonId()) != null) {
            logerTool.log(Level.SEVERE, "Attempt to add duplicate patient with ID: {0}", patient.getPersonId());
            throw new CWBadRequestException("A prior doctor using this ID has been made: " + patient.getPersonId());
        }
        
        // Add the patient to the list
        patientsList.add(patient);
        logerTool.log(Level.INFO, "Patient added with ID: {0}, Current list size: {1}", new Object[]{patient.getPersonId(), patientsList.size()});
    }

    // Method to get a patient by ID
    public Patient getPatientById(String id){
        // Find and return the patient by ID
        Patient patient = findPatientUsingId(id);
        // Check if patient is found
        if (patient == null) {
            logerTool.log(Level.SEVERE, "No patient found with ID: {0}", id);
            throw new CWNotFoundException("Sorry, Cannot find a patient with provided ID: " + id);
        }
        return patient;
    }

    // Method to update a patient
    public void updatePatient(Patient patient){
        // Check if the patient is null
        if (patient == null){
            logerTool.log(Level.SEVERE, "Attempt to update a null patient");
            throw new CWBadRequestException("Sorry, provided patient is invalid. ");
        }

        // Find the existing patient
        Patient existingPatient = findPatientUsingId(patient.getPersonId());
        // Check if existing patient is found
        if (existingPatient == null) {
            logerTool.log(Level.SEVERE, "No patient found for update with ID: {0}", patient.getPersonId());
            throw new CWNotFoundException("Sorry, Can't find a patient with provided ID: " + patient.getPersonId());
        }

        // Update the patient in the list
        patientsList.set(patientsList.indexOf(existingPatient), patient);
        logerTool.log(Level.INFO, "Patient updated with ID: {0}", patient.getPersonId());
    }

    // Method to delete a patient
    public void deletePatient(String id){
        // Find the patient by ID, this will throw if not found
        Patient patient = getPatientById(id); // This will throw if not found
        // Remove the patient from the list
        patientsList.remove(patient);
        logerTool.log(Level.INFO, "Patient deleted with ID: {0}", id);
    }

    // Method to get all patients
    public List<Patient> getAllPatients(){
        // Log fetching all patients
        logerTool.log(Level.INFO, "Returning all patients. Total count: {0}", patientsList.size());
        return new ArrayList<>(patientsList);
    }
    
    // Helper method to find a patient using ID
    private Patient findPatientUsingId(String id) {
        return patientsList.stream()
                           .filter(p -> p.getPersonId().equals(id))
                           .findFirst()
                           .orElse(null);
    }
}
