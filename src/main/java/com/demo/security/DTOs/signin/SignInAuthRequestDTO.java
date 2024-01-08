package com.demo.security.DTOs.signin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignInAuthRequestDTO {
    private String email;
    private String username;
    private String password;


    String role;
}
