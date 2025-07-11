package pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.WaitForSelectorState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import io.qameta.allure.Step;
import org.junit.jupiter.api.DisplayName;

public class MessagesPage {
    private static final Logger log = LogManager.getLogger(MessagesPage.class);
    private final Page page;
    private final Locator messagesInput;
    private final Locator sendButton;

    public MessagesPage(Page page){
        this.page = page;
        this.messagesInput = page.locator("//div[@data-tsid='write_msg_input-input']");
        this.sendButton = page.locator("//button[@data-tsid='button_send' and @title='Send']").first();
    }

    @Step("Открываем чат с пользователем '{username}'")
    public MessagesPage openChatWith(String username){
        log.info(String.format("Открываем чат с пользователем %s", username));
        Locator userChat = this.page.locator("//span[text()='" + username +
                " " + username + "']/..").first();
        userChat.click(new Locator.ClickOptions().setTimeout(10000));
        return this;
    }

    @Step("Отправляем сообщение '{message}'")
    public MessagesPage sendMessage(String message){
        log.info(String.format("Отправляем сообщение %s пользователю", message));
        messagesInput.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(10000));
        messagesInput.fill(message);
        sendButton.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(10000));
        sendButton.click();
        return this;
    }

    @Step("Проверяем, что пришло сообщение '{expectedMessage}' от '{expectedUsername}'")
    @DisplayName("Проверка, что пришло сообщение, которое отправили")
    public MessagesPage verifyLastMessageFrom(String expectedUsername, String expectedMessage){
        log.info(String.format("Проверяем, что действительно пришло сообщение %s от пользователя %s", expectedMessage, expectedUsername));
        openChatWith(expectedUsername);
        Locator lastMessage = this.page.locator("//div[@class='txt-okmsg']").last();
        lastMessage.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(10000));
        String actualMessage = lastMessage.innerText();
        if (!actualMessage.contains(expectedMessage)){
            throw new AssertionError("Ожидалось сообщение " +
                    expectedMessage + ", но получено " + actualMessage);
        }
        return this;
    }
}