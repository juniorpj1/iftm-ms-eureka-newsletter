package com.iftm.logpool.models.dtos;

import com.iftm.trabalho.models.Post;
import com.iftm.trabalho.models.Tags;
import org.bson.types.ObjectId;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class PostDTO implements Serializable {

    private String id;
    private String title;

    private String authorName;

    private String body;

    private List<Tags> tags;

    public PostDTO() { }

    public PostDTO(String title, String authorName, String body, List<Tags> tags) {
        this.title = title;
        this.authorName = authorName;
        this.body = body;
        this.tags = tags;
    }

    public PostDTO(Post post) {
        if(post.getId() != null)
            this.id = post.getId().toString();
        this.title = post.getTitle();
        this.authorName = post.getAuthorName();
        this.body = post.getBody();
        if (post.getTags() != null)
            this.tags = post.getTags().stream().map(tags -> {
                return tags;
            }).collect(java.util.stream.Collectors.toList());
    }

    public Post toPost() {
        ObjectId id = null;
        if(this.id != null)
            id = new ObjectId(this.id);

        List<Tags> tags = null;
        if(this.tags != null)
            tags = this.tags.stream().map(tagsDTO -> {
                return tagsDTO;
            }
            ).collect(java.util.stream.Collectors.toList());

        return new Post(id,
                this.title,
                this.authorName,
                this.body,
                tags);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public List<Tags> getTags() {
        return tags;
    }

    public void setTags(List<Tags> tags) {
        this.tags = tags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PostDTO postDTO = (PostDTO) o;
        return Objects.equals(id, postDTO.id) && Objects.equals(title, postDTO.title) && Objects.equals(authorName, postDTO.authorName) && Objects.equals(body, postDTO.body) && Objects.equals(tags, postDTO.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, authorName, body, tags);
    }

    @Override
    public String toString() {
        return "PostDTO{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", authorName='" + authorName + '\'' +
                ", body='" + body + '\'' +
                ", tags=" + tags +
                '}';
    }
}
