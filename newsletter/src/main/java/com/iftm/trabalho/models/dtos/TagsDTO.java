package com.iftm.trabalho.models.dtos;

import com.iftm.trabalho.models.Tags;
import org.bson.types.ObjectId;

import java.io.Serializable;
import java.util.Objects;

public class TagsDTO implements Serializable {

    private String id;

    private String name;

    public TagsDTO() { }

    public TagsDTO (String name) {
        this.name = name;
    }

    public TagsDTO (Tags tags){
        if (tags.getId() !=  null)
            this.id = tags.getId().toString();
        this.name = tags.getName();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Tags toTags() {
        ObjectId id = null;
        if(this.id != null)
            id = new ObjectId(this.id);

        return new Tags(
                id,
                this.name
        );

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TagsDTO tagsDTO = (TagsDTO) o;
        return Objects.equals(id, tagsDTO.id) && Objects.equals(name, tagsDTO.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "TagsDTO{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
