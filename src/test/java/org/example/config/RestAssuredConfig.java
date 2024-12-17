package org.example.config;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

import static io.restassured.filter.log.LogDetail.ALL;
import static io.restassured.filter.log.LogDetail.URI;
import static io.restassured.http.ContentType.JSON;

/**
 * @Configuration: Аннотация, которая обозначает класс как конфигурационный, содержащий определения
 * бинов Spring.
 * @Bean: Эта аннотация указывает, что метод возвращает объект, управляемый Spring container.
 * @PostConstruct:
 */
@Data
@Configuration
@ComponentScan("org.example")
public class RestAssuredConfig {

    @Bean
    public AllureRestAssured allureRestAssured() {
        return new AllureRestAssured();
    }

    @Bean
    public RequestSpecification requestSpec() {
        return new RequestSpecBuilder()
                .log(URI)
                .setContentType(JSON)
                .build();
    }

    @PostConstruct
    private void postConstruct() {
        RestAssured.requestSpecification = requestSpec();
        RestAssured.useRelaxedHTTPSValidation();
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails(ALL);
    }
}
