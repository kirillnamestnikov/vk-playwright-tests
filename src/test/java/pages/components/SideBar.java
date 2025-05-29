package pages.components;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class SideBar {
    protected final Locator photoButton;
    private final Page page;

    public SideBar(Page page) {
        this.page = page;
        this.photoButton = page.locator("a[data-l='t,userPhotos']");
    }

    public void clickPhotoButton() {
        assertThat(photoButton).isVisible();
        assertThat(photoButton).isEnabled();
        photoButton.click();
    }
}