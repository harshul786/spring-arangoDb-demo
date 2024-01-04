package com.demo.repositories;

import com.arangodb.springframework.annotation.Query;
import com.arangodb.springframework.repository.ArangoRepository;
import com.demo.models.Character;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.Set;

public interface CharacterRepository extends ArangoRepository<Character, String> {
    public Optional<Character> findByNameAndSurname(String firstName, String LastName);

    @Query(
            "FOR v IN 1..2 INBOUND @arangoId @@edgeCol" +
                    " SORT v.age DESC " +
                    "RETURN DISTINCT v"
    )
    public Set<Character> getAllChildsAndGrandchilds(@Param("arangoId") String arangoId, @Param("@edgeCol") Class<?> edgeCollection);


    @Query(
            "FOR c in characters" +
            " FILTER c.age >= @lowerAge && c.age <= @upperAge" +
            " RETURN c"
    )
    public Set<Character> customGetAllCharactersInAgeRange(@Param("lowerAge") Integer lowerAge, @Param("upperAge") Integer upperAge);
}
