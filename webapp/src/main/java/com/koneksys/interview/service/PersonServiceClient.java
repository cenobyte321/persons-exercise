package com.koneksys.interview.service;

import com.koneksys.interview.dto.PersonDTO;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Named
@ApplicationScoped
public class PersonServiceClient {


    @PostConstruct
    public void init(){

    }

    public PersonDTO createPerson(PersonDTO person) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8080/service/api/persons");
        return target.request(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .post(Entity.json(person), PersonDTO.class);
    }

    public PersonDTO updatePerson(PersonDTO person) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8080/service/api/persons/"+person.getId());
        return target.request(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .put(Entity.json(person), PersonDTO.class);
    }

    public void deletePerson(PersonDTO person) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8080/service/api/persons/"+person.getId());
        target.request().delete();
        client.close();
    }

    public List<PersonDTO> getPersons() {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8080/service/api/persons");
        return target.request(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .get(new GenericType<List<PersonDTO>>(){});
    }

    public PersonDTO findPerson(Long id) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8080/service/api/persons/"+id);
        return target.request().get(PersonDTO.class);
    }
}
