package edu.alvin.ninja.mctab.models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "main")
public class Main {
    @Id
    private Integer id;
    private String name;
    private String value;
    @OneToMany(mappedBy = "main", orphanRemoval = true, cascade = CascadeType.ALL)
    @OrderBy("id")
    private Set<Child> children = new LinkedHashSet<>();
    @Enumerated(EnumType.STRING)
    private Status status = Status.VALID;

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

    public Set<Child> getChildren() {
        return children;
    }

    public void setChildren(Set<Child> children) {
        this.children = children;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void clearChildren() {
        children.clear();
    }

    public void addChild(Child child) {
        children.add(child);
    }

    public boolean hasChildren() {
        return children != null && !children.isEmpty();
    }

    public int childrenCount() {
        return children.size();
    }

    public enum Status {
        VALID,
        INVALID
    }
}
