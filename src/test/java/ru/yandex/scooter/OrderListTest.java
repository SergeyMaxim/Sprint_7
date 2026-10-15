package ru.yandex.scooter;

import io.qameta.allure.junit5.AllureJunit5;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import ru.yandex.scooter.client.OrderClient;

import static org.hamcrest.Matchers.notNullValue;

@ExtendWith(AllureJunit5.class)
public class OrderListTest {
    private OrderClient orderClient;

    @BeforeEach
    public void setUp() {
        orderClient = new OrderClient();
    }

    @Test
    @DisplayName("Запрос списка заказов возвращает массив orders")
    public void getOrderListReturnsOrders() {
        Response response = orderClient.getList();
        response.then().statusCode(200).body("orders", notNullValue());
    }
}