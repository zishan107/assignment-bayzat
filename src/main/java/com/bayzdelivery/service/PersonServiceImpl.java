package com.bayzdelivery.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.bayzdelivery.repositories.PersonRepository;
import com.bayzdelivery.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    PersonRepository personRepository;

    @Override
    public List<Person> getAll() {
        List<Person> personList = new ArrayList<>();
        personRepository.findAll().forEach(personList::add);
        return personList;
    }

    @Override
    public Person save(Person p) {
        // Validate that a user cannot be both a customer and a delivery man
        if (!"customer".equals(p.getRole()) && !"delivery_man".equals(p.getRole())) {
            throw new IllegalArgumentException("Role must be either 'customer' or 'delivery_man'");
        }
        return personRepository.save(p);
    }

    @Override
    public Person findById(Long personId) {
        Optional<Person> dbPerson = personRepository.findById(personId);
        return dbPerson.orElse(null);
    }
}