import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;

public class PracticeForm {

    WebDriver driver;
    @Before
    public void Setup(){
        System.setProperty("webdriver.chrome.driver", "./src/test/resources/chromedriver.exe");
        ChromeOptions option = new ChromeOptions();
        option.addArguments("--headed");
        driver = new ChromeDriver(option);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
    }

    WebElement txtFirstName;
    @Test
    public void writeFirstName(){
        driver.get("https://demoqa.com/automation-practice-form");
        txtFirstName = driver.findElement(By.id("firstName"));
        txtFirstName.sendKeys("Shahriar");
    }

    WebElement txtLastName;
    @Test
    public void writeLastName() {
        driver.get("https://demoqa.com/automation-practice-form");
        txtLastName = driver.findElement(By.id("lastName"));
        txtLastName.sendKeys("Sadi");
    }

    WebElement txtEmail;
    @Test
    public void inputEmail() {
        driver.get("https://demoqa.com/automation-practice-form");
        txtEmail = driver.findElement(By.id("userEmail"));
        txtEmail.sendKeys("shahriarsadi@test.com");
    }

    @Test
    public void clickRadioButton1() {
        driver.get("https://demoqa.com/automation-practice-form");
        Actions actions = new Actions(driver);
        WebElement rb1 = driver.findElement(By.id("gender-radio-1"));
        actions.click(rb1).perform();
    }

    WebElement txtMobileNumber;
    @Test
    public void inputMobileNumber() {
        driver.get("https://demoqa.com/automation-practice-form");
        txtMobileNumber = driver.findElement(By.id("userNumber"));
        txtMobileNumber.sendKeys("0171212121");
    }

    WebElement txtDate;
    @Test
    public void inputDateOfBirth() throws InterruptedException {
        driver.get("https://demoqa.com/automation-practice-form");
        txtDate = driver.findElement(By.id("dateOfBirthInput"));
        Actions actions = new Actions(driver);
        actions.moveToElement(txtDate).doubleClick().click().
                keyDown(Keys.BACK_SPACE).
                keyUp(Keys.BACK_SPACE).
                perform();
        Thread.sleep(1000);
        txtDate.sendKeys("09/10/2022");
        txtDate.sendKeys(Keys.ENTER);
    }

    WebElement txtSubject;
    @Test
    public void inputSubject() {
        driver.get("https://demoqa.com/automation-practice-form");
        txtSubject = driver.findElement(By.id("subjectsContainer"));
        txtSubject.sendKeys("Computer Fundamental");
    }
    @Test
    public void clickCbBox3() {
        driver.get("https://demoqa.com/automation-practice-form");
        WebElement cbBox = driver.findElement(By.id("hobbies-checkbox-3"));
        Actions actions = new Actions(driver);
        actions.click(cbBox).perform();
    }

    @Test
    public void storeStudentInfo() throws IOException, ParseException {
        String filePath = "./src/test/resources/student.json";

        JSONParser jsonParser = new JSONParser();
        Object obj = jsonParser.parse(new FileReader(filePath));
        JSONObject studentObj = new JSONObject();

        studentObj.put("FirstName" , txtFirstName);
        studentObj.put("LastName" , txtLastName);
        studentObj.put("Email" , txtEmail);
        studentObj.put("Mobile" , txtMobileNumber);
        studentObj.put("DateOfBirth" , txtDate);
        studentObj.put("Subject" , txtSubject);

        JSONArray studentInfoArray = (JSONArray) obj;
        studentInfoArray.add(studentObj);

        FileWriter fileWriter = new FileWriter(filePath);
        fileWriter.write(studentInfoArray.toJSONString());
        fileWriter.flush();
        fileWriter.close();
    }
}
