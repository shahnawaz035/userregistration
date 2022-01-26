package com.exercise.userregistration.service;

import com.exercise.userregistration.model.GeoLocation;

public interface GeoLocationService {

    GeoLocation getGeoLocation(String ipAddress);
}
