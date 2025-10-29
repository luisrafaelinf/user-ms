package com.nisum.user.domain.model.response;

import java.time.LocalDate;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

public record NewUserResponse(
    UUID id,
    LocalDate created,
    LocalDate modified,
    @JsonProperty("last_login")
    LocalDate lastLogin,
    String token,
    boolean isActive
) {

}
