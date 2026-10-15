package ru.yandex.scooter.model;

public class Courier {
    private String login;
    private String password;
    private String firstName;

    // Пустой конструктор нужен библиотеке Jackson для десериализации
    public Courier() {}

    // Конструктор со всеми полями
    public Courier(String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }

    // Конструктор без имени (для негативных тестов или логина)
    public Courier(String login, String password) {
        this.login = login;
        this.password = password;
    }

    // Геттеры и сеттеры
    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
}