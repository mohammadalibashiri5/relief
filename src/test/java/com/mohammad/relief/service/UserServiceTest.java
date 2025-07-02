package com.mohammad.relief.service;

import com.mohammad.relief.data.dto.response.ModifiedUserDto;
import com.mohammad.relief.data.entity.Visitor;
import com.mohammad.relief.exception.ReliefApplicationException;
import com.mohammad.relief.mapper.UserMapper;
import com.mohammad.relief.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Captor
    ArgumentCaptor<Visitor> captor;

    @Test
    void createNullUser() {
        ReliefApplicationException reliefException = assertThrows(ReliefApplicationException.class, () -> userService.registerUser(null));
        assertEquals("UserRequestDto is null", reliefException.getMessage());
    }

    @Test
    void readAUserWhoIsNotInDatabase() {
        ReliefApplicationException userException = assertThrows(ReliefApplicationException.class, () -> userService.getUserDetails("dupont"));
        assertEquals("No such a user", userException.getMessage());

    }

//    @Test()
//    void shouldRegisterUserSuccessfullyWhenUsernameDoesNotExist() throws ReliefApplicationException {
//        UserRequestDto userRequestDto = new UserRequestDto("test", "test", "testing", "test@test.com", "password", LocalDate.of(2020, 1, 1));
//        Visitor mappedVisitor = new Visitor();
//        mappedVisitor.setUsername("testing");
//        mappedVisitor.setEmail("test@test.com");
//
//        Visitor savedVisitor = new Visitor();
//        savedVisitor.setUsername("testing");
//        savedVisitor.setEmail("test@test.com");
//        savedVisitor.setPassword("hashedPassword");
//        savedVisitor.setRole("ROLE_USER");
//        UserResponseDto expectedResponse = new UserResponseDto("test", "test", "testing", "test@test.com", LocalDateTime.now(), LocalDate.of(2020, 1, 1), List.of(new AddictionResponseDto(1L,"name", "ffdf", Severity.LOW, 2)));
//
//        when(userRepository.existsByUsername("testing")).thenReturn(false);
//        when(passwordEncoder.encode("password")).thenReturn("hashedPassword");
//        when(userMapper.toEntity(userRequestDto)).thenReturn(mappedVisitor);
//        when(userRepository.save(mappedVisitor)).thenReturn(savedVisitor);
//        when(userMapper.toResponseDto(savedVisitor)).thenReturn(expectedResponse);
//
//        // Act
//        UserResponseDto response = userService.registerUser(userRequestDto);
//
//        // Assert
//        assertEquals("testing", response.username());
//        assertEquals("test@test.com", response.email());
//    }
//
//    @Test
//    void shouldThrowExceptionWhenUsernameAlreadyExists() {
//
//        // Arrange
//        UserRequestDto userRequestDto = new UserRequestDto("John", "Doe", "johndoe", "john@example.com", "password123", LocalDate.of(1995, 5, 20));
//
//        when(userRepository.existsByUsername("johndoe")).thenReturn(true);
//
//        // Act & Assert
//        ReliefApplicationException exception = assertThrows(ReliefApplicationException.class,
//                () -> userService.registerUser(userRequestDto));
//
//        assertEquals("Visitor already exists", exception.getMessage());
//    }
//
//    @Test
//    void shouldEncodePasswordBeforeSaving() throws ReliefApplicationException {
//        // Arrange
//        UserRequestDto userRequestDto = new UserRequestDto("John", "Doe", "johndoe", "john@example.com", "password123", LocalDate.of(1995, 5, 20));
//        Visitor mappedVisitor = new Visitor();
//        mappedVisitor.setUsername("johndoe");
//        mappedVisitor.setEmail("john@example.com");
//
//        Visitor savedVisitor = new Visitor();
//        savedVisitor.setUsername("johndoe");
//        savedVisitor.setEmail("john@example.com");
//        savedVisitor.setPassword("hashedPassword");
//
//        when(userRepository.existsByUsername("johndoe")).thenReturn(false);
//        when(passwordEncoder.encode("password123")).thenReturn("hashedPassword");
//        when(userMapper.toEntity(userRequestDto)).thenReturn(mappedVisitor);
//        when(userRepository.save(mappedVisitor)).thenReturn(savedVisitor);
//
//        // Act
//        userService.registerUser(userRequestDto);
//
//
//        // Assert
//        verify(passwordEncoder).encode("password123");
//    }
//
//    @Test
//    void shouldMapUserRequestDtoToVisitor() throws ReliefApplicationException {
//        // Arrange
//        UserRequestDto userRequestDto = new UserRequestDto("John", "Doe", "johndoe", "john@example.com", "password123", LocalDate.of(1995, 5, 20));
//        Visitor mappedVisitor = new Visitor();
//        mappedVisitor.setUsername("johndoe");
//        mappedVisitor.setEmail("john@example.com");
//
//        when(userRepository.existsByUsername("johndoe")).thenReturn(false);
//        when(userMapper.toEntity(userRequestDto)).thenReturn(mappedVisitor);
//
//        // Act
//        userService.registerUser(userRequestDto);
//
//
//        // Assert
//        verify(userMapper).toEntity(userRequestDto);
//    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {
        // Arrange
        String username = "unknownUser";
        when(userRepository.findByEmail(username)).thenReturn(Optional.empty());

        // Act & Assert
        ReliefApplicationException exception = assertThrows(ReliefApplicationException.class,
                () -> userService.updateUser(new ModifiedUserDto("newName", "newFamily", "newPass", LocalDate.of(2000, 1, 1)), username));

        assertEquals("Visitor not found", exception.getMessage());
    }

    @Test
    void shouldUpdateUserName() throws ReliefApplicationException {
        // Arrange
        String username = "existingUser";
        Visitor existingVisitor = new Visitor();
        existingVisitor.setUsername(username);
        existingVisitor.setName("oldName");

        ModifiedUserDto updateRequest = new ModifiedUserDto("newName", null, null, null);

        when(userRepository.findByEmail(username)).thenReturn(Optional.of(existingVisitor));
        when(userRepository.save(any(Visitor.class))).thenReturn(existingVisitor);
        //when(userMapper.toResponseDto(any(Visitor.class))).thenReturn(new UserResponseDto(username, "test", "testing", "test@test.com", LocalDateTime.now(), LocalDate.of(2020, 1, 1), List.of(new AddictionResponseDto(1L,"name", "ffdf", Severity.LOW, 2))));

        // Act
        userService.updateUser(updateRequest, username);


        // Assert
        assertEquals("newName", existingVisitor.getName());
        verify(userRepository).save(existingVisitor);
    }

    @Test
    void shouldUpdateUserFamilyName() throws ReliefApplicationException {
        // Arrange
        String username = "existingUser";
        Visitor existingVisitor = new Visitor();
        existingVisitor.setUsername(username);
        existingVisitor.setFamilyName("oldFamily");

        ModifiedUserDto updateRequest = new ModifiedUserDto(null, "newFamily", null, null);

        when(userRepository.findByEmail(username)).thenReturn(Optional.of(existingVisitor));
        when(userRepository.save(any(Visitor.class))).thenReturn(existingVisitor);

        // Act
        userService.updateUser(updateRequest, username);


        // Assert
        assertEquals("newFamily", existingVisitor.getFamilyName());
        verify(userRepository).save(existingVisitor);
    }

    @Test
    void shouldUpdateUserPassword() throws ReliefApplicationException {
        // Arrange
        String username = "existingUser";
        Visitor existingVisitor = new Visitor();
        existingVisitor.setUsername(username);
        existingVisitor.setPassword("oldPassword");

        ModifiedUserDto updateRequest = new ModifiedUserDto(null, null, "newPassword", null);

        when(userRepository.findByEmail(username)).thenReturn(Optional.of(existingVisitor));
        when(userRepository.save(any(Visitor.class))).thenReturn(existingVisitor);

        // Act
        userService.updateUser(updateRequest, username);


        // Assert
        assertNotEquals("oldPassword", existingVisitor.getPassword()); // Should be hashed
        verify(userRepository).save(existingVisitor);
    }

    @Test
    void shouldUpdateUserDateOfBirth() throws ReliefApplicationException {
        // Arrange
        String username = "existingUser";
        Visitor existingVisitor = new Visitor();
        existingVisitor.setUsername(username);
        existingVisitor.setDateOfBirth(LocalDate.of(1990, 1, 1));

        LocalDate newDateOfBirth = LocalDate.of(2000, 1, 1);
        ModifiedUserDto updateRequest = new ModifiedUserDto(null, null, null, newDateOfBirth);

        when(userRepository.findByEmail(username)).thenReturn(Optional.of(existingVisitor));
        when(userRepository.save(any(Visitor.class))).thenReturn(existingVisitor);

        // Act
        userService.updateUser(updateRequest, username);


        // Assert
        assertEquals(newDateOfBirth, existingVisitor.getDateOfBirth());
        verify(userRepository).save(existingVisitor);
    }

    @Test
    void shouldNotUpdateWhenNoFieldsAreChanged() throws ReliefApplicationException {
        // Arrange
        String username = "existingUser";
        Visitor existingVisitor = new Visitor();
        existingVisitor.setId(UUID.randomUUID());
        existingVisitor.setUsername(username);
        existingVisitor.setName("sameName");
        existingVisitor.setEmail(any());
        existingVisitor.setFamilyName("sameFamily");
        existingVisitor.setPassword("samePassword");
        existingVisitor.setDateOfBirth(LocalDate.of(1990, 1, 1));

        ModifiedUserDto updateRequest = new ModifiedUserDto("sameName", "sameFamily", "samePassword", LocalDate.of(1990, 1, 1));

        when(userRepository.findByEmail(username)).thenReturn(Optional.of(existingVisitor));

        // Act
        userService.updateUser(updateRequest, username);


        // Assert
        verify(userRepository, never()).save(any()); // No changes should trigger a save
    }
    @Test
    void shouldDeleteUserIfExists() {
        // Arrange
        String username = "deletableUser";
        Visitor visitor = new Visitor();
        visitor.setUsername(username);

        when(userRepository.findByEmail(username)).thenReturn(Optional.of(visitor));

        // Act
        userService.deleteUser(username);

        // Assert
        verify(userRepository).delete(captor.capture()); // Capturing the deleted user
        assertEquals(username, captor.getValue().getUsername());
    }
