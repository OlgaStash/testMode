package ru.netology;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static ru.netology.util.DataGenerator.Registration.getRegisteredUser;
import static ru.netology.util.DataGenerator.Registration.getUser;
import static ru.netology.util.DataGenerator.generateLogin;
import static ru.netology.util.DataGenerator.generatePassword;


public class AuthTest {

    @BeforeEach
    public void setUp() {
        open("http://localhost:9999");
    }

    //Зарегистированный пользователь
    @Test
    public void shouldCheckRegisteredUser() {
        var registeredUser = getRegisteredUser("active");
        $("[data-test-id='login'] [class='input__control']").setValue(registeredUser.getLogin());
        $("[data-test-id='password'] [class='input__control']").setValue(registeredUser.getPassword());
        $$("button").find(text("Продолжить")).click();
        $(byText("Личный кабинет")).shouldBe(visible);
    }

    //Незарегистированный пользователь
    @Test
    public void shouldCheckNotRegisteredUser() {
        var notRegisteredUser = getUser("active");
        $("[data-test-id='login'] [class='input__control']").setValue(notRegisteredUser.getLogin());
        $("[data-test-id='password'] [class='input__control']").setValue(notRegisteredUser.getPassword());
        $$("button").find(text("Продолжить")).click();
        $(".notification_status_error").shouldBe(appear).shouldHave(text("Ошибка! Неверно указан логин или пароль"));
    }

    //Зарегистированный пользователь неверный логин
    @Test
    public void shouldCheckRegisteredUserInvalidLogin() {
        var registeredUser = getRegisteredUser("active");
        var invalidLogin = generateLogin();
        $("[data-test-id='login'] [class='input__control']").setValue(invalidLogin);
        $("[data-test-id='password'] [class='input__control']").setValue(registeredUser.getPassword());
        $$("button").find(text("Продолжить")).click();
        $(".notification_status_error").shouldBe(appear).shouldHave(text("Ошибка! Неверно указан логин или пароль"));
    }

    //Зарегистированный пользователь неверный пароль
    @Test
    public void shouldCheckRegisteredUserInvalidPassword() {
        var registeredUser = getRegisteredUser("active");
        var invalidPassword = generatePassword();
        $("[data-test-id='login'] [class='input__control']").setValue(registeredUser.getLogin());
        $("[data-test-id='password'] [class='input__control']").setValue(invalidPassword);
        $$("button").find(text("Продолжить")).click();
        $(".notification_status_error").shouldBe(appear).shouldHave(text("Ошибка! Неверно указан логин или пароль"));
    }

    //    Зарегистированный заблокированный пользователь
    @Test
    public void shouldCheckBlockedUser() {
        var blockedUser = getRegisteredUser("blocked");
        $("[data-test-id='login'] [class='input__control']").setValue(blockedUser.getLogin());
        $("[data-test-id='password'] [class='input__control']").setValue(blockedUser.getPassword());
        $$("button").find(text("Продолжить")).click();
        $(".notification_status_error").shouldBe(appear).shouldHave(text("Ошибка! Пользователь заблокирован"));
    }
}
