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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import phone_accessories.app.entity.AdminUser;
import phone_accessories.app.repository.AdminUserRepository;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest // 👈 Tells Spring Boot to enable @Autowired injection for tests
class FullApplicationFlowTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @Autowired // 👈 Now this will resolve cleanly
    private AdminUserRepository adminUserRepository; // 👈 Updated to match your exact repository name

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        adminUserRepository.deleteAll(); // clear old data

        AdminUser admin = new AdminUser(); // 👈 Updated to match your exact entity name
        admin.setUsername("admin");
        admin.setPassword(passwordEncoder.encode("admin123"));
        admin.setRole("ROLE_ADMIN");

        adminUserRepository.save(admin);

        // Initializes the Chrome browser driver
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        // Configures explicit waits for your Angular app elements (Increased to 10s for smooth routing)
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // 1. Opens your running Angular Application URL
        driver.get("http://localhost:4200");
    }

    @Test
    void testCreateBrand_FullUIFlow() {
        // --- 1. LOGIN STEP (Fixes the timeout issue by actually logging in first!) ---
        WebElement usernameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//input[@type='text' or @name='username' or @placeholder='Username']")
        ));
        usernameInput.sendKeys("admin");

        WebElement passwordInput = driver.findElement(
                By.xpath("//input[@type='password' or @name='password' or @placeholder='Password']")
        );
        passwordInput.sendKeys("admin123");

        WebElement loginButton = driver.findElement(
                By.xpath("//button[@type='submit' or contains(text(), 'Login')]")
        );
        loginButton.click();


        // --- 2. NAVIGATION AND INTERACTION STEP ---
        // Locate and click the Brand management navigation link using your exact routerLink
        WebElement brandLink = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[@routerlink='/brands' or contains(text(), 'Brands')]")
        ));
        brandLink.click();

        // Click "+ Add Brand" button to open the hidden form section (*ngIf="showForm")
        WebElement addBrandButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(text(), '+ Add Brand')]")
        ));
        addBrandButton.click();

        // Find the input field using your placeholder text
        WebElement brandInput = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//input[@placeholder='Example: Apple']")
        ));
        brandInput.sendKeys("Google Pixel Accessories");

        // Find and click the Submit button inside your form
        WebElement saveButton = driver.findElement(
                By.xpath("//form//button[@type='submit' or contains(text(), 'Add Brand')]")
        );
        saveButton.click();

        // Verification: Check that your specific table row text displays the brand item
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