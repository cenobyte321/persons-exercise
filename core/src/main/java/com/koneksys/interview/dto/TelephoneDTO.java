package com.koneksys.interview.dto;

import java.io.Serializable;
import java.util.Objects;

public class TelephoneDTO implements Serializable {

    private Long id;
    private String telephoneNumber;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TelephoneDTO that = (TelephoneDTO) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(telephoneNumber, that.telephoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, telephoneNumber);
    }
}
