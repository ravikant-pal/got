package org.house.got.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.house.got.dto.response.CharacterDto;
import org.house.got.model.Character;
import org.house.got.model.House;
import org.house.got.repository.CharacterRepository;
import org.house.got.service.CharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CharacterServiceImpl implements CharacterService {
    private final CharacterRepository characterRepository;

    @Autowired
    public CharacterServiceImpl(CharacterRepository characterRepository) {
        this.characterRepository = characterRepository;
    }

    @Override
    public Optional<Character> findCharacterByName(String characterName) {
        return characterRepository.findByCharacterName(characterName);
    }

    @Override
    public CharacterDto getCharacterById(long id) {
        Optional<Character> byId = characterRepository.findById(id);
        return byId.map(this::getCoreToDto).orElse(null);
    }

    @Override
    public List<Character> getParentsByChild(Character child) {
        return characterRepository.findByChildrenContains(child);
    }

    @Override
    public List<Character> getKilledByCharacter(Character child) {
        return characterRepository.findByChildrenContains(child);
    }

    @Override
    public Optional<Character> getCharactersByHouseId() {
        return Optional.empty();
    }

    @Override
    @Transactional
    public Character findOrCreateCharacter(String characterName) {
        Optional<Character> characterByName = findCharacterByName(characterName);
        if(characterByName.isPresent()) {
            return characterByName.get();
        } else {
            Character character = new Character();
            character.setCharacterName(characterName);
            return characterRepository.save(character);
        }
    }

    @Override
    @Transactional
    public Character saveCharacter(Character character) {
        return characterRepository.save(character);
    }

    @Override
    public List<Character> getFamilyTreeByHouseName(String houseName) {
        return characterRepository.findByHouseName(houseName);
    }

    @Override
    @Transactional
    public void saveAllCharacters(List<Character> charactersToSave) {
        characterRepository.saveAll(charactersToSave);
    }

    @Override
    public CharacterDto toggleFavourite(Long id) {
        return characterRepository.findById(id)
                .map(character -> {
                    character.setFavorite(!character.isFavorite());
                    return getCoreToDto(saveCharacter(character));
                })
                .orElse(null);
    }


    private CharacterDto getCoreToDto(Character character) {
        List<Character> parents = getParentsByChild(character);
        List<Character> killedByByCharacter = getKilledByCharacter(character);
        return CharacterDto.builder()
                .id(character.getId())
                .actorName(character.getActorName())
                .characterName(character.getCharacterName())
                .characterImageUrl(character.getCharacterImageUrl())
                .houses(character.getHouses().stream().map(House::getName).collect(Collectors.toList()))
                .parents(parents.stream().map(Character::getCharacterName).collect(Collectors.toList()))
                .children(character.getChildren().stream().map(Character::getCharacterName).collect(Collectors.toList()))
                .killedBy(killedByByCharacter.stream().map(Character::getCharacterName).collect(Collectors.toList()))
                .killed(character.getKilled().stream().map(Character::getCharacterName).collect(Collectors.toList()))
                .favorite(character.isFavorite())
                .build();
    }
}
