/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.coursework.dao;

import com.coursework.model.Prescription;
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

public class PrescriptionDAO {

    // Logger for logging messages and errors
    private static final Logger logerTool = Logger.getLogger(PrescriptionDAO.class.getName());
    // List to store prescription objects
    private List<Prescription> prescriptionsList = new ArrayList<>(); 
    //instance of PrescriptionDAO
    private static PrescriptionDAO instance; 

    // Private constructor to prevent external instantiation.
    private PrescriptionDAO() {}

    // Method to get the singleton instance of PrescriptionDAO
    public static synchronized PrescriptionDAO getInstance() {
        if (instance == null) {
            instance = new PrescriptionDAO();
        }
        return instance;
    }

    // Method to add a prescription to the list
    public void addPrescription(Prescription prescription){
        // Check if the prescription is null
        if (prescription == null){
            throw new CWBadRequestException("provided prescription is invalid");
        }
        
        // Check for duplicate prescription ID
        for(Prescription existingPrescription : prescriptionsList){
            if(existingPrescription.getPrescriptionId().equals(prescription.getPrescriptionId())){
                throw new CWBadRequestException("A prior prescription using this ID has been made: " + prescription.getPrescriptionId());
            }
        }
        
        // Add the prescription to the list
        prescriptionsList.add(prescription);
        logerTool.log(Level.INFO, "Prescription added: {0}", prescription.getPrescriptionId());
    }
    
    // Method to get a prescription by its ID
    public Prescription getPrescriptionById(String prescriptionId){
        // Search for the prescription by ID
        for(Prescription prescription : prescriptionsList){
            if(prescription.getPrescriptionId().equals(prescriptionId)){
                logerTool.log(Level.INFO, "Prescription found by ID: {0}", prescriptionId);
                return prescription;
            }
        }
        // If prescription is not found, throw CWNotFoundException
        throw new CWNotFoundException("Cannot find rescription with provided ID: " + prescriptionId);
    }
    
    // Method to update a prescription
    public void updatePrescription(Prescription prescription){
        // Check if the prescription is null
        if (prescription == null){
            throw new CWBadRequestException("provided prescription is invalid");
        }
        
        // Flag to indicate if prescription is found
        boolean found = false;
        // Search for the prescription by ID and update if found
        for(int i = 0; i < prescriptionsList.size(); i++){
            Prescription existingPrescription = prescriptionsList.get(i);
            if(existingPrescription.getPrescriptionId().equals(prescription.getPrescriptionId())){
                prescriptionsList.set(i, prescription);
                found = true;
                logerTool.log(Level.INFO, "Prescription updated: {0}", prescription.getPrescriptionId());
                break;
            }
        }
        
        // If prescription is not found, throw CWNotFoundException
        if (!found) {
            throw new CWNotFoundException("Prescription cannot found with provided ID: " + prescription.getPrescriptionId());
        }
    }
    
    // Method to delete a prescription by its ID
    public void deletePrescription(String prescriptionId){
        // Find the prescription by ID, this will throw if not found
        Prescription prescription = getPrescriptionById(prescriptionId); 
        // Remove the prescription from the list
        prescriptionsList.remove(prescription);
        logerTool.log(Level.INFO, "Prescription deleted: {0}", prescriptionId);
    }
    
    // Method to get all prescriptions
    public List<Prescription> getAllPrescriptions(){
        // Log fetching all prescriptions
        logerTool.log(Level.INFO, "Returning all prescriptions");
        return new ArrayList<>(prescriptionsList);
    }
}
