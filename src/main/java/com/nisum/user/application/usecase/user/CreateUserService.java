package com.nisum.user.application.usecase.user;

import com.nisum.user.domain.common.CommandCase;
import com.nisum.user.domain.model.command.UserCommand;
import com.nisum.user.domain.model.response.NewUserResponse;

public interface CreateUserService extends CommandCase<UserCommand.Create, NewUserResponse> {

}
