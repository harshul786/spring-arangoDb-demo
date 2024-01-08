package com.demo.security.repository;

import com.arangodb.springframework.repository.ArangoRepository;
import com.demo.security.model.RolePermissionReference;

public interface RolePermissionReferenceRepository extends ArangoRepository<RolePermissionReference, String> {
}
