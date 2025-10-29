package ma.theBeans.app.test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.time.Duration;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.File;
import java.io.IOException;
import java.awt.Desktop; 
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BookManagementSeleniumTest {
    
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
        options.addArguments("--headless");
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
    public void testAddNewBook() {
        try {
            // Naviguer vers la page de gestion des livres
            driver.get(baseUrl + "/app/admin/book/book/list");
            Thread.sleep(1500);

            // Cliquer sur le bouton New
            wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("/html/body/app-root/app-layout/div/div[2]/div/app-book-list-admin/div/div/div/p-toolbar/div/div[1]/button[1]")
            )).click();
            Thread.sleep(2000);

            // Attendre que le formulaire soit visible
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[contains(@class, 'p-dialog-content')]")
            ));
            Thread.sleep(1500);
            // Remplir le formulaire avec des attentes explicites
            wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[contains(@class, 'p-dialog-content')]//input[@id='1']")
            )).sendKeys("12345");
            Thread.sleep(1500);
            wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[contains(@class, 'p-dialog-content')]//input[@id='2']")
            )).sendKeys("Biography");
            Thread.sleep(1500);
            wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[contains(@class, 'p-dialog-content')]//input[@id='3']")
            )).sendKeys("Harriet Tubman");
            Thread.sleep(1500);
            // Sélectionner la catégorie
            wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id=\"8\"]/div/span")
            )).click();
            Thread.sleep(1000); // Attendre que le dropdown s'ouvre

            // Sélectionner la catégorie "Biography"
            wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id=\"pr_id_12_list\"]/p-dropdownitem/li/span[1]")
            )).click();
            Thread.sleep(1500);
            // Cliquer sur le champ de date pour ouvrir le calendrier
            wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id='4']/span/input")
            )).click();
            Thread.sleep(1000); // Attendre que le calendrier s'ouvre

         
            wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("/html/body/div/div[1]/div/div[2]/table/tbody/tr[4]/td[2]/span")
            )).click();

            wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id=\"5\"]")
            )).sendKeys("This is a historical book");
            Thread.sleep(1500);
            wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id=\"6\"]/span/input")
            )).sendKeys("2");
            Thread.sleep(1500);
            wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id=\"7\"]/div/span")
            )).click();
            // Gérer le dropdown Author
            wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id=\"9\"]/div/span")
            )).click();
            Thread.sleep(1000);

            wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id=\"pr_id_13_list\"]/p-dropdownitem/li")
            )).click();

            // Ajouter l'URL de l'image
            wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[contains(@class, 'p-dialog-content')]//input[@id='11']")
            )).sendKeys("https://tse1.mm.bing.net/th?id=OIP.MBBOofO0Zpwub_wFeBETXAHaLH&pid=Api&P=0&h=180");

            // Soumettre le formulaire
            wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("/html/body/app-root/app-layout/div/div[2]/div/app-book-list-admin/div/div/app-book-create-admin/p-dialog/div/div/div[3]/div/button[1]")
            )).click();
            
           
    
            Thread.sleep(1500);
        } catch (Exception e) {
            throw new RuntimeException("Test failed: " + e.getMessage());
        }
    }

    @Test
    @Order(2)
    public void testEditBook() {
        try {
            driver.get(baseUrl + "/app/admin/book/book/list");
            
            // Click the edit button for the first book
            wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//*[@id='pr_id_3-table']/tbody/tr[1]/td[6]/button[1]")
            )).click();
            
            // Wait for the dialog to be visible
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[contains(@class, 'p-dialog-content')]")
            ));
            Thread.sleep(1500);
            // Modifier les champs
            wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[contains(@class, 'p-dialog-content')]//input[@id='3']")
            )).clear();
            wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[contains(@class, 'p-dialog-content')]//input[@id='3']")
            )).sendKeys("The Road to Freedom");
            Thread.sleep(1500);
            // Wait for the next field to be clickable
            wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id=\"5\"]")
            )).clear();
            wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id=\"5\"]")
            )).sendKeys("This is a biography book");
            Thread.sleep(1500);
            // Clear and fill the number of copies field
          wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id=\"6\"]/span/input")
            )).clear();
            wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id=\"6\"]/span/input")
            )).sendKeys("6"); 
            Thread.sleep(1500);
            // Sauvegarder les modifications
            wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[contains(@class, 'p-dialog-footer')]//button[1]")
            )).click();
            Thread.sleep(1500);
            // Vérifier le message de succès
    
            
        } catch (Exception e) {
            throw new RuntimeException("Test failed: " + e.getMessage());
        }
    }

    @Test
    @Order(3)
    public void testDeleteBook() {
        try {
            driver.get(baseUrl + "/app/admin/book/book/list");
            
            // Cliquer sur le bouton de suppression du premier livre
            wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//*[@id=\"pr_id_3-table\"]/tbody/tr[1]/td[6]/button[3]/span")
            )).click();
            
            // Confirmer la suppression
            wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("/html/body/app-root/app-layout/div/div[2]/div/app-book-list-admin/div/div/p-confirmdialog/div/div/div[3]/button[2]/span[1]")
            )).click();
            
            // Vérifier le message de succès
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
    public void testSearchBook() {
        try {
            driver.get(baseUrl + "/app/admin/book/book/list");
            
            // Rechercher un livre
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"pr_id_3\"]/div[1]/div/span/input")))
                .sendKeys("Holmes");
                Thread.sleep(1500);
            // Vérifier que les résultats sont filtrés
            wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//table//tr[contains(., 'Holmes')]")
            ));
            Thread.sleep(1500);
        } catch (Exception e) {
            throw new RuntimeException("Test failed: " + e.getMessage());
        }
    }

    @Test
    @Order(5)
    public void testInvalidBookData() {
        try {
            driver.get(baseUrl + "/app/admin/book/book/list");
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("/html/body/app-root/app-layout/div/div[2]/div/app-book-list-admin/div/div/div/p-toolbar/div/div[1]/button[1]"))).click();
            Thread.sleep(1500);

            // Attempt to submit the form without filling in required fields
            wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("/html/body/app-root/app-layout/div/div[2]/div/app-book-list-admin/div/div/app-book-create-admin/p-dialog/div/div/div[3]/div/button[1]/span[2]")
            )).click();
            Thread.sleep(1500); // Wait for the error messages to appear

            // Verify that the error messages are displayed
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("/html/body/app-root/app-layout/div/div[2]/div/app-book-list-admin/div/div/p-toast/div/p-toastitem/div/div/div/div[2]")
            ));

            // Optionally, you can also check the text of the error message
            String errorMessage = driver.findElement(By.xpath("/html/body/app-root/app-layout/div/div[2]/div/app-book-list-admin/div/div/p-toast/div/p-toastitem/div/div/div/div[2]")).getText();
            assertEquals("Merci de corrigé les erreurs sur le formulaire", errorMessage); // Adjust the expected message as needed

        } catch (Exception e) {
            throw new RuntimeException("Test failed: " + e.getMessage());
        }
    }

    @Test
    @Order(6)
    public void testFilterBooks() {
        try {
            driver.get(baseUrl + "/app/admin/book/book/list");
         
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("/html/body/app-root/app-layout/div/div[2]/div/app-book-list-admin/div/div/div/p-toolbar[1]/div/div[1]/button[3]"))).click();
            // Enter a search term to filter books
            Thread.sleep(1500); 

            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"8\"]/div/span"))).click();
            Thread.sleep(1500); 

            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"pr_id_12_list\"]/p-dropdownitem[1]/li"))).click();
            // Example search term
            Thread.sleep(1500); 

            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("/html/body/app-root/app-layout/div/div[2]/div/app-book-list-admin/div/div/div/p-toolbar[2]/div/div/div[2]/button"))).click();
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
@Order(7)
public void testExportExcelBooks() {
    try {
        // Naviguer vers la page de gestion des livres
        driver.get(baseUrl + "/app/admin/book/book/list");
        Thread.sleep(1500); // Attendre que la page se charge

        // Cliquer sur le bouton d'exportation
        wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("/html/body/app-root/app-layout/div/div[2]/div/app-book-list-admin/div/div/div/p-toolbar/div/div[2]/div/p-splitbutton/div/button[2]")
        )).click();
        Thread.sleep(2000); // Attendre que le fichier soit téléchargé
        wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("/html/body/app-root/app-layout/div/div[2]/div/app-book-list-admin/div/div/div/p-toolbar/div/div[2]/div/p-splitbutton/div/p-tieredmenu/div/p-tieredmenusub/ul/li[2]/a/span[2]")
        )).click();
        Thread.sleep(2000);
        // Vérifier que le fichier a été téléchargé (ajustez le chemin et le nom du fichier selon vos besoins)
 // Vérifier que le fichier a été téléchargé (ajustez le chemin et le nom du fichier selon vos besoins)
 File downloadDir = new File(System.getProperty("user.home") + "\\Downloads");
 File[] files = downloadDir.listFiles();
 boolean fileFound = false;
 
 if (files != null) {
     for (File file : files) {
         if (file.getName().equals("Book.xlsx")) { // Replace with your file name
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
@Order(8)
public void testExportPdfBooks() {
    try {
        // Naviguer vers la page de gestion des livres
        driver.get(baseUrl + "/app/admin/book/book/list");
        Thread.sleep(1500); // Attendre que la page se charge

        // Cliquer sur le bouton d'exportation
        wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("/html/body/app-root/app-layout/div/div[2]/div/app-book-list-admin/div/div/div/p-toolbar/div/div[2]/div/p-splitbutton/div/button[2]")
        )).click();
        Thread.sleep(2000); // Attendre que le fichier soit téléchargé
        wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("/html/body/app-root/app-layout/div/div[2]/div/app-book-list-admin/div/div/div/p-toolbar/div/div[2]/div/p-splitbutton/div/p-tieredmenu/div/p-tieredmenusub/ul/li[3]/a/span[2]")
        )).click();
        Thread.sleep(2000);
        // Vérifier que le fichier a été téléchargé (ajustez le chemin et le nom du fichier selon vos besoins)
 // Vérifier que le fichier a été téléchargé (ajustez le chemin et le nom du fichier selon vos besoins)
 File downloadDir = new File(System.getProperty("user.home") + "\\Downloads");
File[] files = downloadDir.listFiles();
boolean fileFound = false;

if (files != null) {
    for (File file : files) {
        if (file.getName().equals("Book.pdf")) { // Replace with your file name
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
@Order(9)
public void testExportCsvBooks() {
    try {
        // Naviguer vers la page de gestion des livres
        driver.get(baseUrl + "/app/admin/book/book/list");
        Thread.sleep(1500); // Attendre que la page se charge

        // Cliquer sur le bouton d'exportation
        wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("/html/body/app-root/app-layout/div/div[2]/div/app-book-list-admin/div/div/div/p-toolbar/div/div[2]/div/p-splitbutton/div/button[2]")
        )).click();
        Thread.sleep(2000); // Attendre que le fichier soit téléchargé
        wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("/html/body/app-root/app-layout/div/div[2]/div/app-book-list-admin/div/div/div/p-toolbar/div/div[2]/div/p-splitbutton/div/p-tieredmenu/div/p-tieredmenusub/ul/li[1]/a/span[2]")
        )).click();
        Thread.sleep(2000);

        // Vérifier que le fichier a été téléchargé
        File downloadDir = new File(System.getProperty("user.home") + "\\Downloads");
        File[] files = downloadDir.listFiles();
        boolean fileFound = false;

        if (files != null) {
            for (File file : files) {
                if (file.getName().equals("Book.csv")) { // Remplacez par le nom de votre fichier
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


  

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
} 