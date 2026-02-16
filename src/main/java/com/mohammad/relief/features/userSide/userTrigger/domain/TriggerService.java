package com.mohammad.relief.features.userSide.userTrigger.domain;

import com.mohammad.relief.features.userSide.userTrigger.api.TriggerMapper;
import com.mohammad.relief.features.userSide.userTrigger.data.TriggerRepository;
import com.mohammad.relief.features.userSide.userAddiction.domain.UserAddictionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TriggerService {

    private final TriggerMapper triggerMapper;
    private final TriggerRepository triggerRepository;
    private final UserAddictionService addictionService;


//    public TriggerResponseDto addTrigger(
//            UserTriggerRequestDto triggerRequestDTO,
//            String username,
//            Long addictionId) throws ReliefApplicationException {
//
//        if (triggerRequestDTO == null) {
//            throw new ReliefApplicationException("UserTrigger is null");
//        }
//
//        UserAddiction addiction = addictionService.findAddictionByIdAndValidateOwnership(addictionId, username);
//
//        UserTrigger trigger = triggerMapper.toEntity(triggerRequestDTO);
//        trigger.setUserAddiction(addiction);
//        trigger.setCreatedAt(LocalDateTime.now());
//
//        if(!isTriggerExist(trigger.getName())) {
//            trigger.setRepetitionCount(1);
//        }
//
//        UserTrigger savedTrigger = triggerRepository.save(trigger);
//        return triggerMapper.toDto(savedTrigger);
//    }
//
//    public List<TriggerResponseDto> getAllTriggersByAddiction(String username, Long addictionId) throws ReliefApplicationException {
//        UserAddiction addiction = addictionService.findAddictionByIdAndValidateOwnership(addictionId, username);
//
//        if (addiction == null) {
//            throw new ReliefApplicationException("Addiction not found");
//        }
//        List<UserTrigger> triggers = triggerRepository.findByUserAddiction(addiction);
//        return triggers
//                .stream()
//                .map(triggerMapper::toDto)
//                .toList();
//    }
//
//    public TriggerResponseDto updateTrigger(UserTriggerRequestDto triggerRequestDTO, Long id) throws ReliefApplicationException {
//        Optional<UserTrigger> foundTrigger = triggerRepository.findById(id);
//        boolean isUpdated = false;
//        if (foundTrigger.isEmpty()) {
//            throw new ReliefApplicationException("UserTrigger not found");
//        }
//        if (triggerRequestDTO == null) {
//            throw new ReliefApplicationException("UserTriggerRequestDto is null");
//        }
//        UserTrigger trigger = foundTrigger.get();
//
//        if (triggerRequestDTO.name() != null && !triggerRequestDTO.name().equals(trigger.getName())) {
//            trigger.setName(triggerRequestDTO.name());
//            isUpdated = true;
//        }
//        if (triggerRequestDTO.description() != null && !triggerRequestDTO.description().equals(trigger.getDescription())) {
//            trigger.setDescription(triggerRequestDTO.description());
//            isUpdated = true;
//        }
//        UserTrigger savedTrigger = new UserTrigger();
//        if (isUpdated) {
//            savedTrigger = triggerRepository.save(trigger);
//        }
//
//        return triggerMapper.toDto(savedTrigger);
//    }
//
//   public void deleteTrigger( Long id) throws ReliefApplicationException {
//       Optional<UserTrigger> name = triggerRepository.findById(id);
//       if (name.isPresent()) {
//           triggerRepository.delete(name.get());
//       }else throw new ReliefApplicationException("UserTrigger could not be deleted");
//   }
//
//   boolean isTriggerExist(String triggerName) {
//       Optional<UserTrigger> existingTrigger = triggerRepository.findByName(triggerName);
//       boolean isPresent = false;
//       if (existingTrigger.isPresent()) {
//           UserTrigger existing = existingTrigger.get();
//           existing.setRepetitionCount(existing.getRepetitionCount() + 1);
//           triggerRepository.save(existing);
//           isPresent = true;
//       }
//       return isPresent;
//   }
}