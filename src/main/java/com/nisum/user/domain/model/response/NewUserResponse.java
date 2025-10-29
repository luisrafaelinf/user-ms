package com.nisum.user.domain.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

public record NewUserResponse(
    UUID id,
    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate created,
    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate modified,
    @JsonProperty("last_login")
    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate lastLogin,
    String token,
    boolean isActive
) {

}
