package edu.alvin.ninja.core.controllers;

import edu.alvin.ninja.core.filters.ContextProvider;
import edu.alvin.ninja.core.filters.PersistFilter;
import edu.alvin.ninja.core.freemarker.directives.ValidationErrorDirective;
import edu.alvin.ninja.core.util.I18nMessage;
import ninja.FilterWith;
import ninja.exceptions.BadRequestException;

import javax.inject.Inject;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@FilterWith({ContextProvider.class, PersistFilter.class})
public class ControllerSupport {
    protected static final String VALIDATION_KEY = ValidationErrorDirective.VALIDATION;
    private static final Map<Class<?>, Map<String, String>> templatePathCache = new ConcurrentHashMap<>();
    @Inject
    protected I18nMessage message;

    protected String named(Class<?> controllerClass, String templateName) {
        Map<String, String> templateMap = templatePathCache.get(controllerClass);
        if (templateMap != null) {
            String templateFile = templateMap.get(templateName);
            if (templateFile != null) {
                return templateFile;
            }
        }
        if (templateMap == null) {
            templatePathCache.put(controllerClass, new ConcurrentHashMap<>());
        }
        String controllerPackageName = controllerClass.getPackage().getName();
        String parentPackageOfController = controllerPackageName.replace("controllers", "views");
        String maybeEnhancedClassName = getClass().getSimpleName();
        int dollarIndex = maybeEnhancedClassName.indexOf('$');
        String className = dollarIndex >= 0 ? maybeEnhancedClassName.substring(0, dollarIndex) : maybeEnhancedClassName;
        String templateFile = String.format("%s/%s/%s.ftl.html", parentPackageOfController.replace('.', '/'), className, templateName);
        templatePathCache.get(controllerClass).put(templateName, templateFile);
        return templateFile;
    }

    protected String named(String templateName) {
        return named(getClass(), templateName);
    }

    protected <T> T checkEntity(Optional<T> optional) {
        return checkEntity(optional, "invalidEntity");
    }

    protected <T> T checkEntity(Optional<T> optional, String messageKey, Object... args) {
        if (!optional.isPresent()) {
            throw new BadRequestException(message.get(messageKey, args));
        }
        return optional.get();
    }
}
