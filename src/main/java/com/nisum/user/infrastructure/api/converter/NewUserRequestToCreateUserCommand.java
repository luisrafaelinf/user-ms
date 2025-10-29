package com.nisum.user.infrastructure.api.converter;

import java.util.List;
import java.util.function.Function;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import com.nisum.user.domain.model.command.UserCommand;
import com.nisum.user.domain.model.command.UserCommand.Create;
import com.nisum.user.domain.model.command.UserCommand.PhoneUser;
import com.nisum.user.infrastructure.api.request.NewUserRequest;
import com.nisum.user.infrastructure.api.request.PhoneRequest;

@Component
public class NewUserRequestToCreateUserCommand implements Converter<NewUserRequest, UserCommand.Create> {

    @Override
    @Nullable
    public Create convert(final NewUserRequest source) {

        return new UserCommand.Create(
                source.name(),
                source.email(),
                source.password(), 
                parsePhones(source.phones())
                );
        
    }

    private List<UserCommand.PhoneUser> parsePhones(List<PhoneRequest> request) {

        return request.stream()
                .map(toPhoneuser())
                .toList();

    }

    private Function<PhoneRequest, PhoneUser> toPhoneuser() {
        return p -> new UserCommand.PhoneUser(p.number(), p.cityCode(), p.countryCode());
    }

}
