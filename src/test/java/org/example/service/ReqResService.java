package org.example.service;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import org.assertj.core.api.SoftAssertions;
import org.example.models.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

import static io.restassured.RestAssured.given;

@Service
public class ReqResService {
private final SoftAssertions softly = new SoftAssertions();
    @Value("${rest-assured.base-url}")
    private String baseUrl;

    @Value("${rest-assured.users-endpoint}")
    private String usersEndpoint;

    @Step("Получить список пользователей со страницы: {0}")
    public List<User> getUsersPage(int page) {
        return given()
                .queryParam("limit", page)
                .when()
                .get(baseUrl + usersEndpoint)
                .then()
                .statusCode(200)
                .extract().jsonPath().getList("data", User.class);
    }

    @Step("Проверить, что поле 'email' not null")
    public ReqResService checkEmailFieldIsNotNull(List<User> users) {
        users.forEach(user ->
                softly.assertThat(user.getEmail())
                        .as("Email не должно быть null для user с ID: " + user.getId())
                        .isNotNull()
        );
        softly.assertAll();
        return this;
    }

    @Step("Проверить, что поле 'lastName' not null")
    public void checkLastNameFieldIsNotNull(List<User> users) {
        users.forEach(user ->
                softly.assertThat(user.getLast_name())
                        .as("LastName не должно быть null для user с ID: " + user.getId())
                        .isNotNull()
        );
        softly.assertAll();
    }

}
// Метод для выполнения POST запроса


// Подключить нужные property из application.yaml через Spring - @Value


// Создать здесь методы (аннотированные @Step) для GET и POST запросов, используя REST Assured

// Здесь же можно сделать и другие шаги с проверками
// В методах для GET и POST запросов, а также в методах с проверками можно возвращать - return this, и в тестах вызывать их по цепочке

// Либо свой вариант


