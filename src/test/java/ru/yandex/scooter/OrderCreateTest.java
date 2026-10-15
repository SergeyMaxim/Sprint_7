package ru.yandex.scooter;

import io.qameta.allure.junit5.AllureJunit5;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import ru.yandex.scooter.client.OrderClient;
import ru.yandex.scooter.model.Order;

import java.util.List;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.notNullValue;

@ExtendWith(AllureJunit5.class)
public class OrderCreateTest {
    private OrderClient orderClient;

    @BeforeEach
    public void setUp() {
        orderClient = new OrderClient();
    }

    // Источник данных для параметризации: BLACK, GREY, оба цвета, без цвета
    public static Stream<List<String>> colorData() {
        return Stream.of(
                List.of("BLACK"),
                List.of("GREY"),
                List.of("BLACK", "GREY"),
                List.of()
        );
    }

    @ParameterizedTest
    @MethodSource("colorData")
    @DisplayName("Создание заказа с различными вариациями цветов")
    public void createOrderWithDifferentColors(List<String> colors) {
        Order order = new Order(
                "Naruto", "Uzumaki", "Konoha 142", "4",
                "+7 800 555 35 35", 5, "2026-10-15", "Comment", colors
        );

        Response response = orderClient.create(order);
        // Проверяем, что в ответе возвращается track номер
        response.then().statusCode(201).body("track", notNullValue());
    }
}