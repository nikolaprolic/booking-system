package com.booking.e2e;

import org.junit.jupiter.api.Disabled;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@Disabled("E2E tests require running services - run locally")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BookingSystemE2ETest {

    private static final String BASE_URL = "http://localhost:8080";

    @BeforeAll
    static void setUp() {
        RestAssured.baseURI = BASE_URL;
    }

    @Test
    @Order(1)
    void testUserServiceHealth() {
        given()
                .when()
                .get("/actuator/health")
                .then()
                .statusCode(200);
    }

    @Test
    @Order(2)
    void testCreateUser() {
        given()
                .contentType(ContentType.JSON)
                .body("{\"name\":\"Test User\",\"email\":\"test@e2e.com\",\"password\":\"test123\"}")
                .when()
                .post("/api/users")
                .then()
                .statusCode(200)
                .body("name", equalTo("Test User"))
                .body("email", equalTo("test@e2e.com"));
    }

    @Test
    @Order(3)
    void testCreateResource() {
        given()
                .contentType(ContentType.JSON)
                .body("{\"name\":\"E2E Sala\",\"description\":\"Test sala\",\"type\":\"sala\"}")
                .when()
                .post("/api/resources")
                .then()
                .statusCode(200)
                .body("name", equalTo("E2E Sala"))
                .body("available", equalTo(true));
    }

    @Test
    @Order(4)
    void testCreateBooking() {
        given()
                .contentType(ContentType.JSON)
                .body("{\"userId\":1,\"resourceId\":1,\"startTime\":\"2026-04-01T10:00:00\",\"endTime\":\"2026-04-01T12:00:00\"}")
                .when()
                .post("/api/bookings")
                .then()
                .statusCode(200)
                .body("status", equalTo("PENDING"));
    }

    @Test
    @Order(5)
    void testGetAllUsers() {
        given()
                .when()
                .get("/api/users")
                .then()
                .statusCode(200)
                .body("$", not(empty()));
    }

    @Test
    @Order(6)
    void testGetAllResources() {
        given()
                .when()
                .get("/api/resources")
                .then()
                .statusCode(200);
    }

    @Test
    @Order(7)
    void testGetAllBookings() {
        given()
                .when()
                .get("/api/bookings")
                .then()
                .statusCode(200);
    }
}
