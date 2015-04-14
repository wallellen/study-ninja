package edu.alvin.ninja.async.controllers;

import com.google.inject.Injector;
import edu.alvin.ninja.async.models.Async;
import edu.alvin.ninja.async.services.AsyncService;
import edu.alvin.ninja.core.controllers.ControllerSupport;
import edu.alvin.ninja.core.extractors.I18n;
import edu.alvin.ninja.core.util.I18nMessage;
import edu.alvin.web.annotations.Controller;
import edu.alvin.web.annotations.Route;
import edu.alvin.web.annotations.http.Get;
import edu.alvin.web.annotations.http.Post;
import ninja.Result;
import ninja.Results;
import ninja.params.PathParam;
import ninja.session.FlashScope;
import ninja.validation.JSR303Validation;
import ninja.validation.Validation;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@Singleton
@Controller
public class AsyncController extends ControllerSupport {

    private AsyncService asyncService;
    private Injector injector;

    @Inject
    public AsyncController(AsyncService asyncService,
                           Injector injector) {
        this.asyncService = asyncService;
        this.injector = injector;
    }

    @Get
    @Route("/async/setting")
    public Result asyncSetting() {
        return Results.html();
    }

    @Post
    @Route("/async")
    public Result asyncWork(@JSR303Validation AsyncForm asyncForm,
                            Validation validation,
                            FlashScope flashScope) {
        if (validation.hasViolations()) {
            return Results.html()
                    .render(VALIDATION_KEY, validation)
                    .render("form", asyncForm)
                    .template(named("asyncSetting"));
        }
        Async async = asyncForm.toModel();
        asyncService.save(async);

        new Thread(new AsyncRunner(async.getId(), injector)).start();
        flashScope.success("async.setting.success");
        return Results.redirect("/async");
    }

    @Get
    @Route("/async")
    public Result asyncProcess() {
        Optional<Async> asyncOptional = asyncService.findNewAsync();
        return Results.html()
                .render("async", asyncOptional.isPresent() ? asyncOptional.get() : null);
    }

    @Get
    @Route("/async/{id}/json")
    public Result queryProcess(@PathParam("id") Integer id,
                               @I18n I18nMessage i18n) {
        Optional<Async> asyncOptional = asyncService.findById(id);
        if (!asyncOptional.isPresent()) {
            return Results.badRequest()
                    .json()
                    .render("error", i18n.get("async.process.invalid"));
        }
        return Results.json()
                .render(asyncOptional.get());
    }

    public static final class AsyncForm {
        @NotNull
        @Min(0)
        private Integer step;

        @NotNull
        @Min(0)
        private Integer maximum;

        @NotNull
        @Min(0)
        private Integer speed;

        public Async toModel() {
            Async model = new Async();
            model.setStep(step);
            model.setMaximum(maximum);
            model.setSpeed(speed);
            return model;
        }

        public Integer getStep() {
            return step;
        }

        public void setStep(Integer step) {
            this.step = step;
        }

        public Integer getMaximum() {
            return maximum;
        }

        public void setMaximum(Integer maximum) {
            this.maximum = maximum;
        }

        public Integer getSpeed() {
            return speed;
        }

        public void setSpeed(Integer speed) {
            this.speed = speed;
        }
    }
}
