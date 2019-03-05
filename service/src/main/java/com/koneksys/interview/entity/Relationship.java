package com.koneksys.interview.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class Relationship implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long personAId;
    private String personAServer;

    private Long personBId;
    private String personBServer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPersonAId() {
        return personAId;
    }

    public void setPersonAId(Long personAId) {
        this.personAId = personAId;
    }

    public String getPersonAServer() {
        return personAServer;
    }

    public void setPersonAServer(String personAServer) {
        this.personAServer = personAServer;
    }

    public Long getPersonBId() {
        return personBId;
    }

    public void setPersonBId(Long personBId) {
        this.personBId = personBId;
    }

    public String getPersonBServer() {
        return personBServer;
    }

    public void setPersonBServer(String personBServer) {
        this.personBServer = personBServer;
    }
}
