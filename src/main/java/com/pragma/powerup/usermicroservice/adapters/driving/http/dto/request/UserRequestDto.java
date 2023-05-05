
package com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@AllArgsConstructor
@Getter
public class UserRequestDto {
    @NotBlank(message = "Mandatory")
    private String name;
    @NotBlank(message = "Mandatory")
    private String surname;
    @NotBlank(message = "Mandatory")
    private String dniNumber;
    @Size(min = 13 , max = 13,message = "Phone number must have 13 characters")
    @NotBlank(message = "Mandatory")
    private String phone;
    @Past(message = "The date must before today")
    @NotNull(message = "Mandatory")
    private Date birthDate;
    @Email(message = "Invalid Email")
    @NotBlank(message = "Mandatory")
    private String mail;
    @NotBlank(message = "Mandatory")
    private String password;



    public static final String example =  "{" +
            "\"name\":\"John\"," +
            "\"surname\":\"Doe\"," +
            "\"dniNumber\":\"1231231231\"," +
            "\"phone\":\"+573000000000\"," +
            "\"birthDate\":\"1980-05-04T20:04:26.908597\"," +
            "\"mail\":\"john@example.com\"," +
            "\"password\":\"secret\"" +
            "}";


}



