package com.nisum.user.domain.model.command;

import java.util.List;

import com.nisum.user.domain.common.Command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public sealed interface UserCommand extends Command {

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static final class Create implements UserCommand {
        
        private String name;
        private String email;
        private String Password;
        private List<PhoneUser> phones;
        
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static final class PhoneUser implements UserCommand {

        private String number;
        private String cityCode;
        private String countryCode;
    }
}
