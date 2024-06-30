/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.coursework.resource;

import com.coursework.model.Appointment;
import com.coursework.dao.AppointmentDAO;
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

@Path("/appointments")
public class AppointmentResource {
    // Logger for logging messages and errors
    private static final Logger logerTool = Logger.getLogger(AppointmentResource.class.getName());
    // AppointmentDAO instance to interact with appointment data
    private AppointmentDAO appointmentDao = AppointmentDAO.getInstance(); 

    // Method to fetch all appointments
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Appointment> getAllAppointments() {
        logerTool.info("Fetching all appointments");
        return appointmentDao.getAllAppointments();
    }

    // Method to fetch an appointment by ID
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAppointment(@PathParam("id") String id) {
        try {
            logerTool.log(Level.INFO, "Fetching appointment with ID: {0}", id);
            Appointment appointment = appointmentDao.getAppointmentById(id);
            return Response.ok(appointment).build();
        } catch (CWNotFoundException e) {
            logerTool.log(Level.WARNING, "Not found error: " + e.getMessage(), e);
            return Response.status(Response.Status.NOT_FOUND).entity(new ErrorResponse("Not Found", e.getMessage())).build();
        }catch (CWBadRequestException e) {
            logerTool.log(Level.SEVERE, "Error getting appointment with ID: " + e.getMessage(), e);
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorResponse("Bad Request", e.getMessage())).build();
        }
    }

    // Method to add a new appointment
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addAppointment(Appointment appointment) {
        try {
            logerTool.info("Adding new appointment");
            appointmentDao.addAppointment(appointment);
            return Response.status(Response.Status.CREATED).entity(appointment).build();
        } catch (CWBadRequestException e) {
            logerTool.log(Level.SEVERE, "Bad request error: " + e.getMessage(), e);
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorResponse("Bad Request", e.getMessage())).build();
        }
    }

    // Method to update an existing appointment
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateAppointment(@PathParam("id") String id, Appointment appointment) {
        try {
            Appointment existingAppointment = appointmentDao.getAppointmentById(id);
            if (existingAppointment == null) {
                throw new CWNotFoundException("Appointment not found with ID: " + id);
            }
            appointment.setAppointmentId(id);
            appointmentDao.updateAppointment(appointment);
            logerTool.log(Level.INFO, "Appointment updated successfully with ID: {0}", id);
            return Response.ok(appointment).build();
        } catch (CWNotFoundException e) {
            logerTool.log(Level.WARNING, "Not found error: " + e.getMessage(), e);
            return Response.status(Response.Status.NOT_FOUND).entity(new ErrorResponse("Not Found", e.getMessage())).build();
        } catch (CWBadRequestException e) {
            logerTool.log(Level.SEVERE, "Error updating appointment with ID: " + e.getMessage(), e);
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorResponse("Bad Request", e.getMessage())).build();
        } 
    }


    // Method to delete an appointment by ID
    @DELETE
    @Path("/{id}")
    public Response deleteAppointment(@PathParam("id") String id) {
        try {
            logerTool.log(Level.INFO, "Deleting appointment with ID: {0}", id);
            appointmentDao.deleteAppointment(id);
            return Response.status(Response.Status.NO_CONTENT).build();
        
        } catch (CWNotFoundException e) {
            logerTool.log(Level.WARNING, "Not found error: " + e.getMessage(), e);
            return Response.status(Response.Status.NOT_FOUND).entity(new ErrorResponse("Not Found", e.getMessage())).build();
        }catch (CWBadRequestException e) {
            logerTool.log(Level.SEVERE, "Error deleting appointment with ID: " + e.getMessage(), e);
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorResponse("Bad Request", e.getMessage())).build();
        }
    }
}
