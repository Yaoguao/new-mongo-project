package org.example.newmongoproject.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NETWORK_AUTHENTICATION_REQUIRED)
public class NotAuthorizedExeption extends RuntimeException{
}
