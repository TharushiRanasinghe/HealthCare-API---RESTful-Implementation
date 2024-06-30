/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.coursework.resource;

import com.coursework.model.Person;
import com.coursework.dao.PersonDAO;
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

@Path("/persons")
public class PersonResource {
    // Logger for logging messages and errors
    private static final Logger logerTool = Logger.getLogger(PersonResource.class.getName());
    // PersonDAO instance to interact with person data
    private PersonDAO personDao = PersonDAO.getInstance(); // Use the singleton instance

    // Method to retrieve all persons
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Person> getAllPersons() {
        logerTool.info("Request to fetch all persons");
        return personDao.getAllPersons();
    }

    // Method to retrieve a person by ID
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPersonById(@PathParam("id") String id) {
        try {
            logerTool.log(Level.INFO, "Fetching person with ID: {0}", id);
            Person person = personDao.getPersonById(id);
            return Response.ok(person).build();
        } catch (CWNotFoundException e) {
            logerTool.log(Level.WARNING, "No person found with ID: {0}, {1}", new Object[]{id, e.getMessage()});
            return Response.status(Response.Status.NOT_FOUND).entity(new ErrorResponse("Not Found", e.getMessage())).build();
        }
        catch (CWBadRequestException e) {
            logerTool.log(Level.SEVERE, "Error getting person with ID: {0}", new Object[]{id, e.getMessage()});
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorResponse("Bad Request", e.getMessage())).build();
        }
    }

    // Method to add a new person
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addPerson(Person person) {
        try {
            logerTool.info("Attempting to add new person");
            personDao.addPerson(person);
            return Response.status(Response.Status.CREATED).entity(person).build();
        } catch (CWBadRequestException e) {
            logerTool.log(Level.SEVERE, "Error adding person: {0}, {1}", new Object[]{person.getPersonId(), e.getMessage()});
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorResponse("Bad Request", e.getMessage())).build();
        }
    }

    // Method to update an existing person
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updatePerson(@PathParam("id") String id, Person person) {
        try {
            
            if (!id.equals(person.getPersonId())) {
                logerTool.log(Level.SEVERE, "Mismatch between path ID and person's ID.");
                throw new CWBadRequestException("ID in the URL and ID in the person object do not match.");
            }
            Person existingPerson = personDao.getPersonById(id);  // Ensures the person exists before attempting an update
            if (existingPerson == null) {
                throw new CWNotFoundException("Person not found with ID: " + id);
            }
            personDao.updatePerson(person);  // Assume this method correctly handles setting the person ID
            logerTool.log(Level.INFO, "Person updated successfully for ID: {0}", id);
            return Response.ok(person).build();
        } catch (CWNotFoundException e) {
            logerTool.log(Level.WARNING, "Person not found error: " + e.getMessage(), e);
            return Response.status(Response.Status.NOT_FOUND).entity(new ErrorResponse("Not Found", e.getMessage())).build();
        } catch (CWBadRequestException e) {
            logerTool.log(Level.SEVERE, "Error updating person with ID: {0}", new Object[]{id, e.getMessage()});
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorResponse("Bad Request", e.getMessage())).build();
        }
    }

    // Method to delete a person by ID
    @DELETE
    @Path("/{id}")
    public Response deletePerson(@PathParam("id") String id) {
        try {
            logerTool.log(Level.INFO, "Deleting person with ID: {0}", id);
            personDao.deletePerson(id);
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (CWNotFoundException e) {
            logerTool.log(Level.SEVERE, "Error deleting person with ID: {0}, {1}", new Object[]{id, e.getMessage()});
            return Response.status(Response.Status.NOT_FOUND).entity(new ErrorResponse("Not Found", e.getMessage())).build();
        }
        catch (CWBadRequestException e) {
            logerTool.log(Level.SEVERE, "Error deleting person with ID: {0}", new Object[]{id, e.getMessage()});
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorResponse("Bad Request", e.getMessage())).build();
        }
    }
}
