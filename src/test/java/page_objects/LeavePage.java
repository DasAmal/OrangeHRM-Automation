package page_objects;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LeavePage {
	
	private WebDriver driver;
	private WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	
	@FindBy(xpath = "//a[normalize-space()='Apply']") private WebElement applyLeave;
	@FindBy(xpath = "//div[@class='oxd-select-text oxd-select-text--active']") private WebElement leaveTypeDrop;
	@FindBy(xpath = "(//input[@placeholder='yyyy-dd-mm'])[1]") private WebElement fromDate;
	@FindBy(xpath = "(//input[@placeholder='yyyy-dd-mm'])[2]") private WebElement toDate;
	@FindBy(xpath = "//textarea[contains(@class,'oxd-textarea')]") private WebElement comment;
	@FindBy(xpath = "(//button[normalize-space()='Apply'])[1]") private WebElement submit;
	@FindBy(xpath = "//*[contains(text(), 'No Leave Types with Leave Balance')]")
    private WebElement noLeaveTypesMessage;
	@FindBy(xpath = "//div[@class='oxd-grid-4 orangehrm-full-width-grid']") private WebElement requiredFieldError;
	
	public LeavePage(WebDriver driver) {
		this.driver=driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		PageFactory.initElements(driver, this);
	}
	
	 public void clickApplyLeave() {
	        wait.until(ExpectedConditions.elementToBeClickable(applyLeave)).click();
	        // Wait for form or error message
	        wait.until(ExpectedConditions.or(
	                ExpectedConditions.visibilityOf(fromDate),
	                ExpectedConditions.visibilityOf(noLeaveTypesMessage)
	        ));
	    }
	
	 public void selectLeaveType(String type) {
	        wait.until(ExpectedConditions.elementToBeClickable(leaveTypeDrop)).click();
	        WebElement option = wait.until(ExpectedConditions.elementToBeClickable(
	                By.xpath("//div[@role='listbox']//span[text()='" + type + "']")));
	        option.click();
	    }
	 
	 public void enterLeaveDetails(String from, String to, String com) {
	        wait.until(ExpectedConditions.visibilityOf(fromDate)).clear();
	        fromDate.sendKeys(from);

	        wait.until(ExpectedConditions.visibilityOf(toDate)).clear();
	        toDate.sendKeys(to);

	        wait.until(ExpectedConditions.visibilityOf(comment)).clear();
	        comment.sendKeys(com);
	    }
	 
	 public void submitLeave() {
	        wait.until(ExpectedConditions.elementToBeClickable(submit)).click();
	    }
	
	

	    // Check if required field error is displayed (for blank dates, etc.)
	    public boolean isRequiredFieldErrorDisplayed() {
	        try {
	            wait.until(ExpectedConditions.visibilityOf(requiredFieldError));
	            return requiredFieldError.isDisplayed();
	        } catch (Exception e) {
	            return false;
	        }
	    }
	
	    // Check if "No leave balance" message is displayed
	    public boolean isNoLeaveBalanceMessageDisplayed() {
	        try {
	            wait.until(ExpectedConditions.visibilityOf(noLeaveTypesMessage));
	            return noLeaveTypesMessage.isDisplayed();
	        } catch (Exception e) {
	            return false;
	        }
	    }

	    // Combined method to apply leave in one go
	    public void applyLeave(String type, String from, String to, String com) {
	        clickApplyLeave();

	        if (isNoLeaveBalanceMessageDisplayed()) {
	            throw new RuntimeException("Cannot apply leave: No leave type with balance available");
	        }

	        selectLeaveType(type);
	        enterLeaveDetails(from, to, com);
	        submitLeave();
	    }
	}