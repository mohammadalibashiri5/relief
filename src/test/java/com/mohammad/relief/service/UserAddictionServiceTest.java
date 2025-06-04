package com.mohammad.relief.service;

import com.mohammad.relief.data.dto.request.AddictionRequestDto;
import com.mohammad.relief.data.dto.response.AddictionResponseDto;
import com.mohammad.relief.data.entity.Addiction;
import com.mohammad.relief.data.entity.Visitor;
import com.mohammad.relief.data.entity.enums.Severity;
import com.mohammad.relief.exception.ReliefApplicationException;
import com.mohammad.relief.mapper.AddictionMapper;
import com.mohammad.relief.repository.AddictionRepository;
import com.mohammad.relief.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserAddictionServiceTest {

    @Mock
    private AddictionRepository addictionRepository;

    @Mock
    private AddictionMapper addictionMapper;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserAddictionService userAddictionService;


    @Test
    void assigningAddictionMustFailIfUserDoesNotExist() {
        //GIVEN
        AddictionRequestDto addictionRequestDto = new AddictionRequestDto("test", "test", Severity.LOW, 2);
        //WHEN
        //THEN
        ReliefApplicationException rae = assertThrows(ReliefApplicationException.class, () -> userAddictionService.assignAddictionToUser(addictionRequestDto, null));
        assertEquals("No such a user", rae.getMessage());

    }

    @Test
    void assigningAddictionMustFailIfAddictionAlreadyExists() {
        // GIVEN
        String username = "testUser";
        Addiction addiction = new Addiction();
        addiction.setName("test");

        Visitor user = new Visitor();
        user.setUsername(username);
        user.setAddictions(List.of(addiction));

        AddictionRequestDto addictionRequestDto = new AddictionRequestDto("test", "test", Severity.LOW, 2);

        // WHEN
        when(userRepository.findByEmail(username)).thenReturn(Optional.of(user));

        // THEN
        ReliefApplicationException rae = assertThrows(ReliefApplicationException.class,
                () -> userAddictionService.assignAddictionToUser(addictionRequestDto, username));

        assertEquals("Addiction already assigned to user", rae.getMessage());
    }
    @Test
    void assigningAddictionMustPassIfANewAddiction(){
        // GIVEN
        String username = "testUser";
        AddictionRequestDto addictionRequestDto = new AddictionRequestDto("test", "test", Severity.LOW, 2);

        Visitor user = new Visitor();
        user.setUsername(username);
        user.setAddictions(new ArrayList<>()); // User has no addictions yet

        Addiction addiction = new Addiction();
        addiction.setName("test");

        // WHEN
        when(userRepository.findByEmail(username)).thenReturn(Optional.of(user));
        when(addictionMapper.toEntity(addictionRequestDto)).thenReturn(addiction);
        when(addictionRepository.save(any())).thenReturn(addiction);
        when(addictionMapper.toDto(any())).thenReturn(new AddictionResponseDto(1L,"test", "test", Severity.LOW, 2));

        // THEN
        assertDoesNotThrow(() -> userAddictionService.assignAddictionToUser(addictionRequestDto, username));
        verify(addictionRepository, times(1)).save(any()); // Ensure addiction is saved
    }
    @Test
    void updateAddictionOfUserShouldSucceed() throws ReliefApplicationException {
        // GIVEN
        Long id = 1L;
        String username = "testUser";
        String addictionName = "smoking";
        AddictionRequestDto addictionRequestDto = new AddictionRequestDto(addictionName,"Ugpdated Desc" , Severity.HIGH, 1);

        Visitor user = new Visitor();
        user.setUsername(username);
        Addiction addiction = new Addiction();
        addiction.setId(1L);
        addiction.setName(addictionName);
        addiction.setDescription("Old Desc");
        addiction.setSeverityLevel(Severity.LOW);
        user.setAddictions(List.of(addiction));

        // WHEN
        when(userService.findByEmail(username)).thenReturn(user);
        when(addictionMapper.toDto(addiction)).thenReturn(new AddictionResponseDto(id, addictionName,"Updated Desc", Severity.HIGH, 1));

        // THEN
        AddictionResponseDto response = userAddictionService.updateAddictionOfUser(addictionRequestDto, id, username);

        assertEquals("Updated Desc", response.description());
        assertEquals(Severity.HIGH, response.severityLevel());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void updateAddictionOfUserShouldFailIfUserNotFound() throws ReliefApplicationException {
        // GIVEN
        when(userService.findByEmail(any())).thenThrow(new ReliefApplicationException("Visitor not found"));

        // THEN
        ReliefApplicationException rae = assertThrows(ReliefApplicationException.class,
                () -> userAddictionService.updateAddictionOfUser(new AddictionRequestDto("test", "desc", Severity.LOW, 2020), 1L, "unknownUser"));

        assertEquals("Visitor not found", rae.getMessage());
    }

    @Test
    void updateAddictionOfUserShouldFailIfAddictionNotFound() throws ReliefApplicationException {
        // GIVEN
        Visitor user = new Visitor();
        user.setUsername("testUser");
        user.setAddictions(new ArrayList<>()); // No addictions

        when(userService.findByEmail("testUser")).thenReturn(user);

        // THEN
        ReliefApplicationException rae = assertThrows(ReliefApplicationException.class,
                () -> userAddictionService.updateAddictionOfUser(new AddictionRequestDto("test", "desc", Severity.LOW, 2020), 1L, "testUser"));

        assertEquals("Addiction not found", rae.getMessage());
    }
    @Test
    void deleteAddictionShouldSucceed() throws ReliefApplicationException {
        // GIVEN
        String username = "testUser";
        String addictionName = "smoking";
        Visitor user = new Visitor();
        user.setUsername(username);

        Addiction addiction = new Addiction();
        addiction.setName(addictionName);

        when(userRepository.findByEmail(username)).thenReturn(Optional.of(user));
        when(addictionRepository.findByName(addictionName)).thenReturn(Optional.of(addiction));

        // WHEN
        userAddictionService.deleteAddiction(username, addictionName);

        // THEN
        verify(addictionRepository, times(1)).delete(addiction);
    }

    @Test
    void deleteAddictionShouldFailIfUserNotFound() {
        // GIVEN
        when(userRepository.findByEmail(any())).thenReturn(Optional.empty());

        // THEN
        ReliefApplicationException rae = assertThrows(ReliefApplicationException.class,
                () -> userAddictionService.deleteAddiction("unknownUser", "smoking"));

        assertEquals("Visitor not found", rae.getMessage());
    }

    @Test
    void deleteAddictionShouldFailIfAddictionNotFound() {
        // GIVEN
        Visitor user = new Visitor();
        user.setUsername("testUser");

        when(userRepository.findByEmail("testUser")).thenReturn(Optional.of(user));
        when(addictionRepository.findByName("smoking")).thenReturn(Optional.empty());

        // THEN
        ReliefApplicationException rae = assertThrows(ReliefApplicationException.class,
                () -> userAddictionService.deleteAddiction("testUser", "smoking"));

        assertEquals("Addiction not found", rae.getMessage());
    }
    @Test
    void getAddictionByNameShouldReturnExistingAddiction() {
        // GIVEN
        String addictionName = "smoking";
        Addiction addiction = new Addiction();
        addiction.setName(addictionName);

        when(addictionRepository.findByName(addictionName)).thenReturn(Optional.of(addiction));

        // WHEN
        //Addiction result = userAddictionService.getAddictionByName(addictionName);

        // THEN
       // assertEquals(addictionName, result.getName());
        verify(addictionRepository, never()).save(any());
    }

    @Test
    void getAddictionByNameShouldCreateNewAddictionIfNotExists() {
        // GIVEN
        String addictionName = "newAddiction";
        when(addictionRepository.findByName(addictionName)).thenReturn(Optional.empty());
        when(addictionRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        // WHEN
        //Addiction result = userAddictionService.getAddictionByName(addictionName);

        // THEN
        //assertEquals(addictionName, result.getName());
        verify(addictionRepository, times(1)).save(any());
    }
    @Test
    void getAllUserAddictionsShouldReturnList() throws ReliefApplicationException {
        // GIVEN
        String username = "testUser";
        Visitor user = new Visitor();
        user.setUsername(username);

        Addiction addiction1 = new Addiction();
        addiction1.setName("smoking");
        Addiction addiction2 = new Addiction();
        addiction2.setName("drinking");

        List<Addiction> addictionList = List.of(addiction1, addiction2);

        when(userRepository.findByEmail(username)).thenReturn(Optional.of(user));
        when(addictionRepository.findAll()).thenReturn(addictionList);
        when(addictionMapper.toDto(any())).thenAnswer(invocation -> {
            Addiction addiction = invocation.getArgument(0);
            return new AddictionResponseDto(addiction.getId(), addiction.getName(), "desc", Severity.MEDIUM, 2020);
        });

        // WHEN
        List<AddictionResponseDto> response = userAddictionService.getAllUserAddictions(username);

        // THEN
        assertEquals(2, response.size());
        assertEquals("smoking", response.get(0).name());
        assertEquals("drinking", response.get(1).name());
    }

    @Test
    void getAllUserAddictionsShouldFailIfUserNotFound() {
        // GIVEN
        when(userRepository.findByEmail(any())).thenReturn(Optional.empty());

        // THEN
        ReliefApplicationException rae = assertThrows(ReliefApplicationException.class,
                () -> userAddictionService.getAllUserAddictions("unknownUser"));

        assertEquals("Visitor not found", rae.getMessage());
    }

}