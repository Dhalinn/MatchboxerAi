package com.example.matchboxer.controller;

import com.example.matchboxer.dto.MatchRequest;
import com.example.matchboxer.dto.MatchResponse;
import com.example.matchboxer.service.MatchService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class Matchcontroller {

    private final MatchService matchService;

    public Matchcontroller(MatchService matchService) {
        this.matchService = matchService;
    }

    @PostMapping("/matchboxer")
    public ResponseEntity<MatchResponse> matchBoxer(@RequestBody MatchRequest request) throws JsonProcessingException {
        MatchResponse response = matchService.getBoxerMatch(request);
        return ResponseEntity.ok(response);
    }
}
