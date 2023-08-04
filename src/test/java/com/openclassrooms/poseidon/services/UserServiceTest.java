package com.openclassrooms.poseidon.services;

import com.openclassrooms.poseidon.domain.Users;
import com.openclassrooms.poseidon.repositories.UserRepository;
import com.openclassrooms.poseidon.services.Impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserServiceImpl userServiceImpl;
    @Mock
    private UserRepository userRepository;
    @Captor
    private ArgumentCaptor<Users> UserArgumentCaptor;
    @Mock
    private PasswordEncoder passwordEncoder;


    @Test
    public void testSaveUser() {
        Users user = new Users(1234567890123456789L, "Hamza", "1234", "ADMIN", "ADMIN");

        userServiceImpl.saveUser(user);

        verify(userRepository).save(UserArgumentCaptor.capture());
        Users capturedUser = UserArgumentCaptor.getValue();
        assertThat(capturedUser).isEqualTo(user);
    }


    @Test
    public void testListAllUsers() {
        // Arrange
        List<Users> expectedUsers = new ArrayList<>();
        expectedUsers.add(new Users(1234567890123456789L, "Hamza", "1234", "ADMIN", "ADMIN"));
        expectedUsers.add(new Users(1234567891234567891L, "John", "5678", "USER", "USER"));

        when(userRepository.findAll()).thenReturn(expectedUsers);

        // Act
        List<Users> actualUsers = userServiceImpl.listAll();

        // Assert
        verify(userRepository).findAll();
        assertThat(actualUsers).isEqualTo(expectedUsers);
    }

    @Test
    public void testFindUserByUsername() {
        // Arrange
        String username = "Hamza";
        Users expectedUser = new Users(1234567890123456789L, "Hamza", "1234", "ADMIN", "ADMIN");
        Optional<Users> expectedOptional = Optional.of(expectedUser);

        when(userRepository.findByUsername(username)).thenReturn(expectedOptional);

        // Act
        Optional<Users> actualOptional = userServiceImpl.findUserByUsername(username);

        // Assert
        verify(userRepository).findByUsername(username);
        assertThat(actualOptional).isEqualTo(expectedOptional);
    }


    @Test
    public void testUpdateUser () {
        // Arrange
        Long id = 1234567890123456789L;
        Users expectedUser = new Users(id, "Hamza", "1234", "ADMIN", "ADMIN");
        Optional<Users> expectedOptional = Optional.of(expectedUser);

        when(userRepository.findById(id)).thenReturn(expectedOptional);

        // Act
        Users actualUser = userServiceImpl.updateUser(id);

        // Assert
        verify(userRepository).findById(id);
        assertThat(actualUser).isEqualTo(expectedUser);
    }

    @Test
    public void newUser_whenUserExists_thenUpdatesAndReturnsUser() {
        // Given
        Long id = 1L;
        Users existingUser = new Users();
        existingUser.setUsername("existingUser");
        Users updatedUser = new Users();
        updatedUser.setUsername("updatedUser");

        when(userRepository.findById(id)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(Users.class))).thenReturn(updatedUser);

        // When
        Users result = userServiceImpl.newUser(id, updatedUser);

        // Then
        assertEquals(updatedUser.getUsername(), result.getUsername());
        verify(userRepository, times(1)).findById(id);
        verify(userRepository, times(1)).save(any(Users.class));
    }

    @Test
    public void newUser_whenUserNotExists_thenThrowsException() {
        // Given
        Long id = 1L;
        Users updatedUser = new Users();
        updatedUser.setUsername("updatedUser");

        when(userRepository.findById(id)).thenReturn(Optional.empty());

        // When / Then
        assertThrows(RuntimeException.class, () -> userServiceImpl.newUser(id, updatedUser));
        verify(userRepository, times(1)).findById(id);
    }


    @Test
    public void findById_whenUserExists_returnsUser() {
        // Given
        Users user = new Users();
        user.setFullName("John Doe");
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        // When
        Optional<Users> result = userServiceImpl.findById(1L);

        // Then
        assertTrue(result.isPresent());
    }



    @Test
    public void testDeleteUser() {
        // Arrange
        Long id = 1234567890123456789L;

        // Act
        userServiceImpl.delete(id);

        // Assert
        verify(userRepository, times(1)).deleteById(id);

    }
}





