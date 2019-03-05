package com.koneksys.interview.endpoint;

import com.koneksys.interview.dto.PersonDTO;
import com.koneksys.interview.entity.Person;
import com.koneksys.interview.service.PersonServiceDatabase;

import javax.inject.Inject;
import javax.print.attribute.standard.Media;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 *
 * @author airhacks.com
 */
@Path("persons")
public class PersonEndpoint {

    @Inject
    PersonServiceDatabase personServiceDatabase;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<PersonDTO> getPersons() {
        return personServiceDatabase.getPersons();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findPerson(@PathParam("id") Long id) {
        PersonDTO person = personServiceDatabase.findPerson(id);
        return Response.ok(person, MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createPerson(PersonDTO person){
        PersonDTO createdPerson = personServiceDatabase.createPerson(person);
        return Response.ok(createdPerson, MediaType.APPLICATION_JSON).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updatePerson(@PathParam("id") Long id, PersonDTO person){
        person.setId(id);
        PersonDTO updatedPerson = personServiceDatabase.updatePerson(person);
        return Response.ok(updatedPerson, MediaType.APPLICATION_JSON).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deletePerson(@PathParam("id") Long id){
        personServiceDatabase.deletePerson(id);
        return Response.ok().build();
    }



}
