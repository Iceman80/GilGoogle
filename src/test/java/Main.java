import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class Main {
        private WebDriver driver;

        private boolean acceptNextAlert = true;
        private StringBuffer verificationErrors = new StringBuffer();

        @Before
        public void setUp() throws Exception {
                driver = new FirefoxDriver();

                driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        }

        @Test
        public void test1() throws Exception {
                driver.get("https://www.google.com.ua/?gfe_rd=cr&ei=Wu5mVtmfA4KUZOHliNAO&gws_rd=ssl");
                driver.findElement(By.id("lst-ib")).clear();
                driver.findElement(By.id("lst-ib")).sendKeys("gileya.org");
                driver.findElement(By.name("btnG")).click();
                for (int second = 0;; second++) {
                        if (second >= 60) fail("timeout");
                        try { if ("Гілея: науковий вісник".equals(driver.findElement(By.cssSelector("a.l")).getText())) break; } catch (Exception e) {}
                        Thread.sleep(1000);
                }

                driver.findElement(By.linkText("Гілея: науковий вісник")).click();
                driver.findElement(By.xpath("//map[@id='Map']/area[2]")).click();
                driver.findElement(By.xpath("//map[@id='Map']/area[3]")).click();
        }

        @After
        public void tearDown() throws Exception {
                driver.quit();
                String verificationErrorString = verificationErrors.toString();
                if (!"".equals(verificationErrorString)) {
                        fail(verificationErrorString);
                }
        }

        private boolean isElementPresent(By by) {
                try {
                        driver.findElement(by);
                        return true;
                } catch (NoSuchElementException e) {
                        return false;
                }
        }

        private boolean isAlertPresent() {
                try {
                        driver.switchTo().alert();
                        return true;
                } catch (NoAlertPresentException e) {
                        return false;
                }
        }

        private String closeAlertAndGetItsText() {
                try {
                        Alert alert = driver.switchTo().alert();
                        String alertText = alert.getText();
                        if (acceptNextAlert) {
                                alert.accept();
                        } else {
                                alert.dismiss();
                        }
                        return alertText;
                } finally {
                        acceptNextAlert = true;
                }
        }
}
