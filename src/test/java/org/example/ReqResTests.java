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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = AtConfig.class)
public class ReqResTests {

    @Autowired
    private ReqResService reqResService;


    @Test
    @DisplayName("Получение страницы со списком пользователей")
    void getUsersPage() {
        List<User> response = reqResService.getUsersPage();

        assertNotNull(response, "Response should not be null");
        assertEquals(HttpStatus.OK, response.getStatusCode(), "Status code should be 200 OK");

        List<User> users = response.getBody();
        assertNotNull(users, "Users list should not be null");
        assertFalse(users.isEmpty(), "Users list should not be empty");
    }
}

    /*@ParameterizedTest(name = "Создание пользователя - {index}")
    @MethodSource("path-to-method")
    void createUser(*//*User user*//*) {
        // вызвать метод ReqResService через инжектированную переменную для отправки POST запроса
    }*/
//}
