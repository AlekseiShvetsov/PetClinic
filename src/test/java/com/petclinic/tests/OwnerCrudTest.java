package com.petclinic.tests;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@Epic("PetClinic API")
@Feature("Owner Management")
public class OwnerCrudTest extends BaseTest {

  @Test
  @Story("CRUD flow")
  @Description("Create, read, edit, delete and check dletion")
  void ownerCrudCheck() {

    Map<String, Object> newOwner = new HashMap<>();
    newOwner.put("firstName", "Aleksei");
    newOwner.put("lastName", "Shvetsov");
    newOwner.put("address", "Serbia");
    newOwner.put("city", "Novi Sad");
    newOwner.put("telephone", "8169834785");

    Integer ownerId =
        given()
            .contentType(ContentType.JSON)
            .body(newOwner)
            .when()
            .post("api/owners")
            .then()
            .statusCode(201)
            .extract()
            .jsonPath()
            .getInt("id");

    assertThat(ownerId).isPositive();

    Map<String, Object> readOwner =
        given()
            .when()
            .get("/api/owners/{id}", ownerId)
            .then()
            .statusCode(200)
            .extract()
            .jsonPath()
            .getMap("");

    assertThat(readOwner.get("firstName")).isEqualTo("Aleksei");
    assertThat(readOwner.get("lastName")).isEqualTo("Shvetsov");
    assertThat(readOwner.get("address")).isEqualTo("Serbia");
    assertThat(readOwner.get("city")).isEqualTo("Novi Sad");
    assertThat(readOwner.get("telephone")).isEqualTo("8169834785");

    Map<String, Object> updateOwner = new HashMap<>();
    updateOwner.put("firstName", "Alex");
    updateOwner.put("lastName", "Shvet");
    updateOwner.put("address", "Old Balkans");
    updateOwner.put("city", "Beograd");
    updateOwner.put("telephone", "7778855966");

    given()
        .contentType(ContentType.JSON)
        .body(updateOwner)
        .when()
        .put("/api/owners/{id}", ownerId)
        .then()
        .statusCode(204);

    given().when().delete("/api/owners/{id}", ownerId).then().statusCode(204);

    given().when().get("/api/owners/{id}", ownerId).then().statusCode(404);
  }
}
