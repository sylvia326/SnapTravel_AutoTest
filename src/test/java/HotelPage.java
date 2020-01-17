import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class HotelPage {
    WebDriver driver = new ChromeDriver();
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    String baseUrl = "https://www.snaptravel.com/search?encrypted_user_id=5xqebwRCiWusH08KS2yJKA&otp=5549929985";


    @Test
    public void selectHotelTest() {
        hotelPage();
        String areasButtonStr = selectHotel();
        assertEquals(areasButtonStr, "Popular Areas");
    }


    @Test
    public void selectRoomTest() {
        selectHotelTest();
        String bookingInfo = selectRoom();
        assertEquals("Guest Details'", bookingInfo);
    }

    @Test
    public void bookingInfoTest() {
        // the function is to type in name, phone, email
        selectRoom();
        List<WebElement> ErrorBorder = bookingInfo();
        assertTrue(ErrorBorder.isEmpty());
    }

    @Test
    public void paymentInfoTest() {
        bookingInfoTest();
        List<WebElement> ErrorBorder2 = paymentInfo();
        assertTrue(ErrorBorder2.isEmpty());
    }


    public void hotelPage() {
        driver.get(baseUrl);
        driver.findElement(By.xpath("//img[@class='omnisearch__close-icon']")).click();
        // Call SearchPage Class function to return the Hotel ListView Page
        SearchPage.inputInfo();
        By addItem = By.xpath("//body/div[@id='root']/div[@class='src-components-hotels_components-styles-___hotels_screen_desktop_styles__hotels-screen-container___tuG3z']/div[@class='st-row src-components-hotels_components-styles-___hotels_screen_desktop_styles__container___2Z89o']/div[@class='st-col-11']/div/div[1]/div[1]/div[1]");
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(addItem));
    }


    public String selectHotel() {
        //to catch all web elements into list
        List<WebElement> myHotelList = driver.findElements(By.className("src-components-styles-___button_component__btn___3mUFc"));
        String windowHandleBefore = driver.getWindowHandle();

        for (int i = 0; i < myHotelList.size(); i++) {
            myHotelList.get(i).click();
            // when click opens up a new tab. Switch the driver to use the new tab
            for (String winHandle : driver.getWindowHandles()) {
                driver.switchTo().window(winHandle);
            }
            WebElement hotelErrorView = null;
            // try to see if hotel is available. If timeout, then means hotel is Available. Break the loop to continue
            try {
                hotelErrorView = wait.until(ExpectedConditions.presenceOfElementLocated(By.className("src-hotel_cards-ErrorView-___ErrorView__section___1iLuu")));
            } catch (Exception ex) {
                break;
            }
            // check if hotel is available. If not, close the tab, and jump back to previous window
            if (hotelErrorView != null) {
                driver.close();
                driver.switchTo().window(windowHandleBefore);
            }
        }
        return driver.findElement(By.xpath("//p[contains(text(),'Popular Areas')]")).getText();
    }

    public String selectRoom() {
        driver.findElement(By.xpath("//div[@id='rooms']//div[1]//div[2]//div[1]//div[3]//div[1]")).click();
        // jump to info and payment page
        By addItem = By.xpath("//button[@id='btn-next-to-payment']");
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(addItem));
        return driver.findElement(By.xpath("//div[contains(text(),'Guest Details')]")).getText();
    }

    public void clear(By by) {
        driver.findElement(by).clear();
    }

    public void click(By by) {
        driver.findElement(by).click();
    }

    public void type(By by, String value) {
        driver.findElement(by).sendKeys(value);
    }

    public List<WebElement> bookingInfo() {
        clear(By.xpath("//input[@id='first-name']"));
        click(By.xpath("//button[@id='btn-next-to-payment']"));
        type(By.xpath("//input[@id='first-name']"), "sylvia");
        clear(By.xpath("//input[@id='last-name']"));
        type(By.xpath("//input[@id='last-name']"), "He");
        clear(By.xpath("//input[@id='email']"));
        type(By.xpath("//input[@id='email']"), "test@snaptravel.com");
        clear(By.xpath("//input[@id='phone-number']"));
        type(By.xpath("//input[@id='phone-number']"), "6471111111");
        click(By.xpath("//button[@id='btn-next-to-payment']"));
        return driver.findElements(By.xpath("//*[contains(text(),'input__error')]"));
    }


    public List<WebElement> paymentInfo() {
        click(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[1]/div[2]/div[1]/div[2]/div[1]/div[2]/*"));
        type(By.xpath("//div[@class='src-booking_form-components-credit-card-___credit-card-number__credit-card-number___3DeJe src-booking_form-components-credit-card-___credit-card-number__error___3eQBz']"), "4111 1111 1111 1111");
        type(By.xpath("//input[@id='expiry-year']"), "0322");
        type(By.xpath("//div[@class='src-booking_form-components-credit-card-___new-card__form-group___2mK1z src-booking_form-components-credit-card-___new-card__right-field___1sLeS']//div[@class='src-booking_form-components-input-___input__input___3QGZF']"), "550");
        type(By.xpath("//input[@id='billing-name']"), "Sylvia");
        type(By.xpath("//input[@id='billing-address']"), "Toronto");
        return driver.findElements(By.xpath("//*[contains(text(),'input__error')]"));
    }
}