/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.coursework.dao;

import com.coursework.model.Billing;
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

public class BillingDAO {

    // Logger for logging messages
    private static final Logger logerTool = Logger.getLogger(BillingDAO.class.getName());
    // List to store billing records
    private List<Billing> billingsList = new ArrayList<>(); 
    //instance of BillingDAO
    private static BillingDAO instance; 

    // Private constructor to prevent instantiation from outside
    private BillingDAO() {}

    // Method to get a singleton instance of BillingDAO
    public static synchronized BillingDAO getInstance() {
        if (instance == null) {
            instance = new BillingDAO();
        }
        return instance;
    }

    // Method to add a billing record
    public void addBilling(Billing billing){
        // Check if the billing record is null
        if (billing == null){
            logerTool.log(Level.SEVERE, "Attempt to add a null billing");
            throw new CWBadRequestException(" Provided billing is invalid.");
        }
        
        // Check for duplicate invoice ID
        if (findBillingUsingInvoiceId(billing.getInvoiceId()) != null) {
            logerTool.log(Level.SEVERE, "Attempt to add duplicate billing with invoice ID: {0}", billing.getInvoiceId());
            throw new CWBadRequestException("A prior billing using this ID has been made: " + billing.getInvoiceId());
        }
        
        // Add the billing record to the list
        billingsList.add(billing);
        logerTool.log(Level.INFO, "Billing added with invoice ID: {0}", billing.getInvoiceId());
    }

    // Method to get a billing record by invoice ID
    public Billing getBillingByInvoiceId(String invoiceId){
        // Find and return the billing record by invoice ID
        Billing billing = findBillingUsingInvoiceId(invoiceId);
        // Check if billing record is found
        if (billing == null) {
            logerTool.log(Level.SEVERE, "No billing found with invoice ID: {0}", invoiceId);
            throw new CWNotFoundException("Cannot find any billing with this invoice ID: " + invoiceId);
        }
        return billing;
    }

    // Method to update a billing record
    public void updateBilling(Billing billing){
        // Check if the billing record is null
        if (billing == null){
            logerTool.log(Level.SEVERE, "Attempt to update a null billing");
            throw new CWBadRequestException("Sorry, provided billing is invalid.");
        }

        // Find the existing billing record
        Billing existingBilling = findBillingUsingInvoiceId(billing.getInvoiceId());
        // Check if existing billing record is found
        if (existingBilling == null) {
            logerTool.log(Level.SEVERE, "No billing found for update with invoice ID: {0}", billing.getInvoiceId());
            throw new CWNotFoundException("Soory, Can't find a billing with provided invoice ID: " + billing.getInvoiceId());
        }

        // Update the billing record in the list
        billingsList.set(billingsList.indexOf(existingBilling), billing);
        logerTool.log(Level.INFO, "Billing updated with invoice ID: {0}", billing.getInvoiceId());
    }

    // Method to delete a billing record
    public void deleteBilling(String invoiceId){
        // Find the billing record by invoice ID, this will throw if not found
        Billing billing = getBillingByInvoiceId(invoiceId);  // This will throw if not found
        // Remove the billing record from the list
        billingsList.remove(billing);
        logerTool.log(Level.INFO, "Billing deleted with invoice ID: {0}", invoiceId);
    }

    // Method to get all billing records
    public List<Billing> getAllBillings(){
        // Log fetching all billings
        logerTool.log(Level.INFO, "Returning all billings");
        return new ArrayList<>(billingsList);
    }
    
    // Helper method to find a billing record using invoice ID
    private Billing findBillingUsingInvoiceId(String invoiceId) {
        // Search for the billing record by invoice ID and return it if found, otherwise return null
        return billingsList.stream()
                       .filter(b -> b.getInvoiceId().equals(invoiceId))
                       .findFirst()
                       .orElse(null);
    }
}
