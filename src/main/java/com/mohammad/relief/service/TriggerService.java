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
import org.springframework.stereotype.Service;

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

}
