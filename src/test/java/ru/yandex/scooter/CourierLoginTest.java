package ru.yandex.scooter;

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
import static org.hamcrest.Matchers.notNullValue;

@ExtendWith(AllureJunit5.class)
public class CourierLoginTest {
    private CourierClient courierClient;
    private Courier courier;

    @BeforeEach
    public void setUp() {
        courierClient = new CourierClient();
        // Подготовка данных: создаем курьера перед каждым тестом
        courier = new Courier("ninja_" + RandomStringUtils.randomAlphanumeric(6), "1234", "saske");
        courierClient.create(courier);
    }

    @AfterEach
    public void cleanUp() {
        // Очистка данных после теста
        if (courier != null && courier.getLogin() != null && courier.getPassword() != null) {
            Response loginResponse = courierClient.login(CourierCredentials.from(courier));
            if (loginResponse.statusCode() == 200) {
                int courierId = loginResponse.path("id");
                courierClient.delete(courierId);
            }
        }
    }

    @Test
    @DisplayName("Успешная авторизация курьера возвращает id")
    public void courierCanLogin() {
        Response response = courierClient.login(CourierCredentials.from(courier));
        response.then().statusCode(200).body("id", notNullValue());
    }

    @Test
    @DisplayName("Авторизация с неверным паролем возвращает ошибку 404")
    public void loginWithWrongPasswordReturnsError() {
        CourierCredentials wrongCreds = new CourierCredentials(courier.getLogin(), "wrong_pass");
        Response response = courierClient.login(wrongCreds);
        response.then().statusCode(404).body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Авторизация без логина возвращает ошибку 400")
    public void loginWithoutLoginReturnsError() {
        CourierCredentials noLoginCreds = new CourierCredentials("", courier.getPassword());
        Response response = courierClient.login(noLoginCreds);
        response.then().statusCode(400).body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Авторизация под несуществующим пользователем возвращает ошибку 404")
    public void loginNonExistentUserReturnsError() {
        CourierCredentials nonExistent = new CourierCredentials("non_existent_login_9999", "password");
        Response response = courierClient.login(nonExistent);
        response.then().statusCode(404).body("message", equalTo("Учетная запись не найдена"));
    }
}