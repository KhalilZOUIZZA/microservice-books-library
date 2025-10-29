package ma.theBeans.app.test;
import java.io.IOException;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class AuthorSeleniumTest {
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
     
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(120));
        driver.manage().window().maximize();
        
        // Se connecter en tant qu'admin avant chaque test
        login();
    }

    private void login() {
        try {
            driver.get(baseUrl + "/admin/login");
            wait.until(ExpectedConditions.presenceOfElementLocated(By.id("username")));
            driver.findElement(By.id("username")).sendKeys("admin");
            driver.findElement(By.id("password")).sendKeys("123");
            driver.findElement(By.xpath("//button[@type='submit']")).click();
            wait.until(ExpectedConditions.urlContains("/app/admin"));
            Thread.sleep(1500);
        } catch (Exception e) {
            throw new RuntimeException("Login failed: " + e.getMessage());
        }
    }


 @Test
    @Order(1)
    public void testAddAuthor() {
        try {
            // Naviguer vers la page de gestion des livres
            driver.get(baseUrl + "/app/admin/book/author/list");
            Thread.sleep(1500);

            // Cliquer sur le bouton New
            wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("/html/body/app-root/app-layout/div/div[2]/div/app-author-list-admin/div/div/div/p-toolbar/div/div[1]/button[1]")
            )).click();
            Thread.sleep(2000);

            // Attendre que le formulaire soit visible
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[contains(@class, 'p-dialog-content')]")
            ));
            Thread.sleep(1500);
            // Remplir le formulaire avec des attentes explicites
            wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id=\"1\"]")
            )).sendKeys("YF7");
            Thread.sleep(1500);
            wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id=\"2\"]")
            )).sendKeys("Writer");
            Thread.sleep(1500);
            wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id=\"3\"]")
            )).sendKeys("Moliere");
            Thread.sleep(1500);
            wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("/html/body/app-root/app-layout/div/div[2]/div/app-author-list-admin/div/div/app-author-create-admin/p-dialog/div/div/div[3]/div/button[1]")
            )).click();
            
            Thread.sleep(1500);
        } catch (Exception e) {
            throw new RuntimeException("Test failed: " + e.getMessage());
        }
    }

    @Test
    @Order(2)
    public void testEditAuthor() {
        try {
            driver.get(baseUrl + "/app/admin/book/author/list");
            

            wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//*[@id=\"pr_id_3-table\"]/tbody/tr[1]/td[5]/button[1]")
            )).click();
            
            // Wait for the dialog to be visible
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[contains(@class, 'p-dialog-content')]")
            ));
            Thread.sleep(1500);
            // Modifier les champs
            wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id=\"2\"]")
            )).clear();
            wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id=\"2\"]")
            )).sendKeys("Poete");
            Thread.sleep(1500);
           
            wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("/html/body/app-root/app-layout/div/div[2]/div/app-author-list-admin/div/div/app-author-edit-admin/p-dialog/div/div/div[3]/div/button[1]")
            )).click();
            Thread.sleep(1500);
        
    
            
        } catch (Exception e) {
            throw new RuntimeException("Test failed: " + e.getMessage());
        }
    }

    @Test
    @Order(3)
    public void testDeleteAuthor() {
        try {
            driver.get(baseUrl + "/app/admin/book/author/list");
            

            wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//*[@id=\"pr_id_3-table\"]/tbody/tr[1]/td[5]/button[3]")
            )).click();
            
            // Confirm deletion
            wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("/html/body/app-root/app-layout/div/div[2]/div/app-author-list-admin/div/div/p-confirmdialog/div/div/div[3]/button[2]")
            )).click();
            
            // Verify the success message
            wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//p-toast//div[contains(@class, 'p-toast-message-success')]")
            ));
            Thread.sleep(1500);

        } catch (Exception e) {
            throw new RuntimeException("Test failed: " + e.getMessage());
        }
    }

    @Test
    @Order(4)
     public void testSearchAuthor() {
         try {
             driver.get(baseUrl + "/app/admin/book/author/list");
             
             // Rechercher un livre
             wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"pr_id_3\"]/div[1]/div/span/input")))
                 .sendKeys("Nancy");
                 Thread.sleep(1500);
             // Vérifier que les résultats sont filtrés
             wait.until(ExpectedConditions.presenceOfElementLocated(
                 By.xpath("//table//tr[contains(., 'Nancy')]")
             ));
             Thread.sleep(1500);
         } catch (Exception e) {
             throw new RuntimeException("Test failed: " + e.getMessage());
         }
     }


     @Test
    @Order(5)
    public void testFilterAuthors() {
        try {
            driver.get(baseUrl + "/app/admin/book/author/list");
         
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("/html/body/app-root/app-layout/div/div[2]/div/app-author-list-admin/div/div/div/p-toolbar[1]/div/div[1]/button[3]"))).click();
            // Enter a search term to filter books
            Thread.sleep(1500); 

            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"3\"]"))).sendKeys("Na");
            Thread.sleep(1500); 


            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("/html/body/app-root/app-layout/div/div[2]/div/app-author-list-admin/div/div/div/p-toolbar[2]/div/div/div[2]/button"))).click();
            // Example search term
            Thread.sleep(1500); //

              JavascriptExecutor js = (JavascriptExecutor) driver;
              Thread.sleep(1500);
        js.executeScript("window.scrollBy(0, 900);"); 
         
            Thread.sleep(1500); // Wait for the results to filter

            // Optionally, you can assert the count of displayed books
            // Add your assertion logic here if needed

        } catch (Exception e) {
            throw new RuntimeException("Test failed: " + e.getMessage());
        }
    }



 @Test
