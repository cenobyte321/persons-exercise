package com.koneksys.interview.service;

import com.koneksys.interview.dto.PersonDTO;
import com.koneksys.interview.dto.TelephoneDTO;
import com.koneksys.interview.entity.Person;
import com.koneksys.interview.entity.Relationship;
import com.koneksys.interview.entity.Telephone;
import com.koneksys.interview.message.PersonDeleteMessage;
import com.koneksys.interview.message.RelationshipMessage;
import fish.payara.micro.cdi.Inbound;
import fish.payara.micro.cdi.Outbound;

import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.LinkedList;
import java.util.List;

@Transactional
public class PersonServiceDatabase{

    @PersistenceContext(unitName = "personPU")
    EntityManager entityManager;

    @Inject
    @Outbound(eventName = "addRelationshipEvent")
    Event<RelationshipMessage> addRelationshipEvent;

    @Inject
    @Outbound(eventName = "deletePersonEvent")
    Event<PersonDeleteMessage> deletePersonEvent;

    @Inject
    @Outbound(eventName = "updatePersonEvent")
    Event<PersonDeleteMessage> updatePersonEvent;

    public void observeDeletePersonEvent(@Observes @Inbound(eventName = "deletePersonEvent") PersonDeleteMessage personDeleteMessage){
        Query deleteQuery = entityManager.createQuery("DELETE FROM Relationship r WHERE (r.personAId = :personId AND r.personAServer = :personServer) OR (r.personBId = :personId AND r.personBServer = :personServer)");
        executeDelete(deleteQuery, "personId", personDeleteMessage.getPersonIdToDelete(), "personServer", personDeleteMessage.getPersonCountryToDelete());
    }



    public void observeUpdatePersonEvent(@Observes @Inbound(eventName = "updatePersonEvent") PersonDeleteMessage personDeleteMessage){
        System.out.println("Removing for update: "+ personDeleteMessage.getPersonIdToDelete()+ " " + personDeleteMessage.getPersonCountryToDelete());
        Query deleteQuery = entityManager.createQuery("DELETE FROM Relationship r WHERE r.personAId = :personId AND r.personAServer = :personServer");
        executeDelete(deleteQuery, "personId", personDeleteMessage.getPersonIdToDelete(), "personServer", personDeleteMessage.getPersonCountryToDelete());
    }

    public void observeAddRelationshipEvent(@Observes @Inbound(eventName = "addRelationshipEvent") RelationshipMessage relationship){
        System.out.println("Adding relationship " + relationship.getPersonAId() + " " + relationship.getPersonAServer() + " ->" + relationship.getPersonBId() + " " + relationship.getPersonBServer());
        Relationship relationshipToSave = new Relationship();
        relationshipToSave.setPersonAId(relationship.getPersonAId());
        relationshipToSave.setPersonAServer(relationship.getPersonAServer());
        relationshipToSave.setPersonBId(relationship.getPersonBId());
        relationshipToSave.setPersonBServer(relationship.getPersonBServer());
        entityManager.persist(relationshipToSave);
    }

    public PersonDTO createPerson(PersonDTO person){
        Person personEntity = new Person();
        personEntity = convertToPersonEntity(person, personEntity);
        entityManager.persist(personEntity);

        createRelationships(person, personEntity);

        person.setId(personEntity.getId());
        return person;
    }

    public PersonDTO updatePerson(PersonDTO person){
        Person personEntityToUpdate = entityManager.find(Person.class, person.getId());
        personEntityToUpdate.getTelephones().clear();

        personEntityToUpdate = convertToPersonEntity(person, personEntityToUpdate);
        entityManager.merge(personEntityToUpdate);
        PersonDeleteMessage message = new PersonDeleteMessage(personEntityToUpdate.getId(),personEntityToUpdate.getCountry());
        Query deleteQuery = entityManager.createQuery("DELETE FROM Relationship r WHERE r.personAId = :personAId AND r.personAServer = :personAServer");
        executeDelete(deleteQuery, "personAId", personEntityToUpdate.getId(), "personAServer", personEntityToUpdate.getCountry());
        updatePersonEvent.fire(message);
        createRelationships(person, personEntityToUpdate);

        return person;
    }



