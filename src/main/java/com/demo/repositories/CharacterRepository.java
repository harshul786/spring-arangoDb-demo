package com.demo.repositories;

import com.arangodb.springframework.repository.ArangoRepository;
import com.demo.models.Character;

public interface CharacterRepository extends ArangoRepository<Character, String> {

}
