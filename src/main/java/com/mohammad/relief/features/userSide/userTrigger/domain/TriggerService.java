package com.mohammad.relief.features.userSide.userTrigger.domain;

import com.mohammad.relief.common.exception.ReliefApplicationException;
import com.mohammad.relief.features.adminSide.globalTrigger.data.GlobalTriggerRepository;
import com.mohammad.relief.features.adminSide.globalTrigger.domain.GlobalTrigger;
import com.mohammad.relief.features.userManagement.domain.UserService;
import com.mohammad.relief.features.userManagement.domain.Visitor;
import com.mohammad.relief.features.userSide.userTrigger.api.TriggerMapper;
import com.mohammad.relief.features.userSide.userTrigger.api.TriggerResponseDto;
import com.mohammad.relief.features.userSide.userTrigger.api.UserTriggerRequestDto;
import com.mohammad.relief.features.userSide.userTrigger.data.TriggerRepository;
import com.mohammad.relief.features.userSide.userAddiction.domain.UserAddictionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TriggerService {

    private final TriggerMapper triggerMapper;
    private final GlobalTriggerRepository  globalTriggerRepository;
    private final TriggerRepository  userTriggerRepository;
    private final UserService userService;
    private final UserAddictionService userAddictionService;


    public TriggerResponseDto adoptTrigger(UserTriggerRequestDto dto,long globalTriggerId, String email) throws ReliefApplicationException {
        Visitor user = userService.findByEmail(email);
        Optional<GlobalTrigger> optionalGlobalTrigger = globalTriggerRepository.findById(globalTriggerId);
        UserTrigger userTrigger = triggerMapper.toEntity(dto);
        userTrigger.setUser(user);

        if (globalTriggerRepository.existsByTitleIgnoreCase(dto.name())) {
            throw new ReliefApplicationException("This trigger name already exists");
        }

        optionalGlobalTrigger.ifPresent(globalTrigger -> userTrigger.setName(globalTrigger.getTitle()));

        return triggerMapper.toDto(userTriggerRepository.save(userTrigger));
    }


    public List<TriggerResponseDto> getAllTriggers(long addictionId, String email) throws ReliefApplicationException {
        Visitor user = userService.findByEmail(email);
        userAddictionService.findAddictionByIdAndValidateOwnership(addictionId, email);
        return userTriggerRepository.findAllByUser(user).stream().map(triggerMapper::toDto).toList();

    }




}