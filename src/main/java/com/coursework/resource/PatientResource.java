/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.coursework.resource;

import com.coursework.model.Patient;
import com.coursework.dao.PatientDAO;
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

@Path("/patients")
public class PatientResource {
    private static final Logger logerTool = Logger.getLogger(PatientResource.class.getName());
    private PatientDAO patientDao = PatientDAO.getInstance(); // Use the singleton instance

    // Method to retrieve all patients
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Patient> getAllPatients() {
        logerTool.info("Request to fetch all patients");
        return patientDao.getAllPatients();
    }

    // Method to retrieve a patient by ID
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPatientById(@PathParam("id") String id) {
        try {
            logerTool.log(Level.INFO, "Fetching patient with ID: {0}", id);
            Patient patient = patientDao.getPatientById(id);
            return Response.ok(patient).build();
        } catch (CWNotFoundException e) {
            logerTool.log(Level.WARNING, "No patient found with ID: {0}, {1}", new Object[]{id, e.getMessage()});
            return Response.status(Response.Status.NOT_FOUND).entity(new ErrorResponse("Not Found", e.getMessage())).build();
        }catch (CWBadRequestException e) {
            logerTool.log(Level.SEVERE, "Error getting patient with ID: {0}", new Object[]{id, e.getMessage()});
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorResponse("Bad Request", e.getMessage())).build();
        }
    }

    // Method to add a new patient
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addPatient(Patient patient) {
        try {
            logerTool.info("Attempting to add new patient");
            patientDao.addPatient(patient);
            return Response.status(Response.Status.CREATED).entity(patient).build();
        } catch (CWBadRequestException e) {
            logerTool.log(Level.SEVERE, "Error adding patient: {0}, {1}", new Object[]{patient.getPersonId(), e.getMessage()});
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorResponse("Bad Request", e.getMessage())).build();
        }
    }

    // Method to update an existing patient
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updatePatient(@PathParam("id") String id, Patient patient) {
        try {
            
            if (!id.equals(patient.getPersonId())) {
                logerTool.log(Level.SEVERE, "Mismatch between path ID and patient's ID.");
                throw new CWBadRequestException("ID in the URL and ID in the patient object do not match.");
            }
            Patient existingPatient = patientDao.getPatientById(id);  // Ensures the patient exists before attempting an update
            if (existingPatient == null) {
                throw new CWNotFoundException("Patient not found with ID: " + id);
            }
            patientDao.updatePatient(patient);  // Assume this method correctly handles setting the patient ID
            logerTool.log(Level.INFO, "Patient updated successfully for ID: {0}", id);
            return Response.ok(patient).build();
        } catch (CWNotFoundException e) {
            logerTool.log(Level.WARNING, "Patient not found error: " + e.getMessage(), e);
            return Response.status(Response.Status.NOT_FOUND).entity(new ErrorResponse("Not Found", e.getMessage())).build();
        } catch (CWBadRequestException e) {
            logerTool.log(Level.SEVERE, "Error updating patient with ID: {0}", new Object[]{id, e.getMessage()});
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorResponse("Bad Request", e.getMessage())).build();
        }
    }

    // Method to delete a patient by ID
    @DELETE
    @Path("/{id}")
    public Response deletePatient(@PathParam("id") String id) {
        try {
            logerTool.log(Level.INFO, "Deleting patient with ID: {0}", id);
            patientDao.deletePatient(id);
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (CWNotFoundException e) {
            logerTool.log(Level.SEVERE, "Error deleting patient with ID: {0}, {1}", new Object[]{id, e.getMessage()});
            return Response.status(Response.Status.NOT_FOUND).entity(new ErrorResponse("Not Found", e.getMessage())).build();
        }catch (CWBadRequestException e) {
            logerTool.log(Level.SEVERE, "Error deleting patient with ID: {0}", new Object[]{id, e.getMessage()});
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorResponse("Bad Request", e.getMessage())).build();
        }
    }
}
