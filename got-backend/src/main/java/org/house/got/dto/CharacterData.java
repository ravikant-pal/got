package org.house.got.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;


public record CharacterData(List<Map<String, Object>> characters) {
    @JsonCreator
    public CharacterData(@JsonProperty("characters") List<Map<String, Object>> characters) {
        this.characters = characters;
    }
}

