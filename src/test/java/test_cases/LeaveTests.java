package test_cases;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import page_objects.DashboardPage;
import page_objects.LeavePage;
import page_objects.LoginPage;
import test_base.base_class;

public class LeaveTests extends base_class{
	
	@Test(priority=6, groups= {"Smoke","Critical"})
	public void TC_006_SubmitLeaveWithValidDates() {
	    logger.info("***TC_006: Submit leave with valid dates***");

	    LoginPage login = new LoginPage(driver);
	    login.enterUsername(prop.getProperty("username"));
	    login.enterPassword(prop.getProperty("password"));
	    login.clickLogin();

	    DashboardPage dash = new DashboardPage(driver);
	    dash.navigateToLeave();

	    LeavePage leave = new LeavePage(driver);
	    leave.applyLeave("CAN - Vacation", "2025-10-10", "2025-10-12", "Personal trip");

	    logger.info("***Leave applied successfully with valid dates***");
	}

	@Test(priority=7, groups= {"Regression"})
	public void TC_007_SubmitLeaveWithoutDates() {
	    logger.info("***TC_007: Submit leave without valid dates***");

	    LoginPage login = new LoginPage(driver);
	    login.enterUsername(prop.getProperty("username"));
	    login.enterPassword(prop.getProperty("password"));
	    login.clickLogin();

	    DashboardPage dashboard = new DashboardPage(driver);
	    dashboard.navigateToLeave();

	    LeavePage leave = new LeavePage(driver);
	    leave.clickApplyLeave();
	    leave.selectLeaveType("CAN - Vacation");
	    leave.enterLeaveDetails("", "", "Forgot to enter dates");
	    leave.submitLeave();

	    Assert.assertTrue(leave.isRequiredFieldErrorDisplayed(), 
	            "Error message not displayed for missing dates!");
	    logger.info("***Validation: Leave submission failed due to blank dates - 'Required' message displayed***");
	}
	
	@AfterMethod
    public void logoutIfLoggedIn() {
        try {
            DashboardPage dashboard = new DashboardPage(driver);
            dashboard.logout();
            logger.info("Logged out after test method.");
        } catch (Exception e) {
            logger.warn("Logout skipped - likely already on login page.");
        }
}
}

