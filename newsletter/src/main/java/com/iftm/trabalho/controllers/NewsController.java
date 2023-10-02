package com.iftm.trabalho.controllers;

import com.iftm.trabalho.models.dtos.NewsDTO;
import com.iftm.trabalho.models.dtos.PostDTO;
import com.iftm.trabalho.services.NewsService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/news")
public class NewsController {

    @Autowired
    private NewsService service;

    @GetMapping
    public ResponseEntity<List<NewsDTO>> findAll() {
        return service.findAll();
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<NewsDTO> findById(@PathVariable("id") ObjectId id) {
        return service.findById(id);
    }

    @GetMapping("/post/id/{id}")
    public ResponseEntity<NewsDTO> findByPostId(@PathVariable("id") ObjectId id) {
        return service.findNewsByPostId(id);
    }

    @PostMapping
    public ResponseEntity<NewsDTO> create(@RequestBody NewsDTO newsDTO) {
        return service.save(newsDTO);
    }

    @PostMapping("/news/{id}/addPostToNews")
    public ResponseEntity<NewsDTO> addPost(@PathVariable("id") ObjectId id, @RequestBody PostDTO postDTO) {
        return service.addPost(id, postDTO);
    }

    @PutMapping
    public ResponseEntity<NewsDTO> update(@RequestBody NewsDTO newsDTO) {
        return service.update(newsDTO);
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") ObjectId id) {
        return service.delete(id);
    }

}
