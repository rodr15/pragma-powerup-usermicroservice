package com.ti.acelera.usermicroservice.configuration;

import com.ti.acelera.usermicroservice.adapters.driven.jpa.mysql.exception.MailAlreadyExistsException;
import com.ti.acelera.usermicroservice.adapters.driven.jpa.mysql.exception.NoDataFoundException;
import com.ti.acelera.usermicroservice.adapters.driven.jpa.mysql.exception.PersonAlreadyExistsException;
import com.ti.acelera.usermicroservice.adapters.driven.jpa.mysql.exception.PersonNotFoundException;
import com.ti.acelera.usermicroservice.adapters.driven.jpa.mysql.exception.RoleNotAllowedForCreationException;
import com.ti.acelera.usermicroservice.adapters.driven.jpa.mysql.exception.RoleNotFoundException;
import com.ti.acelera.usermicroservice.adapters.driven.jpa.mysql.exception.UserAlreadyExistsException;
import com.ti.acelera.usermicroservice.adapters.driven.jpa.mysql.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collections;
import java.util.Map;

import static com.ti.acelera.usermicroservice.configuration.Constants.MAIL_ALREADY_EXISTS_MESSAGE;
import static com.ti.acelera.usermicroservice.configuration.Constants.NO_DATA_FOUND_MESSAGE;
import static com.ti.acelera.usermicroservice.configuration.Constants.PERSON_ALREADY_EXISTS_MESSAGE;
import static com.ti.acelera.usermicroservice.configuration.Constants.PERSON_NOT_FOUND_MESSAGE;
import static com.ti.acelera.usermicroservice.configuration.Constants.RESPONSE_ERROR_MESSAGE_KEY;
import static com.ti.acelera.usermicroservice.configuration.Constants.ROLE_NOT_ALLOWED_MESSAGE;
import static com.ti.acelera.usermicroservice.configuration.Constants.ROLE_NOT_FOUND_MESSAGE;
import static com.ti.acelera.usermicroservice.configuration.Constants.USER_ALREADY_EXISTS_MESSAGE;
import static com.ti.acelera.usermicroservice.configuration.Constants.USER_NOT_FOUND_MESSAGE;

@ControllerAdvice
public class ControllerAdvisor {
    @ExceptionHandler(NoDataFoundException.class)
    public ResponseEntity<Map<String, String>> handleNoDataFoundException(NoDataFoundException noDataFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, NO_DATA_FOUND_MESSAGE));
    }
    @ExceptionHandler(PersonAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handlePersonAlreadyExistsException(
            PersonAlreadyExistsException personAlreadyExistsException) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, PERSON_ALREADY_EXISTS_MESSAGE));
    }

    @ExceptionHandler(MailAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleMailAlreadyExistsException(
            MailAlreadyExistsException mailAlreadyExistsException) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, MAIL_ALREADY_EXISTS_MESSAGE));
    }
    @ExceptionHandler(PersonNotFoundException.class)
    public ResponseEntity<Map<String, String>> handlePersonNotFoundException(
            PersonNotFoundException personNotFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, PERSON_NOT_FOUND_MESSAGE));
    }
    @ExceptionHandler(RoleNotAllowedForCreationException.class)
    public ResponseEntity<Map<String, String>> handleRoleNotAllowedForCreationException(
            RoleNotAllowedForCreationException roleNotAllowedForCreationException) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, ROLE_NOT_ALLOWED_MESSAGE));
    }
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleUserAlreadyExistsException(
            UserAlreadyExistsException userAlreadyExistsException) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, USER_ALREADY_EXISTS_MESSAGE));
    }
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleUserNotFoundException(
            UserNotFoundException userNotFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, USER_NOT_FOUND_MESSAGE));
    }
    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleRoleNotFoundException(
            RoleNotFoundException roleNotFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, ROLE_NOT_FOUND_MESSAGE));
    }
}
