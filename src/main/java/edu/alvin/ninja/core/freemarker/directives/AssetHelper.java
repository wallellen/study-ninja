package edu.alvin.ninja.core.freemarker.directives;

import edu.alvin.ninja.conf.Routes;
import ninja.utils.NinjaProperties;
import org.apache.commons.io.FilenameUtils;

import javax.inject.Inject;
import javax.inject.Singleton;

@SuppressWarnings("UnusedDeclaration")
@Singleton
public class AssetHelper {
    private String assetsPath;

    @Inject
    public AssetHelper(NinjaProperties ninjaProperties) {
        assetsPath = FilenameUtils.normalizeNoEndSeparator(ninjaProperties.get(Routes.HTTP_ASSETS_PATH), true);
    }

    public String css(String fileName) {
        return assetsPath + "/css/" + fileName;
    }

    public String js(String fileName) {
        return assetsPath + "/js/" + fileName;
    }
}
