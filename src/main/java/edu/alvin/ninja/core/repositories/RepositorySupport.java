package edu.alvin.ninja.core.repositories;

import com.google.inject.Provider;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Optional;

@SuppressWarnings("UnusedDeclaration")
public class RepositorySupport<T> implements Repository<T> {

    private Provider<EntityManager> emProvider;
    private Class<T> entityClass;

    protected RepositorySupport(Provider<EntityManager> emProvider) {
        this.emProvider = emProvider;

        ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
        entityClass = (Class<T>) genericSuperclass.getActualTypeArguments()[0];
    }

    public Optional<T> find(Object key) {
        return Optional.ofNullable(em().find(entityClass, key));
    }

    protected Optional<T> findBy(String ql, QueryPrepare queryPrepare) {
        List<T> list = list(ql, queryPrepare);
        return list.isEmpty() ? Optional.empty() : Optional.of(list.get(0));
    }

    protected List<T> list(String ql, QueryPrepare queryPrepare) {
        TypedQuery<T> query = em().createQuery(ql, entityClass);
        if (queryPrepare != null) {
            queryPrepare.prepare(query);
        }
        return query.getResultList();
    }

    protected List<T> list(String ql) {
        return list(ql, null);
    }

    protected long count(String ql, QueryPrepare queryPrepare) {
        Query query = em().createQuery(ql);
        if (queryPrepare != null) {
            queryPrepare.prepare(query);
        }
        return (long) query.getSingleResult();
    }

    public void save(T model) {
        em().persist(model);
    }

    public void saveOrUpdate(T model) {
        em().merge(model);
    }

    public void delete(T model) {
        em().remove(model);
    }

    protected int execute(String ql, QueryPrepare prepare) {
        Query query = em().createQuery(ql);
        if (prepare != null) {
            prepare.prepare(query);
        }
        return query.executeUpdate();
    }

    public EntityManager em() {
        return emProvider.get();
    }
}
