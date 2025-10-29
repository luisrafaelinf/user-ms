package com.nisum.user.infrastructure.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record PhoneRequest(

    @NotNull(message = "Número de teléfono inválido.")
    @NotEmpty(message = "Número de teléfono inválido.")
    String number,

    @JsonProperty("citycode")
    String cityCode,
    @JsonProperty("countrycode")
    String countryCode
) {

}
