package test_cases;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import page_objects.DashboardPage;
import page_objects.LoginPage;
import test_base.base_class;

public class LoginTests extends base_class {

    @Test(priority = 1, groups = {"Smoke", "Critical"})
    public void TC_001_ValidLogin() {
        logger.info("*** TC_001: Verify login with valid credentials ***");
        LoginPage login = new LoginPage(driver);
        login.enterUsername(prop.getProperty("username"));
        login.enterPassword(prop.getProperty("password"));
        login.clickLogin();

        Assert.assertTrue(driver.getCurrentUrl().contains("dashboard"), "Not navigated to dashboard after valid login");
        logger.info("Valid login successful - Dashboard accessed.");
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

    @Test(priority = 2)
    public void TC_002_InvalidLogin() {
        logger.info("*** TC_002: Verify login with invalid credentials ***");
        LoginPage login = new LoginPage(driver);
        login.enterUsername("Admin");
        login.enterPassword("wrongpass");
        login.clickLogin();

        String actualError = login.getErrorMessage();
        String expectedError = "Invalid credentials";
        Assert.assertEquals(actualError, expectedError, 
            "Invalid login error message mismatch");
    }
}