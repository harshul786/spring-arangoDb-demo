package com.demo.security.DTOs.signup;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignupAuthRequestDTO {
    private String username;
    private String password;

    List<String> roles;
}
