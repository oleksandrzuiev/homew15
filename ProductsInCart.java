package homeWork15;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.time.Duration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProductsInCart {

    protected static WebDriver driver;
    protected static WebDriverWait wait;

    protected final String pageTitleClass = "Navbar_logo__26S5Y";
    protected final int firstItem = 1;
    protected final int secondItem = 2;
    protected final String itemIdPath = "//div[@id='%s']";
    protected final String pricePath = itemIdPath + "//div[@class='val']//b";
    protected final String titlePath = itemIdPath + "//p[@class='shelf-item__title']";
    protected final String addButtonPath = itemIdPath + "//div[@class='shelf-item__buy-btn']";

    protected final String itemFieldPath = "//div[@class='float-cart__shelf-container']//div[@class='shelf-item']";
    protected final String emptyCartViewPath = "//div[@class='float-cart__shelf-container']//p[@class='shelf-empty']";
    protected final String priceCartPath = itemFieldPath + "[%s]//div[@class='shelf-item__price']//p";
    protected final String titleCartPath = itemFieldPath + "[%s]//p[@class='title']";
    protected final String quantityCartPath = itemFieldPath + "[%s]//p[@class='desc']";
    protected final String cartViewClass = "float-cart__content";
    protected final String removeItemButtonPath = "//div[@class='shelf-item__del']";
    protected final String totalCartPriceClass = "sub-price__val";
    protected final String closeCartPath = "//div[@class='float-cart__close-btn']";
    protected final String checkoutButtonPath = "//div[@class='buy-btn' and text()='Checkout']";
    protected final String totalPriceRegex = "\\$\\s*(\\d+)\\.\\d{2}";
    protected final String quantityCartRegex = "\\bQuantity: (\\d+)\\b";

    public static void findAndClick(String xpath) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
        element.click();
    }

    public static int extractNumber(String text, String patternString) {
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(text);

        if (matcher.find()) {
            try {
                return Integer.parseInt(matcher.group(1));
            } catch (NumberFormatException e) {
                return -1;
            }
        }
        return -1;
    }

    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().setSize(new Dimension(1920, 1080));
        driver.get("https://www.bstackdemo.com/");
        wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className(pageTitleClass)));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

    public WebElement findItemElement(String path, int itemNumber) {
        String xpathExpression = String.format(path, itemNumber);
        return driver.findElement(By.xpath(xpathExpression));
    }

    public void findAndClickItemElement(String path, int itemNumber) {
        String xpathExpression = String.format(path, itemNumber);
        WebElement element = driver.findElement(By.xpath(xpathExpression));
        element.click();
    }
}
