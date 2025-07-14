package tests;

import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import org.junit.jupiter.api.*;
import pages.*;
import tags.pages.MessagesTag;
import tags.types.RegressTag;
import io.qameta.allure.*;
import static constants.message.MessageValues.SENDER_NAME;
import static constants.message.MessageValues.RECEIVER_NAME;
import static constants.message.MessageValues.MESSAGE_TEXT;

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

        HomePage user1MessagePage = new LoginPage(user1Page)
                .open()
                .enterEmailAndPassword(getLogin(SENDER_NAME), getPassword(SENDER_NAME))
                .submit();

        HomePage user2MessagePage = new LoginPage(user2Page)
                .open()
                .enterEmailAndPassword(getLogin(RECEIVER_NAME), getPassword(RECEIVER_NAME))
                .submit();
        user1MessagePage.openMessages().openChatWith(RECEIVER_NAME).sendMessage(MESSAGE_TEXT);
        user2MessagePage.openMessages().openChatWith(SENDER_NAME).verifyLastMessageFrom(SENDER_NAME, MESSAGE_TEXT);
        user1Context.close();
        user2Context.close();
    }
}