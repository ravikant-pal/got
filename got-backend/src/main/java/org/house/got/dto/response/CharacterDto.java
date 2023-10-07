package org.house.got.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CharacterDto {
    private long id;
    private boolean favorite;
    private String actorName;
    private String characterName;
    private String characterImageUrl;
    private List<String> houses;
    private List<String> parents;
    private List<String> children;
    private List<String> killedBy;
    private List<String> killed;

}
