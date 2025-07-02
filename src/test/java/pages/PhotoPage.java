package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.FilePayload;

import java.nio.file.Files;
import java.nio.file.Paths;
import io.qameta.allure.Step;
import org.junit.jupiter.api.DisplayName;

public class PhotoPage {
    private final Page page;
    private final Locator uploadButton;

    public PhotoPage(Page page) {
        this.page = page;
        this.uploadButton = page.locator("//span[@data-l=\"t,upload-new-photo\"]/div");
    }

    @Step("Загружаем файл '{filePath}'")
    public void uploadFile(String filePath) throws Exception {
        byte[] fileContent = Files.readAllBytes(Paths.get(filePath));
        String fileName = Paths.get(filePath).getFileName().toString();

        FilePayload filePayload = new FilePayload(
                fileName,
                Files.probeContentType(Paths.get(filePath)),
                fileContent
        );

        page.onFileChooser(fileChooser -> {
            fileChooser.setFiles(filePayload);
        });

        uploadButton.click();

        page.waitForTimeout(2000);
    }

    @Step("Ждем результатов загрузки")
    @DisplayName("Проверка успешной загрузки файла")
    public void waitForUploadResultsElement() {
        page.waitForSelector("up-uploading-results",
                new Page.WaitForSelectorOptions().setTimeout(15000));
    }
}
