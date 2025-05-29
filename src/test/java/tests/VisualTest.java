package tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.HomePage;
import pages.LoginPage;

public class VisualTest extends BaseTest {
    private static final int X_SCREENSHOT = 0;
    private static final int Y_SCREENSHOT = 345;
    private static final int WIDTH_SCREENSHOT = 215;
    private static final int HEIGHT_SCREENSHOT = 335;

    String referenceScreenshotPath = "src/test/resources/screenshots/homepage-reference.png";
    String actualScreenshotPath = "src/test/resources/screenshots/actual/actual-homepage.png";
    String diffScreenshotPath = "src/test/resources/screenshots/diff/diff-homepage.png";


    @Test
    @DisplayName("Visual testing of home page")
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
                actualScreenshotPath,
                referenceScreenshotPath,
                diffScreenshotPath
        );
    }
}
