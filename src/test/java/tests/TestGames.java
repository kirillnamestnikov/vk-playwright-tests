package tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;
import io.qameta.allure.*;

import pages.LoginPage;
import tags.GamesTag;
import pages.GamesPage;

@Epic(value = "Игры")
@Link("https://t.me/imwizyx")
@DisplayName("Тесты для проверки функциональности игр")
public class TestGames extends BaseTest{
    @BeforeEach
    public void setupTest(){
        homePage = new LoginPage(page).open()
                .enterEmailAndPassword(getLogin(), getPassword())
                .submit();
    }

    @ParameterizedTest(name = "Проверка положительного количества карточек с играми")
    @ValueSource(ints = {0, 5 ,10})
    @GamesTag
    @Feature("Отображение карточек с играми")
    @Description("Тест проверяет, что количество карточек с играми положительное")
    @Owner("Kirill Namestnikov")
    @Severity(value = SeverityLevel.NORMAL)
    public void testPositiveGamesCount(int count){
        GamesPage gamesPage = homePage.openGames();
        int actualCount = gamesPage.getGamesListSize();
        assertAll(
                () -> gamesPage.checkGamesCard(),
                () -> assertTrue(actualCount > count,
                        String.format("Число игр должно быть больше %d",
                                count))
        );
    }
}