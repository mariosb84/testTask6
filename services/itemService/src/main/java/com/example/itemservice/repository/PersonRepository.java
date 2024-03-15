package com.example.itemservice.repository;

import com.example.itemservice.domain.Person;
import org.springframework.data.repository.CrudRepository;


import java.util.List;
import java.util.Optional;

public interface PersonRepository extends CrudRepository<Person, Integer> {

    List<Person> findAll();

    Optional<Person> findPersonByLogin(String login);

}
