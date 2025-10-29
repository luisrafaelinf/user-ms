package com.nisum.user.infrastructure.api.converter;

import com.nisum.user.domain.entity.TokenGenerator;
import com.nisum.user.domain.model.command.UserCommand;
import com.nisum.user.fakedata.FakeUser;
import com.nisum.user.infrastructure.persistence.converter.UserCommandCreateToUser;
import com.nisum.user.infrastructure.persistence.model.Phone;
import com.nisum.user.infrastructure.persistence.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserCommandCreateToUserTest {

    @Mock
    private TokenGenerator tokenGenerator;

    private UserCommandCreateToUser converter;
    
    private final String expectedToken = "creadotokenfalsoparaprueba";

    @BeforeEach
    void setUp() {
        
        this.converter = new UserCommandCreateToUser(tokenGenerator);
    }

    @Test
    void testConvertUserCommandToUserEntity() {

        UserCommand.Create userCommand = FakeUser.userCommandCreate();

        
        when(tokenGenerator.generate(userCommand.getEmail())).thenReturn(expectedToken);

        User userEntity = converter.convert(userCommand);

        assertNotNull(userEntity, "La entidad User no debe ser nula");
        assertEquals(userCommand.getName(), userEntity.getName());
        assertEquals(userCommand.getEmail(), userEntity.getEmail());
        assertEquals(userCommand.getPassword(), userEntity.getPassword());
        assertTrue(userEntity.getActive());
        assertNotNull(userEntity.getCreatedDate());
        assertNotNull(userEntity.getModifiedDate());
        assertNotNull(userEntity.getLastLogin());
        assertEquals(expectedToken, userEntity.getToken());

        List<Phone> phones = userEntity.getPhones();
        assertNotNull(phones);
        assertFalse(phones.isEmpty());
        assertEquals(1, phones.size());
        
        Phone phone = phones.getFirst();
        UserCommand.PhoneUser phoneExpected = userCommand.getPhones().getFirst();
        
        assertEquals(phoneExpected.getNumber(), phone.getNumber());
        assertEquals(phoneExpected.getCityCode(), phone.getCityCode());
        assertEquals(phoneExpected.getCountryCode(), phone.getCountryCode());
        assertEquals(userEntity, phone.getUser(), "El usuario debe estar asignado al teléfono");
    }

    @Test
    void TestEmptyPhoneList() {
        
        UserCommand.Create userCommand = FakeUser.userCommandCreate();
        userCommand.setPhones(List.of());

        when(tokenGenerator.generate(userCommand.getEmail())).thenReturn(expectedToken);

        User userEntity = converter.convert(userCommand);

        assertNotNull(userEntity);
        assertTrue(userEntity.getPhones().isEmpty(), "La lista de teléfonos debe estar vacía");
    }
}