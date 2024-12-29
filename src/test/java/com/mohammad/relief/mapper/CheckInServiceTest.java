package com.mohammad.relief.mapper;

/*import com.mohammad.relief.data.entity.CheckIn;
import com.mohammad.relief.data.entity.User;
import com.mohammad.relief.exception.ReliefApplicationException;
import com.mohammad.relief.repository.CheckInRepository;
import com.mohammad.relief.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
@Disabled
class CheckInServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private CheckInRepository checkInRepository;

    @InjectMocks
    private CheckInService checkInService;

    private User user;
    private LocalDate today;
    private LocalDate yesterday;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(UUID.randomUUID());
        user.setUsername("testuser");
        user.setStreak(5);

        today = LocalDate.now();
        yesterday = today.minusDays(1);
    }

    @Test
    void performCheckIn_userNotFound_throwsException() {
        Mockito.when(userRepository.findByUsername("testuser"))
                .thenReturn(Optional.empty());

        assertThrows(ReliefApplicationException.class,
                () -> checkInService.performCheckIn("testuser"));
    }
    @Test
    void performCheckIn_existingCheckIn_returnsExistingCheckIn() throws ReliefApplicationException {
        CheckIn existingCheckIn = new CheckIn(user, today, "completed");

        Mockito.when(userRepository.findByUsername("testuser"))
                .thenReturn(Optional.of(user));
        Mockito.when(checkInRepository.findByUserIdAndDate(user.getId(), today))
                .thenReturn(Optional.of(existingCheckIn));

        CheckIn result = checkInService.performCheckIn("testuser");

        assertEquals(existingCheckIn, result);
        Mockito.verify(checkInRepository, Mockito.never()).save(Mockito.any());
    }
    @Test
    void performCheckIn_missedCheckIn_updatesStatus() throws ReliefApplicationException {
        CheckIn yesterdayCheckIn = new CheckIn(user, yesterday, "pending");

        Mockito.when(userRepository.findByUsername("testuser"))
                .thenReturn(Optional.of(user));
        Mockito.when(checkInRepository.findByUserIdAndDate(user.getId(), today))
                .thenReturn(Optional.empty());
        Mockito.when(checkInRepository.findByUserIdAndDate(user.getId(), yesterday))
                .thenReturn(Optional.of(yesterdayCheckIn));
        Mockito.when(checkInRepository.existsByUserIdAndDate(user.getId(), yesterday))
                .thenReturn(true);

        checkInService.performCheckIn("testuser");

        assertEquals("missed", yesterdayCheckIn.getStatus());
        Mockito.verify(checkInRepository).save(yesterdayCheckIn);
    }
    @Test
    void performCheckIn_incrementStreak_success() throws ReliefApplicationException {
        Mockito.when(userRepository.findByUsername("testuser"))
                .thenReturn(Optional.of(user));
        Mockito.when(checkInRepository.findByUserIdAndDate(user.getId(), today))
                .thenReturn(Optional.empty());
        Mockito.when(checkInRepository.existsByUserIdAndDate(user.getId(), yesterday))
                .thenReturn(true);

        checkInService.performCheckIn("testuser");

        assertEquals(6, user.getStreak());
        Mockito.verify(userRepository).save(user);
    }
    @Test
    void performCheckIn_resetStreak_success() throws ReliefApplicationException {
        Mockito.when(userRepository.findByUsername("testuser"))
                .thenReturn(Optional.of(user));
        Mockito.when(checkInRepository.findByUserIdAndDate(user.getId(), today))
                .thenReturn(Optional.empty());
        Mockito.when(checkInRepository.existsByUserIdAndDate(user.getId(), yesterday))
                .thenReturn(false);

        checkInService.performCheckIn("testuser");

        assertEquals(1, user.getStreak());
        Mockito.verify(userRepository).save(user);
    }
    @Test
    void performCheckIn_success_createsNewCheckIn() throws ReliefApplicationException {
        Mockito.when(userRepository.findByUsername("testuser"))
                .thenReturn(Optional.of(user));
        Mockito.when(checkInRepository.findByUserIdAndDate(user.getId(), today))
                .thenReturn(Optional.empty());
        Mockito.when(checkInRepository.existsByUserIdAndDate(user.getId(), yesterday))
                .thenReturn(true);
        Mockito.when(checkInRepository.save(Mockito.any(CheckIn.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        CheckIn result = checkInService.performCheckIn("testuser");

        assertEquals(today, result.getDate());
        assertEquals("completed", result.getStatus());
        assertEquals(user, result.getUser());
    }





}*/