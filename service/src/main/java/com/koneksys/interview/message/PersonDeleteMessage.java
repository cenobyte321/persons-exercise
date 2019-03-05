package com.koneksys.interview.message;

import java.io.Serializable;

public class PersonDeleteMessage implements Serializable {
    private Long personIdToDelete;
    private String personCountryToDelete;

    public PersonDeleteMessage(Long id, String country){
        this.personIdToDelete = id;
        this.personCountryToDelete = country;
    }


    public Long getPersonIdToDelete() {
        return personIdToDelete;
    }

    public void setPersonIdToDelete(Long personIdToDelete) {
        this.personIdToDelete = personIdToDelete;
    }

    public String getPersonCountryToDelete() {
        return personCountryToDelete;
    }

    public void setPersonCountryToDelete(String personCountryToDelete) {
        this.personCountryToDelete = personCountryToDelete;
    }
}
