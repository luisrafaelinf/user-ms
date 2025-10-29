package com.nisum.user.infrastructure.api;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.any;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import com.nisum.user.Application;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
@ContextConfiguration(classes = {Application.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@NoArgsConstructor
public class UserControllerTest {

    @LocalServerPort
    int port;

    @BeforeAll
    public void setUpClass() {

        RestAssured.port = port;
        RestAssured.baseURI = "http://localhost";
    }

    @AfterAll
    public void tearDownClass() {
    }

    @Test
    @Order(1)
    @DisplayName("Create a new user")
    public void testCreate() {

        RestAssured.given()
                .body("""
                        {
                            "name": "Samuel Jimenez",
                            "email": "sjimenez@gmail.com",
                            "password": "hunterX@1",
                            "phones": [
                                {
                                "number": "1234567",
                                "citycode": "1",
                                "countrycode": "57"
                                }
                            ]
                        }
                        """)
                .contentType(ContentType.JSON)
                .when()
                .post("user-service/api/v1/user")
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(201)
                .contentType(ContentType.JSON)
                .body("id", any(String.class));

    }
   
    @Test
    @Order(2)
    @DisplayName("Create a new user with existent email")
    public void testCreateWithExistentEmail() {

        RestAssured.given()
                .body("""
                        {
                            "name": "Samuel Jimenez",
                            "email": "sjimenez@gmail.com",
                            "password": "hunterX@1",
                            "phones": [
                                {
                                "number": "1234567",
                                "citycode": "1",
                                "countrycode": "57"
                                }
                            ]
                        }
                        """)
                .contentType(ContentType.JSON)
                .when()
                .post("user-service/api/v1/user")
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(400)
                .contentType(ContentType.JSON)
                .body("mensaje", equalTo("Correo electronico ya existe."));

    }
   
    @Test
    @DisplayName("Create a new user with wrong password")
    public void testCreateWithWrongPassword() {

        RestAssured.given()
                .body("""
                        {
                            "name": "Samuel Jimenez",
                            "email": "sjimenezcorrea@gmail.com",
                            "password": "hunte",
                            "phones": [
                                {
                                "number": "1234567",
                                "citycode": "1",
                                "countrycode": "57"
                                }
                            ]
                        }
                        """)
                .contentType(ContentType.JSON)
                .when()
                .post("user-service/api/v1/user")
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(400);

    }
   
    @Test
    @DisplayName("Create a new user with wrong email")
    public void testCreateWithWrongEmail() {

        RestAssured.given()
                .body("""
                        {
                            "name": "Samuel Jimenez",
                            "email": "sjimenezcorrea@gmail",
                            "password": "hunte",
                            "phones": [
                                {
                                "number": "1234567",
                                "citycode": "1",
                                "countrycode": "57"
                                }
                            ]
                        }
                        """)
                .contentType(ContentType.JSON)
                .when()
                .post("user-service/api/v1/user")
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(400);

    }

}
