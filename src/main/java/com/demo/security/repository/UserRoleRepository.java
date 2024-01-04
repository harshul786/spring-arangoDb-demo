package com.demo.security.repository;

import com.arangodb.springframework.repository.ArangoRepository;
import com.demo.security.model.UserRole;

public interface UserRoleRepository extends ArangoRepository<UserRole, String> {
    public UserRole findByName(String Name);

}
