package com.nisum.user.fakedata;

import com.nisum.user.domain.model.command.UserCommand;
import com.nisum.user.infrastructure.api.request.NewUserRequest;
import com.nisum.user.infrastructure.api.request.PhoneRequest;
import com.nisum.user.infrastructure.persistence.model.Phone;
import com.nisum.user.infrastructure.persistence.model.User;
import java.util.List;

public final class FakeUser {
    
    public static User userCreate() {
    
        final Phone phone = new Phone("8094562147", "1", "1");
        
        final User user = new User();
        user.setName("Luis Suarez");
        user.setPassword("password");
        user.setActive(true);
        user.setEmail("lsuarez@gmail.com");
        user.setToken("sjhdgvhkdsbkfvjhgbdkjshvkldnsl");
        
        return user;
        
    }
    
    public static UserCommand.Create userCommandCreate() {
    
        final UserCommand.PhoneUser phone = new UserCommand.PhoneUser("8094562147", "1", "1");
        
        final UserCommand.Create user = new UserCommand.Create();
        user.setName("Luis Suarez");
        user.setPassword("password");
        user.setEmail("lsuarez@gmail213.com");
        user.setPhones(List.of(phone));
        
        return user;
        
    }
    
    public static NewUserRequest userRequestCreateValidPassword() {
    
        final PhoneRequest phone = new PhoneRequest("8094562147", "1", "1");
        
        final NewUserRequest user = new NewUserRequest("Luis Suarez", "lsuarez@gmail.com", "Password@1", List.of(phone));
        
        return user;
        
    }
    
    public static NewUserRequest userRequestCreateInvalidPassword() {
    
        final PhoneRequest phone = new PhoneRequest("8094562147", "1", "1");
        
        final NewUserRequest user = new NewUserRequest("Luis Suarez", "lsuarez@gmail.com", "Passwor", List.of(phone));
        
        return user;
        
    }

}
