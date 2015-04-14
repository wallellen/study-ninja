package edu.alvin.ninja.mctab.controllers;

import edu.alvin.ninja.mctab.models.Child;
import edu.alvin.ninja.mctab.models.Child.Type;
import edu.alvin.ninja.mctab.models.Main;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;

public class MainForm {
    private Integer id;
    @NotEmpty
    @Length(min = 1, max = 20)
    private String name;
    @Length(max = 500)
    private String value;
    @Valid
    private ChildForm[] children;

    public static MainForm of(Main main) {
        MainForm mainForm = new MainForm();
        mainForm.id = main.getId();
        mainForm.name = main.getName();
        mainForm.value = main.getValue();
        if (main.hasChildren()) {
            mainForm.children = new ChildForm[main.childrenCount()];
            int index = 0;
            for (Child child : main.getChildren()) {
                mainForm.children[index++] = ChildForm.of(child);
            }
        }
        return mainForm;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public ChildForm[] getChildren() {
        return children;
    }

    public void setChildren(ChildForm[] children) {
        this.children = children;
    }

    public Main migrate(Main main) {
        main.setName(name);
        main.setValue(value);
        main.clearChildren();
        if (children != null) {
            for (ChildForm child : this.children) {
                main.addChild(child.toModel(main));
            }
        }
        return main;
    }

    public Main toModel() {
        return migrate(new Main());
    }

    public static class ChildForm {
        @NotEmpty
        @Length(max = 20)
        private String name;
        @Length(max = 500)
        private String value;
        private Type type;

        public static ChildForm of(Child child) {
            ChildForm childForm = new ChildForm();
            childForm.name = child.getName();
            childForm.value = child.getValue();
            childForm.type = child.getType();
            return childForm;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public Child.Type getType() {
            return type;
        }

        public void setType(Child.Type type) {
            this.type = type;
        }

        public Child toModel(Main main) {
            Child child = new Child();
            child.setName(name);
            child.setValue(value);
            child.setType(type);
            child.setMain(main);
            return child;
        }
    }
}