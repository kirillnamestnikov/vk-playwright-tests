package pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.assertions.LocatorAssertions;
import com.microsoft.playwright.options.WaitForSelectorState;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import io.qameta.allure.Step;

public class GamesPage {
    private static final Logger log = LogManager.getLogger(GamesPage.class);
    private final Page page;
    private final Locator gamesList;

    public GamesPage(Page page){
        this.page = page;
        this.gamesList = page.locator("//div[contains(@class, 'games-card')]");
    }

    @Step("Получаем количество карточек с играми")
    public int getGamesListSize(){
        log.info("Получаем количество карточек с играми");
        waitForGamesCount("На странице с играми должны отображаться игры");
        return (int) gamesList.all()
                .stream()
                .filter(Locator::isVisible)
                .count();
    }

    @Step("Проверяем, что первая карта с игрой видна")
    public GamesPage checkGamesCard(){
        log.info("Проверяем, что первая карточка с игрой является видимой");
        waitForGamesCount("Количество игр должно быть больше 0");
        waitForCardVisible(gamesList.first(), "Карточка с игрой должна быть видимой");
        return this;
    }

    @Step("Ждем прогрузки всех карт с играми")
    private void waitForGamesCount(String message){
        log.info("Ждем, когда все карточки с играми прогрузятся");
        try{
            assertThat(gamesList)
                    .not()
                    .hasCount(0, new LocatorAssertions.HasCountOptions());
        } catch (Exception e){
            throw new AssertionError(message, e);
        }
    }

    @Step("Ждем прогрузки первой карты с игрой")
    private void waitForCardVisible(Locator card, String message){
        log.info("Ждем, когда прогрузится первая карточка с игрой");
        try{
            card.waitFor(new Locator.WaitForOptions()
                    .setState(WaitForSelectorState.VISIBLE)
                    .setTimeout(10000));
        } catch (Exception e){
            throw new AssertionError(message, e);
        }
    }
}