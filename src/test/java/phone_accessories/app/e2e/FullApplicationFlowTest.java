package phone_accessories.app.e2e;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

class FullApplicationFlowTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    void setUp() {
        // Initializes the Chrome browser driver
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        // Configures explicit waits for your Angular app elements to load safely
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        // 1. Opens your running Angular Application URL
        driver.get("http://localhost:4200");
    }

    @Test
    void testCreateBrand_FullUIFlow() {
        // 2. UI Interaction: Locate and click the Brand management navigation link using your exact routerLink
        WebElement brandLink = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[@routerlink='/brands' or contains(text(), 'Brands')]")
        ));
        brandLink.click();

        // 2b. Click "+ Add Brand" button to open the hidden form section (*ngIf="showForm")
        WebElement addBrandButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(text(), '+ Add Brand')]")
        ));
        addBrandButton.click();

        // 3. UI Interaction: Find the input field using your placeholder text
        WebElement brandInput = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//input[@placeholder='Example: Apple']")
        ));
        brandInput.sendKeys("Google Pixel Accessories");

        // 4. UI Interaction: Find and click the Submit button inside your form
        WebElement saveButton = driver.findElement(
                By.xpath("//form//button[@type='submit' or contains(text(), 'Add Brand')]")
        );
        saveButton.click();

        // 5. Verification: Check that your specific table row text displays the brand item
        WebElement updatedList = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//td[@class='brand-name' and contains(text(), 'Google Pixel Accessories')]")
        ));

        assertTrue(updatedList.isDisplayed(), "The new brand was not found on the UI after submission.");
    }
    @AfterEach
    void tearDown() {
        // Safely terminates and closes the browser instance after the test completes
        if (driver != null) {
            driver.quit();
        }
    }
}