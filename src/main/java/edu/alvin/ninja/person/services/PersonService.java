package edu.alvin.ninja.person.services;

import com.google.inject.persist.Transactional;
import edu.alvin.ninja.person.models.Person;
import edu.alvin.ninja.person.repositories.PersonRepository;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;
import java.util.Optional;

@Singleton
public class PersonService {

    private PersonRepository personRepository;

    @Inject
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<Person> findAll() {
        return personRepository.findAll();
    }

    @Transactional
    public void save(Person person) {
        personRepository.save(person);
    }

    public boolean exist(String name, Integer id) {
        return personRepository.exist(name, id == null ? 0 : id);
    }

    public Optional<Person> findById(Integer id) {
        return personRepository.findById(id);
    }

    @Transactional
    public void update(Person person) {
        personRepository.save(person);
    }

    @Transactional
    public void delete(Person person) {
        personRepository.delete(person);
    }
}
