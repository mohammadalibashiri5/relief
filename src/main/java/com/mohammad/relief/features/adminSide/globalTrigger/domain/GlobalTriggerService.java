package com.mohammad.relief.features.adminSide.globalTrigger.domain;

import com.mohammad.relief.common.exception.ReliefApplicationException;
import com.mohammad.relief.features.adminSide.aminAddiction.domain.AdminAddiction;
import com.mohammad.relief.features.adminSide.aminAddiction.domain.AdminAddictionService;
import com.mohammad.relief.features.adminSide.globalTrigger.api.GlobalTriggerMapper;
import com.mohammad.relief.features.adminSide.globalTrigger.api.GlobalTriggerReqDto;
import com.mohammad.relief.features.adminSide.globalTrigger.api.GlobalTriggerResponseDto;
import com.mohammad.relief.features.adminSide.globalTrigger.data.GlobalTriggerRepository;
import com.mohammad.relief.features.userManagement.domain.Admin;
import com.mohammad.relief.features.userManagement.domain.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GlobalTriggerService {
    private final GlobalTriggerRepository globalTriggerRepository;
    private final GlobalTriggerMapper globalTriggerMapper;
    private final AdminAddictionService adminAddictionService;
    private final UserService userService;

    public GlobalTriggerResponseDto addGlobalTrigger(GlobalTriggerReqDto globalTrigger, Long addictionId, String email) throws ReliefApplicationException {
        Optional<AdminAddiction> addiction = adminAddictionService.findById(addictionId);
        Admin admin = userService.findAdminByEmail(email);
        GlobalTrigger globalTriggerEntity = globalTriggerMapper.toEntity(globalTrigger);
        globalTriggerEntity.setAdmin(admin);
        globalTriggerEntity.setAdminAddiction(addiction.get());
        return globalTriggerMapper.toDto(globalTriggerRepository.save(globalTriggerEntity));
    }

    public List<GlobalTriggerResponseDto> getGlobalTriggersByAddiction(Long addictionId) throws ReliefApplicationException {
        List<GlobalTrigger> globalTriggerByAdminAddictionId = globalTriggerRepository.findByAdminAddictionId(addictionId);
        if (globalTriggerByAdminAddictionId.isEmpty()) {
            throw new ReliefApplicationException("No such an addiction with this category type");
        }
        return globalTriggerByAdminAddictionId.stream()
                .map(globalTriggerMapper::toDto)
                .toList();
    }
}
