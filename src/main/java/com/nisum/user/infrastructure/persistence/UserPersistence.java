package com.nisum.user.infrastructure.persistence;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import com.nisum.user.application.repository.UserRepository;
import com.nisum.user.domain.exception.UserException;
import com.nisum.user.domain.model.command.UserCommand.Create;
import com.nisum.user.domain.model.response.NewUserResponse;
import com.nisum.user.infrastructure.persistence.converter.UserCommandCreateToUser;
import com.nisum.user.infrastructure.persistence.jpa.UserJpaPersistence;
import com.nisum.user.infrastructure.persistence.model.User;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Repository
public class UserPersistence implements UserRepository, UserDetailsService {

    private final UserJpaPersistence userJpaPersistence;
    private final UserCommandCreateToUser userCommandCreateToUser;
    
    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {

        User user = userJpaPersistence.findByUserEmailAndActive(username)
                    .orElseThrow(() -> new UserException.NotFound());

        return org.springframework.security.core.userdetails.User.builder()
				.username(user.getEmail())
				.password(user.getPassword())
				.authorities(new ArrayList<>())
				.build();
    }

    @Override
    public User finByUserName(final String username) {

        return userJpaPersistence.findByUserEmailAndActive(username)
                    .orElseThrow(() -> new UserException.NotFound());
    }

    @Override
    public NewUserResponse create(final Create command) {

        final User user = userCommandCreateToUser.convert(command);

        User userSaved = userJpaPersistence.saveAndFlush(user);
        
        return new NewUserResponse(
            userSaved.getId(), 
            userSaved.getCreatedDate().toLocalDate(),
            userSaved.getModifiedDate().toLocalDate(),
            userSaved.getLastLogin().toLocalDate(),
            userSaved.getToken(),
            userSaved.getActive()
            );
    }

    @Override
    public Optional<User> finByEmail(final String email) {

        return userJpaPersistence.findByEmail(email);

    }

}
