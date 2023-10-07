package org.house.got.dto.response;

import lombok.Data;
import org.house.got.model.Character;

import java.util.ArrayList;
import java.util.List;
@Data
public class CharacterHierarchy {
    private String name;
    private Attributes attributes;
    private List<CharacterHierarchy> children;

    public CharacterHierarchy(long id, String name, String imageUrl) {
        this.name = name;
        this.attributes = new Attributes(id, imageUrl);
        this.children = new ArrayList<>();
    }

    public static CharacterHierarchy buildHierarchy(Character character) {
        CharacterHierarchy hierarchyNode = new CharacterHierarchy(character.getId(), character.getCharacterName(), character.getCharacterImageUrl());
        for (Character child : character.getChildren()) {
            CharacterHierarchy childNode = buildHierarchy(child);
            hierarchyNode.getChildren().add(childNode);
        }
        return hierarchyNode;
    }
}
