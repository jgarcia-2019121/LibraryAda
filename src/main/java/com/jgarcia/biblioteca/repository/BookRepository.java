package com.jgarcia.biblioteca.repository;

import com.jgarcia.biblioteca.entity.BookEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends MongoRepository<BookEntity, String> {

}
