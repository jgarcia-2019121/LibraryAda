package com.jgarcia.biblioteca.service;

import com.jgarcia.biblioteca.entity.BookEntity;
import com.jgarcia.biblioteca.entity.LoanEntity;
import com.jgarcia.biblioteca.repository.BookRepository;
import com.jgarcia.biblioteca.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class LibraryService {

    private final BookRepository bookRepository;
    private final LoanRepository prestamoRepository;

    @Autowired
    public LibraryService(BookRepository bookRepository, LoanRepository loanRepository) {
        this.bookRepository = bookRepository;
        this.prestamoRepository = loanRepository;
    }

    public boolean prestarLibro(String libroId, String usuarioId) {
        Optional<BookEntity> libroOpt = bookRepository.findById(libroId);
        if (libroOpt.isPresent() && libroOpt.get().isDisponible()) {
            BookEntity libro = libroOpt.get();
            libro.setDisponible(false);
            bookRepository.save(libro);

            LoanEntity prestamo = new LoanEntity();
            prestamo.setLibroId(libroId);
            prestamo.setUsuarioId(usuarioId);
            prestamo.setFechaPrestamo(new Date());
            prestamoRepository.save(prestamo);
            return true;
        }
        return false;
    }

    public boolean devolverLibro(String libroId, String usuarioId) {
        Optional<BookEntity> libroOpt = bookRepository.findById(libroId);

        if (libroOpt.isPresent()) {

            BookEntity libro = libroOpt.get();
            Optional<LoanEntity> prestamoOpt = prestamoRepository.findByLibroIdAndUsuarioId(libroId, usuarioId);

            if (prestamoOpt.isPresent()) {
                LoanEntity prestamo = prestamoOpt.get();
                libro.setDisponible(true);
                bookRepository.save(libro);
                prestamo.setFechaDevolucion(new Date());
                prestamoRepository.save(prestamo);

                return true;
            } else {
                return false;
            }
        }
        return false;
    }
}
