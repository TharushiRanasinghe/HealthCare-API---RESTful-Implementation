/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.coursework.resource;

import com.coursework.model.MedicalRecord;
import com.coursework.dao.MedicalRecordDAO;
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

@Path("/medicalrecords")
public class MedicalRecordResource {
    // Logger for logging messages and errors
    private static final Logger logerTool = Logger.getLogger(MedicalRecordResource.class.getName());
    // MedicalRecordDAO instance to interact with medical record data
    private MedicalRecordDAO medicalRecordDao = MedicalRecordDAO.getInstance(); // Use the singleton instance

    // Method to fetch all medical records
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<MedicalRecord> getAllMedicalRecords() {
        logerTool.info("Request to fetch all medical records");
        return medicalRecordDao.getAllMedicalRecords();
    }

    // Method to fetch a medical record by record ID
    @GET
    @Path("/{recordId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMedicalRecordByRecordId(@PathParam("recordId") String recordId) {
        try {
            logerTool.log(Level.INFO, "Fetching medical record with record ID: {0}", recordId);
            MedicalRecord medicalRecord = medicalRecordDao.getMedicalRecordByRecordId(recordId);
            return Response.ok(medicalRecord).build();
        } catch (CWNotFoundException e) {
            logerTool.log(Level.WARNING, "Medical record not found error: " + e.getMessage(), e);
            return Response.status(Response.Status.NOT_FOUND).entity(new ErrorResponse("Not Found", e.getMessage())).build();
        }catch (CWBadRequestException e) {
            logerTool.log(Level.SEVERE, "Error getting medical record with ID: " + e.getMessage(), e);
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorResponse("Bad Request", e.getMessage())).build();
        }
    }

    // Method to add a new medical record
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addMedicalRecord(MedicalRecord medicalRecord) {
        try {
            logerTool.info("Attempting to add new medical record");
            medicalRecordDao.addMedicalRecord(medicalRecord);
            return Response.status(Response.Status.CREATED).entity(medicalRecord).build();
        } catch (CWBadRequestException e) {
            logerTool.log(Level.SEVERE, "Error adding medical record: " + e.getMessage(), e);
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorResponse("Bad Request", e.getMessage())).build();
        }
    }

    // Method to update an existing medical record
    @PUT
    @Path("/{recordId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateMedicalRecord(@PathParam("recordId") String recordId, MedicalRecord medicalRecord) {
        try {
            
            // Ensure the record ID from the URL matches the ID in the medical record object
            if (!recordId.equals(medicalRecord.getRecordId())) {
                logerTool.log(Level.SEVERE, "Mismatch between path record ID and medical record's record ID.");
                throw new CWBadRequestException("ID in the URL and ID in the medical record do not match.");
            }
            MedicalRecord existingRecord = medicalRecordDao.getMedicalRecordByRecordId(recordId); // Checks if the medical record exists
            if (existingRecord == null) {
                throw new CWNotFoundException("Medical record not found with ID: " + recordId);
            }
            medicalRecordDao.updateMedicalRecord(medicalRecord); 
            logerTool.log(Level.INFO, "Medical record updated successfully for record ID: {0}", recordId);
            return Response.ok(medicalRecord).build();
        } catch (CWNotFoundException e) {
            logerTool.log(Level.WARNING, "Medical record not found error: " + e.getMessage(), e);
            return Response.status(Response.Status.NOT_FOUND).entity(new ErrorResponse("Not Found", e.getMessage())).build();
        } catch (CWBadRequestException e) {
            logerTool.log(Level.SEVERE, "Error updating medical record with ID: " + e.getMessage(), e);
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorResponse("Bad Request", e.getMessage())).build();
        }
    }


    // Method to delete a medical record by record ID
    @DELETE
    @Path("/{recordId}")
    public Response deleteMedicalRecord(@PathParam("recordId") String recordId) {
        try {
            logerTool.log(Level.INFO, "Deleting medical record with record ID: {0}", recordId);
            medicalRecordDao.deleteMedicalRecord(recordId);
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (CWNotFoundException e) {
            logerTool.log(Level.SEVERE, "Error deleting medical record with record ID: " + e.getMessage(), e);
            return Response.status(Response.Status.NOT_FOUND).entity(new ErrorResponse("Not Found", e.getMessage())).build();
        }catch (CWBadRequestException e) {
            logerTool.log(Level.SEVERE, "Error deleting medical record with ID: " + e.getMessage(), e);
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorResponse("Bad Request", e.getMessage())).build();
        }
    }
}
