package com.koneksys.interview.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String country;
    private Integer age;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "person", orphanRemoval = true)
    private List<Telephone> telephones = new ArrayList<>();


    @ManyToMany
    private List<Person> knows = new ArrayList<>();

    @ManyToMany(mappedBy = "knows")
    private List<Person> knownBy = new ArrayList<>();

    @PreRemove
    private void removeKnowns(){
        for(Person person : knownBy){
            person.getKnows().remove(this);
        }
    }

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

    public List<Telephone> getTelephones() {
        return telephones;
    }

    public void setTelephones(List<Telephone> telephones) {
        this.telephones = telephones;
    }

    public List<Person> getKnows() {
        return knows;
    }

    public void setKnows(List<Person> knows) {
        this.knows = knows;
    }

    public List<Person> getKnownBy() {
        return knownBy;
    }

    public void setKnownBy(List<Person> knownBy) {
        this.knownBy = knownBy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return getId().equals(person.getId()) &&
                getName().equals(person.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName());
    }
}
