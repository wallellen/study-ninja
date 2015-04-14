package edu.alvin.ninja.mctab.controllers;

import edu.alvin.ninja.core.controllers.ControllerSupport;
import edu.alvin.ninja.mctab.models.Main;
import edu.alvin.ninja.mctab.services.MainService;
import edu.alvin.web.annotations.Controller;
import edu.alvin.web.annotations.Route;
import edu.alvin.web.annotations.http.Delete;
import edu.alvin.web.annotations.http.Get;
import edu.alvin.web.annotations.http.Post;
import edu.alvin.web.annotations.http.Put;
import ninja.Result;
import ninja.Results;
import ninja.params.PathParam;
import ninja.session.FlashScope;
import ninja.validation.JSR303Validation;
import ninja.validation.Validation;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
@Controller
public class MainChildTableController extends ControllerSupport {
    private MainService mainService;

    @Inject
    public MainChildTableController(MainService mainService) {
        this.mainService = mainService;
    }

    @Get
    @Route("/main-child-tables")
    public Result index() {
        return Results.html().render("mains", mainService.findAll());
    }

    @Get
    @Route("/main-child-tables/new")
    public Result add() {
        return Results.html().render("main", new MainForm());
    }

    @Get
    @Route("/main-child-tables/{id}/edit")
    public Result edit(@PathParam("id") Integer id) {
        Main main = checkEntity(mainService.findById(id));
        return Results.html().render("main", MainForm.of(main));
    }

    @Post
    @Route("/main-child-tables")
    public Result save(@JSR303Validation MainForm mainForm, Validation validation, FlashScope flashScope) {
        if (validation.hasViolations()) {
            return Results.html()
                    .render("main", mainForm)
                    .render(VALIDATION_KEY, validation)
                    .template(named("add"));
        }
        mainService.save(mainForm.toModel());
        flashScope.success("message.success.add");
        return Results.redirect("/main-child-table");
    }

    @Put
    @Route("/main-child-tables")
    public Result update(@JSR303Validation MainForm mainForm, Validation validation, FlashScope flashScope) {
        if (validation.hasViolations()) {
            return Results.html()
                    .render("main", mainForm)
                    .render(VALIDATION_KEY, validation)
                    .template(named("edit"));
        }
        Main main = checkEntity(mainService.findById(mainForm.getId()));
        mainService.saveOrUpdate(mainForm.migrate(main));
        flashScope.success("message.success.modify");
        return Results.redirect("/main-child-table");
    }

    @Delete
    @Route("/main-child-tables/{id}")
    public Result delete(@PathParam("id") Integer id, FlashScope flashScope) {
        Main main = checkEntity(mainService.findById(id));
        mainService.delete(main);
        flashScope.success("message.success.delete");
        return Results.redirect("/main-child-table");
    }
}
