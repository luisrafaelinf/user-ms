package com.nisum.user.application.repository;

import java.util.Optional;

import com.nisum.user.domain.model.command.UserCommand.Create;
import com.nisum.user.domain.model.response.NewUserResponse;
import com.nisum.user.infrastructure.persistence.model.User;

public interface UserRepository {

    public User finByUserName(String username);

    public NewUserResponse create(Create command);

    public Optional<User> finByEmail(String email);

}
