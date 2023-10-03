package com.iftm.trabalho.models;

import org.bson.types.ObjectId;

import java.util.Objects;

public class Tags {
    private String name;

    public Tags() { }

    public Tags(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tags tags = (Tags) o;
        return Objects.equals(name, tags.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Tags{" +
                "name='" + name + '\'' +
                '}';
    }
}