package com.exercise.userregistration.service.impl;

import com.exercise.userregistration.model.GeoLocation;
import com.exercise.userregistration.service.GeoLocationService;
import com.exercise.userregistration.service.UserRegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UserRegistrationServiceImpl implements UserRegistrationService {

    private final GeoLocationService geoLocationService;

    @Value("${password.rule.regex}")
    private String passwordRuleRegex;

    @Override
    public String registerUser(String userName, String password, String ipAddress) {
        if (!isValidPassword(password))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password doesn't match criteria.");

        GeoLocation geoLocation = geoLocationService.getGeoLocation(ipAddress);
        if (geoLocation == null)
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "error while calling IP-API service");
        else if (!"success".equals(geoLocation.getStatus()))
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, String.format("error while calling IP-API service %s", geoLocation.getMessage()));
        else if (!"CA".equalsIgnoreCase(geoLocation.getCountryCode()))
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "User is not eligible to register");

        return String.format("Hello %1$s from %2$s city and userId: %3$s", userName, geoLocation.getCity(), UUID.randomUUID());
    }


    public boolean isValidPassword(String password) {
        Pattern pattern = Pattern.compile(passwordRuleRegex);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }
}
