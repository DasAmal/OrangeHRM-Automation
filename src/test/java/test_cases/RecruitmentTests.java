package test_cases;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import page_objects.DashboardPage;
import page_objects.LoginPage;
import page_objects.RecruitmentPage;
import test_base.base_class;

public class RecruitmentTests extends base_class {

    @Test(priority = 8, groups = {"Smoke", "Critical"})
    public void TC_008_AddCandidateWithValidDetails() {
        logger.info("*** TC_008: Add candidate with valid details ***");
        LoginPage login = new LoginPage(driver);
        login.enterUsername(prop.getProperty("username"));
        login.enterPassword(prop.getProperty("password"));
        login.clickLogin();

        DashboardPage dashboard = new DashboardPage(driver);
        dashboard.navigateToRecruitment();

        RecruitmentPage recruit = new RecruitmentPage(driver);
        recruit.clickAddCandidate();
        recruit.enterCandidateDetails("Alice", "Brown", "alice@example.com", "C:\\\\Users\\\\amald\\\\Downloads\\\\AMALResume.pdf", "9876543210", "2025-02-10", "Software Engineer");
        recruit.saveCandidate();

        logger.info("Candidate added successfully.");
    }

    @Test(priority = 9, groups = {"Regression"})
    public void TC_009_UploadResumeSuccessfully() {
        logger.info("*** TC_009: Upload resume successfully ***");
        LoginPage login = new LoginPage(driver);
        login.enterUsername(prop.getProperty("username"));
        login.enterPassword(prop.getProperty("password"));
        login.clickLogin();

        DashboardPage dashboard = new DashboardPage(driver);
        dashboard.navigateToRecruitment();

        RecruitmentPage recruit = new RecruitmentPage(driver);
        recruit.clickAddCandidate();
        recruit.enterCandidateDetails("Amal", "Das", "das@example.com", "C:\\Users\\amald\\Downloads\\AMALResume.pdf", "8765432109", "2025-02-10", "Software Engineer");

        Assert.assertTrue(recruit.isUploadSuccessful(), 
                "❌ Resume upload failed or success message not displayed");

            logger.info("✅ Resume uploaded successfully for candidate.");
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