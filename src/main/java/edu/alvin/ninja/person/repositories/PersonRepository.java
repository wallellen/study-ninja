package edu.alvin.ninja.person.repositories;

import com.google.inject.Provider;
import edu.alvin.ninja.core.repositories.RepositorySupport;
import edu.alvin.ninja.person.models.Person;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Singleton
public class PersonRepository extends RepositorySupport<Person> {

    @Inject
    public PersonRepository(Provider<EntityManager> emProvider) {
        super(emProvider);
    }

    public List<Person> findAll() {
        return list("from Person p", null);
    }

    public boolean exist(String name, Integer id) {
        return count("select count(1) from Person p where p.name=:name and p.id<>:id",
                query -> {
                    query.setParameter("name", name);
                    query.setParameter("id", id);
                }) > 0;
    }

    public Optional<Person> findById(Integer id) {
        return find(id);
    }
}
