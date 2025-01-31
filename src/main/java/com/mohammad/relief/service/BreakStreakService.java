package com.mohammad.relief.service;

import com.mohammad.relief.data.dto.request.TriggerRequestDTO;
import com.mohammad.relief.data.entity.Addiction;
import com.mohammad.relief.data.entity.Break;
import com.mohammad.relief.data.entity.Trigger;
import com.mohammad.relief.data.entity.User;
import com.mohammad.relief.exception.ReliefApplicationException;
import com.mohammad.relief.mapper.TriggerMapper;
import com.mohammad.relief.repository.AddictionRepository;
import com.mohammad.relief.repository.BreakRepository;
import com.mohammad.relief.repository.TriggerRepository;
import com.mohammad.relief.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
@Service
public class BreakStreakService {
    private final UserRepository userRepository;
    private final AddictionRepository addictionRepository;
    private final TriggerRepository triggerRepository;
    private final TriggerMapper triggerMapper;
    private final BreakRepository breakRepository;

    public BreakStreakService(UserRepository userRepository, AddictionRepository addictionRepository, TriggerRepository triggerRepository, TriggerMapper triggerMapper, BreakRepository breakRepository) {
        this.userRepository = userRepository;
        this.addictionRepository = addictionRepository;
        this.triggerRepository = triggerRepository;
        this.triggerMapper = triggerMapper;
        this.breakRepository = breakRepository;
    }

    public void AddictionBreakStreak(String username, String addictionName, TriggerRequestDTO triggerDTO) throws ReliefApplicationException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ReliefApplicationException("User not found"));

        Addiction addiction = addictionRepository.findByName(addictionName)
                .orElseThrow(() -> new ReliefApplicationException("Addiction not found"));

        Trigger trigger = triggerRepository.findByTriggerName(triggerDTO.triggerName())
                .orElseGet(() -> {
                    Trigger newTrigger = triggerMapper.toEntity(triggerDTO);
                    newTrigger.setUser(user);
                    return triggerRepository.save(newTrigger);
                });

        trigger.setRepetitionCount(trigger.getRepetitionCount() + 1);  // Increment trigger usage
        triggerRepository.save(trigger);

        // Reset streak to zero
        addiction.setStreakCount(0);
        addictionRepository.save(addiction);

        // Record the break
        Break breakEvent = new Break();
        breakEvent.setUser(user);
        breakEvent.setAddiction(addiction);
        breakEvent.setTrigger(trigger);
        breakEvent.setBreakTime(LocalDateTime.now());

        breakRepository.save(breakEvent);
    }
}
