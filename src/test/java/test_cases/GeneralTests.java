package test_cases;

import org.testng.Assert;
import org.testng.annotations.Test;

import page_objects.DashboardPage;
import page_objects.LoginPage;
import test_base.base_class;

public class GeneralTests extends base_class {

    @Test(priority = 10, groups = {"Smoke"})
    public void TC_010_LogoutAndBackButtonValidation() {
        logger.info("*** TC_010: Logout and back button validation ***");

        // Login
        LoginPage login = new LoginPage(driver);
        login.enterUsername(prop.getProperty("username"));
        login.enterPassword(prop.getProperty("password"));
        login.clickLogin();

        // Logout using DashboardPage method
        DashboardPage dashboard = new DashboardPage(driver);
        dashboard.logout();

        // Attempt to go back
        driver.navigate().back();

        // Verify login button is displayed (ensures logout worked)
        Assert.assertTrue(login.getLoginButton().isDisplayed(), 
            "Able to navigate back after logout — Bug!");
        logger.info("Logout successful and back button validation passed.");
    }

    @Test(priority = 11, groups = {"Critical"})
    public void TC_011_SessionTimeoutAfterInactivity() throws InterruptedException {
        logger.info("*** TC_011: Verify session timeout after inactivity ***");

        // Login
        LoginPage login = new LoginPage(driver);
        login.enterUsername(prop.getProperty("username"));
        login.enterPassword(prop.getProperty("password"));
        login.clickLogin();

        // Wait for session timeout (simulate inactivity)
        logger.info("Waiting for session timeout...");
        Thread.sleep(65000); // 65 seconds — adjust based on your demo environment

        // Refresh page to trigger session check
        driver.navigate().refresh();

        // Verify login button is displayed (session timed out)
        Assert.assertTrue(login.getLoginButton().isDisplayed(), 
            "Session timeout not triggered after inactivity.");
        logger.info("Session timeout validation successful.");
    }
   
    @Test(priority= 12, groups= {"Regression", "UI"})
    public void TC_0012_Verify_Logo_Redirection() {
    	 logger.info("*** TC_019: Verify OrangeHRM logo/title redirection ***");
    	 
    	 LoginPage login = new LoginPage(driver);
         login.enterUsername(prop.getProperty("username"));
         login.enterPassword(prop.getProperty("password"));
         login.clickLogin();

         DashboardPage dashboard = new DashboardPage(driver);
         dashboard.clickOrangeHRMLogo();
         
         String currentURL = driver.getCurrentUrl();
         logger.info("Current URL after clicking Logo: " + currentURL);
         
         Assert.assertTrue(driver.getCurrentUrl().contains("dashboard"), "❌ BUG FOUND: User redirected to an external OrangeHRM site instead of staying on dashboard!");
         logger.info("*** Logo redirection validation completed ***");
    }
}
