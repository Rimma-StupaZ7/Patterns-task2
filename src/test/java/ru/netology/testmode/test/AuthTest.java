package ru.netology.testmode.test;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.appear;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.testmode.data.DataGenerator.Registration.getRegisteredUser;
import static ru.netology.testmode.data.DataGenerator.Registration.getUser;
import static ru.netology.testmode.data.DataGenerator.getLogin;
import static ru.netology.testmode.data.DataGenerator.getPassword;

public class AuthTest{

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    void shouldSucсsessfulLoginRegisteredUser() {
        var registereduser = getRegisteredUser("active");
        $("[data-test-id='login'] input").setValue(registereduser.getLogin());
        $("[data-test-id='password'] input").setValue(registereduser.getPassword());
        $("button.button").click();
        $(withText("Личный кабинет")).shouldBe(appear);

    }

    @Test
    void shouldSucсsessfulLoginNotRegisteredUser() {
        var notregistereduser = getUser("active");
        $("[data-test-id='login'] input").setValue(notregistereduser.getLogin());
        $("[data-test-id='password'] input").setValue(notregistereduser.getPassword());
        $("button.button").click();
        $("[data-test-id='error-notification']")
                .shouldHave(Condition.text("Ошибка! Неверно указан логин или пароль"))
                .shouldBe((Condition.visible));

    }

    @Test
    void shouldSucсsessfulLoginRegisteredBlockedUser() {
        var registereduser = getRegisteredUser("blocked");
        $("[data-test-id='login'] input").setValue(registereduser.getLogin());
        $("[data-test-id='password'] input").setValue(registereduser.getPassword());
        $("button.button").click();
        $("[data-test-id='error-notification']")
                .shouldHave(Condition.text("Ошибка! Пользователь заблокирован"))
                .shouldBe((Condition.visible));

    }

    @Test
    void shouldWrongLoginRegisteredUser() {
        var registereduser = getRegisteredUser("active");
        var wrongLogin = getLogin();
        $("[data-test-id='login'] input").setValue(wrongLogin);
        $("[data-test-id='password'] input").setValue(registereduser.getPassword());
        $("button.button").click();
        $("[data-test-id='error-notification']")
                .shouldHave(Condition.text("Ошибка! Неверно указан логин или пароль"))
                .shouldBe((Condition.visible));

    }

    @Test
    void shouldWrongPasswordRegisteredUser() {
        var registereduser = getRegisteredUser("active");
        var wrongPassword = getPassword();
        $("[data-test-id='login'] input").setValue(registereduser.getLogin());
        $("[data-test-id='password'] input").setValue(wrongPassword);
        $("button.button").click();
        $("[data-test-id='error-notification']")
                .shouldHave(Condition.text("Ошибка! Неверно указан логин или пароль"))
                .shouldBe((Condition.visible));

    }

}
