package org.house.got.service;

import org.house.got.dto.response.CharacterDto;
import org.house.got.model.Character;

import java.util.List;
import java.util.Optional;

public interface CharacterService {
    CharacterDto getCharacterById(long id);

    Optional<Character> findCharacterByName(String characterName);

    Optional<Character> getCharactersByHouseId();

    List<Character> getParentsByChild(Character child);

    List<Character> getKilledByCharacter(Character character);

    Character findOrCreateCharacter(String characterName);

    Character saveCharacter(Character character);

    List<Character> getFamilyTreeByHouseName(String houseName);

    void saveAllCharacters(List<Character> charactersToSave);

    CharacterDto toggleFavourite(Long id);
}
