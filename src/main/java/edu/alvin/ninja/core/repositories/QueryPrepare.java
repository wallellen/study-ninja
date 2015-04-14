package edu.alvin.ninja.core.repositories;

import javax.persistence.Query;

public interface QueryPrepare {
    void prepare(Query query);
}
