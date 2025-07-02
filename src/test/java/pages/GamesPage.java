package pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.assertions.LocatorAssertions;
import com.microsoft.playwright.options.WaitForSelectorState;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import io.qameta.allure.Step;

public class GamesPage {
    private final Page page;
    private final Locator gamesList;

    public GamesPage(Page page){
        this.page = page;
        this.gamesList = page.locator("//div[contains(@class, 'games-card')]");
    }

    @Step("Получаем количество карточек с играми")
    public int getGamesListSize(){
        waitForGamesCount("На странице с играми должны отображаться игры");
        return (int) gamesList.all()
                .stream()
                .filter(Locator::isVisible)
                .count();
    }

    @Step("Проверяем, что первая карта с игрой видна")
    public GamesPage checkGamesCard(){
        waitForGamesCount("Количество игр должно быть больше 0");
        waitForCardVisible(gamesList.first(), "Карточка с игрой должна быть видимой");
        return this;
    }

    @Step("Ждем прогрузки всех карт с играми")
    private void waitForGamesCount(String message){
        try{
            assertThat(gamesList)
                    .not()
                    .hasCount(0, new LocatorAssertions.HasCountOptions());
        } catch (Exception e){
            throw new AssertionError(message, e);
        }
    }

    @Step("Ждем прогрузки первой карты с играми")
    private void waitForCardVisible(Locator card, String message){
        try{
            card.waitFor(new Locator.WaitForOptions()
                    .setState(WaitForSelectorState.VISIBLE)
                    .setTimeout(10000));
        } catch (Exception e){
            throw new AssertionError(message, e);
        }
    }
}