/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.coursework.dao;

import com.coursework.model.MedicalRecord;
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

public class MedicalRecordDAO {
    // Logger for logging messages
    private static final Logger logerTool = Logger.getLogger(MedicalRecordDAO.class.getName());
    // List to store medical records
    private List<MedicalRecord> medicalRecordsList = new ArrayList<>(); 
    //instance of MedicalRecordDAO
    private static MedicalRecordDAO instance; 

    // Private constructor to prevent external instantiation.
    private MedicalRecordDAO() {}

    // Method to get a singleton instance of MedicalRecordDAO
    public static synchronized MedicalRecordDAO getInstance() {
        if (instance == null) {
            instance = new MedicalRecordDAO();
        }
        return instance;
    }

    // Method to add a medical record
    public void addMedicalRecord(MedicalRecord medicalRecord){
        // Check if the medical record is null
        if (medicalRecord == null){
            logerTool.log(Level.SEVERE, "Attempt to add a null medical record");
            throw new CWBadRequestException("provided medical record is invalid.");
        }

         // Check for duplicate record ID
        if (findMedicalRecordUsingRecordId(medicalRecord.getRecordId()) != null) {
            logerTool.log(Level.SEVERE, "Attempt to add duplicate medical record with Record ID: {0}", medicalRecord.getRecordId());
            throw new CWBadRequestException("A prior medical record using this ID has been made: " + medicalRecord.getRecordId());
        }
        
        // Add the medical record to the list
        medicalRecordsList.add(medicalRecord);
        logerTool.log(Level.INFO, "Medical record added with Record ID: {0}, Current list size: {1}", new Object[]{medicalRecord.getRecordId(), medicalRecordsList.size()});
    }

    // Method to get a medical record by record ID
    public MedicalRecord getMedicalRecordByRecordId(String recordId){
        // Find and return the medical record by record ID
        MedicalRecord medicalRecord = findMedicalRecordUsingRecordId(recordId);
        // Check if medical record is found
        if (medicalRecord == null) {
            logerTool.log(Level.SEVERE, "No medical record found with Record ID: {0}", recordId);
            throw new CWNotFoundException("Sorry, any medical record cannot find with Record ID: " + recordId);
        }
        return medicalRecord;
    }

    // Method to update a medical record
    public void updateMedicalRecord(MedicalRecord medicalRecord){
        // Check if the medical record is null
        if (medicalRecord == null){
            logerTool.log(Level.SEVERE, "Attempt to update a null medical record");
            throw new CWBadRequestException("Sorry, provided medical record is invalid.");
        }

        // Find the existing medical record
        MedicalRecord existingMedicalRecord = findMedicalRecordUsingRecordId(medicalRecord.getRecordId());
        // Check if existing medical record is found
        if (existingMedicalRecord == null) {
            logerTool.log(Level.SEVERE, "No medical record found for update with Record ID: {0}", medicalRecord.getRecordId());
            throw new CWNotFoundException("Sorry, Can't find a medical record with provided Record ID: " + medicalRecord.getRecordId());
        }

        //update the medical record in the list
        medicalRecordsList.set(medicalRecordsList.indexOf(existingMedicalRecord), medicalRecord);
        logerTool.log(Level.INFO, "Medical record updated with Record ID: {0}", medicalRecord.getRecordId());
    }

    // Method to delete a medical record
    public void deleteMedicalRecord(String recordId){
        // Find the medical record by record ID, this will throw if not found
        MedicalRecord medicalRecord = getMedicalRecordByRecordId(recordId); // This will throw if not found
        // Remove the medical record from the list
        medicalRecordsList.remove(medicalRecord);
        logerTool.log(Level.INFO, "Medical record deleted with Record ID: {0}", recordId);
    }


    // Method to get all medical records
    public List<MedicalRecord> getAllMedicalRecords(){
        // Log fetching all medical records
        logerTool.log(Level.INFO, "Returning all medical records. Total count: {0}", medicalRecordsList.size());
        return new ArrayList<>(medicalRecordsList);
    }
    
    // Helper method to find a medical record using record ID
    private MedicalRecord findMedicalRecordUsingRecordId(String recordId) {
        return medicalRecordsList.stream()
                                 .filter(mr -> mr.getRecordId().equals(recordId))
                                 .findFirst()
                                 .orElse(null);
    }
}
