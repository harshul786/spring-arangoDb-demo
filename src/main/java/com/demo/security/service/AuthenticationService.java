package com.demo.security.service;

import com.demo.security.DTOs.JwtResponseDTO;
import com.demo.security.DTOs.signin.SignInAuthRequestDTO;
import com.demo.security.DTOs.signup.SignupAuthRequestDTO;

public interface AuthenticationService {
    JwtResponseDTO signup(SignupAuthRequestDTO request);

    JwtResponseDTO signin(SignInAuthRequestDTO request);
}