@Order(6)
public void testExportExcelAuthors() {
    try {
        // Naviguer vers la page de gestion des livres
        driver.get(baseUrl + "/app/admin/book/author/list");
        Thread.sleep(1500); // Attendre que la page se charge

        // Cliquer sur le bouton d'exportation
        wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("/html/body/app-root/app-layout/div/div[2]/div/app-author-list-admin/div/div/div/p-toolbar/div/div[2]/div/p-splitbutton/div/button[2]")
        )).click();
        Thread.sleep(2000); // Attendre que le fichier soit téléchargé
        wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("/html/body/app-root/app-layout/div/div[2]/div/app-author-list-admin/div/div/div/p-toolbar[1]/div/div[2]/div/p-splitbutton/div/p-tieredmenu/div/p-tieredmenusub/ul/li[2]/a")
        )).click();
        Thread.sleep(2000);
        // Vérifier que le fichier a été téléchargé (ajustez le chemin et le nom du fichier selon vos besoins)
 // Vérifier que le fichier a été téléchargé (ajustez le chemin et le nom du fichier selon vos besoins)
 File downloadDir = new File(System.getProperty("user.home") + "\\Downloads");
 File[] files = downloadDir.listFiles();
 boolean fileFound = false;
 
 if (files != null) {
     for (File file : files) {
         if (file.getName().equals("Author.xlsx")) { // Replace with your file name
             fileFound = true;
 
             // Open the file
             Process process = null;
             try {
                 // Open Excel with the file
                 process = new ProcessBuilder("C:\\Program Files\\Microsoft Office\\root\\Office16\\EXCEL.EXE", file.getAbsolutePath()).start();
                 
                 // Wait for a specified time (e.g., 10 seconds)
                 Thread.sleep(5000); // Adjust the time as needed
 
             } catch (IOException e) {
                 throw new RuntimeException("Failed to open the file: " + e.getMessage());
             } catch (InterruptedException e) {
                 Thread.currentThread().interrupt(); // Restore interrupted status
             } finally {
                 // Close Excel
                 if (process != null) {
                     process.destroy(); // Terminate the Excel process
                 }
             }
             break;
         }
     }
 }
    } catch (Exception e) {
        throw new RuntimeException("Test failed: " + e.getMessage());
    }
}

@Test
@Order(7)
public void testExportPdfAuthors() {
    try {
        // Naviguer vers la page de gestion des livres
        driver.get(baseUrl + "/app/admin/book/author/list");
        Thread.sleep(1500); // Attendre que la page se charge

        // Cliquer sur le bouton d'exportation
        wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("/html/body/app-root/app-layout/div/div[2]/div/app-author-list-admin/div/div/div/p-toolbar/div/div[2]/div/p-splitbutton/div/button[2]")
        )).click();
        Thread.sleep(2000); // Attendre que le fichier soit téléchargé
        wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("/html/body/app-root/app-layout/div/div[2]/div/app-author-list-admin/div/div/div/p-toolbar[1]/div/div[2]/div/p-splitbutton/div/p-tieredmenu/div/p-tieredmenusub/ul/li[3]/a")
        )).click();
        Thread.sleep(2000);
        // Vérifier que le fichier a été téléchargé (ajustez le chemin et le nom du fichier selon vos besoins)
 // Vérifier que le fichier a été téléchargé (ajustez le chemin et le nom du fichier selon vos besoins)
 File downloadDir = new File(System.getProperty("user.home") + "\\Downloads");
