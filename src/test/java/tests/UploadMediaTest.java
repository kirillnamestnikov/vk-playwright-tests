package tests;

import org.junit.jupiter.api.*;
import pages.HomePage;
import pages.LoginPage;
import pages.PhotoPage;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class UploadMediaTest extends BaseTest {
    final String filePath = "src/test/resources/media/test-image.jpg";

    @Test
    @Disabled
    @DisplayName("Test of uploading media")
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

