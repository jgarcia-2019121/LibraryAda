package com.jgarcia.biblioteca.service;

import com.jgarcia.biblioteca.entity.user.UserEntity;
import com.jgarcia.biblioteca.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getUserByEmail_ShouldReturnUserWhenExists() {
        UserEntity user = new UserEntity();
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));

        Optional<UserEntity> result = userService.getUserByEmail("test@example.com");

        assertTrue(result.isPresent());
        assertEquals(user, result.get());
        verify(userRepository, times(1)).findByEmail("test@example.com");
    }

    @Test
    void isUserOwner_ShouldReturnTrueWhenEmailMatches() {
        UserEntity user = new UserEntity();
        user.setEmail("test@example.com");

        when(userRepository.findById("1")).thenReturn(Optional.of(user));

        boolean result = userService.isUserOwner("test@example.com", "1");

        assertTrue(result);
        verify(userRepository, times(1)).findById("1");
    }

    @Test
    void isUserOwner_ShouldReturnFalseWhenEmailDoesNotMatch() {
        UserEntity user = new UserEntity();
        user.setEmail("other@example.com");

        when(userRepository.findById("1")).thenReturn(Optional.of(user));

        boolean result = userService.isUserOwner("test@example.com", "1");

        assertFalse(result);
        verify(userRepository, times(1)).findById("1");
    }
}
