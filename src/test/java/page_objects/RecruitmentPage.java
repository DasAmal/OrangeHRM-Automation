package page_objects;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RecruitmentPage {
	private WebDriver driver;
	private WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	private Actions actions;
	
	@FindBy(xpath = "//button[normalize-space()='Add']") private WebElement candidateAdd;
	@FindBy(xpath = "//input[@placeholder='First Name']") private WebElement firstNameField;
	@FindBy(xpath = "//input[@placeholder='Last Name']") private WebElement lastNameField;
	@FindBy(xpath = "//label[normalize-space()='Vacancy']/following::div[contains(@class,'oxd-select-text-input')][1]") private WebElement vacancyDropdown;
	@FindBy(xpath = "//label[normalize-space()='Email']/following::input[@placeholder='Type here']") private WebElement emailField;
	@FindBy(xpath = "(//input[@placeholder='Type here'])[2]") private WebElement phoneField;
	@FindBy(xpath = "//input[@type='file']") private WebElement resumeUpload;
	@FindBy(xpath = "//input[contains(@placeholder,'yyyy')]") private WebElement dateOfApplication;
	@FindBy(xpath = "(//button[normalize-space()='Save'])[1]") private WebElement saveBtn;
	@FindBy(xpath = "(//h6[normalize-space()='Candidate Profile'])[1]") private WebElement candidatesHeader;

	
public RecruitmentPage(WebDriver driver) {
	this.driver = driver;
	this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	this.actions = new Actions(driver);
	PageFactory.initElements(driver, this);
}
	
public void clickAddCandidate() {
	wait.until(ExpectedConditions.elementToBeClickable(candidateAdd)).click();;
}

public void enterCandidateDetails(String first, String last, String email, String resumePath, String phone, String date, String vacancyName) {
	firstNameField.clear();
	firstNameField.sendKeys(first);
	
	lastNameField.clear();
	lastNameField.sendKeys(last);
	
	emailField.clear();
	emailField.sendKeys(email);
	
	phoneField.clear();
	phoneField.sendKeys(phone);
	
	resumeUpload.sendKeys(resumePath);
	
	 dateOfApplication.sendKeys(Keys.chord(Keys.CONTROL, "a"));
	    dateOfApplication.sendKeys(Keys.DELETE);
	    dateOfApplication.sendKeys(date);

	    selectVacancy(vacancyName);	
}

public void selectVacancy(String vacancyName) {
    wait.until(ExpectedConditions.elementToBeClickable(vacancyDropdown)).click();
    WebElement option = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//div[@role='listbox']//span[normalize-space()='" + vacancyName + "']")));
    option.click();
}

public void uploadResume(String filePath) {
    wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@type='file']")));
    resumeUpload.sendKeys(filePath);
}

public boolean isUploadSuccessful() {
    try {
        wait.until(ExpectedConditions.visibilityOf(candidatesHeader));
        return candidatesHeader.isDisplayed();
    } catch (Exception e) {
        return false;
    }
}


public void saveCandidate() {
    // Wait until button is displayed AND enabled
    wait.until(driver -> saveBtn.isDisplayed() && saveBtn.isEnabled());

    // Scroll into view
    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", saveBtn);

    // Wait a tiny moment for any overlays to disappear
    try { Thread.sleep(500); } catch (InterruptedException e) {}

    // Try clicking via Actions
    try {
        actions.moveToElement(saveBtn).click().perform();
    } catch (Exception e) {
        // fallback to JS click
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", saveBtn);
    }
}


}

