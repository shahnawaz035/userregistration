package com.exercise.userregistration.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class GeoLocation {
   private String status;
   private String message;
   private String countryCode;
   private String countryName;
   private String region;
   private String regionName;
   private String city;
   private String zipCode;
}
