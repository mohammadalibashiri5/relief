package com.mohammad.relief.service;

import com.mohammad.relief.data.dto.request.TriggerRequestDTO;
import com.mohammad.relief.data.dto.response.TriggerResponseDTO;
import com.mohammad.relief.data.entity.Addiction;
import com.mohammad.relief.data.entity.Trigger;
import com.mohammad.relief.data.entity.Visitor;
import com.mohammad.relief.exception.ReliefApplicationException;
import com.mohammad.relief.mapper.TriggerMapper;
import com.mohammad.relief.repository.TriggerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TriggerServiceTest {

    @Mock
    private TriggerMapper triggerMapper;

    @Mock
    private TriggerRepository triggerRepository;

    @Mock
    private UserAddictionService addictionService;

    @InjectMocks
    private TriggerService triggerService;

    private TriggerRequestDTO triggerRequestDTO;
    private TriggerResponseDTO triggerResponseDTO;
    private Trigger trigger;
    private Addiction addiction;

    @BeforeEach
    void setUp() {
        triggerRequestDTO = new TriggerRequestDTO("Test Trigger", "Test Description");
        triggerResponseDTO = new TriggerResponseDTO(1L, "Test Trigger", "addiction", "dsfsfsf", 1, LocalDateTime.now());
        trigger = new Trigger();
        trigger.setId(1L);
        trigger.setName("Test Trigger");
        trigger.setDescription("Test Description");
        trigger.setRepetitionCount(1);

        addiction = new Addiction();
        addiction.setId(1L);
    }

    // Test for addTrigger method
    @Test
    void addTrigger_ShouldThrowException_WhenTriggerRequestIsNull() {
        assertThrows(ReliefApplicationException.class,
                () -> triggerService.addTrigger(null, "user", 1L));
    }

    @Test
    void addTrigger_ShouldThrowException_WhenAddictionNotFound() throws ReliefApplicationException {
        when(addictionService.getAddictionByIdAndUser(1L, "user")).thenReturn(null);

        assertThrows(ReliefApplicationException.class,
                () -> triggerService.addTrigger(triggerRequestDTO, "user", 1L));
    }

    @Test
    void addTrigger_ShouldSaveNewTrigger_WhenTriggerDoesNotExist() throws ReliefApplicationException {
        when(addictionService.getAddictionByIdAndUser(1L, "user")).thenReturn(addiction);
        when(triggerMapper.toEntity(triggerRequestDTO)).thenReturn(trigger);
        when(triggerRepository.findByName("Test Trigger")).thenReturn(Optional.empty());
        when(triggerRepository.save(trigger)).thenReturn(trigger);
        when(triggerMapper.toDto(trigger)).thenReturn(triggerResponseDTO);

        TriggerResponseDTO result = triggerService.addTrigger(triggerRequestDTO, "user", 1L);

        assertEquals(1, trigger.getRepetitionCount());
        assertEquals(triggerResponseDTO, result);
        verify(triggerRepository).save(trigger);
    }

//    @Test
//    void addTrigger_ShouldIncrementCount_WhenTriggerExists() throws ReliefApplicationException {
//        String username = "testUser";
//        Long addictionId = 1L;
//
//        Trigger savedTrigger = new Trigger();
//        savedTrigger.setId(1L);
//        savedTrigger.setName("New Trigger");
//        savedTrigger.setRepetitionCount(1);
//        savedTrigger.setCreatedAt(LocalDateTime.now());
//
//        // Mock dependencies
//        when(addictionService.getAddictionByIdAndUser(addictionId, username)).thenReturn(addiction);
//        when(triggerMapper.toEntity(triggerRequestDTO)).thenReturn(trigger);
//        when(triggerRepository.findByName("New Trigger")).thenReturn(Optional.empty()); // isTriggerExist = false
//        when(triggerRepository.save(trigger)).thenReturn(savedTrigger);
//        when(triggerMapper.toDto(savedTrigger)).thenReturn(triggerResponseDTO);
//
//        // Act
//        TriggerResponseDTO result = triggerService.addTrigger(triggerRequestDTO, username, addictionId);
//
//        // Assert
//        assertNotNull(result);
//        assertEquals("New Trigger", result.name());
//        assertEquals(1, savedTrigger.getRepetitionCount());
//
//        verify(triggerRepository).save(trigger);
//        verify(triggerMapper).toDto(savedTrigger);
//    }

    // Tests for getAllTriggersByAddiction method
    @Test
    void getAllTriggersByAddiction_ShouldThrowException_WhenAddictionNotFound() throws ReliefApplicationException {
        when(addictionService.getAddictionByIdAndUser(1L, "user")).thenReturn(null);

        assertThrows(ReliefApplicationException.class,
                () -> triggerService.getAllTriggersByAddiction("user", 1L));
    }

    @Test
    void getAllTriggersByAddiction_ShouldReturnEmptyList_WhenNoTriggersFound() throws ReliefApplicationException {
        when(addictionService.getAddictionByIdAndUser(1L, "user")).thenReturn(addiction);
        when(triggerRepository.findByAddiction(addiction)).thenReturn(Collections.emptyList());

        List<TriggerResponseDTO> result = triggerService.getAllTriggersByAddiction("user", 1L);

        assertTrue(result.isEmpty());
    }

    @Test
    void getAllTriggersByAddiction_ShouldReturnTriggerList() throws ReliefApplicationException {
        when(addictionService.getAddictionByIdAndUser(1L, "user")).thenReturn(addiction);
        when(triggerRepository.findByAddiction(addiction)).thenReturn(List.of(trigger));
        when(triggerMapper.toDto(trigger)).thenReturn(triggerResponseDTO);

        List<TriggerResponseDTO> result = triggerService.getAllTriggersByAddiction("user", 1L);

        assertEquals(1, result.size());
        assertEquals(triggerResponseDTO, result.get(0));
    }

    // Tests for updateTrigger method
    @Test
    void updateTrigger_ShouldThrowException_WhenTriggerNotFound() {
        when(triggerRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ReliefApplicationException.class,
                () -> triggerService.updateTrigger(triggerRequestDTO, 1L));
    }

    @Test
    void updateTrigger_ShouldThrowException_WhenTriggerRequestIsNull() {
        assertThrows(ReliefApplicationException.class,
                () -> triggerService.updateTrigger(null, 1L));
    }

    @Test
    void updateTrigger_ShouldUpdateName_WhenNameIsDifferent() throws ReliefApplicationException {
        when(triggerRepository.findById(1L)).thenReturn(Optional.of(trigger));
        when(triggerRepository.save(trigger)).thenReturn(trigger);
        when(triggerMapper.toDto(trigger)).thenReturn(triggerResponseDTO);

        TriggerRequestDTO updateDTO = new TriggerRequestDTO("New Name", null);
        TriggerResponseDTO result = triggerService.updateTrigger(updateDTO, 1L);

        assertEquals("New Name", trigger.getName());
        assertEquals(triggerResponseDTO, result);
        verify(triggerRepository).save(trigger);
    }

    @Test
    void updateTrigger_ShouldUpdateDescription_WhenDescriptionIsDifferent() throws ReliefApplicationException {
        when(triggerRepository.findById(1L)).thenReturn(Optional.of(trigger));
        when(triggerRepository.save(trigger)).thenReturn(trigger);
        when(triggerMapper.toDto(trigger)).thenReturn(triggerResponseDTO);

        TriggerRequestDTO updateDTO = new TriggerRequestDTO(null, "New Description");
        TriggerResponseDTO result = triggerService.updateTrigger(updateDTO, 1L);

        assertEquals("New Description", trigger.getDescription());
        assertEquals(triggerResponseDTO, result);
        verify(triggerRepository).save(trigger);
    }

//    @Test
//    void updateTrigger_ShouldNotSave_WhenNoChanges() throws ReliefApplicationException {
//        when(triggerRepository.findById(1L)).thenReturn(Optional.of(trigger));
//        when(triggerMapper.toDto(trigger)).thenReturn(triggerResponseDTO);
//
//        TriggerRequestDTO updateDTO = new TriggerRequestDTO(trigger.getName(), trigger.getDescription());
//        TriggerResponseDTO result = triggerService.updateTrigger(updateDTO, 1L);
//
//        verify(triggerRepository, never()).save(any());
//        assertEquals(triggerResponseDTO, result);
//    }

    // Tests for deleteTrigger method
//    @Test
//    void deleteTrigger_ShouldDeleteTrigger_WhenTriggerExists() throws ReliefApplicationException {
//        when(triggerRepository.findByName("Test Trigger")).thenReturn(Optional.of(trigger));
//
//        triggerService.deleteTrigger("Test Trigger");
//
//        verify(triggerRepository).delete(trigger);
//    }
//
//    @Test
//    void deleteTrigger_ShouldThrowException_WhenTriggerNotFound() {
//        when(triggerRepository.findByName("Test Trigger")).thenReturn(Optional.empty());
//
//        assertThrows(ReliefApplicationException.class,
//                () -> triggerService.deleteTrigger("Test Trigger"));
//    }

    // Test for isTriggerExist helper method
    @Test
    void isTriggerExist_ShouldReturnTrueAndIncrementCount_WhenTriggerExists() {
        Trigger existingTrigger = new Trigger();
        existingTrigger.setRepetitionCount(2);

        when(triggerRepository.findByName("Existing Trigger")).thenReturn(Optional.of(existingTrigger));
        when(triggerRepository.save(existingTrigger)).thenReturn(existingTrigger);

        boolean result = triggerService.isTriggerExist("Existing Trigger");

        assertTrue(result);
        assertEquals(3, existingTrigger.getRepetitionCount());
        verify(triggerRepository).save(existingTrigger);
    }

    @Test
    void isTriggerExist_ShouldReturnFalse_WhenTriggerDoesNotExist() {
        when(triggerRepository.findByName("Non-existing Trigger")).thenReturn(Optional.empty());

        boolean result = triggerService.isTriggerExist("Non-existing Trigger");

        assertFalse(result);
        verify(triggerRepository, never()).save(any());
    }
}
