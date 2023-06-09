package com.pragma.powerup.usermicroservice.adapters.driving.http.controller;

import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request.UserRequestDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.response.RoleResponseDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.handlers.IUserHandler;
import com.pragma.powerup.usermicroservice.configuration.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@SecurityRequirement(name = "jwt")
public class UserRestController {
    private final IUserHandler personHandler;

    /**
     * @deprecated
     * <p> Use {@link /user-admin/add-owner} instead.
     */
    @Deprecated(since = "",forRemoval = true)
    @Operation(summary = "Add a new Owner",
            responses = { 
                @ApiResponse(responseCode = "201", description = "Owner created",
                        content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Map"))),
                @ApiResponse(responseCode = "409", description = "Person already exists",
                        content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error"))),

            })
    @PostMapping("add-owner")
    public ResponseEntity<Map<String, String>> saveOwner(@Valid @RequestBody @io.swagger.v3.oas.annotations.media.Schema(
            description = "The request body",
            example = UserRequestDto.example

    ) UserRequestDto userRequestDto) {

        personHandler.saveUserOwner(userRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Collections.singletonMap(Constants.RESPONSE_MESSAGE_KEY, Constants.PERSON_CREATED_MESSAGE));
    }

    @GetMapping("user-role/{userDni}")
    public ResponseEntity<RoleResponseDto> getUserRole(@PathVariable String userDni) {
        return ResponseEntity.ok(personHandler.getRoleByUserId(userDni));
    }

    @GetMapping("user-phone/{userDni}")
    public ResponseEntity<String>  getUserPhone(@PathVariable String userDni) {
        return ResponseEntity.ok(personHandler.getUserPhone(userDni));
    }

}



