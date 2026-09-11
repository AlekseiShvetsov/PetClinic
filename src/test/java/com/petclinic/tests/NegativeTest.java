package com.petclinic.tests;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@Epic("PetClinic API")
@Feature("Owner Validation")
public class NegativeTest extends BaseTest {

  @Test
  @Story("Invalid owner creation")
  @Description("Owner with empty mandatory fields should return 400 with validation errors")
  void invalidCreatingOwner() {

    Map<String, Object> invalidOwner = new HashMap<>();
    invalidOwner.put("firstName", "Valeriia");
    invalidOwner.put("lastName", "");
    invalidOwner.put("address", "");
    invalidOwner.put("city", "");
    invalidOwner.put("telephone", "123");

    Response response =
        given()
            .contentType(ContentType.JSON)
            .body(invalidOwner)
            .when()
            .post("/api/owners")
            .then()
            .statusCode(400)
            .extract()
            .response();

    String title = response.jsonPath().get("title");
    assertThat(title).isEqualTo("MethodArgumentNotValidException");

    var errors = response.jsonPath().getList("schemaValidationErrors");
    assertThat(errors).isNotEmpty();

    String responseBody = response.getBody().asString();
    assertThat(responseBody).contains("lastName");
    assertThat(responseBody).contains("address");
    assertThat(responseBody).contains("city");
  }
}
