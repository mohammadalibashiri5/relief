package com.mohammad.relief.controller;

import com.mohammad.relief.data.dto.AddictionRequestDto;
import com.mohammad.relief.data.dto.AddictionResponseDto;
import com.mohammad.relief.service.AddictionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/addictions")
public class AddictionController {

    private final AddictionService addService;

    public AddictionController(AddictionService addService) {
        this.addService = addService;
    }

    @PostMapping("register")
    public ResponseEntity<?> create(@RequestBody AddictionRequestDto requestDto, BindingResult br) {
        ResponseEntity<String> response;
        if (br.hasErrors()) {
            String message = "";
            for (ObjectError error : br.getAllErrors()) {
                message += error.getDefaultMessage();
            }
            response = ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(message);
        } else {
            try {
                addService.register(requestDto);
                response = ResponseEntity.status(HttpStatus.CREATED).build();
            }catch (Exception e) {
                response = ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
            }
        }
        return response;
    }

}
