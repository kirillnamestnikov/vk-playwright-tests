package pages;


import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.WaitForSelectorState;
import org.junit.jupiter.api.DisplayName;
import static constants.login.LoginValues.LOGIN_ERROR_MESSAGE_MASK;

public class LoginPage {
    private static final Logger log = LogManager.getLogger(LoginPage.class);
    private final Page page;
    private final Locator emailField;
    private final Locator passwordField;
    private final Locator submitButton;
    private final Locator errorMessage;

    public LoginPage(Page page) {
        this.page = page;
        this.emailField = page.locator("//input[@name='st.email']");
        this.passwordField = page.locator("//input[@name='st.password']");
        this.submitButton = page.locator("//input[@value='Войти в Одноклассники']");
        this.errorMessage = page.locator("//*[contains(@class, 'login_error')]");
    }

    @Step("Проверяем, что открылась страница с логином")
    @DisplayName("Проверка открытия страницы Login")
    public LoginPage open() {
        log.info("Проверка, что открылась страница с логином");
        page.navigate("https://ok.ru");
        waitForElementsVisible();
        return this;
    }

    @Step("Ждем, пока поля ввода не станут доступными")
    private void waitForElementsVisible() {
        log.info("Ждем, пока поля ввода логина и пароля не станут доступными");
        emailField.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(10000));
        passwordField.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(10000));
    }

    @Step("Заполняем поля с почтой и паролем")
    public LoginPage enterEmailAndPassword(String email, String password) {
        log.info("Вводим логин и пароль в форму логина");
        emailField.fill(email);
        passwordField.fill(password);
        return this;
    }

    @Step("Выполняем авторизацию пользователя")
    public HomePage submit() {
        log.info("Проходим авторизацию пользователя");
        submitButton.click();
        return new HomePage(page);
    }

    @Step("Пробуем пройти авторизацию с неверными данными")
    public LoginPage submitWithError() {
        log.info("Пытаемся пройти авторизацию с неверными данными");
        submitButton.click();
        return this;
    }

    @Step("Проверяем сообщение об ошибке")
    public LoginPage checkErrorMessage() {
        log.info("Проверяем сообщение об ошибке при авторизации с неверными данными");
        errorMessage.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(10000));

        if (!errorMessage.textContent().matches(LOGIN_ERROR_MESSAGE_MASK)) {
            throw new AssertionError("Сообщение об ошибке не соответствует ожидаемому");
        }
        return this;
    }
}