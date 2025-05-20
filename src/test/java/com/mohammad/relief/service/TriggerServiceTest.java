package com.mohammad.relief.service;

import com.mohammad.relief.data.dto.request.TriggerRequestDTO;
import com.mohammad.relief.data.dto.response.TriggerResponseDTO;
import com.mohammad.relief.data.entity.Addiction;
import com.mohammad.relief.data.entity.Trigger;
import com.mohammad.relief.exception.ReliefApplicationException;
import com.mohammad.relief.mapper.TriggerMapper;
import com.mohammad.relief.repository.TriggerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TriggerServiceTest {
    @Mock
    private TriggerRepository triggerRepository;

    @Mock
    private TriggerMapper triggerMapper;

    @Mock
    private UserAddictionService userAddictionService;

    @InjectMocks
    private TriggerService triggerService;

    private Addiction addiction;

    @BeforeEach
    void setUp() {
        addiction = new Addiction();
        addiction.setName("addictionName");
    }

    @Test
    void shouldThrowExceptionWhenTriggerIsNull() {
        ReliefApplicationException reliefApplicationException = assertThrows(ReliefApplicationException.class, () -> {
            triggerService.addTrigger(null, 10L);
        });
        assertEquals("Trigger is null", reliefApplicationException.getMessage());
    }

//    @Test
//    void shouldThrowExceptionWhenAddictionNameIsNullOrEmpty() {
//        String addictionName = null;
//        TriggerRequestDTO requestDTO = new TriggerRequestDTO("addictionName", "addictionDescription");
//        ReliefApplicationException reliefApplicationException = assertThrows(ReliefApplicationException.class, () -> {
//            triggerService.addTrigger(requestDTO, addictionName);
//        });
//        assertEquals("Addiction not found", reliefApplicationException.getMessage());
//    }

   //@Test
   //void shouldSaveTheTriggerWhenAddictionNameIsFoundInDatabase() {
   //    // GIVEN
   //    String addictionName = "addictionName";
   //    Addiction newAddiction = new Addiction();
   //    newAddiction.setName(addictionName);
   //    newAddiction.setDescription("addictionDescription");

   //    TriggerRequestDTO requestDTO = new TriggerRequestDTO("triggerName", "triggerDescription");
   //    Trigger trigger = new Trigger();
   //    trigger.setName("triggerName");
   //    trigger.setAddiction(newAddiction);
   //    trigger.setRepetitionCount(1);
   //    Long id = 1L;

   //    TriggerResponseDTO responseDTO = new TriggerResponseDTO(id, "triggerName", "triggerName", "description", 1);

   //    // WHEN
   //    when(userAddictionService.getAddictionByName(addictionName)).thenReturn(newAddiction);
   //    when(triggerMapper.toEntity(requestDTO)).thenReturn(trigger);
   //    when(triggerRepository.save(any())).thenReturn(trigger);
   //    when(triggerMapper.toDto(trigger)).thenReturn(responseDTO);

   //    // THEN
   //    assertDoesNotThrow(() -> triggerService.addTrigger(requestDTO, addictionName));
   //    verify(triggerRepository, times(1)).save(any());
   //}

//   @ParameterizedTest
//   @CsvSource({
//           "existingTrigger, 1, 2, true",   // Existing trigger: repetitionCount increases
//           "newTrigger, 0, 1, false"        // New trigger: repetitionCount starts at 1
//   })
//   void shouldUpdateOrSaveTriggerCorrectly(String triggerName, int initialCount, int expectedCount, boolean triggerExists) throws ReliefApplicationException {
//       // Given
//       TriggerRequestDTO requestDTO = new TriggerRequestDTO("addictionName", triggerName);
//       Trigger trigger = new Trigger();
//       trigger.setName(triggerName);
//       trigger.setAddiction(addiction);
//       trigger.setRepetitionCount(initialCount);

//       TriggerResponseDTO responseDTO = new TriggerResponseDTO(1L, triggerName, addiction.getName(),"machin" , expectedCount);

//       // WHEN
//       when(userAddictionService.getAddictionByName("addictionName")).thenReturn(addiction);
//       when(triggerMapper.toEntity(requestDTO)).thenReturn(trigger);
//       when(triggerMapper.toDto(any())).thenReturn(responseDTO);

//       if (triggerExists) {
//           // Mock existing trigger case
//           Trigger existingTrigger = new Trigger();
//           existingTrigger.setName(triggerName);
//           existingTrigger.setRepetitionCount(initialCount);

//           when(triggerRepository.findByName(triggerName)).thenReturn(Optional.of(existingTrigger));
//           when(triggerRepository.save(existingTrigger)).thenReturn(existingTrigger);
//       } else {
//           // Mock new trigger case
//           when(triggerRepository.findByName(triggerName)).thenReturn(Optional.empty());
//           when(triggerRepository.save(trigger)).thenReturn(trigger);
//       }

//       // When
//       TriggerResponseDTO result = triggerService.addTrigger(requestDTO, "addictionName");

//       // Then
//       assertEquals(triggerName, result.name());
//       assertEquals(expectedCount, result.repetitionCount());

//       if (triggerExists) {
//           verify(triggerRepository, times(1)).save(any(Trigger.class));
//       } else {
//           verify(triggerRepository, times(1)).save(trigger);
//       }
//   }
    @Test
    void shouldReturnTriggerWhenIdExists() {
        // Given
        Long triggerId = 1L;
        Trigger trigger = new Trigger();
        trigger.setId(triggerId);
        trigger.setName("Sample Trigger");

        when(triggerRepository.findById(triggerId)).thenReturn(Optional.of(trigger));

        // When
        Optional<Trigger> result = triggerService.getById(triggerId);

        // Then
        assertTrue(result.isPresent());
        assertEquals(triggerId, result.get().getId());
        assertEquals("Sample Trigger", result.get().getName());
        verify(triggerRepository, times(1)).findById(triggerId);
    }

    @Test
    void shouldReturnEmptyOptionalWhenIdDoesNotExist() {
        // Given
        Long nonExistentId = 99L;

        when(triggerRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        // When
        Optional<Trigger> result = triggerService.getById(nonExistentId);

        // Then
        assertTrue(result.isEmpty());
        verify(triggerRepository, times(1)).findById(nonExistentId);
    }
}
//
//@Test
//void getByName() {
//}
//
//@Test
//void updateTrigger() {
//}
//
//@Test
//void findAll() {
//}
//
//@Test
//void deleteTrigger() {
//}
//}