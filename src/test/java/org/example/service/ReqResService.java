package org.example.service;

import io.qameta.allure.Step;
import io.restassured.common.mapper.TypeRef;
import org.assertj.core.api.SoftAssertions;
import org.example.models.DataForCreateUser;
import org.example.models.User;
import org.example.models.UserWithJob;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@Service
public class ReqResService {
    private final SoftAssertions softly = new SoftAssertions();
    @Value("${rest-assured.base-url}")
    private String baseUrl;

    @Value("${rest-assured.users-endpoint}")
    private String usersEndpoint;

    @Step("Получить список пользователей со страницы: {0}")
    public List<User> getUsers(int page) {
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

    @Step("Создать пользователя")
    public UserWithJob getUserWithJob(DataForCreateUser data) {
        return given()
                .body(data)
                .when()
                .post(baseUrl + usersEndpoint)
                .then()
                .statusCode(201)
                .extract().as(new TypeRef<UserWithJob>() {
                });
    }

    @Step("Проверить, что у созданного пользователя правильное имя")
    public ReqResService checkUserName(DataForCreateUser data, UserWithJob user) {
        assertThat(data.getName())
                .as("Не совпадают имена пользователя: " + data.getName() + " и :" + user.getName())
                .isEqualTo(user.getName());
        return this;
    }

    @Step("Проверить, что у созданного пользователя правильная профессия")
    public void checkUserJob(DataForCreateUser data, UserWithJob user) {
        assertThat(data.getJob())
                .as("Не совпадает профессия пользователя: " + data.getJob() + " и :" + user.getJob())
                .isEqualTo(user.getJob());
    }
}