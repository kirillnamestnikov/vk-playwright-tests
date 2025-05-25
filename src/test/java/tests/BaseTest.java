package tests;


import io.github.cdimascio.dotenv.Dotenv;
import pages.HomePage;
import org.junit.jupiter.api.*;
import com.microsoft.playwright.*;

public abstract class BaseTest{
    protected static final String BASE_URL = "https://ok.ru";
    protected static final Dotenv DOT_ENV = Dotenv.configure().ignoreIfMissing().load();
    protected HomePage homePage;
    protected Page page;
    protected static Playwright playwright;
    protected static Browser browser;
    protected static BrowserContext context;
    protected static String getPassword(){
        return DOT_ENV.get("OK_PASSWORD", System.getenv("OK_PASSWORD"));
    }

    protected static String getLogin(){
        return DOT_ENV.get("OK_LOGIN", System.getenv("OK_LOGIN"));
    }

    protected static String getEmptyLogin(){
        return "";
    }

    protected static String getEmptyPassword(){
        return "";
    }

    protected static String getFailLogin(){
        return "FailLogin";
    }

    protected static String getFailPassword(){
        return "FailPassword";
    }

    @BeforeAll
    public static void init() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                .setHeadless(false)
                .setSlowMo(100));
    }

    @BeforeEach
    public void createContextAndPage() {
        context = browser.newContext(new Browser.NewContextOptions()
                .setViewportSize(2560, 1440));
        page = context.newPage();
        homePage = new HomePage(page);
    }

    @AfterEach
    public void closeContext() {
        context.close();
    }

    @AfterAll
    public static void closeBrowser() {
        if (browser != null) {
            browser.close();
        }
        if (playwright != null) {
            playwright.close();
        }
    }
}