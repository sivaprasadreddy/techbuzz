package com.sivalabs.techbuzz.common.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class UnauthorisedAccessException extends DevZoneException {
    public UnauthorisedAccessException(String message) {
        super(message);
    }
}
