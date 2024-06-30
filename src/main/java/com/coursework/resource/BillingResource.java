/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.coursework.resource;

import com.coursework.model.Billing;
import com.coursework.dao.BillingDAO;
import com.coursework.exception.CWNotFoundException;
import com.coursework.exception.CWBadRequestException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tharushi
 */

@Path("/billings")
public class BillingResource {
    // Logger for logging messages and errors
    private static final Logger logerTool = Logger.getLogger(BillingResource.class.getName());
    // BillingDAO instance to interact with billing data
    private BillingDAO billingDao = BillingDAO.getInstance(); 

    // Method to fetch all billings
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Billing> getAllBillings() {
        logerTool.info("Request to fetch all billings");
        return billingDao.getAllBillings();
    }

    // Method to fetch a billing by invoice ID
    @GET
    @Path("/{invoiceId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBillingByInvoiceId(@PathParam("invoiceId") String invoiceId) {
        try {
            logerTool.log(Level.INFO,"Fetching billing with invoice ID: {0}", invoiceId);
            Billing billing = billingDao.getBillingByInvoiceId(invoiceId);
            return Response.ok(billing).build();
        } catch (CWNotFoundException e) {
            logerTool.log(Level.WARNING, "Billing not found error: " + e.getMessage(), e);
            return Response.status(Response.Status.NOT_FOUND).entity(new ErrorResponse("Not Found", e.getMessage())).build();
        }catch (CWBadRequestException e) {
            logerTool.log(Level.SEVERE, "Error updating getting with invoice ID: " + e.getMessage(), e);
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorResponse("Bad Request", e.getMessage())).build();
        }
    }

    // Method to add a new billing
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addBilling(Billing billing) {
        try {
            logerTool.info("Attempting to add new billing");
            billingDao.addBilling(billing);
            return Response.status(Response.Status.CREATED).entity(billing).build();
        } catch (CWBadRequestException e) {
            logerTool.log(Level.SEVERE, "Error adding billing: " + e.getMessage(), e);
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorResponse("Bad Request", e.getMessage())).build();
        }
    }

    // Method to update an existing billing
    @PUT
    @Path("/{invoiceId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateBilling(@PathParam("invoiceId") String invoiceId, Billing billing) {
        try {
         
            // Check if the ID in the path matches the ID in the object
            if (!invoiceId.equals(billing.getInvoiceId())) {
                throw new CWBadRequestException("Mismatch between path invoice ID and billing's invoice ID.");
            }
            Billing existingBilling = billingDao.getBillingByInvoiceId(invoiceId);
            if (existingBilling == null) {
                throw new CWNotFoundException("Billing not found with invoice ID: " + invoiceId);
            }
            billingDao.updateBilling(billing); // Assume this method internally sets the invoice ID
            logerTool.log(Level.INFO, "Billing updated successfully for invoice ID: {0}", invoiceId);
            return Response.ok(billing).build();
        } catch (CWNotFoundException e) {
            logerTool.log(Level.WARNING, "Billing not found error: " + e.getMessage(), e);
            return Response.status(Response.Status.NOT_FOUND).entity(new ErrorResponse("Not Found", e.getMessage())).build();
        } catch (CWBadRequestException e) {
            logerTool.log(Level.SEVERE, "Error updating billing with invoice ID: " + e.getMessage(), e);
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorResponse("Bad Request", e.getMessage())).build();
        }
    }


    // Method to delete a billing by invoice ID
    @DELETE
    @Path("/{invoiceId}")
    public Response deleteBilling(@PathParam("invoiceId") String invoiceId) {
        try {
            logerTool.log(Level.INFO,"Deleting billing with invoice ID: {0}", invoiceId);
            billingDao.deleteBilling(invoiceId);
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (CWNotFoundException e) {
            logerTool.log(Level.WARNING, "Billing not found error: " + e.getMessage(), e);
            return Response.status(Response.Status.NOT_FOUND).entity(new ErrorResponse("Not Found", e.getMessage())).build();
        }catch (CWBadRequestException e) {
            logerTool.log(Level.SEVERE, "Error deleting billing with invoice ID: " + e.getMessage(), e);
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorResponse("Bad Request", e.getMessage())).build();
        }
    }
}
