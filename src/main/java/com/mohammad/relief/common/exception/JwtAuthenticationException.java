package com.mohammad.relief.common.exception;

import io.jsonwebtoken.JwtException;

public class JwtAuthenticationException extends Throwable {
    public JwtAuthenticationException(String invalidJwtToken, JwtException e) {
    }
}
