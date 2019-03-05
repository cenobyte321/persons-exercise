package com.koneksys.interview.message;

import java.io.Serializable;

public class RelationshipMessage implements Serializable {

    private Long personAId;
    private String personAServer;

    private Long personBId;
    private String personBServer;

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
