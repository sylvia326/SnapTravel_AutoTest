import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static org.junit.Assert.assertTrue;

import java.time.Duration;
import java.util.Set;


public class SearchPage {
    static WebDriver driver = new ChromeDriver();
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    String baseUrl = "https://www.snaptravel.com/search?encrypted_user_id=5xqebwRCiWusH08KS2yJKA&otp=5549929985";
    static String placeHolder = "//input[@placeholder='Search by city or hotel name']";
    static String searchButton = "//a[contains(@class,'ui fluid big button search-container__action-btn')]";
    static String city = "";

    @Before
    public void setUp() {
        driver.manage().deleteAllCookies();
        Set<Cookie> cookies = driver.manage().getCookies();
        driver.get(baseUrl);
        driver.findElement(By.xpath("//img[@class='omnisearch__close-icon']")).click();
    }

    @Test
    public void enterCorrectCity() {
        city = "paris";
        inputInfo();
        By addItem = By.xpath("//body/div[@id='root']/div[@class='src-components-hotels_components-styles-___hotels_screen_desktop_styles__hotels-screen-container___tuG3z']/div[@class='st-row src-components-hotels_components-styles-___hotels_screen_desktop_styles__container___2Z89o']/div[@class='st-col-11']/div/div[1]/div[1]/div[1]");
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(addItem));

        String hotelPage = driver.getTitle();
        assertTrue(hotelPage.contains("Paris"));
    }

    @Test
    public void enterErrorCity() {
        city = "PPPParis";
        inputInfo();
        Alert alert01 = wait.until(ExpectedConditions.alertIsPresent());
        assertTrue(alert01 != null);
    }

    @Test
    public void enterEmptyCity() {
        city = "";
        inputInfo();
        Alert alert01 = wait.until(ExpectedConditions.alertIsPresent());
        assertTrue(alert01 != null);
    }

    public static void inputInfo() {
        //Set<Cookie> cookies2 = driver.manage().getCookies();
        driver.findElement(By.xpath(placeHolder)).sendKeys(city);
        //click check-in button
        driver.findElement(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[1]/div[2]/div[1]/div[2]/div[2]/div[1]/div[1]/div[1]/div[1]")).click();
        //select check-in year
        driver.findElement(By.xpath("//option[contains(text(),'2020')]")).click();
        //select check-in month
        driver.findElement(By.xpath("//option[contains(text(),'January')]")).click();
        //select check-in date
        driver.findElement(By.xpath("//span[@class='rdrDayNumber']//span[contains(text(),'17')]")).click();
        //click check-out button
        driver.findElement(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[1]/div[2]/div[1]/div[2]/div[2]/div[1]/div[1]/div[2]/div[1]")).click();
        //select check-out year
        driver.findElement(By.xpath("//option[contains(text(),'2020')]")).click();
        //select check-out month
        driver.findElement(By.xpath("//option[contains(text(),'January')]")).click();
        //select check-out date
        driver.findElement(By.xpath("//span[contains(text(),'25')]")).click();
        //click guests button
        driver.findElement(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[1]/div[2]/div[1]/div[2]/div[2]/div[2]/div[1]/div[1]")).click();
        //select numbers of adults
        driver.findElement(By.xpath("//div[@class='src-omnisearch-NumberOfAdultsPicker-___NumberOfAdultsPicker__bottom-adults-menu___t2V6C']//div[2]//div[2]//button[2]")).click();
        //select number of children
        driver.findElement(By.xpath("//div[@class='src-omnisearch-NumberOfAdultsPicker-___NumberOfAdultsPicker__bottom-adults-menu___t2V6C']//div[2]//div[2]//button[2]")).click();
        //select age of children
        WebElement ele = driver.findElement(By.xpath("//div[@class='css-1wfymif']"));
        String script = "arguments[0].innerHTML='2'";
        ((JavascriptExecutor) driver).executeScript(script, ele);
        //click search button
        driver.findElement(By.xpath(searchButton)).click();
    }


    @After
    public void tearDown() {

        driver.quit();
    }


}
