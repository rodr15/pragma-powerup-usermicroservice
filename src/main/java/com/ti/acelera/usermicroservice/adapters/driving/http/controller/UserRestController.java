package com.ti.acelera.usermicroservice.adapters.driving.http.controller;

import com.ti.acelera.usermicroservice.adapters.driving.http.dto.request.UserRequest;
import com.ti.acelera.usermicroservice.adapters.driving.http.dto.response.PersonResponse;
import com.ti.acelera.usermicroservice.adapters.driving.http.mapper.IPersonResponseMapper;
import com.ti.acelera.usermicroservice.adapters.driving.http.mapper.IUserRequestMapper;
import com.ti.acelera.usermicroservice.domain.api.IUserServicePort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.ti.acelera.usermicroservice.configuration.Constants.RESPONSE_MESSAGE_KEY;
import static com.ti.acelera.usermicroservice.configuration.Constants.USER_CREATED_MESSAGE;
import static com.ti.acelera.usermicroservice.configuration.Constants.USER_DELETED_MESSAGE;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@SecurityRequirement(name = "jwt")
public class UserRestController {
    private final IUserServicePort userServicePort;
    private final IUserRequestMapper userRequestMapper;
    private final IPersonResponseMapper personResponseMapper;

    @Operation(summary = "Add a new user",
            responses = {
                    @ApiResponse(responseCode = "201", description = "User created",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Map"))),
                    @ApiResponse(responseCode = "409", description = "User already exists",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error"))),
                    @ApiResponse(responseCode = "403", description = "Role not allowed for user creation",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error")))})
    @PostMapping("")
    public ResponseEntity<Map<String, String>> saveUser(@RequestBody UserRequest userRequest) {
        userServicePort.saveUser(userRequestMapper.toUser(userRequest));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Collections.singletonMap(RESPONSE_MESSAGE_KEY, USER_CREATED_MESSAGE));
    }
    @Operation(summary = "Delete an user",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User deleted",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Map"))),
                    @ApiResponse(responseCode = "404", description = "User not found",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error")))})
    @DeleteMapping("")
    public ResponseEntity<Map<String, String>> deleteUser(@RequestBody UserRequest userRequest) {
        userServicePort.deleteUser(userRequestMapper.toUser(userRequest));
        return ResponseEntity.ok(Collections.singletonMap(RESPONSE_MESSAGE_KEY, USER_DELETED_MESSAGE));
    }
    @Operation(summary = "Get all the providers",
            responses = {
                    @ApiResponse(responseCode = "200", description = "All providers returned",
                            content = @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = PersonResponse.class)))),
                    @ApiResponse(responseCode = "404", description = "No data found",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error")))})
    @GetMapping("/provider")
    public ResponseEntity<List<PersonResponse>> getAllProviders(@Parameter(description = "Number of the page to list providers") @RequestParam int page) {
        return ResponseEntity.ok(personResponseMapper.userListToPersonResponseList(userServicePort.getAllProviders(page)));
    }
    @Operation(summary = "Get a provider user",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Provider user returned",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PersonResponse.class))),
                    @ApiResponse(responseCode = "404", description = "User not found with provider role",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error")))})
    @GetMapping("/provider/{id}")
    public ResponseEntity<PersonResponse> getProvider(@PathVariable Long id) {
        return ResponseEntity.ok(personResponseMapper.userToPersonResponse(userServicePort.getProvider(id)));
    }
    @Operation(summary = "Get a employee user",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Employee user returned",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PersonResponse.class))),
                    @ApiResponse(responseCode = "404", description = "User not found with employee role",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error")))})
    @GetMapping("/employee/{id}")
    public ResponseEntity<PersonResponse> getEmployee(@PathVariable Long id) {
        return ResponseEntity.ok(personResponseMapper.userToPersonResponse(userServicePort.getEmployee(id)));
    }
    @Operation(summary = "Get a client user",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Client user returned",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PersonResponse.class))),
                    @ApiResponse(responseCode = "404", description = "User not found with client role",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error")))})
    @GetMapping("/client/{id}")
    public ResponseEntity<PersonResponse> getClient(@PathVariable Long id) {
        return ResponseEntity.ok(personResponseMapper.userToPersonResponse(userServicePort.getClient(id)));
    }
}
