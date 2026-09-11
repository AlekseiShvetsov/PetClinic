package com.petclinic.tests;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

@Epic("PetClinic API")
@Feature("Health Check")
public class HealthCheckTest extends BaseTest {

  @Test
  @Story("Health endpoint")
  @Description("Verify that /actuator/health returns status UP")
  void healthCheck() {
    String status =
        given()
            .contentType(ContentType.JSON)
            .when()
            .get("/actuator/health")
            .then()
            .statusCode(200)
            .extract()
            .jsonPath()
            .getString("status");

    assertThat(status).isEqualTo("UP");
  }
}
