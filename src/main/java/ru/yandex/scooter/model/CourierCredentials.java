package ru.yandex.scooter.model;

public class CourierCredentials {
    private String login;
    private String password;

    public CourierCredentials() {}

    public CourierCredentials(String login, String password) {
        this.login = login;
        this.password = password;
    }

    // Вспомогательный метод для удобного формирования учётных данных из объекта Courier
    public static CourierCredentials from(Courier courier) {
        return new CourierCredentials(courier.getLogin(), courier.getPassword());
    }

    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}