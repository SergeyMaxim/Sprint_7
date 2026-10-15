package ru.yandex.scooter;

import io.qameta.allure.Description;
import io.qameta.allure.junit5.AllureJunit5;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import ru.yandex.scooter.client.CourierClient;
import ru.yandex.scooter.model.Courier;
import ru.yandex.scooter.model.CourierCredentials;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

@ExtendWith(AllureJunit5.class)
public class CourierCreateTest {
    private CourierClient courierClient;
    private Courier courier;

    @BeforeEach
    public void setUp() {
        courierClient = new CourierClient();
    }

    @AfterEach
    public void cleanUp() {
        // Критерий №13: Очистка данных после каждого теста
        if (courier != null && courier.getLogin() != null && courier.getPassword() != null) {
            Response loginResponse = courierClient.login(CourierCredentials.from(courier));
            if (loginResponse.statusCode() == 200) {
                int courierId = loginResponse.path("id");
                courierClient.delete(courierId);
            }
        }
    }

    @Test
    @DisplayName("Курьера можно создать")
    @Description("Проверка успешного создания курьера с возвратом статуса 201 и ok: true")
    public void courierCanBeCreated() {
        // Генерация случайных данных (Критерий №14: Без хардкода)
        courier = new Courier("ninja_" + RandomStringUtils.randomAlphanumeric(6), "1234", "saske");
        Response response = courierClient.create(courier);

        // Критерий №11: Проверка статуса и тела
        response.then().statusCode(201).body("ok", is(true));
    }

    @Test
    @DisplayName("Нельзя создать двух одинаковых курьеров")
    public void duplicateCourierCannotBeCreated() {
        courier = new Courier("ninja_" + RandomStringUtils.randomAlphanumeric(6), "1234", "saske");
        courierClient.create(courier);

        Response response = courierClient.create(courier);
        response.then().statusCode(409).body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
    }

    @Test
    @DisplayName("Создание курьера без логина возвращает ошибку")
    public void createCourierWithoutLoginReturnsError() {
        courier = new Courier(null, "1234", "saske");
        Response response = courierClient.create(courier);

        response.then().statusCode(400).body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Создание курьера без пароля возвращает ошибку")
    public void createCourierWithoutPasswordReturnsError() {
        courier = new Courier("ninja_" + RandomStringUtils.randomAlphanumeric(6), null, "saske");
        Response response = courierClient.create(courier);

        response.then().statusCode(400).body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }
}