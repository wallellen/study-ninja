package edu.alvin.ninja.conf;

import edu.alvin.ninja.core.filters.HttpMethodFilter;
import ninja.servlet.NinjaServletDispatcher;

@SuppressWarnings("UnusedDeclaration")
public class ServletModule extends com.google.inject.servlet.ServletModule {
    @Override
    protected void configureServlets() {
        bind(NinjaServletDispatcher.class).asEagerSingleton();

        filter("/*").through(HttpMethodFilter.class);
        serve("/*").with(NinjaServletDispatcher.class);
    }
}
