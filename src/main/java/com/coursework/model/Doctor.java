/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.coursework.model;

/**
 *
 * @author Tharushi
 */
public class Doctor extends Person{

    private String doctorSpecialization;
    
    //constructor
    
    //Default constructor.
    public Doctor(){
        
    }
    
    //Parameterized constructor to initialize a Doctor object with values.
    public Doctor(String personId,String personName,String personContactInfo,String personAddress,String doctorSpecialization){
        super(personId, personName, personContactInfo, personAddress);
        this.doctorSpecialization = doctorSpecialization;
       
    }

    //Retrieves the specialization of the doctor.
    public String getDoctorSpecialization() {
        return doctorSpecialization;
    }

    //Sets the specialization of the doctor.
    public void setDoctorSpecialization(String doctorSpecialization){
        this.doctorSpecialization=doctorSpecialization;
    }

}
