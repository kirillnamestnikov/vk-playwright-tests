package tests;

import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import org.junit.jupiter.api.*;
import pages.*;
import tags.MessagesTag;

public class TestMessages extends BaseTest{

    @Test
    @MessagesTag
    @DisplayName("Тест взаимодействия двух пользователей путем сообщений")
    void testUsersInteract(){
        BrowserContext user1Context = browser.newContext();
        BrowserContext user2Context = browser.newContext();

        Page user1Page = user1Context.newPage();
        Page user2Page = user2Context.newPage();

        HomePage user1MessagePage = new LoginPage(user1Page)
                .open()
                .enterEmailAndPassword(getLogin("technopol56"), getPassword("technopol56"))
                .submit();
        HomePage user2MessagePage = new LoginPage(user2Page)
                .open()
                .enterEmailAndPassword(getLogin("technopol42"), getPassword("technopol42"))
                .submit();
        user1MessagePage.openMessages().openChatWith("technopol42").sendMessage("Привет от technopol56");
        user2MessagePage.openMessages().openChatWith("technopol56").verifyLastMessageFrom("technopol56",
                "Привет от technopol56");
        user1Context.close();
        user2Context.close();
    }
}