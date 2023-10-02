package com.iftm.trabalho.controllers;

import com.iftm.trabalho.models.dtos.PostDTO;
import com.iftm.trabalho.services.PostService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/v1/posts")
public class PostController {

    @Autowired
    private PostService service;

    @Value("${eureka.instance.instance-id}")
    private String instanceId;

    @GetMapping
    public ResponseEntity<List<PostDTO>> findAll() {
        return service.findAll();
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<PostDTO> findById(@PathVariable("id")ObjectId id){
        return service.findById(id);
    }

    @GetMapping("/instance")
    public String getInstanceId() {
        return instanceId;
    }

    @PostMapping
    public ResponseEntity<PostDTO> create(@RequestBody PostDTO postDTO){
        return service.save(postDTO);
    }

    @PutMapping
    public ResponseEntity<PostDTO> update(@RequestBody PostDTO postDTO){
        return service.update(postDTO);
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<?> delete(@PathVariable("id")ObjectId id){
        return service.delete(id);
    }

}
