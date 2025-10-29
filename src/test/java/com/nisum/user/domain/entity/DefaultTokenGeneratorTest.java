package com.nisum.user.domain.entity;

import com.nisum.user.Application;
import com.nisum.user.fakedata.FakeUser;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import static org.hamcrest.Matchers.not;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(classes = Application.class)
public class DefaultTokenGeneratorTest {
    
    @Autowired
    private TokenGenerator tokenGenerator;
    
    public DefaultTokenGeneratorTest() {
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
    public void testGenerate() {
        
        String result = tokenGenerator.generate(FakeUser.userCommandCreate().getEmail());
        
        MatcherAssert.assertThat(result, Matchers.notNullValue());
        MatcherAssert.assertThat(result, not(Matchers.emptyString()));
    }
    
}
