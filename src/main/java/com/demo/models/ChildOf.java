package com.demo.models;

import com.arangodb.springframework.annotation.Edge;
import com.arangodb.springframework.annotation.From;
import com.arangodb.springframework.annotation.To;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;

@Edge
public class ChildOf {

    @Id
    private String id;

    @JsonIgnoreProperties("characters")
    @From
    private Character child;

    @JsonIgnoreProperties("characters")
    @To
    private Character parent;

    public ChildOf(final Character child, final Character parent) {
        super();
        this.child = child;
        this.parent = parent;
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public Character getChild() {
        return child;
    }

    public void setChild(final Character child) {
        this.child = child;
    }

    public Character getParent() {
        return parent;
    }

    public void setParent(final Character parent) {
        this.parent = parent;
    }

    @Override
    public String toString() {
        return "ChildOf [id=" + id +  "]";
    }

}
