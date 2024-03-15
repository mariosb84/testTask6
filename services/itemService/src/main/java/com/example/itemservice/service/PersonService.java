package com.example.itemservice.service;



import com.example.itemservice.domain.Person;
import com.example.itemservice.domain.PersonDto;

import java.util.List;
import java.util.Optional;

public interface PersonService {

     List<Person> findAll();

    Optional<Person> add(Person person);

   boolean update(Person person);

     Optional<Person> findById(int id);

    boolean delete(Person person);

    boolean updatePatch(PersonDto personDto);

}
