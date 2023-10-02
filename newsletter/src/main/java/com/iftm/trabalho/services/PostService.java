package com.iftm.trabalho.services;

import com.iftm.trabalho.mensages.RabbitMqSendLog;
import com.iftm.trabalho.models.Post;
import com.iftm.trabalho.models.dtos.LogDTO;
import com.iftm.trabalho.models.dtos.PostDTO;
import com.iftm.trabalho.repositories.NewsRepository;
import com.iftm.trabalho.repositories.PostRepository;
import com.iftm.trabalho.repositories.TagsRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class PostService {

    @Autowired
    private PostRepository repository;

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private TagsRepository tagsRepository;

    @Autowired
    private RabbitMqSendLog rabbitMqSendLog;

    public ResponseEntity<List<PostDTO>> findAll() {
        var dbPosts = repository.findAll();
        if(dbPosts.isEmpty())
            return ResponseEntity.notFound().build();

        var postDtos = dbPosts.stream().map(post -> {
            return new PostDTO(post);
        }).collect(Collectors.toList());

        return ResponseEntity.ok(postDtos);
    }

    public ResponseEntity<PostDTO> findById(ObjectId id) {
        if(id == null)
            return ResponseEntity.badRequest().build();

        var dbPost = repository.findById(id);

        if(dbPost.isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(new PostDTO(dbPost.get()));
    }

    public ResponseEntity<PostDTO> save(PostDTO postDTO) {

        if (postDTO.getTitle().isBlank() || postDTO.getBody().isBlank() || postDTO.getAuthorName().isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        var dbPost = repository.save(postDTO.toPost());

       // rabbitMqSendLog.sendLog(new LogDTO<PostDTO>("created", new PostDTO(dbPost)));

        return ResponseEntity.ok(new PostDTO(dbPost));
    }

    public ResponseEntity<PostDTO> update(PostDTO postDTO) {

        if (postDTO.getId() == null) {
            return ResponseEntity.badRequest().build();
        }

        var objectId = new ObjectId(postDTO.getId());
        var dbPost = repository.findById(objectId);

        if (dbPost.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var postObj = dbPost.get();

        postObj.setTitle(postDTO.getTitle());
        postObj.setBody(postDTO.getBody());
        postObj.setAuthorName(postDTO.getAuthorName());

        //rabbitMqSendLog.sendLog(new LogDTO<PostDTO>("updated", new PostDTO(postObj)));

        return ResponseEntity.ok(new PostDTO(repository.save(postObj)));
    }

    public ResponseEntity<?> delete(ObjectId id) {
        if (id == null) {
            return ResponseEntity.badRequest().build();
        }

        var news = newsRepository.findNewsByPostId(id);
        repository.deleteById(id);

        var dbPost = repository.findById(id);
        if (dbPost.isPresent()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        if (!news.isEmpty()) {
            news.get().getPosts().removeIf(post -> post.getId().toString().equals(id.toString()));
            newsRepository.save(news.get());
        }

        //rabbitMqSendLog.sendLog(new LogDTO<PostDTO>("deleted", new PostDTO(dbPost.get())));

        return ResponseEntity.ok().build();
    }



}

