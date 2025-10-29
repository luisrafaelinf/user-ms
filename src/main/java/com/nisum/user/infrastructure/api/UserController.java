package com.nisum.user.infrastructure.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.nisum.user.application.usecase.user.CreateUserService;
import com.nisum.user.domain.model.command.UserCommand;
import com.nisum.user.domain.model.response.NewUserResponse;
import com.nisum.user.infrastructure.api.converter.NewUserRequestToCreateUserCommand;
import com.nisum.user.infrastructure.api.request.NewUserRequest;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "Usuario", description = "Controlador de procesos de manejador de usuario.")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value ="/v1/user", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    private final NewUserRequestToCreateUserCommand newUserRequestToCreateUserCommand;
    private final CreateUserService createUser;

    @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NewUserResponse create(
        @RequestBody @Valid NewUserRequest request
    ) {

        final UserCommand.Create userCommand = newUserRequestToCreateUserCommand.convert(request);

        return createUser.process(userCommand);
    }

}
