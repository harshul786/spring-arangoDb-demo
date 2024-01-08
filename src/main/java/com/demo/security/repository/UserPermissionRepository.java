package com.demo.security.repository;

import com.arangodb.springframework.repository.ArangoRepository;
import com.demo.security.model.UserPermission;

import java.util.Optional;

public interface UserPermissionRepository extends ArangoRepository<UserPermission, String> {
    public Optional<UserPermission> findByName(String name);
}
