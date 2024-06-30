/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.coursework.dao;

import com.coursework.model.Person;
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

public class PersonDAO {
    // Logger for logging messages
    private static final Logger logerTool = Logger.getLogger(PersonDAO.class.getName());
    // List to store Person objects
    private List<Person> personsList = new ArrayList<>(); 
    //instance of PersonDAO
    private static PersonDAO instance; 

    // Private constructor to prevent external instantiation.
    private PersonDAO() {}

    // Method to get a singleton instance of PersonDAO
    public static synchronized PersonDAO getInstance() {
        if (instance == null) {
            instance = new PersonDAO();
        }
        return instance;
    }

    // Method to add a Person object
    public void addPerson(Person person){
        // Check if the person is null
        if (person == null){
            logerTool.log(Level.SEVERE, "Attempt to add a null person");
            throw new CWBadRequestException("Sorry, person provided is invalid.");
        }

        // Check for duplicate person ID
        if (findPersonUsingId(person.getPersonId()) != null) {
            logerTool.log(Level.SEVERE, "Attempt to add duplicate person with ID: {0}", person.getPersonId());
            throw new CWBadRequestException("A prior person using this ID has been made: " + person.getPersonId());
        }
        
        // Add the person to the list
        personsList.add(person);
        logerTool.log(Level.INFO, "Person added with ID: {0}, Current list size: {1}", new Object[]{person.getPersonId(), personsList.size()});
    }

    // Method to get a Person by ID
    public Person getPersonById(String id){
        // Find and return the person by ID
        Person person = findPersonUsingId(id);
        // Check if person is found
        if (person == null) {
            logerTool.log(Level.SEVERE, "No person found with ID: {0}", id);
            throw new CWNotFoundException("Sorry, cannot find any person with ID: " + id);
        }
        return person;
    }

    // Method to update a Person
    public void updatePerson(Person person){
        // Check if the person is null
        if (person == null){
            // log message
            logerTool.log(Level.SEVERE, "Attempt to update a null person");
            throw new CWBadRequestException("provided person is invalid");
        }

        // Find the existing person
        Person existingPerson = findPersonUsingId(person.getPersonId());
        // Check if existing person is found
        if (existingPerson == null) {
            logerTool.log(Level.SEVERE, "No person found for update with ID: {0}", person.getPersonId());
            throw new CWNotFoundException("Sorry, can't find a person with provided ID: " + person.getPersonId());
        }

        // Update the person in the list
        personsList.set(personsList.indexOf(existingPerson), person);
        logerTool.log(Level.INFO, "Person updated with ID: {0}", person.getPersonId());
    }

    // Method to delete a Person
    public void deletePerson(String id){
        // Find the person by ID, this will throw if not found
        Person person = getPersonById(id); // This will throw if not found
        // Remove the person from the list
        personsList.remove(person);
        logerTool.log(Level.INFO, "Person deleted with ID: {0}", id);
    }

    // Method to get all Persons
    public List<Person> getAllPersons(){
        // Log fetching all persons
        logerTool.log(Level.INFO, "Returning all persons. Total count: {0}", personsList.size());
        return new ArrayList<>(personsList);
    }
    
    // Helper method to find a person using ID
    private Person findPersonUsingId(String id) {
        return personsList.stream()
                           .filter(p -> p.getPersonId().equals(id))
                           .findFirst()
                           .orElse(null);
    }
}
