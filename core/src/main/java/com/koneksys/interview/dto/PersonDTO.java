package com.koneksys.interview.dto;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PersonDTO implements Serializable{

    private Long id;
    private String name;
    private String country;
    private Integer age;
    private List<TelephoneDTO> telephones = new ArrayList<>();
    private List<PersonDTO> knows = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public List<TelephoneDTO> getTelephones() {
        return telephones;
    }

    public void setTelephones(List<TelephoneDTO> telephones) {
        this.telephones = telephones;
    }

    public List<PersonDTO> getKnows() {
        return knows;
    }

    public void setKnows(List<PersonDTO> knows) {
        this.knows = knows;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonDTO personDTO = (PersonDTO) o;
        return Objects.equals(id, personDTO.id) &&
                Objects.equals(country, personDTO.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, country);
    }
}
