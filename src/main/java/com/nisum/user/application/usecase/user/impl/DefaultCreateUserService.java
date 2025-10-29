package com.nisum.user.application.usecase.user.impl;

import org.springframework.stereotype.Service;

import com.nisum.user.application.repository.UserRepository;
import com.nisum.user.application.usecase.user.CreateUserService;
import com.nisum.user.application.validation.CreateUserValidation;
import com.nisum.user.domain.model.command.UserCommand;
import com.nisum.user.domain.model.response.NewUserResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class DefaultCreateUserService implements CreateUserService {

    private final UserRepository userRepository;
    private final CreateUserValidation createUserValidation;

    @Override
    public NewUserResponse process(final UserCommand.Create command) {

        createUserValidation.verify(command);
        
        final NewUserResponse newUserResponse = userRepository.create(command);

        return newUserResponse;

    }

}
