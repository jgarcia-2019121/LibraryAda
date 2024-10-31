package com.jgarcia.biblioteca.service;

import com.jgarcia.biblioteca.entity.BookEntity;
import com.jgarcia.biblioteca.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) { this.bookRepository = bookRepository; }

    public BookEntity save(BookEntity libro) { return bookRepository.save(libro); }
    public List<BookEntity> allLibros() { return bookRepository.findAll(); }
    public Optional<BookEntity> getLibroById(String id) { return bookRepository.findById(id); }
    public void deleteLibro(String id) { bookRepository.deleteById(id); }

    public BookEntity updateLibro(BookEntity libro, String id) {
        return bookRepository.findById(id).map(existingLibro -> {
            existingLibro.setTitulo(libro.getTitulo());
            existingLibro.setAutor(libro.getAutor());
            existingLibro.setDisponible(libro.isDisponible());
            return bookRepository.save(existingLibro);
        }).orElse(null);
    }
}
