package edu.alvin.ninja.core.freemarker;

import edu.alvin.ninja.core.freemarker.directives.AssetHelper;
import edu.alvin.ninja.core.freemarker.directives.ValidationErrorDirective;
import edu.alvin.ninja.core.freemarker.wrappers.ExtendedObjectWrapper;
import freemarker.template.Configuration;
import freemarker.template.SimpleHash;
import freemarker.template.TemplateModelException;
import ninja.lifecycle.Start;
import ninja.template.TemplateEngineFreemarker;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

@Singleton
public class FreemarkerConfigurer {

    private Configuration freemarkerConfiguration;
    private AssetHelper assetHelper;
    private ValidationErrorDirective validationErrorDirective;

    @Inject
    public FreemarkerConfigurer(TemplateEngineFreemarker templateEngineFreemarker,
                                AssetHelper assetHelper,
                                ExtendedObjectWrapper extendedObjectWrapper,
                                ValidationErrorDirective validationErrorDirective) throws NoSuchFieldException, IllegalAccessException {
        Field cfgField = templateEngineFreemarker.getClass().getDeclaredField("cfg");
        try {
            cfgField.setAccessible(true);
            freemarkerConfiguration = (Configuration) cfgField.get(templateEngineFreemarker);
            freemarkerConfiguration.setObjectWrapper(extendedObjectWrapper);
        } finally {
            cfgField.setAccessible(false);
        }
        this.assetHelper = assetHelper;
        this.validationErrorDirective = validationErrorDirective;
    }

    @Start(order = 90)
    public void configure() throws TemplateModelException {
        Map<String, Object> settings = new HashMap<>();
        settings.put("asset", assetHelper);
        settings.put("validationError", validationErrorDirective);
        freemarkerConfiguration.setAllSharedVariables(new SimpleHash(settings, null));
    }
}
