package com.mohammad.relief.features.adminSide.globalTrigger.api;

import com.mohammad.relief.common.exception.ReliefApplicationException;
import com.mohammad.relief.features.adminSide.globalTrigger.domain.GlobalTriggerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/globalTrigger")
public class GlobalTriggerController {
    private final GlobalTriggerService globalTriggerService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<GlobalTriggerResponseDto> addGlobalTrigger(@RequestBody GlobalTriggerReqDto globalTrigger, @RequestParam Long addictionId, Principal principal) throws ReliefApplicationException {
        String email = principal.getName();
        GlobalTriggerResponseDto globalTriggerResponse = globalTriggerService.addGlobalTrigger(globalTrigger, addictionId, email);
       return new ResponseEntity<>(globalTriggerResponse, HttpStatus.CREATED);
    }
    @PreAuthorize("hasRole('ADMIN') or hasRole('VISITOR')")
    @GetMapping
    public ResponseEntity<List<GlobalTriggerResponseDto>> getGlobalTriggers(Long addictionId) throws ReliefApplicationException {
        return ResponseEntity.ok(globalTriggerService.getGlobalTriggersByAddiction(addictionId));
    }
}
