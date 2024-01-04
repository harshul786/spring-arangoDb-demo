package com.demo.security.repository;

import com.arangodb.springframework.repository.ArangoRepository;
import com.demo.security.model.UserReference;

public interface UserReferenceRepository extends ArangoRepository<UserReference, String> {
}
