package ru.yandex.scooter.client;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.yandex.scooter.model.Order;

import static io.restassured.RestAssured.given;

public class OrderClient extends BaseClient {
    private static final String ORDERS_PATH = "/api/v1/orders";

    @Step("Создание заказа (POST /api/v1/orders)")
    public Response create(Order order) {
        return given()
                .spec(getSpec())
                .body(order)
                .when()
                .post(ORDERS_PATH);
    }

    @Step("Получение списка заказов (GET /api/v1/orders)")
    public Response getList() {
        return given()
                .spec(getSpec())
                .when()
                .get(ORDERS_PATH);
    }
}