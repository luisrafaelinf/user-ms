package com.nisum.user.application.validation;

import org.springframework.stereotype.Component;

import com.nisum.user.domain.model.command.UserCommand;
import com.nisum.user.domain.model.command.UserCommand.Create;
import com.nisum.user.domain.util.Verificable;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class CreateUserValidation implements Verificable<UserCommand.Create> {

    private final EmailValidator emailValidator;
    
    @Override
    public void verify(Create entity) {

        emailValidator.validate()
            .apply(entity);
    }

}
