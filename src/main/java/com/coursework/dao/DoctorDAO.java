/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.coursework.dao;

import com.coursework.model.Doctor;
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

public class DoctorDAO {
    // Logger for logging messages
    private static final Logger logerTool = Logger.getLogger(DoctorDAO.class.getName());
    // List to store doctor records
    private List<Doctor> doctorsList = new ArrayList<>(); 
    //instance of DoctorDAO
    private static DoctorDAO instance; 

    // Private constructor to prevent instantiation from outside
    private DoctorDAO() {}

    // Method to get a instance of DoctorDAO
    public static synchronized DoctorDAO getInstance() {
        if (instance == null) {
            instance = new DoctorDAO();
        }
        return instance;
    }

   // Method to add a doctor record
    public void addDoctor(Doctor doctor){
        // Check if the doctor record is null
        if (doctor == null){
            logerTool.log(Level.SEVERE, "Attempt to add a null doctor");
            throw new CWBadRequestException("Sorry, doctor provided is invalid");
        }

        // Check for duplicate doctor ID
        if (findDoctorUsingId(doctor.getPersonId()) != null) {
            logerTool.log(Level.SEVERE, "Attempt to add duplicate doctor with ID: {0}", doctor.getPersonId());
            throw new CWBadRequestException("A prior doctor using this ID has been made: " + doctor.getPersonId());
        }
        
        // Add the doctor record to the list
        doctorsList.add(doctor);
        logerTool.log(Level.INFO, "Doctor added with ID: {0}, Current list size: {1}", new Object[]{doctor.getPersonId(), doctorsList.size()});
    }

    // Method to get a doctor record by ID
    public Doctor getDoctorById(String id){
        // Find and return the doctor record by ID
        Doctor doctor = findDoctorUsingId(id);
        // Check if doctor record is found
        if (doctor == null) {
            logerTool.log(Level.SEVERE, "No doctor found with ID: {0}", id);
            throw new CWNotFoundException("Sorry,Can't find any doctor with provided ID: " + id);
        }
        return doctor;
    }

    // Method to update a doctor recordc
    public void updateDoctor(Doctor doctor){
        // Check if the doctor record is null
        if (doctor == null){
            logerTool.log(Level.SEVERE, "Attempt to update a null doctor");
            throw new CWBadRequestException("provided doctor is invalid.");
        }

        // Find the existing doctor record
        Doctor existingDoctor = findDoctorUsingId(doctor.getPersonId());
        // Check if existing doctor record is found
        if (existingDoctor == null) {
            logerTool.log(Level.SEVERE, "No doctor found for update with ID: {0}", doctor.getPersonId());
            throw new CWNotFoundException("Sorry, cannot find a doctor with ID: " + doctor.getPersonId());
        }

        // Update the doctor record in the list
        doctorsList.set(doctorsList.indexOf(existingDoctor), doctor);
        logerTool.log(Level.INFO, "Doctor updated with ID: {0}", doctor.getPersonId());
    }

    // Method to delete a doctor record
    public void deleteDoctor(String id){
        // Find the doctor record by ID, this will throw if not found
        Doctor doctor = getDoctorById(id); // This will throw if not found
        // Remove the doctor record from the list
        doctorsList.remove(doctor);
        logerTool.log(Level.INFO, "Doctor deleted with ID: {0}", id);
    }

    // Method to get all doctor records
    public List<Doctor> getAllDoctors(){
        // Log fetching all doctors
        logerTool.log(Level.INFO, "Returning all doctors. Total count: {0}", doctorsList.size());
        return new ArrayList<>(doctorsList);
    }
    
    // Helper method to find a doctor record using ID
    private Doctor findDoctorUsingId(String id) {
        // Search for the doctor record by ID and return it if found, otherwise return null
        return doctorsList.stream()
                          .filter(d -> d.getPersonId().equals(id))
                          .findFirst()
                          .orElse(null);
    }
}
