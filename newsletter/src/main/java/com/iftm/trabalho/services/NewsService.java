package com.iftm.trabalho.services;

import com.iftm.trabalho.mensages.RabbitMqSendLog;
import com.iftm.trabalho.models.News;
import com.iftm.trabalho.models.Post;
import com.iftm.trabalho.models.dtos.LogDTO;
import com.iftm.trabalho.models.dtos.NewsDTO;
import com.iftm.trabalho.models.dtos.PostDTO;
import com.iftm.trabalho.repositories.NewsRepository;
import com.iftm.trabalho.repositories.PostRepository;
import com.iftm.trabalho.repositories.TagsRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NewsService {

    @Autowired
    private NewsRepository repository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private TagsRepository tagsRepository;

    @Autowired
    private RabbitMqSendLog rabbitMqSendLog;

    public ResponseEntity<List<NewsDTO>> findAll() {
        var dbNews = repository.findAll();
        if(dbNews.isEmpty())
            return ResponseEntity.notFound().build();

        var newsDtos = dbNews.stream().map(newsDTO -> {
            return new NewsDTO((News) newsDTO);
        }).collect(Collectors.toList());

        return ResponseEntity.ok(newsDtos);
    }

    public ResponseEntity<NewsDTO> findById(ObjectId id) {
        if(id == null)
            return ResponseEntity.badRequest().build();
        var dbNews = repository.findById(id);
        if(dbNews.isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(new NewsDTO(dbNews.get()));
    }

    public ResponseEntity<NewsDTO> findNewsByPostId(ObjectId postId) {
        if(postId == null)
            return ResponseEntity.badRequest().build();
        var dbNews = repository.findNewsByPostId(postId);
        if(dbNews.isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(new NewsDTO(dbNews.get()));

    }

    public ResponseEntity<NewsDTO> save(NewsDTO newsDTO) {
        if (newsDTO.getTitle().isBlank()){
            return ResponseEntity.badRequest().build();
        }

    rabbitMqSendLog.sendLog(new LogDTO<NewsDTO>("created", new NewsDTO(repository.save(newsDTO.toNews()))));

        return ResponseEntity.ok(new NewsDTO(repository.save(newsDTO.toNews())));
    }

    public ResponseEntity<NewsDTO> update (NewsDTO newsDTO) {
        if (newsDTO.getId() == null){
            return ResponseEntity.badRequest().build();
        }

        var objectId = new ObjectId(newsDTO.getId());
        var dbNews = repository.findById(objectId);
        if(dbNews.isEmpty())
            return ResponseEntity.notFound().build();

        var dbNewsObj = dbNews.get();
        dbNewsObj.setTitle(newsDTO.getTitle());
        dbNewsObj.setDate(newsDTO.getDate());
        dbNewsObj.setEditorName(newsDTO.getEditorName());
        dbNewsObj.setPosts(newsDTO.getPosts().stream().map(postDTO -> {
            return postDTO.toPost();
        }).collect(Collectors.toList()));

        rabbitMqSendLog.sendLog(new LogDTO<NewsDTO>("updated", new NewsDTO(repository.save(dbNewsObj))));

        return ResponseEntity.ok(new NewsDTO(repository.save(dbNewsObj)));

    }

    public ResponseEntity<NewsDTO> addPostToNews(ObjectId newsId, PostDTO postDTO) {
        if (newsId == null || postDTO.getId() == null){
            return ResponseEntity.badRequest().build();
        }

        var dbNews = repository.findById(newsId);
        var dbPost = postRepository.findById(new ObjectId(postDTO.getId()));

        if(dbNews.isEmpty() || dbPost.isEmpty())
            return ResponseEntity.notFound().build();

        var curNews = dbNews.get();
        List<Post> posts;
        if (curNews.getPosts() == null){
            posts = new ArrayList<Post>();
            curNews.setPosts(posts);
        }



        curNews.getPosts().add(dbPost.get());
        return ResponseEntity.ok(new NewsDTO(repository.save(curNews)));

    }

    public ResponseEntity <?> delete(ObjectId id) {
        if (id == null){
            return ResponseEntity.badRequest().build();
        }

        repository.deleteById(id);

        var dbNews = repository.findById(id);
        if(dbNews.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
        }

        rabbitMqSendLog.sendLog(new LogDTO<NewsDTO>("deleted", new NewsDTO(dbNews.get())));

        return ResponseEntity.ok().build();
    }


    public ResponseEntity<NewsDTO> addPost(ObjectId newsId, PostDTO postDTO) {
        if (newsId == null || postDTO.getId() == null){
            return ResponseEntity.badRequest().build();
        }

        var dbNews = repository.findById(newsId);
        var dbPost = postRepository.findById(new ObjectId(postDTO.getId()));

        if(dbNews.isEmpty() || dbPost.isEmpty())
            return ResponseEntity.notFound().build();

        var curNews = dbNews.get();
        List<Post> posts;
        if (curNews.getPosts() == null){
            posts = new ArrayList<Post>();
            curNews.setPosts(posts);
        }

        curNews.getPosts().add(dbPost.get());
        return ResponseEntity.ok(new NewsDTO(repository.save(curNews)));
    }


}
