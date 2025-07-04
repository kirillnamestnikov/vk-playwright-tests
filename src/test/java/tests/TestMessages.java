package tests;

import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import org.junit.jupiter.api.*;
import pages.*;
import tags.pages.MessagesTag;
import tags.types.RegressTag;
import io.qameta.allure.*;

@Epic(value = "Сообщения")
@Link("https://t.me/imwizyx")
@DisplayName("Тесты для проверки функционала сообщений")
public class TestMessages extends BaseTest{

    @Test
    @Disabled
    @MessagesTag
    @RegressTag
    @Feature(value = "Отправка сообщения")
    @DisplayName("Тест взаимодействия двух пользователей путем сообщений")
    @Story("Доставка сообщения от другого пользователя")
    @Description("Тест проверяет, что один пользователь может отправить другому сообщение и другой пользователь может его прочитать")
    @Owner("Kirill Namestnikov")
    @Severity(SeverityLevel.CRITICAL)
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