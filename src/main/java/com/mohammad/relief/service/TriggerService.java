package com.mohammad.relief.service;

import com.mohammad.relief.data.dto.request.TriggerRequestDTO;
import com.mohammad.relief.data.dto.response.TriggerResponseDTO;
import com.mohammad.relief.data.entity.Addiction;
import com.mohammad.relief.data.entity.Trigger;
import com.mohammad.relief.data.entity.Visitor;
import com.mohammad.relief.exception.ReliefApplicationException;
import com.mohammad.relief.mapper.TriggerMapper;
import com.mohammad.relief.repository.TriggerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TriggerService {

    private final TriggerMapper triggerMapper;
    private final TriggerRepository triggerRepository;
    private final UserService userService;
    private final UserAddictionService addictionService;


    public TriggerResponseDTO addTrigger(
            TriggerRequestDTO triggerRequestDTO,
            String username,
            String addictionName) throws ReliefApplicationException {

        // Fetch user and addiction
        Visitor user = userService.findByUsername(username);
        Addiction addiction = addictionService.getAddictionByName(addictionName);

        // Convert DTO to Entity
        Trigger trigger = triggerMapper.toEntity(triggerRequestDTO);
        trigger.setUser(user);
        trigger.setAddiction(addiction);

        // Check if trigger already exists
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

    public Optional<Trigger> getByName(String triggerName) {
        return triggerRepository.findByName(triggerName);
    }

//
//    public TriggerResponseDTO updateTrigger(TriggerRequestDTO triggerRequestDTO, String username, String name) throws ReliefApplicationException {
//        Visitor user = userRepository.findByUsername(username).orElseThrow(() -> new ReliefApplicationException("Visitor not found"));
//        Optional<Trigger> triggerName = triggerRepository.findByTriggerName(name);
//        if (triggerName.isEmpty()) {
//            throw new ReliefApplicationException("Trigger not found");
//        }
//        if (triggerRequestDTO == null) {
//            throw new ReliefApplicationException("TriggerRequestDTO is null");
//        }
//        Trigger trigger = triggerName.get();
//        trigger.setUser(user);
//        if (triggerRequestDTO.triggerName() != null && !triggerRequestDTO.triggerName().equals(trigger.getTriggerName())) {
//            trigger.setTriggerName(triggerRequestDTO.triggerName());
//        }
//        if (triggerRequestDTO.triggerType() != null && !triggerRequestDTO.triggerType().equals(trigger.getTriggerType())) {
//            trigger.setTriggerType(triggerRequestDTO.triggerType());
//        }
//        if (triggerRequestDTO.triggerDescription() != null && !triggerRequestDTO.triggerDescription().equals(trigger.getTriggerDescription())) {
//            trigger.setTriggerDescription(triggerRequestDTO.triggerDescription());
//        }
//        if (triggerRequestDTO.avoidanceStrategy() != null && !triggerRequestDTO.avoidanceStrategy().equals(trigger.getAvoidanceStrategy())) {
//            trigger.setAvoidanceStrategy(triggerRequestDTO.avoidanceStrategy());
//        }
//        Trigger savedTrigger = triggerRepository.save(trigger);
//        return triggerMapper.toDto(savedTrigger);
//    }
//
//    public List<TriggerResponseDTO> findAll(String username) throws ReliefApplicationException {
//        Optional<Visitor> user = userRepository.findByUsername(username);
//        if (user.isEmpty()) {
//            throw new ReliefApplicationException("Visitor not found");
//        }else return triggerRepository.findAll().stream().map(triggerMapper::toDto).collect(Collectors.toList());
//    }
//
//    public void deleteTrigger( String triggerName,String username) throws ReliefApplicationException {
//        Optional<Visitor> user = userRepository.findByUsername(username);
//        Optional<Trigger> name = triggerRepository.findByTriggerName(triggerName);
//        if (name.isPresent() && name.get().getUser().getUsername().equals(user.get().getUsername())) {
//            triggerRepository.delete(name.get());
//        }else throw new ReliefApplicationException("Trigger could not be deleted");
//    }
}
