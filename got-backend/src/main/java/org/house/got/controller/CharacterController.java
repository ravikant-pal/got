package org.house.got.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.house.got.dto.exception.ExceptionResponse;
import org.house.got.dto.response.CharacterDto;
import org.house.got.dto.response.CharacterHierarchy;
import org.house.got.model.Character;
import org.house.got.service.CharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/characters")
@Tag(name = "Character Controller", description = "Info Related to Character.")
public class CharacterController {

    private final CharacterService characterService;

    @Autowired
    public CharacterController(CharacterService characterService) {
        this.characterService = characterService;
    }

    @Operation(summary = "Get All Character in House", description = "Get All Character in House")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful response", content = @Content(schema = @Schema(implementation = Map.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "404", description = "The specified resource was not found", content = @Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "503", description = "Service is busy or temporarily unavailable. Caller should try again.", content = @Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "500", description = "Unknown or unexpected error encountered", content = @Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE))
    })
    @GetMapping("/family-tree/{houseName}")
    public ResponseEntity<CharacterHierarchy> getFamilyTreeByHouseName(@PathVariable String houseName) {
        List<Character> familyTreeByHouseName = characterService.getFamilyTreeByHouseName(houseName);
        Character characterWithMostChildren = familyTreeByHouseName.stream()
                .max(Comparator.comparingInt(ch -> ch.getChildren().size()))
                .orElse(null);
        if (characterWithMostChildren != null) {
            CharacterHierarchy hierarchy = CharacterHierarchy.buildHierarchy(characterWithMostChildren);
            return ResponseEntity.ok(hierarchy);
        } else {
            // Handle the case where no character with children was found
            return ResponseEntity.notFound().build();
        }

    }

    @Operation(summary = "Get Character by Id", description = "Get Character by Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful response", content = @Content(schema = @Schema(implementation = Map.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "404", description = "The specified resource was not found", content = @Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "503", description = "Service is busy or temporarily unavailable. Caller should try again.", content = @Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "500", description = "Unknown or unexpected error encountered", content = @Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE))
    })
    @GetMapping("/{id}")
    public ResponseEntity<CharacterDto> getCharacterById(@PathVariable Long id) {
        return ResponseEntity.ok(characterService.getCharacterById(id));
    }

    @Operation(summary = "Toggle favourite", description = "Toggle favourite")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful response", content = @Content(schema = @Schema(implementation = Map.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "404", description = "The specified resource was not found", content = @Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "503", description = "Service is busy or temporarily unavailable. Caller should try again.", content = @Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "500", description = "Unknown or unexpected error encountered", content = @Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE))
    })
    @PutMapping("/{id}/favourite")
    public ResponseEntity<CharacterDto> toggleFavourite(@PathVariable Long id) {
        return ResponseEntity.ok(characterService.toggleFavourite(id));
    }

}