//
//    @Test
//    void shouldReturnUserDetailsWhenUserExists() throws ReliefApplicationException {
//        // Arrange
//        String username = "testUser";
//        Visitor visitor = new Visitor();
//        visitor.setUsername(username);
//        visitor.setName("John");
//        visitor.setEmail("john@example.com");
//
//        UserResponseDto responseDto = new UserResponseDto("John", "Doe", "testUser", "john@example.com", LocalDateTime.now() ,LocalDate.of(1990, 1, 1),List.of(new AddictionResponseDto(1L,"","",Severity.LOW,3)));
//
//        when(userRepository.findByEmail(username)).thenReturn(Optional.of(visitor));
//        when(userMapper.toResponseDto(visitor)).thenReturn(responseDto);
//
//        // Act
//        UserResponseDto result = userService.getUserDetails(username);
//
//        // Assert
//        assertNotNull(result);
//        assertEquals("John", result.name());
//        assertEquals("testUser", result.username());
//        verify(userRepository).findByEmail(username);
//    }

    @Test
    void shouldUpdateUserAndCaptureChanges() throws ReliefApplicationException {
        // Arrange
        String username = "existingUser";
        Visitor visitor = new Visitor();
        visitor.setUsername(username);
        visitor.setName("OldName");
        visitor.setEmail("old@example.com");
        visitor.setFamilyName("OldFamily");

        ModifiedUserDto updateRequest = new ModifiedUserDto("NewName", "NewFamily", null, null);

        when(userRepository.findByEmail(username)).thenReturn(Optional.of(visitor));

        // Act
        userService.updateUser(updateRequest, username);

        // Assert
        verify(userRepository).save(captor.capture()); // Capturing the updated user

        Visitor updatedUser = captor.getValue();
        assertEquals("NewName", updatedUser.getName());
        assertEquals("NewFamily", updatedUser.getFamilyName());
    }
}
