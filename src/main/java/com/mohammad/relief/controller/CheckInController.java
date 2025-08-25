package com.mohammad.relief.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/checkin")
@PreAuthorize("hasAuthority('USER')")
@RequiredArgsConstructor
public class CheckInController {

}