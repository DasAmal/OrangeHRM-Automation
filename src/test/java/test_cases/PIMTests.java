package test_cases;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import page_objects.DashboardPage;
import page_objects.LoginPage;
import page_objects.PIMPage;
import test_base.base_class;

public class PIMTests extends base_class {
	
	@Test(priority= 3, groups= {"Smoke", "Critical"})
	public void TC_003_AddEmployeeWithValidData() {
		logger.info("***TC_003: Add employee with valid data***");
		LoginPage login = new LoginPage(driver);
		login.enterUsername(prop.getProperty("username"));
		login.enterPassword(prop.getProperty("password"));
		login.clickLogin();
		
		DashboardPage dashboard = new DashboardPage(driver);
		dashboard.navigateToPIM();
		
		PIMPage pim = new PIMPage(driver);
		pim.clickAddEmployee();
		pim.enterEmployeeDetails("Ben", "Antony", "4024", false, "", "");
		pim.saveEmployee();
		
		
		pim.searchEmployeeByName("Ben");
		Assert.assertTrue(pim.isEmployeeFound(), "Employee not found after adding!");
		logger.info("***Employee added successfully");
	}
		
	@Test(priority= 4, groups= {"Smoke"})
	public void TC_005_SearchEmployee() {
		logger.info("***TC_005: Search Employee***");
		LoginPage login = new LoginPage(driver);		
		login.enterUsername(prop.getProperty("username"));
		login.enterPassword(prop.getProperty("password"));
		login.clickLogin();
		
		DashboardPage dash = new DashboardPage(driver);
		dash.navigateToPIM();
		
		PIMPage pim = new PIMPage(driver);
		pim.searchEmployeeByName("Ben Antony");
		
		Assert.assertTrue(pim.isEmployeeFound(), "Employee not found");
		logger.info("***Employee search succesful by ID***");
	}
	
	@Test(priority= 5, groups= {"Regression"})
	public void TC_004_AddEmployeeWithoutFirstName() {
		logger.info("***TC_004: Add employee without first name");
		LoginPage login = new LoginPage(driver);
		login.enterUsername(prop.getProperty("username"));
		login.enterPassword(prop.getProperty("password"));
		login.clickLogin();
		
		DashboardPage dash = new DashboardPage(driver);
		dash.navigateToPIM();
		
		PIMPage pim = new PIMPage(driver);
		pim.clickAddEmployee();
		pim.enterEmployeeDetails(" ", "Smith", "1234", false, "", "");
		pim.saveEmployee();
		
		Assert.assertTrue(pim.validation(), "message not shown for missing first name");
		logger.info("***Validation: First name left blank- displays error***");
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

	
	
