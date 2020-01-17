import static org.junit.Assert.*;

import org.junit.*;
import org.junit.Assert.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class Verification {
    static WebDriver driver = new ChromeDriver();

    @BeforeClass
    public static void initDriver() {
        //WebDriver driver = new ChromeDriver();
        String baseUrl = "https://www.snaptravel.com/search?encrypted_user_id=5xqebwRCiWusH08KS2yJKA&otp=5549929985";
        driver.get(baseUrl);
        System.out.println("In testing");
    }

    @Test
    public void verifyTitle() {
        System.out.println("In Verify Search Placeholder Test Case");
        assertEquals(driver.getTitle(), "SnapTravel - Hotel Deals");
    }

    @Test
    public void verifySearchBar() {
        System.out.println("In Verify Title Test case");
        WebElement searchBar = driver.findElement(By.xpath("//input[@placeholder='Search by city or hotel name']"));
        String searchBarString = searchBar.getAttribute("placeholder");
        //String yyy = driver.getTitle();
        assertTrue(searchBarString.contains("Search by city or hotel name"));
    }


    @AfterClass
    public static void tearDown() {
        driver.quit();
    }
}
