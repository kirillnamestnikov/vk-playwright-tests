package tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import io.qameta.allure.*;

import org.junit.jupiter.api.Test;
import pages.LoginPage;
import pages.MusicPage;
import tags.pages.MusicTag;
import static constants.music.MusicValues.TRACK_NAME;

@Epic(value = "Музыка")
@Link("https://t.me/imwizyx")
@DisplayName("Тесты для проверки функционала музыки")
public class TestMusic extends BaseTest{
    private MusicPage musicPage;
    @BeforeEach
    public void setupTest(){
        homePage = new LoginPage(page).open()
                .enterEmailAndPassword(getLogin(), getPassword())
                .submit();
        musicPage = homePage.openMusic();
    }

    @Test
    @MusicTag
    @Feature(value = "Добавление музыки")
    @DisplayName("Тест с добавлением трека в свою музыку")
    @Story("Добавление музыкальной композиции в свою музыку")
    @Description("Тест проверяет, что пользователь может добавить трек, введя его название в поиске")
    @Owner("Kirill Namestnikov")
    public void testMusicUpload(){
        musicPage.search(TRACK_NAME)
                .add()
                .checkSuccessMessage();
    }
}