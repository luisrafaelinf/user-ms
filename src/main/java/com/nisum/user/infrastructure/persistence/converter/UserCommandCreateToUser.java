package com.nisum.user.infrastructure.persistence.converter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Function;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.nisum.user.domain.entity.TokenGenerator;
import com.nisum.user.domain.model.command.UserCommand;
import com.nisum.user.domain.model.command.UserCommand.Create;
import com.nisum.user.domain.model.command.UserCommand.PhoneUser;
import com.nisum.user.infrastructure.persistence.model.Phone;
import com.nisum.user.infrastructure.persistence.model.User;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class UserCommandCreateToUser implements Converter<UserCommand.Create, User> {

    private final TokenGenerator tokenGenerator;

    @Override
    public User convert(Create source) {
        
        LocalDateTime datetime = LocalDateTime.now();

        final User user = new User();
        user.setName(source.getName());
        user.setEmail(source.getEmail());
        user.setPassword(source.getPassword());
        user.setActive(true);
        user.setModifiedDate(datetime);
        user.setCreatedDate(datetime);
        user.setLastLogin(datetime);
        user.setToken(tokenGenerator.generate(source.getEmail()));
        user.setPhones(parsePhones(source.getPhones(), user));

        return user;
    }

    private List<Phone> parsePhones(List<PhoneUser> request, User user) {

        return request.stream()
                .map(toPhone())
                .peek(p -> p.setUser(user))
                .toList();
    }

    private Function<PhoneUser, Phone> toPhone() {
        return p -> new Phone(p.getNumber(), p.getCityCode(), p.getCountryCode());
    }

}
