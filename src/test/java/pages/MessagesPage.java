package pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.WaitForSelectorState;


public class MessagesPage {
    private final Page page;
    private final Locator messagesInput;
    private final Locator sendButton;

    public MessagesPage(Page page){
        this.page = page;
        this.messagesInput = page.locator("//div[@data-tsid='write_msg_input-input']");
        this.sendButton = page.locator("//button[@data-tsid='button_send' and @title='Send']").first();
    }

    public MessagesPage openChatWith(String username){
        Locator userChat = this.page.locator("//span[text()='" + username +
                " " + username + "']/..").first();
        userChat.click(new Locator.ClickOptions().setTimeout(10000));
        return this;
    }

    public MessagesPage sendMessage(String message){
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

    public MessagesPage verifyLastMessageFrom(String expectedUsername, String expectedMessage){
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