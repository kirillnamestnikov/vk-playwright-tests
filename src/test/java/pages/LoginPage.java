package pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.WaitForSelectorState;

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

    public LoginPage open() {
        page.navigate("https://ok.ru");
        waitForElementsVisible();
        return this;
    }

    private void waitForElementsVisible() {
        emailField.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(10000));
        passwordField.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(10000));
    }

    public LoginPage enterEmailAndPassword(String email, String password) {
        emailField.fill(email);
        passwordField.fill(password);
        return this;
    }

    public HomePage submit() {
        submitButton.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(10000));
        submitButton.click();
        return new HomePage(page);
    }

    public LoginPage submitWithError() {
        submitButton.click();
        return this;
    }

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