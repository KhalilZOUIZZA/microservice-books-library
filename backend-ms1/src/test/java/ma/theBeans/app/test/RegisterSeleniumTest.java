package ma.theBeans.app.test;

import java.time.Duration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.github.bonigarcia.wdm.WebDriverManager;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RegisterSeleniumTest {
    private WebDriver driver;
    private WebDriverWait wait;
    private final String baseUrl = "http://localhost:4200";

    @BeforeEach
    public void setUp() {
        WebDriverManager.chromedriver().clearDriverCache().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--start-maximized");
        options.addArguments("--disable-gpu");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(120));
    }

    @Test
    public void testSuccessfulRegistration() {
        try {
            driver.get(baseUrl + "/admin/register");
            driver.findElement(By.xpath("//*[@id=\"firstName\"]")).sendKeys("test");
            driver.findElement(By.xpath("//*[@id=\"lastName\"]")).sendKeys("test2");
            driver.findElement(By.xpath("//*[@id=\"email\"]")).sendKeys("test@gmail.com");
            driver.findElement(By.xpath("//*[@id=\"phone\"]")).sendKeys("0654123789");
            driver.findElement(By.xpath("//*[@id=\"userName\"]")).sendKeys("test123");
            driver.findElement(By.xpath("//*[@id=\"password\"]")).sendKeys("TEST_123456789");
            driver.findElement(By.xpath("/html/body/app-root/app-register-admin/div/form/div[7]/button")).click();
            // Wait for confirmation element to be visible
            Thread.sleep(1500);
        } catch (Exception e) {
            throw new RuntimeException("Test failed: " + e.getMessage());
        }
    }

    @Test
    public void testEmptyFieldsRegistration() {
        try {
            driver.get(baseUrl + "/admin/register");
            // Click the "Sign Up" button without filling in fields
            driver.findElement(By.xpath("/html/body/app-root/app-register-admin/div/form/div[7]/button")).click();
            Thread.sleep(1500);
            // Wait for the error message to be visible
            WebElement errorMessageElement = driver.findElement(By.xpath("/html/body/app-root/app-register-admin/p-toast/div/p-toastitem/div/div/div/div[2]"));
            String errorMessage = errorMessageElement.getText();
            assertEquals("Fill in all fields", errorMessage); // Adjust the expected message as needed
            Thread.sleep(1500);
        } catch (Exception e) {
            throw new RuntimeException("Test failed: " + e.getMessage());
        }
    }

    @Test
   // Ensure this test runs after the others
    public void testInvalidEmailFormat() {
        try {
            driver.get(baseUrl + "/admin/register");
            
            // Fill in the registration form with an invalid email
            driver.findElement(By.xpath("//*[@id=\"firstName\"]")).sendKeys("IMANE");
            driver.findElement(By.xpath("//*[@id=\"lastName\"]")).sendKeys("FAHIM");
            driver.findElement(By.xpath("//*[@id=\"email\"]")).sendKeys("imane5020gm.com"); // Invalid email format
            driver.findElement(By.xpath("//*[@id=\"phone\"]")).sendKeys("0654123789");
            driver.findElement(By.xpath("//*[@id=\"userName\"]")).sendKeys("emaFahim");
            driver.findElement(By.xpath("//*[@id=\"password\"]")).sendKeys("IMANE_123456789");
            
            // Click the "Sign Up" button
            driver.findElement(By.xpath("/html/body/app-root/app-register-admin/div/form/div[7]/button")).click();
            Thread.sleep(1500); // Wait for the error message to appear

            // Verify that the error message is displayed
            WebElement errorMessageElement = driver.findElement(By.xpath("/html/body/app-root/app-register-admin/p-toast/div/p-toastitem/div/div/div/div[2]"));
            String errorMessage = errorMessageElement.getText();
            assertEquals("Invalid email format ,valide format is: xxxx@xxx.xxx", errorMessage); // Adjust the expected message as needed

            Thread.sleep(1500); // Wait for a moment to observe the result

        } catch (Exception e) {
            throw new RuntimeException("Test failed: " + e.getMessage());
        }
    }

    @Test
    // Ensure this test runs after the others
     public void testWithShortPwd() {
         try {
             driver.get(baseUrl + "/admin/register");
             
             // Fill in the registration form with an invalid email
             driver.findElement(By.xpath("//*[@id=\"firstName\"]")).sendKeys("IMANE");
             driver.findElement(By.xpath("//*[@id=\"lastName\"]")).sendKeys("FAHIM");
             driver.findElement(By.xpath("//*[@id=\"email\"]")).sendKeys("ema@gmail.com"); 
             driver.findElement(By.xpath("//*[@id=\"phone\"]")).sendKeys("0654123789");
             driver.findElement(By.xpath("//*[@id=\"userName\"]")).sendKeys("imane");
             driver.findElement(By.xpath("//*[@id=\"password\"]")).sendKeys("short");
             
             // Click the "Sign Up" button
             driver.findElement(By.xpath("/html/body/app-root/app-register-admin/div/form/div[7]/button")).click();
             Thread.sleep(1500); // Wait for the error message to appear
 
             // Verify that the error message is displayed
             WebElement errorMessageElement = driver.findElement(By.xpath("/html/body/app-root/app-register-admin/p-toast/div/p-toastitem/div/div/div/div[2]"));
             String errorMessage = errorMessageElement.getText();
             assertEquals("Password must be at least 8 characters long.", errorMessage); // Adjust the expected message as needed
 
             Thread.sleep(1500); // Wait for a moment to observe the result
 
         } catch (Exception e) {
             throw new RuntimeException("Test failed: " + e.getMessage());
         }
     }


     @Test
     public void testUserNameAlreadyExists() {
         try {
             driver.get(baseUrl + "/admin/register");
             driver.findElement(By.xpath("//*[@id=\"firstName\"]")).sendKeys("IMANE");
             driver.findElement(By.xpath("//*[@id=\"lastName\"]")).sendKeys("FAHIM");
             driver.findElement(By.xpath("//*[@id=\"email\"]")).sendKeys("imane50@gmail.com");
             driver.findElement(By.xpath("//*[@id=\"phone\"]")).sendKeys("0654123789");
             driver.findElement(By.xpath("//*[@id=\"userName\"]")).sendKeys("ema");
             driver.findElement(By.xpath("//*[@id=\"password\"]")).sendKeys("IMANE_123456789");
             driver.findElement(By.xpath("/html/body/app-root/app-register-admin/div/form/div[7]/button")).click();

             WebElement errorMessageElement = driver.findElement(By.xpath("/html/body/app-root/app-register-admin/p-toast/div/p-toastitem/div/div/div/div[2]"));
             String errorMessage = errorMessageElement.getText();
             assertEquals("This username has already been taken", errorMessage); // Adjust the expected message as needed
             // Wait for confirmation element to be visible
             Thread.sleep(1500);
         } catch (Exception e) {
             throw new RuntimeException("Test failed: " + e.getMessage());
         }
     }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit(); // Close Chrome and terminate the WebDriver
        }
    }
}