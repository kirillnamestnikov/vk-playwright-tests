package pages;


import io.qameta.allure.Step;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.WaitForSelectorState;
import org.junit.jupiter.api.DisplayName;

public class LoginPage {
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
        page.navigate("https://ok.ru");
        waitForElementsVisible();
        return this;
    }

    @Step("Ждем, пока поля ввода не станут доступными")
    private void waitForElementsVisible() {
        emailField.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(10000));
        passwordField.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(10000));
    }

    @Step("Заполняем поля с почтой и паролем")
    public LoginPage enterEmailAndPassword(String email, String password) {
        emailField.fill(email);
        passwordField.fill(password);
        return this;
    }

    @Step("Выполняем авторизацию пользователя")
    public HomePage submit() {
        submitButton.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(10000));
        submitButton.click();
        return new HomePage(page);
    }

    @Step("Пробуем пройти авторизацию с неверными данными")
    public LoginPage submitWithError() {
        submitButton.click();
        return this;
    }

    @Step("Проверяем сообщение об ошибке")
    public LoginPage checkErrorMessage() {
        errorMessage.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(10000));

        if (!errorMessage.textContent().matches(".*(Неправильно указан логин и/или пароль|Введите пароль|Введите логин).*")) {
            throw new AssertionError("Сообщение об ошибке не соответствует ожидаемому");
        }
        return this;
    }
}