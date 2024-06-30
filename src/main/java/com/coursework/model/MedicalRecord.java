/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.coursework.model;

/**
 *
 * @author Tharushi
 */
public class MedicalRecord {

    private String recordId;
    private Patient patient;
    private String diagnosisRecord;
    private String treatmentRecord;

    // Constructors
    
    //default constructor
    public MedicalRecord(){
        
    }
    
    //Parameterized constructor to initialize a MedicalRecord object with values.
    public MedicalRecord(String recordId, Patient patient, String diagnosisRecord, String treatmentRecord) {
        this.recordId = recordId;
        this.patient = patient;
        this.diagnosisRecord = diagnosisRecord;
        this.treatmentRecord = treatmentRecord;
        
    }
   
    //Retrieves the record ID.
    public String getRecordId() {
        return recordId;
    }

    //Sets the record ID.
    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    //Retrieves the patient associated with the medical record.
    public Patient getPatient() {
        return patient;
    }

    //Sets the patient associated with the medical record.
    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    //Retrieves the diagnosis record.
    public String getDiagnosisRecord() {
        return diagnosisRecord;
    }

    //Sets the diagnosis record.
    public void setDiagnosisRecord(String diagnosisRecord) {
        this.diagnosisRecord = diagnosisRecord;
    }

    //Retrieves the treatment record.
    public String getTreatmentRecord() {
        return treatmentRecord;
    }

    //Sets the treatment record.
    public void setTreatmentRecord(String treatmentRecord) {
        this.treatmentRecord = treatmentRecord;
    }
    
}
