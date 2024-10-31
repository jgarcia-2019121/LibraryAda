package com.jgarcia.biblioteca.controller;

import com.jgarcia.biblioteca.service.LibraryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/library")
public class LibraryController {
    private final LibraryService libraryService;

    public LibraryController(LibraryService libraryService) { this.libraryService = libraryService; }

    @PostMapping("/lend/{id}")
    public ResponseEntity<Void> prestarLibro(@PathVariable("id") String id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String role = auth.getAuthorities().iterator().next().getAuthority();
        String usuarioId = auth.getName();

        if (role.equals("ROLE_CLIENT") || role.equals("ROLE_ADMIN")) {
            boolean prestado = libraryService.prestarLibro(id, usuarioId);
            return prestado ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("/repay/{id}")
    public ResponseEntity<Void> devolverLibro(@PathVariable("id") String id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String role = auth.getAuthorities().iterator().next().getAuthority();
        String usuarioId = auth.getName();

        if (role.equals("ROLE_CLIENT") || role.equals("ROLE_ADMIN")) {
            boolean devuelto = libraryService.devolverLibro(id, usuarioId);
            return devuelto ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
}
