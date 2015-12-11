import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.fail;


public class Pass {
    private WebDriver driver;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();

    @Before
    public void setUp() throws Exception {
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);

    }

    @Test
    public void test11() throws Exception {


        String Url = "http://hotline.ua/computer/noutbuki-netbuki/294407/";
        driver.get(Url);
        String fileText = "";
        String fileTextNot = "";
        String start = "";
        String midle = "";
        String finish = "";
        boolean t = true;

        for (int x = 0; t & x < 1; x++) {
            List<WebElement> resultsDiv = driver.findElements(By.xpath("//td[@id='catalogue']/ul/li"));

            if (isElementPresent(By.cssSelector("#catalogue > div.pager > a[title=\"Следующая\"] > img.next"))) {

                for (int i = 0; i < resultsDiv.size(); i++) {
                    //         System.out.println(i + 1 + ". " + resultsDiv.get(i).getText());
                    String tempPrice = resultsDiv.get(i).getText();
                    String fin = "";
                    Pattern patern = Pattern.compile("\\d{1,4}\\s\\d{1,5}[^\\s(грн)]"); //price
                    Matcher temp1 = patern.matcher(tempPrice);
                    Pattern paternDouble = Pattern.compile("(АКЦИИ:)\\s\\d{0,4}"); // АКЦИИ
                    Matcher temp2 = paternDouble.matcher(tempPrice);
                    Pattern paternEnd = Pattern.compile("(•).+"); //
                    Matcher temp3 = paternEnd.matcher(tempPrice);

                    if (temp1.find()) {
                        start = tempPrice.substring(0, temp1.end());
                    }
                    if (temp2.find() & temp3.find()) {
                        midle = tempPrice.substring(temp2.end(), temp3.end());
                    }

                    fin = start + midle + finish;
                    fileText = fileText + fin + "\n" + "\n";
                    fileTextNot = fileTextNot + tempPrice + "\n";
                }


                driver.findElement(By.cssSelector("#catalogue > div.pager > a[title=\"Следующая\"] > img.next")).click();
                for (int second = 0; ; second++) {
                    if (second >= 60) fail("timeout");
                    try {
                        if ("Сортировать по:".equals(driver.findElement(By.cssSelector("span.txt")).getText())) break;
                    } catch (Exception e) {
                    }
                    Thread.sleep(1000);
                }

            } else {
                t = false;
            }
        }

        Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("price.csv"), "utf-8"));
        writer.write(fileText);
        writer.close();

        Writer writerN = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("priceNotPars.csv"), "utf-8"));
        writerN.write(fileTextNot);
        writer.close();
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
}

