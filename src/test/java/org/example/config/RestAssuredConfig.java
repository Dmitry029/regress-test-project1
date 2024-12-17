package org.example.config;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

import static io.restassured.filter.log.LogDetail.ALL;
import static io.restassured.filter.log.LogDetail.URI;
import static io.restassured.http.ContentType.JSON;

/**
 * @Configuration: аннотация указывает, что класс содержит методы определения @Bean.
 */
@Data
@Configuration
@ComponentScan("org.example")
public class RestAssuredConfig {

    @Value("${rest-assured.base-url}")
    private String baseUrl;

    @Value("${rest-assured.users-endpoint}")
    private String usersEndpoint;

    @Bean
    public AllureRestAssured allureRestAssured() {
        return new AllureRestAssured();
    }

    /**
     * @Bean используется в методах, которые создают объекты. Эти объекты затем автоматически регистрируются в
     * контексте Spring как бины.
     */
    @Bean
    public RequestSpecification requestSpec() {
        return new RequestSpecBuilder()
                .log(URI)
                .setContentType(JSON)
                .setBaseUri(baseUrl)
                .setBasePath(usersEndpoint)
                .build();
    }

    /**
     * @PostConstruct обеспечивает безопасную инициализацию объекта после его создания и внедрения всех зависимостей
     */
    @PostConstruct
    private void postConstruct() {
        RestAssured.requestSpecification = requestSpec();
        RestAssured.useRelaxedHTTPSValidation();
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails(ALL);
    }
}
