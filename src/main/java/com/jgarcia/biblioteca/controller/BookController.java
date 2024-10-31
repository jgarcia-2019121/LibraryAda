package com.jgarcia.biblioteca.controller;

import java.util.List;
import java.util.Optional;
import com.jgarcia.biblioteca.entity.BookEntity;
import com.jgarcia.biblioteca.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/book")
@Slf4j
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) { this.bookService = bookService; }

    @GetMapping("/")
    public ResponseEntity<List<BookEntity>> getAllLibros() {
        List<BookEntity> libros = bookService.allLibros();
        return ResponseEntity.ok(libros);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookEntity> getLibroById(@PathVariable("id") String id) {
        Optional<BookEntity> libroOpt = bookService.getLibroById(id);
        return libroOpt.map(bookEntity -> new ResponseEntity<>(bookEntity, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<BookEntity> createLibro(@RequestBody BookEntity libro) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String role = auth.getAuthorities().iterator().next().getAuthority();

        if (role.equals("ROLE_ADMIN")) {
            BookEntity savedLibro = bookService.save(libro);
            return new ResponseEntity<>(savedLibro, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookEntity> updateLibro(@PathVariable("id") String id, @RequestBody BookEntity libro) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String role = auth.getAuthorities().iterator().next().getAuthority();

        if (role.equals("ROLE_ADMIN")) {
            BookEntity updatedLibro = bookService.updateLibro(libro, id);
            if (updatedLibro != null) {
                return new ResponseEntity<>(updatedLibro, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLibro(@PathVariable("id") String id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String role = auth.getAuthorities().iterator().next().getAuthority();

        if (role.equals("ROLE_ADMIN")) {
            bookService.deleteLibro(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
}
