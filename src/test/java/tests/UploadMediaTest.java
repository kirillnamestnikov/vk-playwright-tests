package tests;

import org.junit.jupiter.api.*;
import pages.HomePage;
import pages.LoginPage;
import pages.PhotoPage;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import io.qameta.allure.*;


@Epic("Загрузка медиа")
@DisplayName("Тесты для загрузки медиа")
@Link("https://t.me/imwizyx")
public class UploadMediaTest extends BaseTest {
    final String filePath = "src/test/resources/media/test-image.jpg";

    @Test
    @Disabled
    @Feature("Загрузка медиа")
    @Story("Загрузка файла в фотографии пользователя")
    @DisplayName("Проверка загрузки медиа")
    @Description("Тест проверяет, что медиа контент успешно загружается в фотографии пользователя")
    @Owner("Yuliya Mukhina")
    @Severity(value = SeverityLevel.NORMAL)
    public void uploadMediaTest() throws Exception {
        HomePage homePage = new LoginPage(page)
                .open()
                .enterEmailAndPassword(getLogin(), getPassword())
                .submit();

        homePage.clickPhotoButton();
        PhotoPage photoPage = new PhotoPage(page);

        photoPage.uploadFile(filePath);

        assertDoesNotThrow(
                () -> photoPage.waitForUploadResultsElement(),
                "Элемент up-uploading-results не появился в течение 15 секунд"
        );
    }
}

