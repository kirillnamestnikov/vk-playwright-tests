package tests;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.HomePage;
import pages.LoginPage;
import tags.pages.VisualTag;
import io.qameta.allure.*;
import static constants.visual.VisualValues.*;

@Epic("Визуал")
@DisplayName("Визуальные тесты")
@Link("https://t.me/imwizyx")
public class VisualTest extends BaseTest {
    @Test
    @VisualTag
    @Disabled
    @Feature("Визуальный тест домашней страницы")
    @Story("Взятие скриншота домашней страницы и сравнение его с эталоном")
    @DisplayName("Проверка соответствия ui домашней страницы с эталоном")
    @Description("Тест проверяет, что ui домашней страницы соответствует эталону")
    @Owner("Yuliya Mukhina")
    @Severity(value = SeverityLevel.NORMAL)
    public void testVisual() throws Exception {
        HomePage homePage = new LoginPage(page)
                .open()
                .enterEmailAndPassword(getLogin(), getPassword())
                .submit();

        homePage.visualTestSideBar(
                X_SCREENSHOT,
                Y_SCREENSHOT,
                WIDTH_SCREENSHOT,
                HEIGHT_SCREENSHOT,
                ACTUAL_SCREENSHOT_PATH,
                REFERENCE_SCREENSHOT_PATH,
                DIFF_SCREENSHOT_PATH
        );
    }
}
