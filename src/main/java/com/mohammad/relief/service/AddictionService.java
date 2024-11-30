package com.mohammad.relief.service;

import com.mohammad.relief.data.dto.AddictionRequestDto;
import com.mohammad.relief.data.dto.AddictionResponseDto;
import com.mohammad.relief.data.entity.Addiction;
import com.mohammad.relief.mapper.AddictionMapper;
import com.mohammad.relief.repository.AddictionRepository;
import org.springframework.stereotype.Service;

@Service
public class AddictionService {
    private final AddictionRepository ar;
    private final AddictionMapper am;

    public AddictionService(AddictionRepository ar, AddictionMapper am) {
        this.ar = ar;
        this.am = am;
    }

    public AddictionResponseDto register(AddictionRequestDto ard) {
        Addiction addiction = am.toEntity(ard);
        Addiction savedAddiction = ar.save(addiction);
        return am.toDto(savedAddiction);
    }
}
