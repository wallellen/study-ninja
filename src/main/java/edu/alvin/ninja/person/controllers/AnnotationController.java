package edu.alvin.ninja.person.controllers;

import com.google.common.collect.ImmutableMap;
import edu.alvin.ninja.core.controllers.ControllerSupport;
import edu.alvin.ninja.core.validators.Telephone;
import edu.alvin.ninja.person.models.Person;
import edu.alvin.ninja.person.services.PersonService;
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
import ninja.validation.ConstraintViolation;
import ninja.validation.FieldViolation;
import ninja.validation.JSR303Validation;
import ninja.validation.Validation;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;


@Controller
public class AnnotationController extends ControllerSupport {

    private PersonService personService;

    @Inject
    public AnnotationController(PersonService personService) {
        this.personService = personService;
    }

    @Get
    @Route("/persons")
    public Result index() {
        List<Person> persons = personService.findAll();
        return Results.html().render("persons", persons);
    }

    @Get
    @Route("/persons/new")
    public Result add() {
        return Results.html();
    }

    @Get
    @Route("/persons/{id}")
    public Result edit(@PathParam("id") Integer id) {
        Person person = checkEntity(personService.findById(id));
        return Results.html().render("form", PersonForm.of(person));
    }

    private Result checkValidation(Validation validation, PersonForm form) {
        if (validation.hasBeanViolations()) {
            return Results.html().render("form", form)
                    .render(VALIDATION_KEY, validation)
                    .template(named("add"));
        }
        if (personService.exist(form.getName(), form.getId())) {
            validation.addBeanViolation(
                    new FieldViolation("name", ConstraintViolation.create(
                            message.get("person.error.name", form.getName()))));
            return Results.html().render("form", form)
                    .render(VALIDATION_KEY, validation)
                    .template(named("add"));
        }
        return null;
    }

    @Post
    @Route("/persons")
    public Result create(@JSR303Validation PersonForm form,
                         Validation validation,
                         FlashScope flashScope) {
        Result result = checkValidation(validation, form);
        if (result != null) {
            return result;
        }
        personService.save(form.toEntity());
        flashScope.success("message.success.add");
        return Results.redirect("/persons");
    }

    @Put
    @Route("/persons")
    public Result update(@JSR303Validation PersonForm form,
                         Validation validation,
                         FlashScope flashScope) {
        Result result = checkValidation(validation, form);
        if (result != null) {
            return result;
        }
        Person person = form.merge(checkEntity(personService.findById(form.getId())));
        personService.update(person);
        flashScope.success("message.success.modify");
        return Results.redirect("/persons");
    }

    @Delete
    @Route("/persons/{id}")
    public Result delete(@PathParam("id") Integer id, FlashScope flashScope) {
        Person person = checkEntity(personService.findById(id));
        personService.delete(person);
        flashScope.success("message.success.delete");
        return Results.redirect("/persons");
    }

    @Get
    @Route("/persons/json")
    public Result json() {
        LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);
        String sNow = now.format(DateTimeFormatter.ISO_DATE_TIME);
        return Results.json().render(ImmutableMap.of("name", "Alvin", "date", sNow));
    }


    @SuppressWarnings("UnusedDeclaration")
    public static class PersonForm {
        private Integer id;
        @NotBlank
        @NotNull
        private String name;
        @NotNull
        @Pattern(regexp = "^F$|^M$")
        private String gender;
        @NotNull
        @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\+00:00$")
        private String birthday;
        @Telephone
        private String telephone;
        @Email
        private String email;

        public static Object of(Person person) {
            PersonForm form = new PersonForm();
            form.id = person.getId();
            form.name = person.getName();
            form.gender = person.getGender();
            form.telephone = person.getTelephone();
            form.email = person.getEmail();
            form.birthday = person.getBirthday().format(DateTimeFormatter.ISO_DATE_TIME);
            return form;
        }

        public Person toEntity() {
            return merge(new Person());
        }

        public Person merge(Person entity) {
            entity.setName(name);
            entity.setGender(gender);
            entity.setBirthday(LocalDateTime.parse(birthday, DateTimeFormatter.ISO_DATE_TIME));
            entity.setTelephone(telephone);
            entity.setEmail(email);
            return entity;
        }

        public Integer getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getTelephone() {
            return telephone;
        }

        public void setTelephone(String telephone) {
            this.telephone = telephone;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }
}
