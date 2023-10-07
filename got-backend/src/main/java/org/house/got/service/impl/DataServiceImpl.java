package org.house.got.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.house.got.model.Character;
import org.house.got.model.House;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.house.got.dto.CharacterData;
import org.house.got.service.CharacterService;
import org.house.got.service.DataService;
import org.house.got.service.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class DataServiceImpl implements DataService {

    private final CharacterService characterService;
    private final HouseService houseService;

    @Autowired
    public DataServiceImpl(CharacterService characterService, HouseService houseService) {
        this.characterService = characterService;
        this.houseService = houseService;
    }

    @Override
    @Transactional
    public String populateData(MultipartFile file) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        CharacterData characterData = objectMapper.readValue(file.getBytes(), CharacterData.class);
        characterData.characters().forEach(characterDataMap -> {
            String characterName = (String) characterDataMap.get("characterName");
            Character character = characterService.findOrCreateCharacter(characterName);

            if (characterDataMap.containsKey("actorName")) {
                character.setActorName((String) characterDataMap.get("actorName"));
            }

            if (characterDataMap.containsKey("characterImageFull")) {
                character.setCharacterImageUrl((String) characterDataMap.get("characterImageFull"));
            }

            if (characterDataMap.containsKey("houseName")) {
                List<String> houseNames = new ArrayList<>();
                if (characterDataMap.get("houseName") instanceof String) {
                    houseNames.add((String) characterDataMap.get("houseName"));
                } else {
                    houseNames.addAll((List<String>) characterDataMap.get("houseName"));
                }
                for (String houseName : houseNames) {
                    House house = houseService.findOrCreateHouse(houseName);
                    character.getHouses().add(house);
                    house.getCharacters().add(character);
                    houseService.saveHouse(house);
                    characterService.saveCharacter(character);
                }
            }

//            if (characterDataMap.containsKey("parents")) {
//                List<String> parentsName = (List<String>) characterDataMap.get("parents");
//                for (String parentName : parentsName) {
//                    Character parent = characterService.findOrCreateCharacter(parentName);
//                    character.getParents().add(parent);
//                    characterService.saveCharacter(character);
//                }
//            }

//            if (characterDataMap.containsKey("killedBy")) {
//                List<String> killerNames = (List<String>) characterDataMap.get("killedBy");
//                for (String killerName : killerNames) {
//                    Character killedBy = characterService.findOrCreateCharacter(killerName);
//                    character.getKilledBy().add(killedBy);
//                    characterService.saveCharacter(character);
//                }
//            }

            if (characterDataMap.containsKey("parentOf")) {
                List<String> childrenNames = (List<String>) characterDataMap.get("parentOf");
                log.error("Character Name : " + characterName + " ==> Children Names: " + childrenNames);
                for (String childrenName : childrenNames) {
                    Character children = characterService.findOrCreateCharacter(childrenName);
                    character.getChildren().add(children);
                    children.setParent(character);
                    characterService.saveCharacter(character);
                    characterService.saveCharacter(children);
                }
                log.error("===============================================================================================");
            }

            if (characterDataMap.containsKey("killed")) {
                List<String> killedCharacterNames = (List<String>) characterDataMap.get("killed");
                log.error("Character Name : " + characterName + " ==> Killed Character Names: " + killedCharacterNames);
                for (String killedCharacterName : killedCharacterNames) {
                    Character killedCharacter = characterService.findOrCreateCharacter(killedCharacterName);
                    character.getKilled().add(killedCharacter);
                    characterService.saveCharacter(character);
                }
                log.error("===============================================================================================");
            }
        });
        return "Characters saved successfully.";
    }


}
