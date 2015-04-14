package edu.alvin.ninja.conf;

import com.google.inject.Inject;
import edu.alvin.ninja.hello.controllers.HelloWorldController;
import edu.alvin.web.routes.AnnotationRoutes;
import ninja.AssetsController;
import ninja.utils.NinjaProperties;
import org.apache.commons.io.FilenameUtils;

public class Routes extends AnnotationRoutes {

    public static final String APPLICATION_CONTROLLER_SCAN = "application.controller.scan";
    public static final String HTTP_ASSETS_PATH = "http.assets.path";
    private NinjaProperties ninjaProperties;

    @Inject
    public Routes(NinjaProperties ninjaProperties) {
        this.ninjaProperties = ninjaProperties;
    }

    @Override
    protected void configure() {
        String packageName = ninjaProperties.get(APPLICATION_CONTROLLER_SCAN);
        if (packageName == null) {
            throw new IllegalArgumentException(APPLICATION_CONTROLLER_SCAN);
        }
        configController();
        scan(packageName, HelloWorldController.class);
        configAssets();
    }

    public void configController() {
        getRouter().GET().route("/hello").with(HelloWorldController.class, "index");
    }

    public void configAssets() {
        String assetsPath = FilenameUtils.normalizeNoEndSeparator(ninjaProperties.get(HTTP_ASSETS_PATH), true);
        getRouter().GET().route(assetsPath + "/{fileName: .*}").with(AssetsController.class, "serveStatic");
        getRouter().GET().route(assetsPath + "/webjars/{fileName: .*}").with(AssetsController.class, "serveWebJars");
    }
}
