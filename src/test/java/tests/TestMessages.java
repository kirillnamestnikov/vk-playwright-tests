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

        String sender = "technopol56";
        HomePage user1MessagePage = new LoginPage(user1Page)
                .open()
                .enterEmailAndPassword(getLogin(sender), getPassword(sender))
                .submit();
        String receiver = "technopol42";
        HomePage user2MessagePage = new LoginPage(user2Page)
                .open()
                .enterEmailAndPassword(getLogin(receiver), getPassword(receiver))
                .submit();
        user1MessagePage.openMessages().openChatWith(receiver).sendMessage("Привет от technopol56");
        user2MessagePage.openMessages().openChatWith(sender).verifyLastMessageFrom(sender,
                "Привет от technopol56");
        user1Context.close();
        user2Context.close();
    }
}