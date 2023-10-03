package com.iftm.trabalho.models.dtos;

import com.iftm.trabalho.models.Tags;
import org.bson.types.ObjectId;

import java.io.Serializable;
import java.util.Objects;

public class TagsDTO implements Serializable {
    private String name;

    public TagsDTO() { }

    public TagsDTO (String name) {
        this.name = name;
    }

    public TagsDTO (Tags tags){
        this.name = tags.getName();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Tags toTags() {
        return new Tags(
                this.name
        );

    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TagsDTO tagsDTO = (TagsDTO) o;
        return Objects.equals(name, tagsDTO.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "TagsDTO{" +
                "name='" + name + '\'' +
                '}';
    }
}
