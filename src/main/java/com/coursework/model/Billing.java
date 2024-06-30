/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.coursework.model;

/**
 *
 * @author Tharushi
 */
public class Billing {

    private String invoiceId;
    private Patient patient;
    private double billAmount;
    private boolean outstandingBalance;

    // Constructors
    
    // Default constructor
    public Billing(){
    
    }

    //Parameterized constructor to initialize a Billing object with values.
    public Billing(String invoiceId, Patient patient, double billAmount, boolean outstandingBalance) {
        this.invoiceId = invoiceId;
        this.patient = patient;
        this.billAmount = billAmount;
        this.outstandingBalance = outstandingBalance;
        
    }

    //Retrieves the invoice ID.
    public String getInvoiceId() {
        return invoiceId;
    }

    //Sets the invoice ID.
    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    //Retrieves the patient associated with the billing.
    public Patient getPatient() {
        return patient;
    }

    //Sets the patient associated with the billing.
    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    //Retrieves the bill amount.
    public double getBillAmount() {
        return billAmount;
    }

    //Sets the bill amount.
    public void setBillAmount(double billAmount) {
        this.billAmount = billAmount;
    }

    //Checks if there's an outstanding balance.
    public boolean isOutstandingBalance() {
        return outstandingBalance;
    }

    //Sets the outstanding balance status.
    public void setOutstandingBalance(boolean outstandingBalance) {
        this.outstandingBalance = outstandingBalance;
    }
    
}
