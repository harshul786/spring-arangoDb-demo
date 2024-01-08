package com.demo.security.controller;

import com.demo.models.ResponseModels.CustomResponseModel;
import com.demo.security.DTOs.JwtResponseDTO;
import com.demo.security.DTOs.PermissionRequestDTO;
import com.demo.security.DTOs.signin.SignInAuthRequestDTO;
import com.demo.security.DTOs.signup.SignupAuthRequestDTO;
import com.demo.security.model.RolePermissionReference;
import com.demo.security.model.UserInfo;
import com.demo.security.model.UserPermission;
import com.demo.security.model.UserRole;
import com.demo.security.repository.RolePermissionReferenceRepository;
import com.demo.security.repository.UserPermissionRepository;
import com.demo.security.repository.UserRepository;
import com.demo.security.repository.UserRoleRepository;
import com.demo.security.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1")
public class AuthController {
    private final AuthenticationService authenticationService;
    private final UserRepository userRepository;
    private final UserPermissionRepository userPermissionRepository;
    private final UserRoleRepository userRoleRepository;
    private final RolePermissionReferenceRepository rolePermissionReferenceRepository;
    @GetMapping("/admin/ping")
    public String test1() {
        try {
            return "admin get request!";
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    @PostMapping("/admin/ping")
    public String test2() {
        try {
            return "admin post request";
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

    @PostMapping("/admin/addPermissionsToRole/{id}")
    public ResponseEntity<CustomResponseModel<UserRole>> addPermissions(@PathVariable String id, @RequestBody PermissionRequestDTO permissionRequestDTO) {
        UserRole userRole = userRoleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No role found with id: " + id));

        permissionRequestDTO.getNames().forEach(permission -> {
            UserPermission userPermission = userPermissionRepository
                    .findByName(permission.toUpperCase())
                    .orElseGet(() -> {
                        UserPermission newPermission = UserPermission.builder().name(permission.toUpperCase()).build();
                        return userPermissionRepository.save(newPermission);
                    });

            userRole.getPermissions().add(userPermission);
        });
        try {
            saveRolePermissionReference(userRole, userRole.getPermissions());
        } catch (Exception e){
            e.printStackTrace();
        }


        UserRole updatedUserRole = userRoleRepository.save(userRole);
        return CustomResponseModel.wrapWithData(updatedUserRole);
    }

    public void saveRolePermissionReference(UserRole role, Collection<UserPermission> permissions) {

        for (UserPermission permission : permissions) {
            RolePermissionReference rolePermissionReference = new RolePermissionReference();
            rolePermissionReference.setRole(role);
            rolePermissionReference.setPermission(permission);
            rolePermissionReferenceRepository.save(rolePermissionReference);
        }
    }

}
