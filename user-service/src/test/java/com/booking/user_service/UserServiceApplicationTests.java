package com.booking.user_service;

import com.booking.user_service.model.User;
import com.booking.user_service.repository.UserRepository;
import com.booking.user_service.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceApplicationTests {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1L);
        testUser.setName("Nikola");
        testUser.setEmail("nikola@test.com");
        testUser.setPassword("test123");
    }

    @Test
    void getAllUsers_ShouldReturnAllUsers() {
        when(userRepository.findAll()).thenReturn(Arrays.asList(testUser));
        List<User> users = userService.getAllUsers();
        assertEquals(1, users.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void createUser_ShouldSaveAndReturnUser() {
        when(userRepository.save(any(User.class))).thenReturn(testUser);
        User created = userService.createUser(testUser);
        assertNotNull(created);
        assertEquals("Nikola", created.getName());
        verify(userRepository, times(1)).save(testUser);
    }

    @Test
    void getUserById_ShouldReturnUser() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        User found = userService.getUserById(1L);
        assertNotNull(found);
        assertEquals(1L, found.getId());
    }

    @Test
    void getUserById_ShouldThrowException_WhenNotFound() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> userService.getUserById(99L));
    }

    @Test
    void deleteUser_ShouldCallRepository() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        userService.deleteUser(1L);
        verify(userRepository, times(1)).deleteById(1L);
    }
}
