package com.ti.acelera.usermicroservice.adapters.driving.http.dto.request;

import javax.validation.constraints.NotBlank;

public class LoginUsuario {
    @NotBlank
    private String userDni;
    @NotBlank
    private String password;

    public String getUserDni() {
        return userDni;
    }
    public void setNombreUsuario(String userDni) {
        this.userDni = userDni;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
