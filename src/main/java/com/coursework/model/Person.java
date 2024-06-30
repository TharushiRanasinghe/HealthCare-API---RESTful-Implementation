/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.coursework.model;

/**
 *
 * @author Tharushi
 */
public class Person {

    private String personId;
    private String personName;
    private String personContactInfo;
    private String personAddress;
    
    //Construstor
    
    // Default constructor
    public Person(){
        
    }
    
    //Parameterized constructor to initialize a Person object with values.
    public Person(String personId,String personName,String personContactInfo,String personAddress){
        this.personId=personId;
        this.personName=personName;
        this.personContactInfo=personContactInfo;
        this.personAddress=personAddress;
        
    }
    
    //Retrieves the ID of the person.
    public String getPersonId(){
        return personId;
    }
    
    //Sets the ID of the person.
    public void setPersonId(String personId){
        this.personId=personId;
    }
    
    //Retrieves the name of the person.
    public String getPersonName(){
        return personName;
    }
    
    //Sets the name of the person.
    public void setPersonName(String personName){
        this.personName=personName;
    }
    
    //Retrieves the contact information of the person.
    public String getPersonContactInfo(){
        return personContactInfo;
    }
    
    //Sets the contact information of the person.
    public void setPersonContactInfo(String personContactInfo){
        this.personContactInfo=personContactInfo;
    }
    
    //Retrieves the address of the person.
    public String getPersonAddress(){
        return personAddress;
    }
    
    //Sets the address of the person.
    public void setPersonAddress(String personAddress){
        this.personAddress=personAddress;
    }
    
}