File[] files = downloadDir.listFiles();
boolean fileFound = false;

if (files != null) {
    for (File file : files) {
        if (file.getName().equals("Author.pdf")) { // Replace with your file name
            fileFound = true;

            // Open the PDF file
            Process process = null;
            try {
                // Open the PDF with the default PDF viewer
                process = new ProcessBuilder("C:\\Program Files (x86)\\Foxit Software\\Foxit PDF Reader\\FoxitPDFReader.exe", file.getAbsolutePath()).start();
                
                // Wait for a specified time (e.g., 10 seconds)
                Thread.sleep(10000); // Adjust the time as needed

            } catch (IOException e) {
                throw new RuntimeException("Failed to open the file: " + e.getMessage());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Restore interrupted status
            } finally {
                // Close the PDF viewer
                if (process != null) {
                    process.destroy(); // Terminate the PDF viewer process
                }
            }
            break;
        }
    }
}
    } catch (Exception e) {
        throw new RuntimeException("Test failed: " + e.getMessage());
    }
}

@Test
@Order(8)
public void testExportCsvAuthors() {
    try {
        // Naviguer vers la page de gestion des livres
        driver.get(baseUrl + "/app/admin/book/author/list");
        Thread.sleep(1500); 
        wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("/html/body/app-root/app-layout/div/div[2]/div/app-author-list-admin/div/div/div/p-toolbar/div/div[2]/div/p-splitbutton/div/button[2]")
        )).click();
        Thread.sleep(2000); 
        wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("/html/body/app-root/app-layout/div/div[2]/div/app-author-list-admin/div/div/div/p-toolbar[1]/div/div[2]/div/p-splitbutton/div/p-tieredmenu/div/p-tieredmenusub/ul/li[1]/a")
        )).click();
        Thread.sleep(2000);

        // Vérifier que le fichier a été téléchargé
        File downloadDir = new File(System.getProperty("user.home") + "\\Downloads");
        File[] files = downloadDir.listFiles();
        boolean fileFound = false;

        if (files != null) {
            for (File file : files) {
                if (file.getName().equals("Author.csv")) { // Remplacez par le nom de votre fichier
                    fileFound = true;

                    // Open the CSV file with Excel
                    Process process = null;
                    try {
                        // Open the CSV with Excel
                        process = new ProcessBuilder("C:\\Program Files\\Microsoft Office\\root\\Office16\\EXCEL.EXE", file.getAbsolutePath()).start();
                        
                        // Wait for a specified time (e.g., 10 seconds)
                        Thread.sleep(5000); // Adjust the time as needed

                    } catch (IOException e) {
                        throw new RuntimeException("Failed to open the file: " + e.getMessage());
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt(); // Restore interrupted status
                    } finally {
                        // Close the Excel application
                        if (process != null) {
                            process.destroy(); // Terminate the Excel process
                        }
                    }
                    break;
                }
            }
        }

    } catch (Exception e) {
        throw new RuntimeException("Test failed: " + e.getMessage());
    }
}
@Test
    @Order(9)
    public void testInvalidAuthorData() {
        try {
            driver.get(baseUrl + "/app/admin/book/author/list");
            wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("/html/body/app-root/app-layout/div/div[2]/div/app-author-list-admin/div/div/div/p-toolbar/div/div[1]/button[1]")
            )).click();
            Thread.sleep(2000);

            // Attempt to submit the form without filling in required fields
            wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("/html/body/app-root/app-layout/div/div[2]/div/app-author-list-admin/div/div/app-author-create-admin/p-dialog/div/div/div[3]/div/button[1]")
            )).click();
            Thread.sleep(1500); // Wait for the error messages to appear

            // Verify that the error messages are displayed
            WebElement errorMessageElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("/html/body/app-root/app-layout/div/div[2]/div/app-author-list-admin/div/div/p-toast/div/p-toastitem/div/div/div/div[2]")
            ));

            // Check the text of the error message
            String errorMessage = errorMessageElement.getText();
            assertEquals("Merci de corrigé les erreurs sur le formulaire", errorMessage); // Adjust the expected message as needed

        } catch (Exception e) {
            throw new RuntimeException("Test failed: " + e.getMessage());
        }
    }
   @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

}