    public void deletePerson(Long id){
        Person personToRemove = entityManager.merge(entityManager.find(Person.class, id));
        PersonDeleteMessage message = new PersonDeleteMessage(personToRemove.getId(), personToRemove.getCountry());
        Query deleteQuery = entityManager.createQuery("DELETE FROM Relationship r WHERE (r.personAId = :personId AND r.personAServer = :personServer) OR (r.personBId = :personId AND r.personBServer = :personServer)");
        deleteQuery.setParameter("personId", personToRemove.getId());
        deleteQuery.setParameter("personServer", personToRemove.getCountry());
        deleteQuery.executeUpdate();

        entityManager.remove(personToRemove);
        entityManager.flush();
        entityManager.clear();
        deletePersonEvent.fire(message);

    }

    public List<PersonDTO> getPersons(){
        List<PersonDTO> personDTOList = new LinkedList<>();
        List<Person> personResultList = entityManager.createQuery("SELECT p FROM Person p", Person.class).getResultList();
        personResultList.forEach(person -> {
            PersonDTO personDTO = convertToPersonDTO(person);
            personDTOList.add(personDTO);
        });
        return personDTOList;
    }


    public PersonDTO findPerson(Long id) {
        return convertToPersonDTO(entityManager.find(Person.class, id));
    }

    private PersonDTO convertToPersonDTO(Person person) {
        PersonDTO personDTO = new PersonDTO();
        personDTO.setName(person.getName());
        personDTO.setId(person.getId());
        personDTO.setCountry(person.getCountry());
        personDTO.setAge(person.getAge());

        person.getTelephones().forEach(telephone -> {
            TelephoneDTO telephoneDTO = new TelephoneDTO();
            telephoneDTO.setId(telephone.getId());
            telephoneDTO.setTelephoneNumber(telephone.getTelephoneNumber());
            personDTO.getTelephones().add(telephoneDTO);
        });

        Query selectQuery = entityManager.createQuery("SELECT r FROM Relationship r WHERE r.personAId = :personId AND r.personAServer = :personServer", Relationship.class);
        selectQuery.setParameter("personId", person.getId());
        selectQuery.setParameter("personServer", person.getCountry());
        List<Relationship> relationshipList = selectQuery.getResultList();
        relationshipList.forEach(relationship -> {
            PersonDTO personKnownDTO = new PersonDTO();
            personKnownDTO.setId(relationship.getPersonBId());
            personKnownDTO.setCountry(relationship.getPersonBServer());
            personDTO.getKnows().add(personKnownDTO);
        });

        return personDTO;
    }

    private Person convertToPersonEntity(PersonDTO personDTO, Person personEntity) {
        personEntity.setName(personDTO.getName());
        personEntity.setCountry(personDTO.getCountry());
        personEntity.setAge(personDTO.getAge());
        personDTO.getTelephones().forEach(telephoneDTO -> {
            Telephone telephone = new Telephone();
            if(telephoneDTO.getId() == null) {
                telephone.setTelephoneNumber(telephoneDTO.getTelephoneNumber());
                telephone.setPerson(personEntity);
                personEntity.getTelephones().add(telephone);
            }
            else{
                telephone = entityManager.find(Telephone.class, telephoneDTO.getId());
                telephone.setTelephoneNumber(telephoneDTO.getTelephoneNumber());
                personEntity.getTelephones().add(telephone);
            }
        });

        return personEntity;
    }

    private void createRelationships(PersonDTO person, Person personEntityToUpdate) {
        Person finalPersonEntityToUpdate = personEntityToUpdate;
        person.getKnows().forEach(personDTO -> {
            Relationship relationship = new Relationship();
            relationship.setPersonAId(finalPersonEntityToUpdate.getId());
            relationship.setPersonAServer(finalPersonEntityToUpdate.getCountry());
            relationship.setPersonBId(personDTO.getId());
            relationship.setPersonBServer(personDTO.getCountry());

            RelationshipMessage message = new RelationshipMessage();
            message.setPersonAId(finalPersonEntityToUpdate.getId());
            message.setPersonAServer(finalPersonEntityToUpdate.getCountry());
            message.setPersonBId(personDTO.getId());
            message.setPersonBServer(personDTO.getCountry());
            System.out.println("Firing create relationship event");
            addRelationshipEvent.fire(message);
            entityManager.persist(relationship);
        });
    }

    private void executeDelete(Query deleteQuery, String personId, Long personIdToDelete, String personServer, String personCountryToDelete) {
        deleteQuery.setParameter(personId, personIdToDelete);
        deleteQuery.setParameter(personServer, personCountryToDelete);
        deleteQuery.executeUpdate();
        entityManager.flush();
        entityManager.clear();
    }
}
