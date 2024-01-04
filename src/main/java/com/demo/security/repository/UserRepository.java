package com.demo.security.repository;

import com.arangodb.springframework.annotation.Query;
import com.arangodb.springframework.repository.ArangoRepository;
import com.demo.security.model.UserInfo;

import java.util.List;
import java.util.Map;

public interface UserRepository extends ArangoRepository<UserInfo, String> {
    public UserInfo findByUsername(String username);

    @Query("FOR u IN USERS FOR r IN userReference FILTER u.id == r.user.id RETURN { user: u, roles: r.role }")
    List<Map<String, Object>> findAllWithRoles();
}
