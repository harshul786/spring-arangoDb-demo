package com.demo.models.RequestModels;

public class CharacterRequest {
    private String name;
    private String surname;
    private boolean alive;
    private Integer age;

    @Override
    public String toString() {
        return "CharacterRequest{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", alive=" + alive +
                ", age=" + age +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
