/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.coursework.resource;

import com.coursework.model.Prescription;
import com.coursework.dao.PrescriptionDAO;
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

@Path("/prescriptions")
public class PrescriptionResource {
    // Logger instance for logging messages and errors
    private static final Logger logerTool = Logger.getLogger(PrescriptionResource.class.getName());
    // Singleton instance of PrescriptionDAO for interacting with prescription data
    private PrescriptionDAO prescriptionDao = PrescriptionDAO.getInstance(); // Use the singleton instance

    // Method to retrieve all prescriptions
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Prescription> getAllPrescriptions() {
        logerTool.info("Request to fetch all prescriptions");
        return prescriptionDao.getAllPrescriptions();
    }

    // Method to retrieve a prescription by ID
    @GET
    @Path("/{prescriptionId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPrescriptionById(@PathParam("prescriptionId") String prescriptionId) {
        try {
            logerTool.log(Level.INFO, "Fetching prescription with ID: {0}", prescriptionId);
            Prescription prescription = prescriptionDao.getPrescriptionById(prescriptionId);
            return Response.ok(prescription).build();
        } catch (CWNotFoundException e) {
            logerTool.log(Level.WARNING, "No prescription found with ID: {0}", prescriptionId);
            return Response.status(Response.Status.NOT_FOUND).entity(new ErrorResponse("Not Found", e.getMessage())).build();
        }catch (CWBadRequestException e) {
            logerTool.log(Level.SEVERE, "Error getting prescription with ID: {0}, {1}", new Object[]{prescriptionId, e.getMessage()});
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorResponse("Bad Request", e.getMessage())).build();
        }
    }

    // Method to add a new prescription
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addPrescription(Prescription prescription) {
        try {
            logerTool.info("Attempting to add new prescription");
            prescriptionDao.addPrescription(prescription);
            return Response.status(Response.Status.CREATED).entity(prescription).build();
        } catch (CWBadRequestException e) {
            logerTool.log(Level.SEVERE, "Error adding prescription: {0}", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorResponse("Bad Request", e.getMessage())).build();
        }
    }

    // Method to update an existing prescription
    @PUT
    @Path("/{prescriptionId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updatePrescription(@PathParam("prescriptionId") String prescriptionId, Prescription prescription) {
        try {
            
            if (!prescriptionId.equals(prescription.getPrescriptionId())) {
                logerTool.log(Level.SEVERE, "Mismatch between path ID and prescription's ID.");
                throw new CWBadRequestException("ID in the URL and ID in the prescription object do not match.");
            }
            Prescription existingPrescription = prescriptionDao.getPrescriptionById(prescriptionId);  // Ensures the prescription exists before attempting an update
            if (existingPrescription == null) {
                throw new CWNotFoundException("Prescription not found with ID: " + prescriptionId);
            }
            prescriptionDao.updatePrescription(prescription);  // Assume this method correctly handles setting the prescription ID
            logerTool.log(Level.INFO, "Prescription updated successfully for ID: {0}", prescriptionId);
            return Response.ok(prescription).build();
        } catch (CWNotFoundException e) {
            logerTool.log(Level.WARNING, "Prescription not found error: " + e.getMessage(), e);
            return Response.status(Response.Status.NOT_FOUND).entity(new ErrorResponse("Not Found", e.getMessage())).build();
        } catch (CWBadRequestException e) {
            logerTool.log(Level.SEVERE, "Error updating prescription with ID: {0}, {1}", new Object[]{prescriptionId, e.getMessage()});
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorResponse("Bad Request", e.getMessage())).build();
        }
    }

    // Method to delete a prescription by ID
    @DELETE
    @Path("/{prescriptionId}")
    public Response deletePrescription(@PathParam("prescriptionId") String prescriptionId) {
        try {
            logerTool.log(Level.INFO, "Deleting prescription with ID: {0}", prescriptionId);
            prescriptionDao.deletePrescription(prescriptionId);
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (CWNotFoundException e) {
            logerTool.log(Level.SEVERE, "Error deleting prescription with ID: {0}", prescriptionId);
            return Response.status(Response.Status.NOT_FOUND).entity(new ErrorResponse("Not Found", e.getMessage())).build();
        }catch (CWBadRequestException e) {
            logerTool.log(Level.SEVERE, "Error deleting prescription with ID: {0}, {1}", new Object[]{prescriptionId, e.getMessage()});
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorResponse("Bad Request", e.getMessage())).build();
        }
    }
}
