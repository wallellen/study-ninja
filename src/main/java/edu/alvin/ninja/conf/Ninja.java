package edu.alvin.ninja.conf;

import ninja.Context;
import ninja.NinjaDefault;
import ninja.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("UnusedDeclaration")
public class Ninja extends NinjaDefault {
    private final Logger logger = LoggerFactory.getLogger(Ninja.class);

    @Override
    public Result getInternalServerErrorResult(Context context, Exception exception) {
        logger.error("500 error caused", exception);
        return super.getInternalServerErrorResult(context, exception)
                .template("edu/alvin/ninja/core/views/errors/500.ftl.html");
    }

    @Override
    public Result getNotFoundResult(Context context) {
        return super.getNotFoundResult(context)
                .template("edu/alvin/ninja/core/views/errors/404.ftl.html");
    }

    @Override
    public Result getBadRequestResult(Context context, Exception exception) {
        String backUrl = context.getHeader("referer");
        Result result = super.getBadRequestResult(context, exception);
        logger.warn("400 error caused!", exception);
        return result.template("edu/alvin/ninja/core/views/errors/400.ftl.html")
                .render("backURL", backUrl)
                .render("errorMessage", exception.getMessage());
    }

    @Override
    public Result onException(Context context, Exception exception) {
        return super.onException(context, exception);
    }
}
