package com.it.authservice.feign.auth;

import com.it.commonservice.model.auth.AuthUser;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Slf4j
@AllArgsConstructor
public class AuthFallback implements AuthServiceClient {

    private Throwable throwable;

    @Override
    public ResponseEntity<AuthUser> getUser(String s) {
        String errorMessage = throwable.getMessage();
        log.error(errorMessage, throwable);
        return new ResponseEntity(throwable, HttpStatus.BAD_REQUEST);
    }
}
