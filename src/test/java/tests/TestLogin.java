package tests;

import org.junit.jupiter.api.*;
import pages.HomePage;
import pages.LoginPage;
import tags.LoginTag;

public class TestLogin extends BaseTest {

    @Test
    @LoginTag
    @DisplayName("Проверка на успешный логин с правильными данными")
    public void checkLoginWithValidCredentials() {
        HomePage homePage = new LoginPage(page)
                .open()
                .enterEmailAndPassword(getLogin(), getPassword())
                .submit();
        homePage.checkFriends();
        Assertions.assertTrue(homePage.isNotAvatarAvailable());
    }

    @Nested
    @DisplayName("Проверка на неуспешный логин")
    class InvalidTestLogin {

        @Test
        @LoginTag
        @DisplayName("Проверка на неуспешный логин с неправильными данными")
        public void checkLoginWithInvalidCredentials() {
            new LoginPage(page)
                    .open()
                    .enterEmailAndPassword(getFailLogin(), getFailPassword())
                    .submitWithError()
                    .checkErrorMessage();
        }

        @Test
        @LoginTag
        @DisplayName("Проверка на неуспешный логин с неправильным логином")
        public void checkLoginWithInvalidLogin() {
            new LoginPage(page)
                    .open()
                    .enterEmailAndPassword(getFailLogin(), getPassword())
                    .submitWithError()
                    .checkErrorMessage();
        }

        @Test
        @LoginTag
        @DisplayName("Проверка на неуспешный логин с неправильным паролем")
        public void checkLoginWithInvalidPassword() {
            new LoginPage(page)
                    .open()
                    .enterEmailAndPassword(getLogin(), getFailPassword())
                    .submitWithError()
                    .checkErrorMessage();
        }

        @Test
        @LoginTag
        @DisplayName("Проверка на неуспешный логин с пустыми данными")
        public void checkLoginWithEmptyCredentials() {
            new LoginPage(page)
                    .open()
                    .enterEmailAndPassword(getEmptyLogin(), getEmptyLogin())
                    .submitWithError()
                    .checkErrorMessage();
        }

        @Test
        @LoginTag
        @DisplayName("Проверка на неуспешный логин с пустым логином")
        public void checkLoginWithEmptyLogin() {
            new LoginPage(page)
                    .open()
                    .enterEmailAndPassword(getEmptyLogin(), getPassword())
                    .submitWithError()
                    .checkErrorMessage();
        }

        @Test
        @LoginTag
        @DisplayName("Проверка на неуспешный логин с пустым паролем")
        public void checkLoginWithEmptyPassword() {
            new LoginPage(page)
                    .open()
                    .enterEmailAndPassword(getLogin(), getEmptyPassword())
                    .submitWithError()
                    .checkErrorMessage();
        }
    }
}