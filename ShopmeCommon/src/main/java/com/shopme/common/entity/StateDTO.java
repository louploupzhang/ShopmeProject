package com.shopme.common.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

//In the entity class State, there is a reference of country
//In the view we just want to use ID and name of the state
//To make the class light weight, we use StateDTO instead of the entire State class
public class StateDTO {
    private Integer id;
    private String name;

    public StateDTO() {
    }

    public StateDTO(Integer id, String name) {
        this.id = id;
        this.name = name;
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
}
