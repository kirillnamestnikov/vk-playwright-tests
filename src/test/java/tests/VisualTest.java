package tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.HomePage;
import pages.LoginPage;

import java.nio.file.*;
import com.microsoft.playwright.Page;
import tests.utils.ImageUtils;

public class VisualTest extends BaseTest {
    private static final int X_SCREENSHOT = 0;
    private static final int Y_SCREENSHOT = 0;
    private static final int WIDTH_SCREENSHOT = 215;
    private static final int HEIGHT_SCREENSHOT = 700;

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

        page.screenshot(new Page.ScreenshotOptions()
                .setPath(Paths.get(actualScreenshotPath))
                .setClip(X_SCREENSHOT, Y_SCREENSHOT, WIDTH_SCREENSHOT, HEIGHT_SCREENSHOT)
        );

        boolean isMatch = ImageUtils.compareImages(referenceScreenshotPath, actualScreenshotPath, diffScreenshotPath);

        if (!isMatch) {
            throw new AssertionError("Визуальные различия обнаружены. См.: " + diffScreenshotPath);
        }
    }
}
