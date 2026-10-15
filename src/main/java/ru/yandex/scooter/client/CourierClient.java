package ru.yandex.scooter.client;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.yandex.scooter.model.Courier;
import ru.yandex.scooter.model.CourierCredentials;

import static io.restassured.RestAssured.given;

public class CourierClient extends BaseClient {
    private static final String COURIER_PATH = "/api/v1/courier";

    @Step("Создание курьера (POST /api/v1/courier)")
    public Response create(Courier courier) {
        return given()
                .spec(getSpec())
                .body(courier)
                .when()
                .post(COURIER_PATH);
    }

    @Step("Авторизация курьера (POST /api/v1/courier/login)")
    public Response login(CourierCredentials credentials) {
        return given()
                .spec(getSpec())
                .body(credentials)
                .when()
                .post(COURIER_PATH + "/login");
    }

    @Step("Удаление курьера по ID (DELETE /api/v1/courier/{courierId})")
    public Response delete(int courierId) {
        return given()
                .spec(getSpec())
                .when()
                .delete(COURIER_PATH + "/" + courierId);
    }
}