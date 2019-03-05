package com.koneksys.interview.web;

import com.koneksys.interview.dto.PersonDTO;
import com.koneksys.interview.dto.TelephoneDTO;
import com.koneksys.interview.service.PersonServiceClient;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@ViewScoped
public class PersonCtrl implements Serializable {

    @Inject
    private transient PersonServiceClient personService;

    private List<PersonDTO> personList = new ArrayList<>();

    private List<PersonDTO> nonRelatedPersonsList = new ArrayList<>();


    private PersonDTO person = new PersonDTO();
    private TelephoneDTO telephone = new TelephoneDTO();
    private String originalCountry = "";
    private boolean editing = false;

    @PostConstruct
    public void init(){
        refreshPersonList();
    }

    public void createPerson(){
        personService.createPerson(getPerson());
        refreshPersonList();
    }

    public void editPerson(PersonDTO person){
        setEditing(true);
        setPerson(person);
        List<PersonDTO> nonRelatedPersons = new ArrayList<>();
        nonRelatedPersons.addAll(personList);
        nonRelatedPersons.remove(person);
        setNonRelatedPersonsList(nonRelatedPersons);
        originalCountry = person.getCountry();
    }

    public void updatePerson(){
        String newCountry = getPerson().getCountry();
        if(originalCountry.equals(newCountry)){
            personService.updatePerson(getPerson());
        }
        else{
            getPerson().setCountry(originalCountry);
            personService.deletePerson(getPerson());
            getPerson().setCountry(newCountry);
            personService.createPerson(getPerson());
        }

        setEditing(false);
        refreshPersonList();
    }

    public void deletePerson(PersonDTO person){
        personService.deletePerson(person);
        refreshPersonList();
    }


    public void addTelephone(){
        getPerson().getTelephones().add(telephone);
        setTelephone(new TelephoneDTO());
    }

    public void removeTelephone(TelephoneDTO telephone){
        getPerson().getTelephones().remove(telephone);
        setTelephone(new TelephoneDTO());
    }

    public PersonDTO findPerson(String personQuery){
        return personService.findPerson(personQuery);
    }

    public void refreshPersonList(){
        setPersonList(personService.getPersons());
        setPerson(new PersonDTO());
        setTelephone(new TelephoneDTO());
        setNonRelatedPersonsList(personService.getPersons());

        setEditing(false);
        originalCountry = "";
    }

    public List<PersonDTO> getPersonList() {
        return personList;
    }

    public void setPersonList(List<PersonDTO> personList) {
        this.personList = personList;
    }

    public List<PersonDTO> getNonRelatedPersonsList() {
        return nonRelatedPersonsList;
    }

    public void setNonRelatedPersonsList(List<PersonDTO> nonRelatedPersonsList) {
        this.nonRelatedPersonsList = nonRelatedPersonsList;
    }


    public PersonDTO getPerson() {
        return person;
    }

    public void setPerson(PersonDTO person) {
        this.person = person;
    }

    public TelephoneDTO getTelephone() {
        return telephone;
    }

    public void setTelephone(TelephoneDTO telephone) {
        this.telephone = telephone;
    }

    public boolean isEditing() {
        return editing;
    }

    public void setEditing(boolean editing) {
        this.editing = editing;
    }
}
