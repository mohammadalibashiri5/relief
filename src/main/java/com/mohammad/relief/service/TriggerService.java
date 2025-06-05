package com.mohammad.relief.service;

import com.mohammad.relief.data.dto.request.TriggerRequestDTO;
import com.mohammad.relief.data.dto.response.TriggerResponseDTO;
import com.mohammad.relief.data.entity.Addiction;
import com.mohammad.relief.data.entity.Trigger;
import com.mohammad.relief.exception.ReliefApplicationException;
import com.mohammad.relief.mapper.TriggerMapper;
import com.mohammad.relief.repository.TriggerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TriggerService {

    private final TriggerMapper triggerMapper;
    private final TriggerRepository triggerRepository;
    private final UserAddictionService addictionService;


    public TriggerResponseDTO addTrigger(
            TriggerRequestDTO triggerRequestDTO,
            String username,
            Long addictionId) throws ReliefApplicationException {

        if (triggerRequestDTO == null) {
            throw new ReliefApplicationException("Trigger is null");
        }

        Addiction addiction = addictionService.getAddictionByIdAndUser(addictionId, username);

        if (addiction == null) {
            throw new ReliefApplicationException("Addiction not found");
        }

        Trigger trigger = triggerMapper.toEntity(triggerRequestDTO);
        trigger.setAddiction(addiction);
        trigger.setCreatedAt(LocalDateTime.now());

        if(!isTriggerExist(trigger.getName())) {
            trigger.setRepetitionCount(1);
        }

        Trigger savedTrigger = triggerRepository.save(trigger);
        return triggerMapper.toDto(savedTrigger);
    }

    public List<TriggerResponseDTO> getAllTriggersByAddiction(String username, Long addictionId) throws ReliefApplicationException {
        Addiction addiction = addictionService.getAddictionByIdAndUser(addictionId, username);

        if (addiction == null) {
            throw new ReliefApplicationException("Addiction not found");
        }
        List<Trigger> triggers = triggerRepository.findByAddiction(addiction);
        return triggers
                .stream()
                .map(triggerMapper::toDto)
                .collect(Collectors.toList());
    }

    public TriggerResponseDTO updateTrigger(TriggerRequestDTO triggerRequestDTO, Long id) throws ReliefApplicationException {
        Optional<Trigger> foundTrigger = triggerRepository.findById(id);
        boolean isUpdated = false;
        if (foundTrigger.isEmpty()) {
            throw new ReliefApplicationException("Trigger not found");
        }
        if (triggerRequestDTO == null) {
            throw new ReliefApplicationException("TriggerRequestDTO is null");
        }
        Trigger trigger = foundTrigger.get();

        if (triggerRequestDTO.name() != null && !triggerRequestDTO.name().equals(trigger.getName())) {
            trigger.setName(triggerRequestDTO.name());
            isUpdated = true;
        }
        if (triggerRequestDTO.description() != null && !triggerRequestDTO.description().equals(trigger.getDescription())) {
            trigger.setDescription(triggerRequestDTO.description());
            isUpdated = true;
        }
        Trigger savedTrigger = new Trigger();
        if (isUpdated) {
            savedTrigger = triggerRepository.save(trigger);
        }

        return triggerMapper.toDto(savedTrigger);
    }

   public void deleteTrigger( String triggerName) throws ReliefApplicationException {
       Optional<Trigger> name = triggerRepository.findByName(triggerName);
       if (name.isPresent()) {
           triggerRepository.delete(name.get());
       }else throw new ReliefApplicationException("Trigger could not be deleted");
   }

   boolean isTriggerExist(String triggerName) {
       Optional<Trigger> existingTrigger = triggerRepository.findByName(triggerName);
       boolean isPresent = false;
       if (existingTrigger.isPresent()) {
           Trigger existing = existingTrigger.get();
           existing.setRepetitionCount(existing.getRepetitionCount() + 1);
           triggerRepository.save(existing);
           isPresent = true;
       }
       return isPresent;
   }
}