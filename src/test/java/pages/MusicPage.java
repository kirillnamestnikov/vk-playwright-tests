package pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.WaitForSelectorState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import io.qameta.allure.Step;
import org.junit.jupiter.api.DisplayName;
import static constants.music.MusicValues.SUGGEST_TITLE;

public class MusicPage {
    private static final Logger log = LogManager.getLogger(MusicPage.class);
    private final Page page;
    private final Locator track;
    private final Locator searchBar;
    private final Locator suggestHeader;
    private final Locator submitSearchButton;
    private final Locator addButton;

    public MusicPage(Page page){
        this.page = page;
        this.track = page.locator("(//*[@data-tsid=\"track\"])[1]");
        this.searchBar = page.locator("//input[@data-tsid=\"inner_input\" and @placeholder=\"Search for music\"]");
        this.suggestHeader = page.locator("//*[@data-tsid=\"suggest_header\"]");
        this.submitSearchButton = page.locator("//*[@data-tsid=\"submit_search_button\"]");
        this.addButton = page.locator("//button[@type=\"button\" and @title=\"To my music\"]").last();
    }

    @Step("Ищем песню с названием '{trackname}'")
    public MusicPage search(String trackname){
        log.info(String.format("Ищем песню в поиске с названием %s", trackname));
        searchBar.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(10000));
        searchBar.fill(trackname);
        submitSearchButton.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(10000));
        submitSearchButton.click();
        return this;
    }

    @Step("Добавляем песню")
    public MusicPage add(){
        log.info("Добавляем песню в свою музыку");
        track.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(10000));
        track.click();
        addButton.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(10000));
        addButton.click();
        return this;
    }

    @Step("Проверяем сообщение об успешном добавлении песни")
    @DisplayName("Проверка добавления песни")
    public MusicPage checkSuccessMessage(){
        log.info("Проверяем сообщение об успешном добавлении песни");
        suggestHeader.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(10000));
        if (!suggestHeader.textContent().startsWith(SUGGEST_TITLE)){
            throw new AssertionError("Сообщение о добавлении песни не соответствует ожидаемому");
        }
        addButton.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(10000));
        addButton.click();
        return this;
    }
}