package page_objects;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PIMPage {

	private WebDriver driver;
	private WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	
	
	@FindBy(xpath = "//button[normalize-space()='Add']") private WebElement addEmployee;
	@FindBy(xpath = "//input[@placeholder='First Name']") private WebElement firstNameField;
	@FindBy(xpath = "//input[@placeholder='Last Name']") private WebElement lastNameField;
	@FindBy(xpath = "//input[contains(@class,'oxd-input--active')]") private WebElement empIdField;
    @FindBy(xpath = "//span[@class='oxd-switch-input oxd-switch-input--active --label-right']	") private WebElement createLoginChk;
	@FindBy(xpath = "//label[text()='Username']/following::input[1]") private WebElement usernameField;
	@FindBy(xpath = "(//input[@type='password'])[1]") private WebElement passwordField;
	@FindBy(xpath = "(//input[@type='password'])[2]") private WebElement confirmPasswordField;
    @FindBy(xpath = "(//button[normalize-space()='Save'])[1]") private WebElement saveBtn;;
    @FindBy(xpath = "//a[normalize-space()='Employee List']") private WebElement backBtn;
    @FindBy(xpath = "(//input[@placeholder='Type for hints...'])[1]") private WebElement employeeName;
    @FindBy(xpath = "(//input[@class='oxd-input oxd-input--active'])[2]") private WebElement employeeId;
    @FindBy(xpath = "(//button[normalize-space()='Search'])[1]") private WebElement searchBtn;
    @FindBy(xpath = "//span[normalize-space()='(1) Record Found']") private WebElement searchResult;
    @FindBy(xpath = "//span[@class='oxd-text oxd-text--span oxd-input-field-error-message oxd-input-group__message']") private WebElement ErrorMsg;
    @FindBy(xpath = "//h6[normalize-space()='Personal Details']") private WebElement saveMsg;
    
    
    public PIMPage(WebDriver driver) {
    	this.driver = driver;
    	this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    	PageFactory.initElements(driver, this);
    }
    
    public void clickAddEmployee() {
    	wait.until(ExpectedConditions.elementToBeClickable(addEmployee)).click();;
    }
	
    public void enterEmployeeDetails(String first , String last, String id, boolean createLogin, String user, String pass) {
    	
    	wait.until(ExpectedConditions.visibilityOf(firstNameField));

    	firstNameField.sendKeys(first);
    	lastNameField.sendKeys(last);
    	
    	empIdField.clear();
    	wait.until(ExpectedConditions.visibilityOf(empIdField));
    	empIdField.sendKeys(id);
    	if(createLogin) {
    		createLoginChk.click();
    		usernameField.sendKeys(user);
    		passwordField.sendKeys(pass);
    		confirmPasswordField.sendKeys(pass);
    	}
    }
    
    public void saveEmployee() {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(
            By.cssSelector("div.oxd-form-loader")
        ));
        wait.until(ExpectedConditions.elementToBeClickable(saveBtn));
        saveBtn.click();
    }
    
    public void searchEmployeeByName(String name) {
        // Go back to the Employee List page
        backBtn.click();
        
        // Wait for the name input field
        wait.until(ExpectedConditions.visibilityOf(employeeName));
        employeeName.sendKeys(name);

        // Wait for the dropdown list to appear and select the first suggestion
        WebElement suggestion = wait.until(ExpectedConditions
            .visibilityOfElementLocated(By.xpath("//div[@role='listbox']//span[contains(text(),'" + name + "')]")));
        suggestion.click();

        // Click on Search
        searchBtn.click();
    }

    
    public boolean isEmployeeFound() {
    	return searchResult.isDisplayed();
    }  
    
    public boolean validation() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.visibilityOf(ErrorMsg));
            return ErrorMsg.isDisplayed();
        } catch (Exception e) {
            return false; // if not found or not visible
        }
    }
}
