package com.exercise.userregistration.service.impl;

import com.exercise.userregistration.model.GeoLocation;
import com.exercise.userregistration.service.GeoLocationService;
import com.exercise.userregistration.service.UserRegistrationService;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserRegistrationServiceImplTest {

    @Mock
    GeoLocationServiceImpl geoLocationService;

    @InjectMocks
    UserRegistrationServiceImpl userRegistrationService;

    @Value("${password.rule.regex}")
    private String passwordRuleRegex;

    @BeforeAll
    public void setUp(){
        ReflectionTestUtils.setField(userRegistrationService,"passwordRuleRegex", passwordRuleRegex);
    }


    @Test
    void registerUser() {
        GeoLocation geoLocation = GeoLocation.builder().city("Toronto").countryCode("CA").status("success").build();
        Mockito.when(geoLocationService.getGeoLocation(ArgumentMatchers.anyString())).thenReturn(geoLocation);
        String result = userRegistrationService.registerUser("user", "P_ssw0rd", "some_ip");
        assert StringUtils.isNotEmpty(result);
    }

    @Test
    void registerUserWithInCorrectPassword() {
        GeoLocation geoLocation = GeoLocation.builder().city("Toronto").countryCode("CA").status("success").build();
        Mockito.when(geoLocationService.getGeoLocation(ArgumentMatchers.anyString())).thenReturn(geoLocation);
        ResponseStatusException responseStatusException = Assertions.assertThrows(ResponseStatusException.class,
                ()-> userRegistrationService.registerUser("user", "Passw0rd", "some_ip"));
        assert responseStatusException.getStatus() == HttpStatus.BAD_REQUEST;
    }

    @Test
    void registerUserWithAPIResponseUS() {
        GeoLocation geoLocation = GeoLocation.builder().city("Toronto").countryCode("US").status("success").build();
        Mockito.when(geoLocationService.getGeoLocation(ArgumentMatchers.anyString())).thenReturn(geoLocation);
        ResponseStatusException responseStatusException = Assertions.assertThrows(ResponseStatusException.class,
                ()-> userRegistrationService.registerUser("user", "P_ssw0rd", "some_ip"));
        assert responseStatusException.getStatus() == HttpStatus.INTERNAL_SERVER_ERROR;
        assert responseStatusException.getReason().equals("User is not eligible to register");
    }

}