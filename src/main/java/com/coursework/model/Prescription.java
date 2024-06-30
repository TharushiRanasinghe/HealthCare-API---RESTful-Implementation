/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.coursework.model;

/**
 *
 * @author Tharushi
 */
public class Prescription {

    
    private String prescriptionId;
    private Patient patient;
    private String prescribedMedication;
    private String prescribedDosage;
    private String medInstructions;
    private String useDuration;

    // Constructors
    
    // Default constructor
    public Prescription(){
        
    }

    //Parameterized constructor to initialize a Prescription object with values.
    public Prescription(String prescriptionId, Patient patient, String prescribedMedication, String prescribedDosage, String medInstructions, String useDuration) {
        this.prescriptionId = prescriptionId;
        this.patient = patient;
        this.prescribedMedication = prescribedMedication;
        this.prescribedDosage = prescribedDosage;
        this.medInstructions = medInstructions;
        this.useDuration = useDuration;
    }

    //Retrieves the prescription ID.
    public String getPrescriptionId() {
        return prescriptionId;
    }

    //Sets the prescription ID.
    public void setPrescriptionId(String prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    //Retrieves the patient associated with the prescription.
    public Patient getPatient() {
        return patient;
    }

    //Sets the patient associated with the prescription.
    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    //Retrieves the prescribed medication.
    public String getPrescribedMedication() {
        return prescribedMedication;
    }

    //Sets the prescribed medication.
    public void setPrescribedMedication(String prescribedMedication) {
        this.prescribedMedication = prescribedMedication;
    }

    //Retrieves the prescribed dosage.
    public String getPrescribedDosage() {
        return prescribedDosage;
    }

    //Sets the prescribed dosage.
    public void setPrescribedDosage(String prescribedDosage) {
        this.prescribedDosage = prescribedDosage;
    }

    //Retrieves the medication instructions.
    public String getMedInstructions() {
        return medInstructions;
    }

    //Sets the medication instructions.
    public void setMedInstructions(String medInstructions) {
        this.medInstructions = medInstructions;
    }

    //Retrieves the duration of use for the medication.
    public String getUseDuration() {
        return useDuration;
    }

    //Sets the duration of use for the medication.
    public void setUseDuration(String useDuration) {
        this.useDuration = useDuration;
    }
    
}
