package edu.alvin.ninja.core.filters;

import ninja.Context;
import ninja.Filter;
import ninja.FilterChain;
import ninja.Result;

import javax.inject.Provider;
import javax.inject.Singleton;

@Singleton
public class ContextProvider implements Filter, Provider<Context> {

    private static final ThreadLocal<Context> contextLocal = new ThreadLocal<>();

    @Override
    public Result filter(FilterChain filterChain, Context context) {
        contextLocal.set(context);
        return filterChain.next(context);
    }

    @Override
    public Context get() {
        return contextLocal.get();
    }
}
