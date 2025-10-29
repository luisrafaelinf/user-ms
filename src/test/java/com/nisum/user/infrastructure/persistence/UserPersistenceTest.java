
package com.nisum.user.infrastructure.persistence;

import com.nisum.user.Application;
import com.nisum.user.application.repository.UserRepository;
import com.nisum.user.domain.model.command.UserCommand;
import com.nisum.user.domain.model.response.NewUserResponse;
import com.nisum.user.fakedata.FakeUser;
import com.nisum.user.infrastructure.persistence.jpa.UserJpaPersistence;
import com.nisum.user.infrastructure.persistence.model.User;
import java.util.Optional;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@ContextConfiguration(classes = Application.class)
@TestPropertySource("classpath:application.properties")
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserPersistenceTest {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private UserJpaPersistence jpaPersistence;
    
    private User user;
    
    public UserPersistenceTest() {
    }
    
    @BeforeAll
    public void setUpClass() {
        
        user = FakeUser.userCreate();
        
        jpaPersistence.saveAndFlush(user);
    }
    
    @AfterAll
    public void tearDownClass() {
        jpaPersistence.deleteAll();
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }
 
    @Test
    public void testFinByUserName() {
        
        User finByUserName = userRepository.finByUserName(user.getEmail());
        
        assertThat(finByUserName, notNullValue());
        assertEquals(user.getEmail(), finByUserName.getEmail());
    }

    @Test
    public void testCreate() {
        
        UserCommand.Create userCommandCreate = FakeUser.userCommandCreate();
        
        NewUserResponse create = userRepository.create(userCommandCreate);
        
        assertThat(create, notNullValue());
        assertThat(create.token(), notNullValue());
    }

    @Test
    public void testFinByEmail() {
        
        Optional<User> found = userRepository.finByEmail(user.getEmail());
        
        assertTrue(found.isPresent());
        assertEquals(FakeUser.userCreate().getEmail(), found.get().getEmail());
        
    }
    
}
