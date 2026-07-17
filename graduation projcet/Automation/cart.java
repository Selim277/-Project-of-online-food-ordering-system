mport io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import java.time.Duration;

public class AddToCartTest {
    public static void main(String[] args) {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        driver.get("https://bstackdemo.com/");

        driver.findElement(By.xpath("(//div[@class='shelf-item__buy-btn'])[1]")).click();

        boolean isCartVisible = driver.findElement(By.className("float-cart")).isDisplayed();

        if (isCartVisible) {
            System.out.println("Add to Cart Test Passed!");
        } else {
            System.out.println("Add to Cart Test Failed!");
        }

        driver.quit();
    }
}