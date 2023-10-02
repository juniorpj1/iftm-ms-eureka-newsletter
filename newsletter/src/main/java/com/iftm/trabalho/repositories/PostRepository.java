package com.iftm.trabalho.repositories;

import com.iftm.trabalho.models.News;
import com.iftm.trabalho.models.Post;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends MongoRepository<Post, ObjectId> {

    @Query("{'news._id': ?0}")
    public Optional<News> findPostsByNewsId(ObjectId newsId);


}
