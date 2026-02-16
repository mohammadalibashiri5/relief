package com.mohammad.relief.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/journey")
public class JourneyController {

//    private final JourneyService journeyService;
//
//    @PreAuthorize("hasRole('VISITOR')")
//    @PostMapping()
//    public ResponseEntity<JourneyResponseDto> startJourney(@RequestBody @Valid JourneyReqDto requestDto, @RequestParam Long userAddictionId, Principal principal) throws Exception {
//        String username = principal.getName();
//        JourneyResponseDto addedArticle = journeyService.startJourney(requestDto,userAddictionId, username);
//        return new ResponseEntity<>(addedArticle, HttpStatus.CREATED);
//    }
//    @PreAuthorize("hasRole('VISITOR')")
//    @GetMapping()
//    public ResponseEntity<List<JourneyResponseDto>> getJourney(@RequestParam Long userAddictionId, Principal principal) throws ReliefApplicationException {
//        String username = principal.getName();
//        return ResponseEntity.ok(journeyService.getJourneys(userAddictionId, username));
//    }
}
