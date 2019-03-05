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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Named
@ApplicationScoped
public class PersonServiceClient {

    private Map<String, String> countryServer = new HashMap<>();

    @PostConstruct
    public void init(){
        countryServer.put("UK", "http://localhost:8080");
        countryServer.put("US", "http://localhost:8081");
    }

    public PersonDTO createPerson(PersonDTO person) {

        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(countryServer.get(person.getCountry())+"/service/api/persons");
        return target.request(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .post(Entity.json(person), PersonDTO.class);
    }

    public PersonDTO updatePerson(PersonDTO person) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(countryServer.get(person.getCountry())+"/service/api/persons/"+person.getId());
        return target.request(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .put(Entity.json(person), PersonDTO.class);
    }

    public void deletePerson(PersonDTO person) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(countryServer.get(person.getCountry())+"/service/api/persons/"+person.getId());
        target.request().delete();
        client.close();
    }

    public List<PersonDTO> getPersons() {
        List<PersonDTO> results = new ArrayList<>();

        countryServer.forEach((k,val) ->{
            Client client = ClientBuilder.newClient();
            WebTarget target = client.target(val+"/service/api/persons");
            results.addAll(target.request(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .get(new GenericType<List<PersonDTO>>(){}));
        });
        return results;
    }

    public PersonDTO findPerson(String personQuery) {
        String[] query = personQuery.split(":");
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(countryServer.get(query[0])+"/service/api/persons/"+query[1]);
        return target.request().get(PersonDTO.class);
    }
}
