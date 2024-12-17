package org.example;

import org.example.config.AtConfig;
import org.example.models.User;
import org.example.service.ReqResService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest(classes = AtConfig.class)
public class ReqResTests {

    @Autowired
    private ReqResService reqResService;

    @Test
    @DisplayName("Проверка, что поля 'email', 'lastName' для всех User из списка - not null")
    void getUsersPage() {
        int page = 2;
        List<User> users = reqResService.getUsersPage(page);
        reqResService.checkEmailFieldIsNotNull(users)
                .checkLastNameFieldIsNotNull(users);
    }

    /*@ParameterizedTest(name = "Создание пользователя - {index}")
    @MethodSource("path-to-method")
    void createUser(*//*User user*//*) {
        // вызвать метод ReqResService через инжектированную переменную для отправки POST запроса
    }*/
}
