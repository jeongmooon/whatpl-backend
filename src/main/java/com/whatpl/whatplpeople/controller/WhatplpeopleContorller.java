package com.whatpl.whatplpeople.controller;

import com.whatpl.global.security.domain.MemberPrincipal;
import com.whatpl.whatplpeople.dto.FindMeWhatplpeopleResponse;
import com.whatpl.whatplpeople.service.WhatplpeopleService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class WhatplpeopleContorller {
    private final WhatplpeopleService whatplpeopleService;

    @GetMapping("/whatplpeople/find-me")
    public ResponseEntity<FindMeWhatplpeopleResponse> findMeWhatPeople(@RequestParam(required = false) String page,
                                                                       @AuthenticationPrincipal MemberPrincipal principal){

        return ResponseEntity.ok(whatplpeopleService.findMeWhatPeople(principal.getId(), page));
    }
}
