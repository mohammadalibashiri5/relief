package com.mohammad.relief.service;

import com.mohammad.relief.features.userManagement.data.UserRepository;
import com.mohammad.relief.features.userManagement.domain.CustomUserDetailsService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CustomUserDetailsServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;

//    @Test
//    void shouldLoadUserByEmail_whenUserExists() {
//        // Arrange
//        Visitor visitor = new Visitor();
//        visitor.setUsername("john");
//        visitor.setEmail("john@example.com");
//        visitor.setPassword("hashed-password");
//        visitor.setRole("USER");
//
//        when(userRepository.findByEmail("john@example.com"))
//                .thenReturn(Optional.of(visitor));
//
//        // Act
//        UserDetails userDetails = customUserDetailsService.loadUserByUsername("john@example.com");
//
//        // Assert
//        assertEquals("john@example.com", userDetails.getUsername());
//        assertEquals("hashed-password", userDetails.getPassword());
//        assertTrue(userDetails.getAuthorities().stream()
//                .anyMatch(auth -> auth.getAuthority().equals("ROLE_" + visitor.getRole())));
//    }

//    @Test
//    void shouldThrowException_whenUserNotFound() {
//        // Arrange
//        when(userRepository.findByEmail("missing@example.com"))
//                .thenReturn(Optional.empty());
//
//        // Act & Assert
//        assertThrows(UsernameNotFoundException.class, () ->
//                customUserDetailsService.loadUserByUsername("missing@example.com"));
//    }



}