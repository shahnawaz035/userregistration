package com.exercise.userregistration.service.impl;

import com.exercise.userregistration.model.GeoLocation;
import com.exercise.userregistration.service.GeoLocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;

@RequiredArgsConstructor
@Service
public class GeoLocationServiceImpl implements GeoLocationService {

    private final WebClient webClient;

    @Override
    public GeoLocation getGeoLocation(String ipAddress) {

        return webClient.get().uri(uriBuilder -> uriBuilder.path("/{ipAddress}").build(ipAddress))
                .retrieve()
                .bodyToMono(GeoLocation.class)
                .block(Duration.ofSeconds(60));
    }
}
