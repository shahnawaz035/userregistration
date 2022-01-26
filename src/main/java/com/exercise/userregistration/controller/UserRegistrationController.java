package com.exercise.userregistration.controller;

import com.exercise.userregistration.service.UserRegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserRegistrationController {

    private final UserRegistrationService userRegistrationService;

    @GetMapping(value="/register")
    public ResponseEntity<String> getGeoLocation(@RequestParam String userName,
                                                 @RequestParam String password,
                                                 @RequestParam String ipAddress){

        return ResponseEntity.ok(userRegistrationService.registerUser(userName, password, ipAddress));

    }

}
