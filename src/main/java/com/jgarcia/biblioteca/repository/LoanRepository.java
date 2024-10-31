package com.jgarcia.biblioteca.repository;

import java.util.Optional;
import com.jgarcia.biblioteca.entity.LoanEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanRepository extends MongoRepository<LoanEntity, String> {
    Optional<LoanEntity> findByLibroIdAndUsuarioId(String libroId, String usuarioId);
}
