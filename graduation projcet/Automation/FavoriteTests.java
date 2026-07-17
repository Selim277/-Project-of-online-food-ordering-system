import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.time.Duration;

public class FavoriteTests {
    WebDriver driver;
    WebDriverWait wait;

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        driver.get("https://bstackdemo.com/");
    }

    @Test
    public void testAddProductToFavorite() {
        WebElement signInBtn = wait.until(ExpectedConditions.elementToBeClickable(By.id("signin")));
        signInBtn.click();

        WebElement usernameDropdown = wait.until(ExpectedConditions.elementToBeClickable(By.id("username")));
        usernameDropdown.click();

        Actions actions = new Actions(driver);
        actions.sendKeys("demouser").perform();

        WebElement selectUser = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[text()='demouser']")));
        selectUser.click();

        WebElement passwordDropdown = wait.until(ExpectedConditions.elementToBeClickable(By.id("password")));
        passwordDropdown.click();

        actions.sendKeys("testingisfun").perform();

        WebElement selectPassword = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[text()='testingisfun']")));
        selectPassword.click();

        driver.findElement(By.id("login-btn")).click();

        WebElement favoriteHeartBtn = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("(//button[contains(@class, 'MuiIconButton-root')])[1]")
        ));
        favoriteHeartBtn.click();

        WebElement favouritesTab = wait.until(ExpectedConditions.elementToBeClickable(By.id("favourites")));
        favouritesTab.click();

        WebElement favouriteProduct = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//p[contains(@class, 'shelf-item__title')]")
        ));

        Assert.assertTrue(favouriteProduct.isDisplayed());
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}