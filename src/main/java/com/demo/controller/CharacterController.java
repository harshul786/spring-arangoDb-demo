package com.demo.controller;

import com.arangodb.springframework.core.ArangoOperations;
import com.demo.models.Character;
import com.demo.models.ChildOf;
import com.demo.models.CustomRelationshipModel;
import com.demo.models.RequestModels.CharacterRequest;
import com.demo.models.ResponseModels.CustomResponseModel;
import com.demo.repositories.CharacterRepository;
import com.demo.repositories.ChildOfRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping(value = "/api/v1/character")
@RequiredArgsConstructor
public class CharacterController {
    @Autowired
    private ArangoOperations operations;
    @Autowired
    private CharacterRepository repository;

    @Autowired
    private ChildOfRepository childOfRepository;

    @GetMapping
    public ResponseEntity<CustomResponseModel<List<Character>>> list(){
        List<Character> list= new ArrayList<Character>();
        repository.findAll().forEach(list::add);

        return CustomResponseModel.wrapWithData(list);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    private ResponseEntity<CustomResponseModel<Character>> create(@RequestBody CharacterRequest character) {
        Character ch = new Character(character.getName(), character.getSurname(), character.isAlive(), character.getAge());
        return CustomResponseModel.wrapWithData(repository.save(ch));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomResponseModel<Character>> update(@PathVariable String id, @RequestBody CharacterRequest character) {
        Character existingCharacter = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Character not found with id: " + id));

        existingCharacter.setName(character.getName());
        existingCharacter.setAge(character.getAge());
        existingCharacter.setAlive(character.isAlive());
        existingCharacter.setSurname(character.getSurname());

        return CustomResponseModel.wrapWithData(repository.save(existingCharacter));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponseModel<String>> deleteCharacter(@PathVariable String id) {
        Optional<Character> toDelete = repository.findById(id);
        if(toDelete.isEmpty()){
            throw new RuntimeException("Failed to delete character, Character not Present!");
        } else {
            repository.delete(toDelete.get());
            String result = "Character deleted successfully";
            return CustomResponseModel.wrapWithData(result);
        }
    }

    @PostMapping("/childOf")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> establishChildOfRelationship(
            @RequestBody CustomRelationshipModel customRelationshipModel
            ) {
        try{
            String parentFirstName=customRelationshipModel.getParentFirstName();
            String parentSurname=customRelationshipModel.getParentSurname();
            String childFirstName=customRelationshipModel.getChildFirstName();
            String childSurname=customRelationshipModel.getChildSurname();
            System.out.println(parentFirstName+ parentSurname+ childFirstName+ childSurname);
            Character parent = repository.findByNameAndSurname(parentFirstName, parentSurname)
                    .orElseThrow(() -> new RuntimeException("Parent character not found: " + parentFirstName + " " + parentSurname));
            Character child = repository.findByNameAndSurname(childFirstName, childSurname)
                    .orElseThrow(() -> new RuntimeException("Child character not found: " + childFirstName + " " + childSurname));

            ChildOf childOfRelationship = new ChildOf(child, parent);
            childOfRepository.save(childOfRelationship);

            String result = "Child-Parent relationship established between " + childFirstName + " " + childSurname +
                    " and " + parentFirstName + " " + parentSurname;
            return ResponseEntity.ok(result);
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }


    @GetMapping("/getAllChildsAndGrandchilds")
    public ResponseEntity<CustomResponseModel<Set<Character>>> childAndGchilds(@RequestParam String arrangoId){
        return CustomResponseModel.wrapWithData(repository.getAllChildsAndGrandchilds(arrangoId, ChildOf.class));
    }

    @GetMapping("/getAllInRange")
    public ResponseEntity<CustomResponseModel<Set<Character>>> charactersInAgeRange(@RequestParam Integer lowerAge, @RequestParam Integer upperAge){
        return CustomResponseModel.wrapWithData(repository.customGetAllCharactersInAgeRange(lowerAge, upperAge));
    }

}
