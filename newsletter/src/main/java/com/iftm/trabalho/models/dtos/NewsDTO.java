package com.iftm.trabalho.models.dtos;

import com.iftm.trabalho.models.News;
import com.iftm.trabalho.models.Post;
import org.bson.types.ObjectId;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class NewsDTO implements Serializable {

    private String id;
    private String title;
    private String date;

    private String editorName;
    private List<PostDTO> posts;

    public NewsDTO() { }

    public NewsDTO(String title, String date, String editorName, List<PostDTO> posts) {
        this.title = title;
        this.date = date;
        this.editorName = editorName;
        this.posts = posts;
    }

    public NewsDTO(News news) {
        if(news.getId() != null)
            this.id = news.getId().toString();
        this.title = news.getTitle();
        this.date = news.getDate();
        this.editorName = news.getEditorName();
        if(news.getPosts() != null)
            this.posts = news.getPosts().stream().map(post -> {
                return new PostDTO(post);
            }).collect(Collectors.toList());
    }

    public News toNews() {
        ObjectId id = null;
        if(this.id != null)
            id = new ObjectId(this.id);

        List<Post> posts = null;
        if(this.posts != null)
            posts = this.posts.stream().map(postDTO -> {
                        return postDTO.toPost();
                    }
            ).collect(Collectors.toList());

        return new News(id,
                this.title,
                this.date,
                this.editorName,
                posts);
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEditorName() {
        return editorName;
    }

    public void setEditorName(String editorName) {
        this.editorName = editorName;
    }

    public List<PostDTO> getPosts() {
        return posts;
    }

    public void setPosts(List<PostDTO> posts) {
        this.posts = posts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NewsDTO newsDTO = (NewsDTO) o;
        return Objects.equals(id, newsDTO.id) && Objects.equals(title, newsDTO.title) && Objects.equals(date, newsDTO.date) && Objects.equals(editorName, newsDTO.editorName) && Objects.equals(posts, newsDTO.posts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, date, editorName, posts);
    }

    @Override
    public String toString() {
        return "NewsDTO{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", date='" + date + '\'' +
                ", editorName='" + editorName + '\'' +
                ", posts=" + posts +
                '}';
    }
}

