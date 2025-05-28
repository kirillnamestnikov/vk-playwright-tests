package pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.WaitForSelectorState;

public class HomePage {
    private final Page page;
    private final Locator avatar;
    private final Locator friendsButton;
    private final Locator gamesButton;
    private final Locator messagesButton;

    public HomePage(Page page) {
        this.page = page;
        this.avatar = page.locator("//div[@class='toolbar_avatar']");
        this.friendsButton = page.locator("//a[contains(@href, '/friends')] | //span[text()='Друзья']/..");
        this.gamesButton = page.locator("//a[contains(@href, '/vitrine')] | //span[text()='Игры']/..");
        this.messagesButton = page.locator("#msg_toolbar_button");
    }

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

    public GamesPage openGames(){
        gamesButton.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(10000));
        gamesButton.click();
        return new GamesPage(page);
    }

    public MessagesPage openMessages(){
        messagesButton.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(10000));
        messagesButton.click();
        return new MessagesPage(page);
    }
}