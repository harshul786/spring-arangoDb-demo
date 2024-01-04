package com.demo.models;

import org.springframework.web.bind.annotation.RequestBody;

public class CustomRelationshipModel {

    private String parentFirstName;
    private String parentSurname;
    private String childFirstName;
    private String childSurname;

    public CustomRelationshipModel(String parentFirstName, String parentSurname, String childFirstName, String childSurname) {
        this.parentFirstName = parentFirstName;
        this.parentSurname = parentSurname;
        this.childFirstName = childFirstName;
        this.childSurname = childSurname;
    }

    public String getParentFirstName() {
        return parentFirstName;
    }

    public void setParentFirstName(String parentFirstName) {
        this.parentFirstName = parentFirstName;
    }

    public String getParentSurname() {
        return parentSurname;
    }

    public void setParentSurname(String parentSurname) {
        this.parentSurname = parentSurname;
    }

    public String getChildFirstName() {
        return childFirstName;
    }

    public void setChildFirstName(String childFirstName) {
        this.childFirstName = childFirstName;
    }

    public String getChildSurname() {
        return childSurname;
    }

    public void setChildSurname(String childSurname) {
        this.childSurname = childSurname;
    }
}
