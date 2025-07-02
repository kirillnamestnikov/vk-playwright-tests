package pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.WaitForSelectorState;
import org.junit.jupiter.api.DisplayName;
import pages.components.SideBar;
import tests.utils.ImageUtils;

import java.io.File;
import java.nio.file.Paths;
import io.qameta.allure.Step;

public class HomePage {
    private final Page page;
    private final Locator avatar;
    private final Locator friendsButton;
    private final SideBar sideBar;
    private final Locator gamesButton;
    private final Locator messagesButton;
    private final Locator feedButton;


    public HomePage(Page page) {
        this.page = page;
        this.avatar = page.locator("//div[@class='toolbar_avatar']");
        this.friendsButton = page.locator("//a[contains(@href, '/friends') and @data-l='t,userFriend']");
        this.feedButton = page.locator("//a[contains(@href, '/feed')] | //span[text()='Лента']/..");
        this.sideBar = new SideBar(page);
        this.gamesButton = page.locator("//a[contains(@href, '/games')] | //span[text()='Игры']/..");
        this.messagesButton = page.locator("#msg_toolbar_button");
    }

    @Step("Проверяем, что аватар недоступен")
    public boolean isNotAvatarAvailable() {
        return !avatar.isVisible();
    }

    public HomePage checkFriends() {
        friendsButton.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(10000));

        if (!friendsButton.textContent().contains("Друзья")) {
            throw new AssertionError("На главной странице должна быть кнопка Друзья");
        }
        return this;
    }

    @Step("Делаем скриншот и сравниваем его с эталоном")
    @DisplayName("Проверка соответствия UI домашней страницы с эталоном")
    public HomePage visualTestSideBar(
            int x, int y, int width, int height,
            String actualPath, String referencePath, String diffPath) throws Exception {

        File referenceFile = new File(referencePath);
        if (!referenceFile.exists()) {
            referenceFile.getParentFile().mkdirs();

            page.screenshot(new Page.ScreenshotOptions()
                    .setPath(Paths.get(referencePath))
                    .setClip(x, y, width, height)
            );
            return this;
        }

        page.screenshot(new Page.ScreenshotOptions()
                .setPath(Paths.get(actualPath))
                .setClip(x, y, width, height)
        );

        boolean isMatch = ImageUtils.compareImages(referencePath, actualPath, diffPath);

        if (!isMatch) {
            throw new AssertionError("Визуальные различия обнаружены. См.: " + diffPath);
        }
        return this;
    }

    @Step("Нажимаем на кнопку с Фото")
    public void clickPhotoButton() {
        sideBar.clickPhotoButton();
    }


    @Step("Открываем страницу с играми")
    public GamesPage openGames(){
        gamesButton.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(10000));
        gamesButton.click();
        return new GamesPage(page);
    }

    @Step("Открываем страницу с сообщениями")
    public MessagesPage openMessages(){
        messagesButton.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(10000));
        messagesButton.click();
        return new MessagesPage(page);
    }

}