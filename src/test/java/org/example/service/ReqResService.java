package org.example.service;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.example.models.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ReqResService {

    @Value("${rest-assured.base-url}")
    private String baseUrl;

    @Value("${rest-assured.users-endpoint}")
    private String usersEndpoint;

    // Метод для выполнения GET запроса
    @Step("Fetching user list with optional query parameters: {0}")
    public List<User> getUsersPage(Map<String, String> queryParams) {
        RequestSpecification request = RestAssured.given()
                .baseUri(baseUrl);

        if (queryParams != null) {
            request.queryParams(queryParams);
        }

        return request.get(usersEndpoint);
    }

    // Метод для выполнения POST запроса
    @Step("Creating a new user with data: {0}")
    public Response createUser(Map<String, Object> requestBody) {
        return RestAssured.given()
                .baseUri(baseUrl)
                .contentType("application/json")
                .body(requestBody)
                .post(usersEndpoint);
    }


    // Подключить нужные property из application.yaml через Spring - @Value


    // Создать здесь методы (аннотированные @Step) для GET и POST запросов, используя REST Assured

    // Здесь же можно сделать и другие шаги с проверками
    // В методах для GET и POST запросов, а также в методах с проверками можно возвращать - return this, и в тестах вызывать их по цепочке

    // Либо свой вариант

}
