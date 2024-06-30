/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.coursework.resource;

import com.coursework.model.Doctor;
import com.coursework.dao.DoctorDAO;
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

@Path("/doctors")
public class DoctorResource {
    // Logger for logging messages and errors
    private static final Logger logerTool = Logger.getLogger(DoctorResource.class.getName());
    // DoctorDAO instance to interact with doctor data
    private DoctorDAO doctorDao = DoctorDAO.getInstance();

    // Method to fetch all doctors
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Doctor> getAllDoctors() {
        logerTool.info("Request to fetch all doctors");
        return doctorDao.getAllDoctors();
    }

    // Method to fetch a doctor by ID
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDoctorById(@PathParam("id") String id) {
        try {
            logerTool.log(Level.INFO,"Fetching doctor with ID: {0}", id);
            Doctor doctor = doctorDao.getDoctorById(id);
            return Response.ok(doctor).build();
        } catch (CWNotFoundException e) {
            logerTool.log(Level.WARNING, "Doctor not found error: " + e.getMessage(), e);
            return Response.status(Response.Status.NOT_FOUND).entity(new ErrorResponse("Not Found", e.getMessage())).build();
        }catch (CWBadRequestException e) {
            logerTool.log(Level.SEVERE, "Error getting doctor with ID: " + e.getMessage(), e);
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorResponse("Bad Request", e.getMessage())).build();
        }
    }

    // Method to add a new doctor
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addDoctor(Doctor doctor) {
        try {
            logerTool.info("Attempting to add new doctor");
            doctorDao.addDoctor(doctor);
            return Response.status(Response.Status.CREATED).entity(doctor).build();
        } catch (CWBadRequestException e) {
            logerTool.log(Level.SEVERE, "Error adding doctor: " + e.getMessage(), e);
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorResponse("Bad Request", e.getMessage())).build();
        }
    }

    // Method to update an existing doctor
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateDoctor(@PathParam("id") String id, Doctor doctor) {
        try {
            
            // Ensure the ID from the URL matches the ID in the doctor object
            if (!id.equals(doctor.getPersonId())) {
                logerTool.log(Level.SEVERE, "Mismatch between path ID and doctor's ID.");
                throw new CWBadRequestException("ID in the URL and ID in the doctor object do not match.");
            }
            Doctor existingDoctor = doctorDao.getDoctorById(id); // Checks if the doctor exists
            if (existingDoctor == null) {
                throw new CWNotFoundException("Doctor not found with ID: " + id);
            }
            doctorDao.updateDoctor(doctor); 
            logerTool.log(Level.INFO, "Doctor updated successfully for ID: {0}", id);
            return Response.ok(doctor).build();
        } catch (CWNotFoundException e) {
            logerTool.log(Level.WARNING, "Doctor not found error: " + e.getMessage(), e);
            return Response.status(Response.Status.NOT_FOUND).entity(new ErrorResponse("Not Found", e.getMessage())).build();
        } catch (CWBadRequestException e) {
            logerTool.log(Level.SEVERE, "Error updating doctor with ID: " + e.getMessage(), e);
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorResponse("Bad Request", e.getMessage())).build();
        }
    }

    // Method to delete a doctor by ID
    @DELETE
    @Path("/{id}")
    public Response deleteDoctor(@PathParam("id") String id) {
        try {
            logerTool.log(Level.INFO,"Deleting doctor with ID: {0}", id);
            doctorDao.deleteDoctor(id);
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (CWNotFoundException e) {
            logerTool.log(Level.WARNING, "Doctor not found error: " + e.getMessage(), e);
            return Response.status(Response.Status.NOT_FOUND).entity(new ErrorResponse("Not Found", e.getMessage())).build();
        }catch (CWBadRequestException e) {
            logerTool.log(Level.SEVERE, "Error deleting doctor with ID: " + e.getMessage(), e);
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorResponse("Bad Request", e.getMessage())).build();
        }
    }
}
