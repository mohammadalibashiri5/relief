package com.mohammad.relief.service;

import com.mohammad.relief.data.dto.request.TriggerRequestDTO;
import com.mohammad.relief.data.dto.response.AddictionResponseDto;
import com.mohammad.relief.data.dto.response.TriggerResponseDTO;
import com.mohammad.relief.data.entity.Addiction;
import com.mohammad.relief.data.entity.Trigger;
import com.mohammad.relief.exception.ReliefApplicationException;
import com.mohammad.relief.mapper.TriggerMapper;
import com.mohammad.relief.repository.TriggerRepository;
import com.mohammad.relief.repository.UserAddictionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TriggerService {

    private final TriggerMapper triggerMapper;
    private final TriggerRepository triggerRepository;
    private final UserAddictionService addictionService;
    private final UserAddictionRepository userAddictionRepository;


    public TriggerResponseDTO addTrigger(
            TriggerRequestDTO triggerRequestDTO,
            Long addictionId) throws ReliefApplicationException {
        if (triggerRequestDTO == null) {
            throw new ReliefApplicationException("Trigger is null");
        }

        Addiction addiction = userAddictionRepository.getAddictionById(addictionId);

        if (addiction == null) {
            throw new ReliefApplicationException("Addiction not found");
        }

        Trigger trigger = triggerMapper.toEntity(triggerRequestDTO);
        trigger.setAddiction(addiction);


        Optional<Trigger> existingTrigger = triggerRepository.findByName(trigger.getName());
        if (existingTrigger.isPresent()) {
            Trigger existing = existingTrigger.get();
            existing.setRepetitionCount(existing.getRepetitionCount() + 1);
            triggerRepository.save(existing); // Update existing trigger
            return triggerMapper.toDto(existing); // Return updated trigger
        }

        // If the trigger does not exist, save a new one
        trigger.setRepetitionCount(1);
        Trigger savedTrigger = triggerRepository.save(trigger);
        return triggerMapper.toDto(savedTrigger);
    }

    public Optional<Trigger> getById(Long id) {
        return triggerRepository.findById(id);
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

   public List<TriggerResponseDTO> findAllByAddictionId(Long addictionId) {
        Addiction addiction = userAddictionRepository.getAddictionById(addictionId);
        return triggerRepository.findByAddiction(addiction)
                .stream()
                .map(triggerMapper::toDto)
                .toList();
   }

   public void deleteTrigger( String triggerName) throws ReliefApplicationException {
       Optional<Trigger> name = triggerRepository.findByName(triggerName);
       if (name.isPresent()) {
           triggerRepository.delete(name.get());
       }else throw new ReliefApplicationException("Trigger could not be deleted");
   }
}