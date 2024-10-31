package com.jgarcia.biblioteca.controller;

import java.util.List;
import java.util.Optional;
import com.jgarcia.biblioteca.entity.user.UserEntity;
import com.jgarcia.biblioteca.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@Slf4j
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public ResponseEntity<List<UserEntity>> getAllUsers() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String role = auth.getAuthorities().iterator().next().getAuthority();

        if (role.equals("ROLE_ADMIN")) {
            List<UserEntity> listUsers = userService.allUsers();
            return ResponseEntity.ok(listUsers);
        } else {
            String email = auth.getName();
            Optional<UserEntity> user = userService.getUserByEmail(email);
            return user.map(u -> ResponseEntity.ok(List.of(u)))
                    .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserEntity> findById(@PathVariable("id") String id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String role = auth.getAuthorities().iterator().next().getAuthority();
        String email = auth.getName();

        if (role.equals("ROLE_ADMIN") || userService.isUserOwner(email, id)) {
            return userService.getUserById(id)
                    .map(user -> {
                        log.info("Usuario encontrado: {}", user);
                        return new ResponseEntity<>(user, HttpStatus.OK);
                    })
                    .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserEntity> updateUser(@RequestBody UserEntity userModel, @PathVariable("id") String id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String role = auth.getAuthorities().iterator().next().getAuthority();
        String email = auth.getName();

        if (role.equals("ROLE_ADMIN") && userService.isUserOwner(email, id)) {
            UserEntity updatedUser = userService.updateUser(userModel, id);
            if (updatedUser != null) {
                log.info("Usuario actualizado {}", updatedUser);
                return new ResponseEntity<>(updatedUser, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } else if (userService.isUserOwner(email, id)) {
            UserEntity updatedUser = userService.updateUser(userModel, id);
            if (updatedUser != null) {
                log.info("Usuario actualizado {}", updatedUser);
                return new ResponseEntity<>(updatedUser, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") String id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String role = auth.getAuthorities().iterator().next().getAuthority();
        String email = auth.getName();

        if (role.equals("ROLE_ADMIN")) {
            Optional<UserEntity> user = userService.getUserById(id);
            if (user.isPresent()) {
                userService.deleteUser(id);
                log.info("Usuario eliminado: {}", id);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } else if (userService.isUserOwner(email, id)) {
            Optional<UserEntity> user = userService.getUserById(id);
            if (user.isPresent()) {
                userService.deleteUser(id);
                log.info("Usuario eliminado: {}", id);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
}
