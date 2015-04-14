package edu.alvin.ninja.mctab.repositories;

import com.google.inject.Provider;
import edu.alvin.ninja.core.repositories.RepositorySupport;
import edu.alvin.ninja.mctab.models.Main;
import edu.alvin.ninja.mctab.models.Main.Status;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import java.util.List;

@Singleton
public class MainRepository extends RepositorySupport<Main> {
    @Inject
    public MainRepository(Provider<EntityManager> emProvider) {
        super(emProvider);
    }

    public List<Main> findByStatus(Status status) {
        return list("from Main m where m.status=:status",
                query -> query.setParameter("status", status));
    }
}
