package edu.alvin.ninja.core.freemarker.directives;

import com.google.common.base.Strings;
import freemarker.core.Environment;
import freemarker.ext.beans.BeanModel;
import freemarker.ext.beans.BeansWrapper;
import freemarker.ext.beans.BeansWrapperBuilder;
import freemarker.template.Configuration;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import freemarker.template.TemplateScalarModel;
import ninja.validation.Validation;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ValidationErrorDirective implements TemplateDirectiveModel {
    public static final String VALIDATION = "__NINJA_VALIDATION___";
    public static final String VALIDATION_CACHE = "__NINJA_VALIDATION_CACHE___";
    public static final String KEY = "for";

    public final BeansWrapper wrapper = new BeansWrapperBuilder(Configuration.VERSION_2_3_21).build();

    @Override
    public void execute(Environment env, Map params, TemplateModel[] loopVers, TemplateDirectiveBody body) throws TemplateException, IOException {
        TemplateScalarModel fieldNameModel = (TemplateScalarModel) params.get(KEY);
        if (fieldNameModel == null) {
            return;
        }
        String fieldName = fieldNameModel.getAsString();
        if (Strings.isNullOrEmpty(fieldName)) {
            return;
        }

        Map<String, Object> validationCache = tryFindCache(env);
        if (validationCache == null) {
            validationCache = makeValidationCache(env);
        }
        if (validationCache == null) {
            return;
        }
        Object error = validationCache.get(fieldName.toLowerCase());
        if (error != null) {
            env.setVariable("message", wrapper.wrap(error));
            body.render(env.getOut());
        }
    }

    private Map<String, Object> makeValidationCache(Environment env) throws TemplateModelException {
        BeanModel model = (BeanModel) env.getVariable(VALIDATION);
        if (model == null) {
            return null;
        }
        Validation validation = (Validation) model.getWrappedObject();

        Map<String, Object> validationCache = new HashMap<>();
        validation.getBeanViolations().forEach(item -> validationCache.put(item.field.toLowerCase(),
                item.constraintViolation.getMessageKey()));
        env.setVariable(VALIDATION_CACHE, wrapper.wrap(validationCache));
        return validationCache;
    }

    private Map<String, Object> tryFindCache(Environment env) throws TemplateModelException {
        BeanModel beanModel = (BeanModel) env.getVariable(VALIDATION_CACHE);
        if (beanModel == null) {
            return null;
        }
        return (Map<String, Object>) beanModel.getWrappedObject();
    }
}
