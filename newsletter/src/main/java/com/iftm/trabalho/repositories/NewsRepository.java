package com.iftm.trabalho.repositories;

import com.iftm.trabalho.models.News;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NewsRepository extends MongoRepository<News, ObjectId> {

    @Query("{'post._id': ?0}")
    public Optional<News> findNewsByPostId(ObjectId postId);

}
