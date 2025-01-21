package org.example;

import io.qameta.allure.Feature;
import org.example.config.AtConfig;
import org.example.models.DataForCreateUser;
import org.example.models.User;
import org.example.models.UserWithJob;
import org.example.service.ReqResService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.of;

@SpringBootTest(classes = AtConfig.class)
public class ReqResTests {

    @Autowired
    private ReqResService reqResService;

    @Test
    @DisplayName("Проверка, что поля 'email', 'lastName' для всех User из списка - not null")
    void checkUsersData() {
        int page = 2;
        List<User> users = reqResService.getUsers(page);
        reqResService
                .checkEmailFieldIsNotNull(users)
                .checkLastNameFieldIsNotNull(users);
        
    }

    @ParameterizedTest(name = "Создание пользователя - {0} и проверка правильности заполнения полей")
    @MethodSource("provideDataForCreateUser")
    void checkUserCreation(DataForCreateUser data) {
        UserWithJob user = reqResService.createUserWithJob(data);
        reqResService
                .checkUserName(data, user)
                .checkUserJob(data, user);
    }

    private static Stream<Arguments> provideDataForCreateUser() {
        return Stream.of(
                of(new DataForCreateUser("John", "driver")),
                of(new DataForCreateUser("Mike", "builder"))
        );
    }
}