package com.pragma.powerup.usermicroservice.adapters.driving.http.controller;

import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request.UserRequestDto;
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

import static com.pragma.powerup.usermicroservice.configuration.Constants.EMPLOYEE_ROLE_ID;

@RestController
@RequestMapping("/user-owner")
@RequiredArgsConstructor
@SecurityRequirement(name = "jwt")
public class UserOwnerRestController {

    private final IUserHandler personHandler;

    @Operation(summary = "Add a new Employee",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Employee created",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Map"))),
                    @ApiResponse(responseCode = "409", description = "Person already exists",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error"))),

            })
    @PostMapping("{restaurantId}/add-employee")
    public ResponseEntity<Map<String, String>> saveOwner(@PathVariable String restaurantId, @Valid @RequestBody @io.swagger.v3.oas.annotations.media.Schema(
            description = "The request body",
            example = UserRequestDto.example

    ) UserRequestDto userRequestDto) {

        personHandler.saveUserWithRole(userRequestDto, EMPLOYEE_ROLE_ID);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Collections.singletonMap(Constants.RESPONSE_MESSAGE_KEY, Constants.PERSON_CREATED_MESSAGE));
    }
}
