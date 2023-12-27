package com.demo.controller;

import com.arangodb.springframework.core.ArangoOperations;
import com.demo.models.Character;
import com.demo.repositories.CharacterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/character")
public class CharacterController {
    @Autowired
    private ArangoOperations operations;
    @Autowired
    private CharacterRepository repository;

    @GetMapping
    public List<Character> list(){
        List<Character> list= new ArrayList<Character>();
        repository.findAll().forEach(list::add);
        return list;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    private Character create(@RequestBody Character character) {
        return repository.save(character);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Character> update(@PathVariable String id, @RequestBody Character character) {
        Character existingCharacter = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Character not found with id: " + id));

        existingCharacter.setName(character.getName());
        existingCharacter.setAge(character.getAge());
        existingCharacter.setAlive(character.isAlive());
        existingCharacter.setSurname(character.getSurname());

        return ResponseEntity.ok(repository.save(character));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteEmployee(@PathVariable String id) {
        Optional<Character> toDelete = repository.findById(id);
        if(toDelete.isEmpty()){
            throw new RuntimeException("Failed to delete character, Character not Present!");
        } else {
            repository.delete(toDelete.get());
            String result = "Character deleted successfully";
            return ResponseEntity.ok(result);
        }
    }


}
