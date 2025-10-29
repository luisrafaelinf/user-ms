package com.nisum.user.infrastructure.api.request;

import java.util.List;

import com.nisum.user.domain.validation.ValidEmail;
import com.nisum.user.domain.validation.ValidPassword;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record NewUserRequest(
    
    @NotNull(message = "Debe especificar el nombre del usuario.")
    @NotBlank(message = "Debe especificar el nombre del usuario.")
    String name,

    @NotNull(message = "Debe especificar el correo electrónico.")
    @NotBlank(message = "Debe especificar el correo electrónico.")
    @ValidEmail(message = "Formato de correo inválido.")
    String email,

    @NotNull(message = "Debe especificar la contraseña.")
    @NotBlank(message = "Debe especificar la contraseña.")
    @ValidPassword(message = "Formato de contraseña inválido. Debe especificar minúscula, mayúscula, números y caracteres especiales con longitud minima de 8 caracteres.")
    String password,
    
    @Size(min = 1, message = "Debe especificar al menos un teléfono.")
    List<@Valid PhoneRequest> phones
) {

}
