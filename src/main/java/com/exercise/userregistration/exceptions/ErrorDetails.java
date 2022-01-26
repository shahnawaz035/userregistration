package com.exercise.userregistration.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class ErrorDetails {
        private Date timestamp;
        private String message;
        private HttpStatus httpStatus;

}
