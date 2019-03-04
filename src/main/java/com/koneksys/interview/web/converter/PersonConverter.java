package com.koneksys.interview.web.converter;

import com.koneksys.interview.entity.Person;
import com.koneksys.interview.service.PersonService;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import javax.inject.Named;

@FacesConverter(value = "personConverter", managed = true)
public class PersonConverter implements Converter<Person> {

    @Inject
    private PersonService personService;

    @Override
    public Person getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        if(s == null){
            return null;
        }

        return personService.findPerson(Long.parseLong(s));
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Person person) {
        if(person == null){
            return "";
        }
        if(person.getId() == null){
            return "";
        }
        return person.getId().toString();
    }
}
