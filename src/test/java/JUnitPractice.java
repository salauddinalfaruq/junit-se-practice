import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;

public class JUnitPractice {

    WebDriver driver;

    @Before
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "./src/test/resources/chromedriver.exe");
        ChromeOptions option = new ChromeOptions();
        option.addArguments("--headed");
        driver = new ChromeDriver(option);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
    }

    @Test
    public void getTitle() {
        driver.get("https://demoqa.com/");
        String title = driver.getTitle();
        // Assert.assertEquals("ToolsQA" , title);
        Assert.assertTrue(title.contains("Tools"));
    }

    @Test
    public void writeText() {
        driver.get("https://demoqa.com/text-box");
        //   driver.findElement(By.id("userName")).sendKeys("Salauddin");
        //   driver.findElement(By.cssSelector("[type = text]")).sendKeys("Salauddin Al-Faruq");
        //   driver.findElement(By.cssSelector("[id = userName]")).sendKeys("Salauddin Al-Faruq");
        //   driver.findElement(By.className("form-control")).sendKeys("Salauddin Al-Faruq");
        WebElement txtFirstName = driver.findElement(By.id("userName"));
        txtFirstName.sendKeys("Salauddin");
        //   WebElement txtEmail = driver.findElement(By.xpath("//input[@id='userEmail']"));
        //   txtEmail.sendKeys("salauddin@test.com");
        WebElement txtEmail1 = driver.findElement(By.cssSelector("[type = 'email']"));
        txtEmail1.sendKeys("salauddin@test.com");
        WebElement submitButton = driver.findElement(By.id("submit"));
        submitButton.click();
    }

    @Test
    public void handleAlert() throws InterruptedException {
        driver.get("https://demoqa.com/alerts");
        driver.findElement(By.id("alertButton")).click();
        Thread.sleep(2000);
        driver.switchTo().alert().accept();
    }

    @Test
    public void handleAlertAfterTime() throws InterruptedException {
        driver.get("https://demoqa.com/alerts");
        driver.findElement(By.id("timerAlertButton")).click();
        Thread.sleep(6000);
        driver.switchTo().alert().accept();
    }

    @Test
    public void confirmAlert() throws InterruptedException {
        driver.get("https://demoqa.com/alerts");
        driver.findElement(By.id("confirmButton")).click();
        Thread.sleep(2000);
        driver.switchTo().alert().dismiss();
        String text = driver.findElement(By.id("confirmResult")).getText();
        Assert.assertTrue(text.contains("Cancel"));
    }

    @Test
    public void writeInAlertBox() throws InterruptedException {
        driver.get("https://demoqa.com/alerts");
        driver.findElement(By.id("promtButton")).click();
        Thread.sleep(2000);
        driver.switchTo().alert().sendKeys("Salauddin");
    }

    @Test
    public void selectDate() throws InterruptedException {
        driver.get("https://demoqa.com/date-picker");
        WebElement txtDate = driver.findElement(By.id("datePickerMonthYearInput"));
        Actions action = new Actions(driver);
        action.moveToElement(txtDate).
                doubleClick().click().
                keyDown(Keys.BACK_SPACE).
                keyUp(Keys.BACK_SPACE).
                perform();
        Thread.sleep(1000);
        txtDate.sendKeys("08/22/2022");
        txtDate.sendKeys(Keys.ENTER);
    }

    @Test
    public void keyboardEvent() {

        driver.get("https://www.google.com/");
        WebElement searchElement = driver.findElement(By.name("q"));
        Actions action = new Actions(driver);
        action.moveToElement(searchElement).
                keyDown(Keys.SHIFT);
        action.sendKeys("Selenium Webdriver").
                keyUp(Keys.SHIFT).
                doubleClick().
                click().
                contextClick().
                perform();
    }

    @Test
    public void actionClick() {

        driver.get("https://demoqa.com/buttons");
        WebElement element = driver.findElement(By.id("doubleClickBtn"));
        Actions action = new Actions(driver);
        action.doubleClick(element).perform();
        WebElement element1 = driver.findElement(By.id("rightClickBtn"));
        action.contextClick(element1).perform();
        WebElement element2 = driver.findElement(By.id("QTbjs"));
        action.click(element2).perform();
    }

    @Test
    public void actionClick1() {

        driver.get("https://demoqa.com/buttons");
        List<WebElement> element = driver.findElements(By.cssSelector("[type=button]"));
        Actions action = new Actions(driver);
        action.doubleClick(element.get(1)).perform();
        action.contextClick(element.get(2)).perform();
        action.click(element.get(3)).perform();
    }

    @Test
    public void selectDropdown() {
        driver.get("https://demoqa.com/select-menu");
        Select colour = new Select(driver.findElement(By.id("oldSelectMenu")));
        colour.selectByValue("1");
        Select cars = new Select(driver.findElement(By.id("cars")));

        if (cars.isMultiple()) {
            cars.selectByValue("volvo");
            cars.selectByValue("opel");
        }
    }

    @Test
    public void mouseHover() throws InterruptedException {
        driver.get("https://green.edu.bd/");
        Actions action = new Actions(driver);
        WebElement admission = driver.findElement(By.xpath("//a[contains(text(),\"Admissions \")]"));
        action.moveToElement(admission).perform();
        Thread.sleep(2000);
        List<WebElement> list1 = driver.findElements(By.xpath("//a[contains(text(),\"About Us\")]"));
        action.moveToElement(list1.get(2)).perform();
    }

    @Test
    public void takeScreenshot() throws IOException {
        driver.get("https://demoqa.com");
        File screenShotFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        String time = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss-aa").format(new Date());
        String fileWithPath = "./src/test/resources/screenshots/" + time + ".png";
        File destFile = new File(fileWithPath);
        FileUtils.copyFile(screenShotFile , destFile);
    }

    @Test
    public void uploadFile(){
        driver.get("https://demoqa.com/upload-download");
        driver.findElement(By.id("uploadFile")).sendKeys("F:\\t.jpg");
    }

    @Test
    public void downloadFile(){
        driver.get("https://demoqa.com/upload-download");
        driver.findElement(By.id("downloadButton")).click();
    }

    @Test
    public void handleTab() throws InterruptedException {
        driver.get("https://demoqa.com/browser-windows");
        driver.findElement(By.id("tabButton")).click();
        Thread.sleep(3000);
        ArrayList<String> win = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(win.get(1));
        System.out.println("New tab title: " + driver.getTitle());
        String txt = driver.findElement(By.id("sampleHeading")).getText();
        Assert.assertEquals(txt , "This is a sample page");
        driver.close();
        driver.switchTo().window(win.get(0));
    }

    @Test
    public void handleWindow() throws InterruptedException {

        driver.get("https://demoqa.com/browser-windows");
        Thread.sleep(2000);
        WebDriverWait wait = new WebDriverWait(driver , Duration.ofSeconds(50));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("windowButton")));
        driver.findElement(By.id("windowButton")).click();
        String mainWindowHandle = driver.getWindowHandle();
        Set<String> allWindowHandles = driver.getWindowHandles();
        Iterator<String> iterator = allWindowHandles.iterator();

        while (iterator.hasNext()){
            String childWindow = iterator.next();

            if (!mainWindowHandle.equalsIgnoreCase(childWindow)){
                driver.switchTo().window(childWindow);
                String txt = driver.findElement(By.id("sampleHeading")).getText();
                Assert.assertTrue(txt.contains("This is a sample page"));
            }
        }
        driver.close();
    }

    @Test
    public void dataScarp(){
        driver.get("https://demoqa.com/webtables");
        WebElement table = driver.findElement(By.className("rt-tbody"));
        List<WebElement> allRows = table.findElements(By.className("rt-tr-group"));
        int i = 0;
        for (WebElement row : allRows){
            List<WebElement> cells = row.findElements(By.className("rt-td"));
            for (WebElement cell : cells){
                i++;
                System.out.println("num["+ i + "] " + cell.getText());
            }
        }
    }

    @Test
    public void handleIFrame(){
        driver.get("https://demoqa.com/frames");
        driver.switchTo().frame("frame2");
        String txt = driver.findElement(By.id("sampleHeading")).getText();
        Assert.assertTrue(txt.contains("This is a sample page"));
        driver.switchTo().defaultContent();
    }

    @After
    public void driverClose(){
        driver.close();
    }
}
