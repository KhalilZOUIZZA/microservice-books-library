package ma.theBeans.app.test;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.time.Duration;

public class AuthenticationSeleniumTest {
    
    private WebDriver driver;
    private WebDriverWait wait;
    private final String baseUrl = "http://localhost:4200";
    
    @BeforeEach
    public void setUp() {
        WebDriverManager.chromedriver().clearDriverCache().setup();
        
        ChromeOptions options = new ChromeOptions();
        // Supprimé le mode headless pour voir le navigateur
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(120));
        driver.manage().window().maximize();
    }
    
    @Test
    public void testSuccessfulAuthentication() {
        try {
            // Ouvrir la page de login
            driver.get(baseUrl + "/admin/login");
            wait.until(ExpectedConditions.presenceOfElementLocated(By.id("username")));
            Thread.sleep(1500);
            
            // Credentials valides
            driver.findElement(By.id("username")).sendKeys("admin");
            driver.findElement(By.id("password")).sendKeys("123");
            driver.findElement(By.xpath("/html/body/app-root/app-login-admin/div/form/div[3]/button")).click();
            
            // Modifier cette ligne pour correspondre à l'URL réelle après connexion
            wait.until(ExpectedConditions.urlContains("/app/admin"));
            Thread.sleep(1500);
            
        } catch (Exception e) {
            throw new RuntimeException("Test failed: " + e.getMessage());
        }
    }

    @Test
    public void testInvalidCredentials() {
        try {
            driver.get(baseUrl + "/admin/login");
            wait.until(ExpectedConditions.presenceOfElementLocated(By.id("username")));
            Thread.sleep(1500);
            
            // Credentials invalides
            driver.findElement(By.id("username")).sendKeys("wrong");
            driver.findElement(By.id("password")).sendKeys("wrong");
            driver.findElement(By.xpath("/html/body/app-root/app-login-admin/div/form/div[3]/button")).click();
            
            // Vérifier le message d'erreur
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("/html/body/app-root/app-login-admin/p-toast/div/p-toastitem/div/div/div/div[2]")));
            Thread.sleep(1500);
            
        } catch (Exception e) {
            throw new RuntimeException("Test failed: " + e.getMessage());
        }
    }
    @Test
    public void testInvalidPwd() {
        try {
            driver.get(baseUrl + "/admin/login");
            wait.until(ExpectedConditions.presenceOfElementLocated(By.id("username")));
            Thread.sleep(1500);
            
            // Credentials invalides
            driver.findElement(By.id("username")).sendKeys("admin");
            driver.findElement(By.id("password")).sendKeys("wrong");
            driver.findElement(By.xpath("/html/body/app-root/app-login-admin/div/form/div[3]/button")).click();
            
            // Vérifier le message d'erreur
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("/html/body/app-root/app-login-admin/p-toast/div/p-toastitem/div/div/div/div[2]")));
            Thread.sleep(1500);
            
        } catch (Exception e) {
            throw new RuntimeException("Test failed: " + e.getMessage());
        }
    }
    @Test
    public void testInvalidUserName() {
        try {
            driver.get(baseUrl + "/admin/login");
            wait.until(ExpectedConditions.presenceOfElementLocated(By.id("username")));
            Thread.sleep(1500);
            
            // Credentials invalides
            driver.findElement(By.id("username")).sendKeys("wrong");
            driver.findElement(By.id("password")).sendKeys("123");
            driver.findElement(By.xpath("/html/body/app-root/app-login-admin/div/form/div[3]/button")).click();
            
            // Vérifier le message d'erreur
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("/html/body/app-root/app-login-admin/p-toast/div/p-toastitem/div/div/div/div[2]")));
            Thread.sleep(1500);
            
        } catch (Exception e) {
            throw new RuntimeException("Test failed: " + e.getMessage());
        }
    }
    @Test
    public void testEmptyCredentials() {
        try {
            driver.get(baseUrl + "/admin/login");
            wait.until(ExpectedConditions.presenceOfElementLocated(By.id("username")));
            
            // Pause pour voir les champs vides
            Thread.sleep(1500);
            
            // Cliquer sur login sans remplir les champs
            driver.findElement(By.xpath("/html/body/app-root/app-login-admin/div/form/div[3]/button")).click();
            
            // Vérifier les messages de validation
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("/html/body/app-root/app-login-admin/p-toast/div/p-toastitem/div/div/div/div[2]")));
            
            // Pause pour voir le message d'erreur
            Thread.sleep(1500);
            
        } catch (Exception e) {
            throw new RuntimeException("Test failed: " + e.getMessage());
        }
    }

   /*@Test
    public void testLogout() {
        try {
            // D'abord se connecter
            testSuccessfulAuthentication();
            
            // Trouver et cliquer sur le bouton logout
            wait.until(ExpectedConditions.elementToBeClickable(By.id("logout-button")));
            driver.findElement(By.xpath("/html/body/app-root/app-layout/div/app-topbar/div/div/button[2]")).click();
            
            // Vérifier la redirection vers la page de login
            wait.until(ExpectedConditions.urlContains("/app/login"));
        } catch (Exception e) {
            throw new RuntimeException("Test failed: " + e.getMessage());
        }
    }*/

   /*  @Test
    public void testSessionTimeout() {
        try {
            testSuccessfulAuthentication();
            
            // Attendre que la session expire (par exemple 30 minutes)
            Thread.sleep(18); // 30 minutes
            
            // Essayer d'accéder à une page protégée
            driver.get(baseUrl + "/app/admin");
            
            // Vérifier la redirection vers la page de login
            wait.until(ExpectedConditions.urlContains("/app/login"));
        } catch (Exception e) {
            throw new RuntimeException("Test failed: " + e.getMessage());
        }
    }
*/
   /*  @Test
    public void testRememberMe() {
        try {
            driver.get(baseUrl + "/admin/login");
            wait.until(ExpectedConditions.presenceOfElementLocated(By.id("username")));
            Thread.sleep(1500);
            
            // Remplir les credentials
            driver.findElement(By.id("username")).sendKeys("admin");
            driver.findElement(By.id("password")).sendKeys("123");
            
            // Cocher "Remember me"
            driver.findElement(By.id("remember-me")).click();
            
            driver.findElement(By.xpath("/html/body/app-root/app-login-admin/div/form/div[3]/button")).click();
            
            // Fermer et rouvrir le navigateur
            driver.quit();
            setUp();
            driver.get(baseUrl + "/app/admin");
            
            // Vérifier qu'on est toujours connecté
            wait.until(ExpectedConditions.urlContains("/app/admin"));
            Thread.sleep(1500);
            
        } catch (Exception e) {
            throw new RuntimeException("Test failed: " + e.getMessage());
        }
    }*/
    
  /*   @Test
public void testForgetPassword() {
    try {
        // Accéder à la page forget password
        driver.get(baseUrl + "/admin/forget-password");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("email")));
        Thread.sleep(1500);
        
        // Test avec un email valide
        driver.findElement(By.id("email")).sendKeys("admin@gmail.com");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        
        // Vérifier la redirection vers la page de changement de mot de passe
        wait.until(ExpectedConditions.urlContains("/admin/changePassword"));
        Thread.sleep(1500);
        
        // Vérifier la présence des champs sur la page de changement de mot de passe
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("email")));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("activationCode")));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("password")));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("confirmPassword")));
        
        // Remplir le formulaire de changement de mot de passe
        driver.findElement(By.id("email")).sendKeys("admin@gmail.com");
        driver.findElement(By.id("activationCode")).sendKeys("12345678");
        driver.findElement(By.id("password")).sendKeys("newPassword123");
        driver.findElement(By.id("confirmPassword")).sendKeys("newPassword123");
        
        // Soumettre le formulaire
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        
        // Attendre le message de succès
        wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//p-toast//div[contains(@class, 'p-toast-message-success')]")
        ));
        Thread.sleep(1500);
        
    } catch (Exception e) {
        throw new RuntimeException("Test failed: " + e.getMessage());
    }
}*/
    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit(); // Ferme Chrome et termine le WebDriver
        }
    }
}