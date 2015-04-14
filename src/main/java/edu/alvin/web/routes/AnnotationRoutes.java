package edu.alvin.web.routes;

import com.google.inject.matcher.Matchers;
import edu.alvin.reflect.Classes;
import edu.alvin.web.annotations.Controller;
import edu.alvin.web.annotations.Route;
import edu.alvin.web.annotations.http.Delete;
import edu.alvin.web.annotations.http.Get;
import edu.alvin.web.annotations.http.Head;
import edu.alvin.web.annotations.http.Post;
import edu.alvin.web.annotations.http.Put;
import ninja.Router;
import ninja.application.ApplicationRoutes;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public abstract class AnnotationRoutes implements ApplicationRoutes {

    private Router router;

    @Override
    public void init(Router router) {
        this.router = router;
        configure();
    }

    protected abstract void configure();

    protected void scan(String packageName, Class<?>... excludes) {
        final Set<Class<?>> useExcludes = excludes == null ? Collections.emptySet() : new HashSet<>();
        if (excludes != null) {
            Collections.addAll(useExcludes, excludes);
        }

        final Set<Class<?>> classes = Classes.matching(Matchers.annotatedWith(Controller.class)).in(packageName);
        classes.forEach(clazz -> {
            if (!useExcludes.contains(clazz)) {
                for (Method method : clazz.getDeclaredMethods()) {
                    if (method.isAnnotationPresent(Get.class)) {
                        router.GET().route(getRoute(method)).with(clazz, method.getName());
                    }
                    if (method.isAnnotationPresent(Post.class)) {
                        router.POST().route(getRoute(method)).with(clazz, method.getName());
                    }
                    if (method.isAnnotationPresent(Put.class)) {
                        router.PUT().route(getRoute(method)).with(clazz, method.getName());
                    }
                    if (method.isAnnotationPresent(Delete.class)) {
                        router.DELETE().route(getRoute(method)).with(clazz, method.getName());
                    }
                    if (method.isAnnotationPresent(Head.class)) {
                        router.HEAD().route(getRoute(method)).with(clazz, method.getName());
                    }
                }
            }
        });
    }

    private String getRoute(Method method) {
        if (!method.isAnnotationPresent(Route.class)) {
            String msg = String.format("Could not determine route for %s::%s",
                    method.getDeclaringClass().getName(), method.getName());
            throw new MissingRouteException(msg);
        }
        return method.getAnnotation(Route.class).value();
    }

    public Router getRouter() {
        return router;
    }
}
