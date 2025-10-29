package com.nisum.user.infrastructure.persistence.jpa;

import com.nisum.user.fakedata.FakeUser;
import com.nisum.user.infrastructure.persistence.model.User;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class UserJpaPersistenceTest {
    
    @Autowired(required = true)
    private UserJpaPersistence userPersistence;
     
    @Autowired
    private TestEntityManager entityManager;
    
    public UserJpaPersistenceTest() {
    }

    
    @BeforeEach
    public void setUp() {
        
        User user = FakeUser.userCreate();
        
        entityManager.persistAndFlush(user);
    }
    
    @AfterEach
    public void tearDown() {
        entityManager.clear();
        userPersistence.deleteAll();
    }

    @Test
    @DisplayName("Find user by email that is active")
    public void testFindByUserEmailAndActive() {

        Optional<User> userFound = userPersistence.findByUserEmailAndActive(FakeUser.userCreate().getEmail());

        assertTrue(userFound.isPresent());
        assertEquals(FakeUser.userCreate().getEmail(), userFound.get().getEmail());
        
    }

    @Test
    @DisplayName("Find user by email only")
    public void testFinByEmail() {
        
        Optional<User> userFound = userPersistence.findByEmail(FakeUser.userCreate().getEmail());
        
        assertTrue(userFound.isPresent());
        assertEquals(FakeUser.userCreate().getEmail(), userFound.get().getEmail());
    }
    
}
