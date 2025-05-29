package tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.HomePage;
import pages.LoginPage;

public class UploadMediaTest extends BaseTest {
    @Test
    @DisplayName("Test of uploading media")
    public void uploadMediaTest() throws Exception {
        HomePage homePage = new LoginPage(page)
                .open()
                .enterEmailAndPassword(getLogin(), getPassword())
                .submit();



    }
}

