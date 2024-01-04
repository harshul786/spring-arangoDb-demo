package com.demo.security.service.impl;

import com.demo.security.DTOs.JwtResponseDTO;
import com.demo.security.DTOs.signin.SignInAuthRequestDTO;
import com.demo.security.DTOs.signup.SignupAuthRequestDTO;
import com.demo.security.model.UserInfo;
import com.demo.security.model.UserReference;
import com.demo.security.model.UserRole;
import com.demo.security.repository.UserReferenceRepository;
import com.demo.security.repository.UserRepository;
import com.demo.security.repository.UserRoleRepository;
import com.demo.security.service.AuthenticationService;
import com.demo.security.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final UserReferenceRepository userReferenceRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public Set<UserRole> createRolesSet(List<String> roles) {
        Set<UserRole> rolesSet = new HashSet<>();

        for (String roleName : roles) {
            String roleUpper = roleName.toUpperCase();
            if(userReferenceRepository.count()==0){
                UserRole userRole = new UserRole(roleUpper);
                userRole = userRoleRepository.save(userRole);
                rolesSet.add(userRole);
                continue;
            }

            UserRole userRole = userRoleRepository.findByName(roleUpper);

            if (userRole == null) {
                userRole = new UserRole(roleUpper);
                userRole = userRoleRepository.save(userRole);
            }
            rolesSet.add(userRole);
        }

        return rolesSet;
    }

    public void saveRoleAndUserReference(Set<UserRole> roles, UserInfo user){
        for(UserRole role: roles){
            UserReference userReference = new UserReference();
            userReference.setRole(role);
            userReference.setUser(user);
            userReferenceRepository.save(userReference);
        }
    }

    @Override
    public JwtResponseDTO signup(SignupAuthRequestDTO request) {
        Set<UserRole> roles = createRolesSet(request.getRoles());
        var user = UserInfo.builder().username(request.getUsername()).password(passwordEncoder.encode(request.getPassword()))
                .build();
        user.setRoles(roles);
        userRepository.save(user);


        saveRoleAndUserReference(roles, user);
        userRepository.save(user);
        System.out.println(user);

        var jwt = jwtService.GenerateToken(user.getUsername());
        return JwtResponseDTO.builder().accessToken(jwt).build();
    }

    @Override
    public JwtResponseDTO signin(SignInAuthRequestDTO request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        var user = userRepository.findByUsername(request.getUsername());

        if(user==null){
            throw new IllegalArgumentException("Invalid email or password.");
        }

        var jwt = jwtService.GenerateToken(user.getUsername());
        return JwtResponseDTO.builder().accessToken(jwt).build();
    }
}
