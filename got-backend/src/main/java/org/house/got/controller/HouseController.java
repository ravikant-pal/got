package org.house.got.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.house.got.dto.exception.ExceptionResponse;
import org.house.got.model.House;
import org.house.got.service.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/characters/houses")
@CrossOrigin(origins = "http://localhost:3000")
@Tag(name = "House Controller", description = "Info Related to House.")
public class HouseController {

    private final HouseService houseService;


    @Autowired
    public HouseController(HouseService houseService) {
        this.houseService = houseService;
    }

    @Operation(summary = "Get All House", description = "Get All the houses in the System.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful response", content = @Content(schema = @Schema(implementation = Map.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "404", description = "The specified resource was not found", content = @Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "503", description = "Service is busy or temporarily unavailable. Caller should try again.", content = @Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "500", description = "Unknown or unexpected error encountered", content = @Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE))
    })
    @GetMapping
    public ResponseEntity<List<House>> getHouses() {
        return ResponseEntity.ok(houseService.getAllHouse());
    }

    @Operation(summary = "Get House", description = "Get House by Id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful response", content = @Content(schema = @Schema(implementation = Map.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "404", description = "The specified resource was not found", content = @Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "503", description = "Service is busy or temporarily unavailable. Caller should try again.", content = @Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "500", description = "Unknown or unexpected error encountered", content = @Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE))
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getHouseById(@PathVariable long id) {
        Optional<House> houseById = houseService.getHouseById(id);
        if (houseById.isPresent()) {
            return ResponseEntity.ok(houseById.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
