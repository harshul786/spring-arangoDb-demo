package com.demo.security.controller;

import com.demo.security.DTOs.JwtResponseDTO;
import com.demo.security.DTOs.signin.SignInAuthRequestDTO;
import com.demo.security.DTOs.signup.SignupAuthRequestDTO;
import com.demo.security.model.UserInfo;
import com.demo.security.repository.UserReferenceRepository;
import com.demo.security.repository.UserRepository;
import com.demo.security.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1")
public class AuthController {
    private final AuthenticationService authenticationService;
    private final UserRepository userRepository;
    private final UserReferenceRepository userReferenceRepository;
    @GetMapping("/admin/ping")
    public String test() {
        try {
            return "Welcome";
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/auth/signUp")
    public ResponseEntity<JwtResponseDTO> signup(@RequestBody SignupAuthRequestDTO request) {
        return ResponseEntity.ok(authenticationService.signup(request));
    }

    @PostMapping("/auth/signIn")
    public ResponseEntity<JwtResponseDTO> signIn(@RequestBody SignInAuthRequestDTO request) {
        return ResponseEntity.ok(authenticationService.signin(request));
    }

    @GetMapping("/admin/listUsers")
    public ResponseEntity<List<UserInfo>> list(){
        List<UserInfo> list= new ArrayList<UserInfo>();
        userRepository.findAll().forEach(list::add);

        return ResponseEntity.ok(list);
    }
}
