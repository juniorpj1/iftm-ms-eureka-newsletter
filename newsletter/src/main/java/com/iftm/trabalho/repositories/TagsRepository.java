package com.iftm.trabalho.repositories;

import com.iftm.trabalho.models.Post;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagsRepository extends MongoRepository<Post, ObjectId> {
}
