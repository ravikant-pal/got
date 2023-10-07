package org.house.got.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.house.got.dto.exception.ExceptionResponse;
import org.house.got.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/populate")
@CrossOrigin(origins = "http://localhost:3000")
@Tag(name = "Data Controller", description = "For persisting info.")
public class DataSetController {

    private final DataService dataService;

    @Autowired
    public DataSetController(DataService dataService) {
        this.dataService = dataService;
    }

    @Operation(summary = "Populate Data", description = "Populate data with json.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful response", content = @Content(schema = @Schema(implementation = Map.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "404", description = "The specified resource was not found", content = @Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "503", description = "Service is busy or temporarily unavailable. Caller should try again.", content = @Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(responseCode = "500", description = "Unknown or unexpected error encountered", content = @Content(schema = @Schema(implementation = ExceptionResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE))
    })
    @PostMapping
    public ResponseEntity<String> uploadJsonFile(@RequestParam("file") MultipartFile file) {
        try {
            return ResponseEntity.ok(dataService.populateData(file));
        } catch (Exception e) {
            log.error("Error: ", e);
            return ResponseEntity.internalServerError().body("Something went wrong!");
        }
    }
}
