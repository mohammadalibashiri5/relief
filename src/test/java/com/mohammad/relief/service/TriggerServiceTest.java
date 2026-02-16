package com.mohammad.relief.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class TriggerServiceTest {
//
//    @Mock
//    private TriggerMapper triggerMapper;
//
//    @Mock
//    private TriggerRepository triggerRepository;
//
//    @Mock
//    private UserAddictionService addictionService;
//
//    @InjectMocks
//    private TriggerService triggerService;
//
//    private UserTriggerRequestDto triggerRequestDTO;
//    private TriggerResponseDto triggerResponseDTO;
//    private UserTrigger trigger;
//    private UserAddiction addiction;
//
//    @BeforeEach
//    void setUp() {
//        triggerRequestDTO = new UserTriggerRequestDto("Test UserTrigger", "Test Description");
//        triggerResponseDTO = new TriggerResponseDto(1L, "Test UserTrigger", "addiction", "dsfsfsf", 1, LocalDateTime.now());
//        trigger = new UserTrigger();
//        trigger.setId(1L);
//        trigger.setName("Test UserTrigger");
//        trigger.setDescription("Test Description");
//        trigger.setRepetitionCount(1);
//
//        addiction = new UserAddiction();
//        addiction.setId(1L);
//    }
//
//    // Test for addTrigger method
//    @Test
//    void addTrigger_ShouldThrowException_WhenTriggerRequestIsNull() {
//        assertThrows(ReliefApplicationException.class,
//                () -> triggerService.addTrigger(null, "user", 1L));
//    }
//
////    @Disabled
////    @Test
////    void addTrigger_ShouldThrowException_WhenAddictionNotFound() throws ReliefApplicationException {
////        when(addictionService.findAddictionByIdAndValidateOwnership(1L, "user")).thenReturn();
////
////        assertThrows(ReliefApplicationException.class,
////                () -> triggerService.addTrigger(triggerRequestDTO, "user", 1L));
////    }
//
//    @Test
//    void addTrigger_ShouldSaveNewTrigger_WhenTriggerDoesNotExist() throws ReliefApplicationException {
//        when(addictionService.findAddictionByIdAndValidateOwnership(1L, "user")).thenReturn(addiction);
//        when(triggerMapper.toEntity(triggerRequestDTO)).thenReturn(trigger);
//        when(triggerRepository.findByName("Test UserTrigger")).thenReturn(empty());
//        when(triggerRepository.save(trigger)).thenReturn(trigger);
//        when(triggerMapper.toDto(trigger)).thenReturn(triggerResponseDTO);
//
//        TriggerResponseDto result = triggerService.addTrigger(triggerRequestDTO, "user", 1L);
//
//        assertEquals(1, trigger.getRepetitionCount());
//        assertEquals(triggerResponseDTO, result);
//        verify(triggerRepository).save(trigger);
//    }
//
////    @Test
////    void addTrigger_ShouldIncrementCount_WhenTriggerExists() throws ReliefApplicationException {
////        String username = "testUser";
////        Long addictionId = 1L;
////
////        UserTrigger savedTrigger = new UserTrigger();
////        savedTrigger.setId(1L);
////        savedTrigger.setName("New UserTrigger");
////        savedTrigger.setRepetitionCount(1);
////        savedTrigger.setCreatedAt(LocalDateTime.now());
////
////        // Mock dependencies
////        when(addictionService.findAddictionByIdAndValidateOwnership(addictionId, username)).thenReturn(addiction);
////        when(triggerMapper.toEntity(triggerRequestDTO)).thenReturn(trigger);
////        when(triggerRepository.findByName("New UserTrigger")).thenReturn(Optional.empty()); // isTriggerExist = false
////        when(triggerRepository.save(trigger)).thenReturn(savedTrigger);
////        when(triggerMapper.toDto(savedTrigger)).thenReturn(triggerResponseDTO);
////
////        // Act
////        TriggerResponseDto result = triggerService.addTrigger(triggerRequestDTO, username, addictionId);
////
////        // Assert
////        assertNotNull(result);
////        assertEquals("New UserTrigger", result.name());
////        assertEquals(1, savedTrigger.getRepetitionCount());
////
////        verify(triggerRepository).save(trigger);
////        verify(triggerMapper).toDto(savedTrigger);
////    }
//
//    // Tests for getAllTriggersByAddiction method
//    @Test
//    void getAllTriggersByAddiction_ShouldThrowException_WhenAddictionNotFound() throws ReliefApplicationException {
//        when(addictionService.findAddictionByIdAndValidateOwnership(1L, "user")).thenReturn(null);
//
//        assertThrows(ReliefApplicationException.class,
//                () -> triggerService.getAllTriggersByAddiction("user", 1L));
//    }
//
//    @Test
//    void getAllTriggersByAddiction_ShouldReturnEmptyList_WhenNoTriggersFound() throws ReliefApplicationException {
//        when(addictionService.findAddictionByIdAndValidateOwnership(1L, "user")).thenReturn(addiction);
//        when(triggerRepository.findByUserAddiction(addiction)).thenReturn(Collections.emptyList());
//
//        List<TriggerResponseDto> result = triggerService.getAllTriggersByAddiction("user", 1L);
//
//        assertTrue(result.isEmpty());
//    }
//
//    @Test
//    void getAllTriggersByAddiction_ShouldReturnTriggerList() throws ReliefApplicationException {
//        when(addictionService.findAddictionByIdAndValidateOwnership(1L, "user")).thenReturn(addiction);
//        when(triggerRepository.findByUserAddiction(addiction)).thenReturn(List.of(trigger));
//        when(triggerMapper.toDto(trigger)).thenReturn(triggerResponseDTO);
//
//        List<TriggerResponseDto> result = triggerService.getAllTriggersByAddiction("user", 1L);
//
//        assertEquals(1, result.size());
//        assertEquals(triggerResponseDTO, result.get(0));
//    }
//
//    // Tests for updateTrigger method
//    @Test
//    void updateTrigger_ShouldThrowException_WhenTriggerNotFound() {
//        when(triggerRepository.findById(1L)).thenReturn(empty());
//
//        assertThrows(ReliefApplicationException.class,
//                () -> triggerService.updateTrigger(triggerRequestDTO, 1L));
//    }
//
//    @Test
//    void updateTrigger_ShouldThrowException_WhenTriggerRequestIsNull() {
//        assertThrows(ReliefApplicationException.class,
//                () -> triggerService.updateTrigger(null, 1L));
//    }
//
//    @Test
//    void updateTrigger_ShouldUpdateName_WhenNameIsDifferent() throws ReliefApplicationException {
//        when(triggerRepository.findById(1L)).thenReturn(Optional.of(trigger));
//        when(triggerRepository.save(trigger)).thenReturn(trigger);
//        when(triggerMapper.toDto(trigger)).thenReturn(triggerResponseDTO);
//
//        UserTriggerRequestDto updateDTO = new UserTriggerRequestDto("New Name", null);
//        TriggerResponseDto result = triggerService.updateTrigger(updateDTO, 1L);
//
//        assertEquals("New Name", trigger.getName());
//        assertEquals(triggerResponseDTO, result);
//        verify(triggerRepository).save(trigger);
//    }
//
//    @Test
//    void updateTrigger_ShouldUpdateDescription_WhenDescriptionIsDifferent() throws ReliefApplicationException {
//        when(triggerRepository.findById(1L)).thenReturn(Optional.of(trigger));
//        when(triggerRepository.save(trigger)).thenReturn(trigger);
//        when(triggerMapper.toDto(trigger)).thenReturn(triggerResponseDTO);
//
//        UserTriggerRequestDto updateDTO = new UserTriggerRequestDto(null, "New Description");
//        TriggerResponseDto result = triggerService.updateTrigger(updateDTO, 1L);
//
//        assertEquals("New Description", trigger.getDescription());
//        assertEquals(triggerResponseDTO, result);
//        verify(triggerRepository).save(trigger);
//    }
//
////    @Test
////    void updateTrigger_ShouldNotSave_WhenNoChanges() throws ReliefApplicationException {
////        when(triggerRepository.findById(1L)).thenReturn(Optional.of(trigger));
////        when(triggerMapper.toDto(trigger)).thenReturn(triggerResponseDTO);
////
////        UserTriggerRequestDto updateDTO = new UserTriggerRequestDto(trigger.getName(), trigger.getDescription());
////        TriggerResponseDto result = triggerService.updateTrigger(updateDTO, 1L);
////
////        verify(triggerRepository, never()).save(any());
////        assertEquals(triggerResponseDTO, result);
////    }
//
//    // Tests for deleteTrigger method
////    @Test
////    void deleteTrigger_ShouldDeleteTrigger_WhenTriggerExists() throws ReliefApplicationException {
////        when(triggerRepository.findByName("Test UserTrigger")).thenReturn(Optional.of(trigger));
////
////        triggerService.deleteTrigger("Test UserTrigger");
////
////        verify(triggerRepository).delete(trigger);
////    }
////
////    @Test
////    void deleteTrigger_ShouldThrowException_WhenTriggerNotFound() {
////        when(triggerRepository.findByName("Test UserTrigger")).thenReturn(Optional.empty());
////
////        assertThrows(ReliefApplicationException.class,
////                () -> triggerService.deleteTrigger("Test UserTrigger"));
////    }
//
//    // Test for isTriggerExist helper method
//    @Test
//    void isTriggerExist_ShouldReturnTrueAndIncrementCount_WhenTriggerExists() {
//        UserTrigger existingTrigger = new UserTrigger();
//        existingTrigger.setRepetitionCount(2);
//
//        when(triggerRepository.findByName("Existing UserTrigger")).thenReturn(Optional.of(existingTrigger));
//        when(triggerRepository.save(existingTrigger)).thenReturn(existingTrigger);
//
//        boolean result = triggerService.isTriggerExist("Existing UserTrigger");
//
//        assertTrue(result);
//        assertEquals(3, existingTrigger.getRepetitionCount());
//        verify(triggerRepository).save(existingTrigger);
//    }
//
//    @Test
//    void isTriggerExist_ShouldReturnFalse_WhenTriggerDoesNotExist() {
//        when(triggerRepository.findByName("Non-existing UserTrigger")).thenReturn(empty());
//
//        boolean result = triggerService.isTriggerExist("Non-existing UserTrigger");
//
//        assertFalse(result);
//        verify(triggerRepository, never()).save(any());
//    }
}
