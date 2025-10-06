package page_objects;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DashboardPage {
	
	public  WebDriver driver;
	private WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	
	@FindBy(xpath = "//span[normalize-space()='PIM']") private WebElement pimMenu;
	@FindBy(xpath = "//span[normalize-space()='Leave']") private WebElement leaveMenu;
	@FindBy(xpath = "//span[normalize-space()='Recruitment']") private WebElement RecruitmentMenu;
	@FindBy(xpath = "//p[@class='oxd-userdropdown-name']") private WebElement userDropdown;
	@FindBy(xpath = "//a[normalize-space()='Logout']") private WebElement logoutConfirm;

	@FindBy(xpath = "//img[@alt='client brand banner']") private WebElement orangeHRMLogo;

	public void clickOrangeHRMLogo() {
	    wait.until(ExpectedConditions.elementToBeClickable(orangeHRMLogo)).click();
	}

	public DashboardPage(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		PageFactory.initElements(driver, this);
	}
	
	public void navigateToPIM( ) {
		wait.until(ExpectedConditions.elementToBeClickable(pimMenu)).click();;
		
	}
	

	public void navigateToLeave( ) {
		wait.until(ExpectedConditions.elementToBeClickable(leaveMenu)).click();;
	}
	
	public void navigateToRecruitment( ) {
		wait.until(ExpectedConditions.elementToBeClickable(RecruitmentMenu)).click();;
	
	}
	
	public void logout() {
		wait.until(ExpectedConditions.elementToBeClickable(userDropdown)).click();;
		wait.until(ExpectedConditions.elementToBeClickable(logoutConfirm)).click();;
	}
}
