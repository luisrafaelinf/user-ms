package com.nisum.user.application.validation;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.nisum.user.application.repository.UserRepository;
import com.nisum.user.domain.exception.UserException;
import com.nisum.user.domain.model.command.UserCommand;
import com.nisum.user.domain.util.GenericValidator;
import com.nisum.user.infrastructure.persistence.model.User;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EmailValidator {

    private final UserRepository userRepository;

    public GenericValidator<UserCommand.Create> validate() {

        return request -> {

            Optional<User> user = userRepository.finByEmail(request.getEmail().trim().toLowerCase());

            if (user.isPresent()) {
                throw new UserException.EmailAlreadyExists();
            }

            return Void.TYPE;
        };
    }

}
