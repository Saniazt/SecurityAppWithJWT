package com.saniazt.springcourse.FirstSecurityApp.util;

import com.saniazt.springcourse.FirstSecurityApp.models.Person;
import com.saniazt.springcourse.FirstSecurityApp.services.PersonDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PersonValidator implements Validator {
    private final PersonDetailsService pds;

    @Autowired
    public PersonValidator(PersonDetailsService pds) {
        this.pds = pds;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;
        try {
            pds.loadUserByUsername(person.getUsername());
        } catch (UsernameNotFoundException ignored){
            return; //все ок, пользователь с таким именем не найден
        }
        errors.rejectValue("username","","Person with this username is already exists");
    }
}
