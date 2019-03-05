package com.koneksys.interview.entity;

import javax.persistence.*;

@Entity
public class Telephone {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String telephoneNumber;
    @ManyToOne
    private Person person;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
