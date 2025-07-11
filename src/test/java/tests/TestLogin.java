package tests;

import org.junit.jupiter.api.*;
import pages.HomePage;
import pages.LoginPage;
import tags.pages.LoginTag;
import tags.types.RegressTag;
import io.qameta.allure.*;
import java.util.concurrent.TimeUnit;

@Epic(value = "Авторизация")
@Link("https://t.me/imwizyx")
@DisplayName("Тесты для авторизации пользователя")
@Timeout(value = 120000, unit = TimeUnit.MILLISECONDS)
public class TestLogin extends BaseTest {

    @Test
    @LoginTag
    @RegressTag
    @Feature(value = "Вход по логину и паролю")
    @Story(value = "Успешная авторизация с правильными данными")
    @Description("Тест проверяет успешное прохождение логина и что после нажатия на кнопку submit, пользователь попадает на домашнюю страницу")
    @DisplayName("Проверка на успешный логин с правильными данными")
    @Owner("Kirill Namestnikov")
    @Severity(value = SeverityLevel.BLOCKER)
    public void checkLoginWithValidCredentials() {
        HomePage homePage = new LoginPage(page)
                .open()
                .enterEmailAndPassword(getLogin(), getPassword())
                .submit();
        homePage.checkFriends();
        Assertions.assertTrue(homePage.isNotAvatarAvailable());
    }

    @Nested
    @Story("Неуспешная авторизация с неправильными данными")
    @DisplayName("Проверка на неуспешный логин")
    class InvalidTestLogin {

        @Test
        @LoginTag
        @RegressTag
        @Feature(value = "Получение ошибки при логине с неправильным логином и паролем")
        @DisplayName("Проверка на неуспешный логин с неправильными данными")
        @Description("Тест проверяет, что появляется ошибка, если попробовать пройти авторизацию с неправильным логином и паролем")
        @Owner("Kirill Namestnikov")
        @Severity(value = SeverityLevel.NORMAL)
        public void checkLoginWithInvalidCredentials() {
            new LoginPage(page)
                    .open()
                    .enterEmailAndPassword(getFailLogin(), getFailPassword())
                    .submitWithError()
                    .checkErrorMessage();
        }

        @Test
        @LoginTag
        @RegressTag
        @Feature(value = "Получение ошибки при логине с неправильным логином и правильным паролем")
        @DisplayName("Проверка на неуспешный логин с неправильным логином")
        @Description("Тест проверяет, что появляется ошибка, если попробовать пройти авторизацию с неправильным логином и правильным паролем")
        @Owner("Kirill Namestnikov")
        @Severity(value = SeverityLevel.NORMAL)
        public void checkLoginWithInvalidLogin() {
            new LoginPage(page)
                    .open()
                    .enterEmailAndPassword(getFailLogin(), getPassword())
                    .submitWithError()
                    .checkErrorMessage();
        }

        @Test
        @LoginTag
        @RegressTag
        @Feature(value = "Получение ошибки при логине с правильным логином и неправильным паролем")
        @DisplayName("Проверка на неуспешный логин с неправильным паролем")
        @Description("Тест проверяет, что появляется ошибка, если попробовать пройти авторизацию с правильным логином и неправильным паролем")
        @Owner("Kirill Namestnikov")
        @Severity(value = SeverityLevel.NORMAL)
        public void checkLoginWithInvalidPassword() {
            new LoginPage(page)
                    .open()
                    .enterEmailAndPassword(getLogin(), getFailPassword())
                    .submitWithError()
                    .checkErrorMessage();
        }

        @Test
        @LoginTag
        @RegressTag
        @Feature(value = "Получение ошибки при логине с пустым логином и паролем")
        @DisplayName("Проверка на неуспешный логин с пустыми данными")
        @Description("Тест проверяет, что появляется ошибка, если попробовать пройти авторизацию с пустым логином и паролем")
        @Owner("Kirill Namestnikov")
        @Severity(value = SeverityLevel.NORMAL)
        public void checkLoginWithEmptyCredentials() {
            new LoginPage(page)
                    .open()
                    .enterEmailAndPassword(getEmptyLogin(), getEmptyLogin())
                    .submitWithError()
                    .checkErrorMessage();
        }

        @Test
        @LoginTag
        @RegressTag
        @Feature(value = "Получение ошибки при логине с пустым логином и правильным паролем")
        @DisplayName("Проверка на неуспешный логин с пустым логином")
        @Description("Тест проверяет, что появляется ошибка, если попробовать пройти авторизацию с пустым логином и правильным паролем")
        @Owner("Kirill Namestnikov")
        @Severity(value = SeverityLevel.NORMAL)
        public void checkLoginWithEmptyLogin() {
            new LoginPage(page)
                    .open()
                    .enterEmailAndPassword(getEmptyLogin(), getPassword())
                    .submitWithError()
                    .checkErrorMessage();
        }

        @Test
        @LoginTag
        @RegressTag
        @Feature(value = "Получение ошибки при логине с правильным логином и пустым паролем")
        @DisplayName("Проверка на неуспешный логин с пустым паролем")
        @Description("Тест проверяет, что появляется ошибка, если попробовать пройти авторизацию с пустым логином и правильным паролем")
        @Owner("Kirill Namestnikov")
        @Severity(value = SeverityLevel.NORMAL)
        public void checkLoginWithEmptyPassword() {
            new LoginPage(page)
                    .open()
                    .enterEmailAndPassword(getLogin(), getEmptyPassword())
                    .submitWithError()
                    .checkErrorMessage();
        }
    }
}