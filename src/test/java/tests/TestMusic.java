package tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;
import io.qameta.allure.*;

import org.junit.jupiter.api.Test;
import pages.LoginPage;
import pages.MusicPage;
import tags.pages.MusicTag;


public class TestMusic extends BaseTest{
    private final String trackName = "yeat 2093";
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
    public void testMusicUpload(){
        musicPage.search(trackName)
                .add()
                .checkSuccessMessage();
    }
}