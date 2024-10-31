package com.jgarcia.biblioteca.controller;

import java.util.Arrays;
import java.util.List;
import com.jgarcia.biblioteca.entity.BookEntity;
import com.jgarcia.biblioteca.service.BookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookControllerTest {

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;

    @Test
    void getAllLibros_ShouldReturnListOfBooks() {

        List<BookEntity> libros = Arrays.asList(
                new BookEntity("1", "Orgullo y prejuicio", "Jane Austen", "978-0-19-953556-9", true),
                new BookEntity("2", "1984", "George Orwell", "978-0-452-28423-4", true),
                new BookEntity("3", "Matar a un ruiseñor", "Harper Lee", "978-0-06-093546-7", true),
                new BookEntity("4", "El Gran Gatsby", "F. Scott Fitzgerald", "978-0-7432-7356-5", true),
                new BookEntity("5", "En busca del tiempo perdido", "Marcel Proust", "978-0-307-47433-6", true)

        );

        when(bookService.allLibros()).thenReturn(libros);

        ResponseEntity<List<BookEntity>> response = bookController.getAllLibros();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(libros.size(), response.getBody().size());
        assertEquals(libros, response.getBody());

        verify(bookService, times(1)).allLibros();
    }
}
