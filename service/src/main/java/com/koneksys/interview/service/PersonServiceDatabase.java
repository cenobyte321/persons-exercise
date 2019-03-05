package com.koneksys.interview.service;

import com.koneksys.interview.dto.PersonDTO;
import com.koneksys.interview.dto.TelephoneDTO;
import com.koneksys.interview.entity.Person;
import com.koneksys.interview.entity.Telephone;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.LinkedList;
import java.util.List;

@Transactional
public class PersonServiceDatabase{

    @PersistenceContext(unitName = "personPU")
    EntityManager entityManager;

    public PersonDTO createPerson(PersonDTO person){
        Person personEntity = new Person();
        personEntity = convertToPersonEntity(person, personEntity);
        entityManager.persist(personEntity);
        person.setId(personEntity.getId());
        return person;
    }

    public PersonDTO updatePerson(PersonDTO person){
        Person personEntityToUpdate = entityManager.find(Person.class, person.getId());
        personEntityToUpdate.getTelephones().clear();
        personEntityToUpdate.getKnows().clear();
        personEntityToUpdate = convertToPersonEntity(person, personEntityToUpdate);
        entityManager.merge(personEntityToUpdate);
        return person;
    }

    public void deletePerson(Long id){

        Person personToRemove = entityManager.merge(entityManager.find(Person.class, id));
        entityManager.remove(personToRemove);
        entityManager.flush();
        entityManager.clear();

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

        person.getKnows().forEach(personKnown -> {
            PersonDTO personKnownDTO = new PersonDTO();
            personKnownDTO.setId(personKnown.getId());
            personKnownDTO.setName(personKnown.getName());
            personKnownDTO.setAge(personKnown.getAge());
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
        personDTO.getKnows().forEach(personKnown ->{
            Person knownPerson = entityManager.find(Person.class, personKnown.getId());
            personEntity.getKnows().add(knownPerson);
        });
        return personEntity;
    }
}
