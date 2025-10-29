package com.nisum.user.domain.validation;

import com.nisum.user.Application;
import com.nisum.user.fakedata.FakeUser;
import com.nisum.user.infrastructure.api.request.NewUserRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(classes = Application.class)

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ValidPasswordTest {
    
    @Autowired
    private Validator validator;

    @BeforeAll
    static void setUpAll() {
    }

    @Test
    void testInvalidPassword() {
        NewUserRequest invalidUser = FakeUser.userRequestCreateInvalidPassword();
        
        Set<ConstraintViolation<NewUserRequest>> violations = validator.validate(invalidUser);
        
        assertFalse(violations.isEmpty());
    }

    @Test
    void testValidPassword() {
        NewUserRequest invalidUser = FakeUser.userRequestCreateValidPassword();
        
        Set<ConstraintViolation<NewUserRequest>> violations = validator.validate(invalidUser);
        
        assertTrue(violations.isEmpty());
    }

}
