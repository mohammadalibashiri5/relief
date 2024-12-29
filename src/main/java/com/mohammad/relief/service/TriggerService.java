package com.mohammad.relief.service;

import com.mohammad.relief.data.dto.request.TriggerRequestDTO;
import com.mohammad.relief.data.dto.response.TriggerResponseDTO;
import com.mohammad.relief.data.entity.Addiction;
import com.mohammad.relief.data.entity.Trigger;
import com.mohammad.relief.data.entity.User;
import com.mohammad.relief.exception.ReliefApplicationException;
import com.mohammad.relief.mapper.TriggerMapper;
import com.mohammad.relief.repository.AddictionRepository;
import com.mohammad.relief.repository.TriggerRepository;
import com.mohammad.relief.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TriggerService {
    private final UserRepository userRepository;
    private final AddictionRepository addictionRepository;
    private final TriggerRepository triggerRepository;
    private final TriggerMapper triggerMapper;

    public TriggerService(UserRepository userRepository, AddictionRepository addictionRepository, TriggerRepository triggerRepository, TriggerMapper triggerMapper) {
        this.userRepository = userRepository;
        this.addictionRepository = addictionRepository;
        this.triggerRepository = triggerRepository;
        this.triggerMapper = triggerMapper;
    }

    public TriggerResponseDTO addTrigger(TriggerRequestDTO triggerRequestDTO, String username, String addictionName) throws ReliefApplicationException {
        // Fetch the User by username
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ReliefApplicationException("User not found"));

        // Fetch the Addiction by name
        Addiction addiction = addictionRepository.findByName(addictionName)
                .orElseThrow(() -> new ReliefApplicationException("Addiction not found"));

        Trigger trigger = triggerMapper.toEntity(triggerRequestDTO);
        trigger.setUser(user);
        trigger.setAddiction(addiction);


        // Save the Trigger entity to the database
        Trigger savedTrigger = triggerRepository.save(trigger);
        // Return the response DTO
        // Map the saved Trigger entity to the response DTO

        return triggerMapper.toDto(savedTrigger);
    }
    public TriggerResponseDTO updateTrigger(TriggerRequestDTO triggerRequestDTO, String username, String name) throws ReliefApplicationException {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new ReliefApplicationException("User not found"));
        Optional<Trigger> triggerName = triggerRepository.findByTriggerName(name);
        if (triggerName.isEmpty()) {
            throw new ReliefApplicationException("Trigger not found");
        }
        if (triggerRequestDTO == null) {
            throw new ReliefApplicationException("TriggerRequestDTO is null");
        }
        Trigger trigger = triggerName.get();
        trigger.setUser(user);
        if (triggerRequestDTO.triggerName() != null && !triggerRequestDTO.triggerName().equals(trigger.getTriggerName())) {
            trigger.setTriggerName(triggerRequestDTO.triggerName());
        }
        if (triggerRequestDTO.triggerType() != null && !triggerRequestDTO.triggerType().equals(trigger.getTriggerType())) {
            trigger.setTriggerType(triggerRequestDTO.triggerType());
        }
        if (triggerRequestDTO.triggerDescription() != null && !triggerRequestDTO.triggerDescription().equals(trigger.getTriggerDescription())) {
            trigger.setTriggerDescription(triggerRequestDTO.triggerDescription());
        }
        if (triggerRequestDTO.avoidanceStrategy() != null && !triggerRequestDTO.avoidanceStrategy().equals(trigger.getAvoidanceStrategy())) {
            trigger.setAvoidanceStrategy(triggerRequestDTO.avoidanceStrategy());
        }
        Trigger savedTrigger = triggerRepository.save(trigger);
        return triggerMapper.toDto(savedTrigger);
    }

    public List<TriggerResponseDTO> findAll(String username) throws ReliefApplicationException {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new ReliefApplicationException("User not found");
        }else return triggerRepository.findAll().stream().map(triggerMapper::toDto).collect(Collectors.toList());
    }
    public void deleteTrigger( String triggerName,String username) throws ReliefApplicationException {
        Optional<User> user = userRepository.findByUsername(username);
        Optional<Trigger> name = triggerRepository.findByTriggerName(triggerName);
        if (name.isPresent() && name.get().getUser().getUsername().equals(user.get().getUsername())) {
            triggerRepository.delete(name.get());
        }else throw new ReliefApplicationException("Trigger could not be deleted");
    }
}
