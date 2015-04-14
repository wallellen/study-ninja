package edu.alvin.ninja.core.filters;

import com.google.inject.Inject;
import com.google.inject.persist.UnitOfWork;
import ninja.Context;
import ninja.Filter;
import ninja.FilterChain;
import ninja.Result;

import javax.inject.Singleton;

@Singleton
final public class PersistFilter implements Filter {

    private final UnitOfWork unitOfWork;

    @Inject
    public PersistFilter(UnitOfWork unitOfWork) {
        this.unitOfWork = unitOfWork;
    }

    @Override
    public Result filter(FilterChain filterChain, Context context) {
        unitOfWork.begin();
        try {
            return filterChain.next(context);
        } finally {
            unitOfWork.end();
        }
    }
}
