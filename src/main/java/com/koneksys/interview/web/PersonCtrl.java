package com.koneksys.interview.web;

import com.koneksys.interview.entity.Person;
import com.koneksys.interview.entity.Telephone;
import com.koneksys.interview.service.PersonService;

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
    private transient PersonService personService;

    private List<Person> personList = new ArrayList<>();
    private Person person = new Person();
    private Telephone telephone = new Telephone();
    private boolean editing = false;

    @PostConstruct
    public void init(){
        refreshPersonList();
    }

    public void createPerson(){
        personService.createPerson(getPerson());
        refreshPersonList();
    }

    public void editPerson(Person person){
        setEditing(true);
        setPerson(person);
    }

    public void updatePerson(){
        personService.updatePerson(getPerson());
        setEditing(false);
        refreshPersonList();
    }

    public void deletePerson(Person person){
        personService.deletePerson(person);
        refreshPersonList();
    }


    public void addTelephone(){
        getPerson().getTelephones().add(telephone);
        telephone.setPerson(getPerson());
        setTelephone(new Telephone());
    }

    public void removeTelephone(Telephone telephone){
        getPerson().getTelephones().remove(telephone);
        setTelephone(new Telephone());
    }


    private void refreshPersonList(){
        setPersonList(personService.getPersons());
        setPerson(new Person());
        setTelephone(new Telephone());
    }

    public List<Person> getPersonList() {
        return personList;
    }

    public void setPersonList(List<Person> personList) {
        this.personList = personList;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public boolean isEditing() {
        return editing;
    }

    public void setEditing(boolean editing) {
        this.editing = editing;
    }

    public Telephone getTelephone() {
        return telephone;
    }

    public void setTelephone(Telephone telephone) {
        this.telephone = telephone;
    }
}
