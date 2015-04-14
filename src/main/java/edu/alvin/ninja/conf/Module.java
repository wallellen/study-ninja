package edu.alvin.ninja.conf;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import edu.alvin.ninja.core.freemarker.FreemarkerConfigurer;

@Singleton
public class Module extends AbstractModule {
    protected void configure() {
        bind(FreemarkerConfigurer.class);
    }
}
