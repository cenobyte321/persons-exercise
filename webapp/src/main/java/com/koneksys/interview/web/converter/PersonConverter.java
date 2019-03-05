package com.koneksys.interview.web.converter;

import com.koneksys.interview.dto.PersonDTO;
import com.koneksys.interview.service.PersonServiceClient;
import com.koneksys.interview.web.PersonCtrl;

import javax.annotation.ManagedBean;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import javax.inject.Named;

@FacesConverter(value = "personConverter", managed = true)
public class PersonConverter implements Converter<PersonDTO> {

    @Inject
    private PersonServiceClient personService;

    @Override
    public PersonDTO getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        if(s == null){
            return null;
        }

        return personService.findPerson(s);
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, PersonDTO person) {
        if(person == null){
            return "";
        }
        if(person.getId() == null){
            return "";
        }
        return person.getCountry()+":"+person.getId().toString();
    }
}