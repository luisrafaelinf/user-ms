
package com.nisum.user.application.usecase.user.impl;

import com.nisum.user.Application;
import com.nisum.user.application.usecase.user.CreateUserService;
import com.nisum.user.domain.exception.UserException;
import com.nisum.user.domain.model.command.UserCommand;
import com.nisum.user.domain.model.response.NewUserResponse;
import com.nisum.user.fakedata.FakeUser;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ContextConfiguration(classes = Application.class)
@TestPropertySource("classpath:application.properties")
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DefaultCreateUserServiceTest {
    
    @Autowired
    private CreateUserService createUserService;
    
    
    public DefaultCreateUserServiceTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }
    
    @Test
    @Order(1)
    public void testProcess() {
        
        UserCommand.Create user = FakeUser.userCommandCreate();
        
        NewUserResponse newUser = createUserService.process(user);
        
        MatcherAssert.assertThat(newUser, Matchers.notNullValue());
        MatcherAssert.assertThat(newUser.token(), Matchers.notNullValue());
        
    }
    
    @Test
    @Order(2)
    public void testProcessWithEmailExists() {
        
        UserCommand.Create user = FakeUser.userCommandCreate();
        
        assertThrows(UserException.EmailAlreadyExists.class, () -> {
            createUserService.process(user);        
        }, "Email exits should be throw");
        
    }
    
}
