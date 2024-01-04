package com.demo.repositories;

import com.arangodb.ArangoCursor;
import com.arangodb.springframework.annotation.BindVars;
import com.arangodb.springframework.annotation.Query;
import com.arangodb.springframework.annotation.QueryOptions;
import com.arangodb.springframework.repository.ArangoRepository;
import com.demo.models.ChildOf;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ChildOfRepository extends ArangoRepository<ChildOf, String> {

}
