package edu.alvin.ninja.async.repositories;

import com.google.inject.Provider;
import edu.alvin.ninja.async.models.Async;
import edu.alvin.ninja.core.repositories.RepositorySupport;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Singleton
public class AsyncRepository extends RepositorySupport<Async> {
    @Inject
    public AsyncRepository(Provider<EntityManager> emProvider) {
        super(emProvider);
    }

    public Optional<Async> findNewAsync() {
        List<Async> asyncs = list("from Async order by id desc", query -> query.setMaxResults(1));
        return asyncs.isEmpty() ? Optional.empty() : Optional.of(asyncs.get(0));
    }
}
