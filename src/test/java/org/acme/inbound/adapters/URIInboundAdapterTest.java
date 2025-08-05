package org.acme.inbound.adapters;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
class URIInboundAdapterTest {
    @Test // GET URIs returns empty array
    void shouldReturnAllUris() {
        given()
                .when().get("/uris")
                .then()
                .statusCode(200)
                .body("$", notNullValue()); // check non-empty response
    }

    @Test // GET URIs returns array with 'https://example.com' at index 0 an 'https://www.example.com' at index 1
    void shouldCreateUris() {
        String body = "https://example.com\nhttps://www.example.com";

        given()
                .contentType("text/plain")
                .body(body)
                .when().post("/uris")
                .then()
                .statusCode(200)
                .body("size()", is(2))
                .body("[0].uri", is("https://example.com"))
                .body("[1].uri", is("https://www.example.com"));
    }

    @Test // that a status code 200 is returned when scanning uris
    void shouldScanUris() {
        given()
                .queryParam("notify", "true")
                .when().get("/uris/scan")
                .then()
                .statusCode(200)
                .body("$", notNullValue());
    }
}