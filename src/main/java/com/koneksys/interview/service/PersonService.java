package com.koneksys.interview.service;

import com.koneksys.interview.entity.Person;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class PersonService {

    @PersistenceContext(unitName = "personPU")
    EntityManager entityManager;

    public Person createPerson(Person person){
        entityManager.persist(person);
        return person;
    }

    public Person updatePerson(Person person){
        entityManager.merge(person);
        return person;
    }

    public void deletePerson(Person person){
        Person personToRemove = entityManager.merge(person);
        entityManager.remove(personToRemove);
    }

    public List<Person> getPersons(){
        return entityManager.createQuery("SELECT p FROM Person p", Person.class).getResultList();
    }

    public List<Person> getNonRelatedPersons(Person person){
        return entityManager.createQuery("SELECT p FROM Person p WHERE p.id != "+ person.getId(), Person.class).getResultList();
    }

    public Person findPerson(Long id) {
        return entityManager.find(Person.class, id);
    }
}
