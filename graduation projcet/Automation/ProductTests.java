import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.time.Duration;
import java.util.List;

public class ProductTests {
    WebDriver driver;
    WebDriverWait wait;

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(15)); // زيادة وقت الانتظار لـ 15 ثانية لضمان الاستقرار
        driver.get("https://bstackdemo.com/");

        // خطوة تسجيل الدخول هتحصل تلقائياً قبل أي تيست عشان نبدأ صح
        wait.until(ExpectedConditions.elementToBeClickable(By.id("signin"))).click();

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[text()='Select Username']"))).click();
        driver.findElement(By.xpath("//div[text()='fav_user']")).click();

        driver.findElement(By.xpath("//div[text()='Select Password']")).click();
        driver.findElement(By.xpath("//div[text()='testingisfun99']")).click();

        driver.findElement(By.id("login-btn")).click();

        // نتأكد إن الصفحة الرئيسية للمنتجات حملت تمام قبل ما التيست يبدأ
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".username")));
    }

    // 1. التيست الأول: التأكد من نجاح تسجيل الدخول وظهور الاسم الصحيح
    @Test(priority = 1)
    public void testLoginSuccessfully() {
        WebElement username = driver.findElement(By.cssSelector(".username"));
        Assert.assertEquals(username.getText(), "fav_user", "اسم المستخدم غير صحيح!");
    }

    // 2. التيست الثاني: ترتيب المنتجات من الأقل للأعلى في السعر
    @Test(priority = 2)
    public void testSortLowestToHighest() {
        // انتظار ظهور الـ Dropdown والتعامل معاه
        WebElement sortDropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".sort select")));
        Select select = new Select(sortDropdown);
        select.selectByValue("lowestprice");

        // انتظار ثانية واحدة عشان المنتجات تترتب في الـ UI
        try { Thread.sleep(1500); } catch (InterruptedException e) { e.printStackTrace(); }

        // التحقق من الأسعار
        List<WebElement> priceElements = driver.findElements(By.xpath("//div[@class='val']/b"));
        float previousPrice = 0;

        for (WebElement priceElement : priceElements) {
            float currentPrice = Float.parseFloat(priceElement.getText());
            Assert.assertTrue(currentPrice >= previousPrice, "الترتيب غير صحيح! سعر " + currentPrice + " ظهر بعد سعر " + previousPrice);
            previousPrice = currentPrice;
        }
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}