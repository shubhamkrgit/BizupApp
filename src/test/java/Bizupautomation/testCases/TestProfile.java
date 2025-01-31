package Bizupautomation.testCases;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import Bizupautomation.testUtils.Base;
import buyer.pageObjects.ProfilePage;

public class TestProfile extends Base {
    
    @BeforeMethod
    public void setUp() {
        // Add any specific setup needed for profile tests
        try {
            Thread.sleep(2000); // Give app time to stabilize
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void ProfileFlow() throws InterruptedException {
        try {
            ProfilePage profilePage = new ProfilePage(driver);
            
            System.out.println("✨✨✨------------ Profile Flow start -----------✨✨✨");
            
            // Navigate to profile page
            profilePage.Profile();
            
            // Add small waits between actions to ensure stability
            Thread.sleep(1000);
            
            // Edit profile
            profilePage.EditProfile("Demo Test", "Test Seller", "Delhi");
            Thread.sleep(1000);
            
            // Refer
            profilePage.Refer();
            Thread.sleep(1000);
            
            // Setting
            profilePage.Setting();
            Thread.sleep(1000);
            
            // My Video
            profilePage.MyVideo();
            
            System.out.println("✨✨✨------------ Profile Flow checked ✅ -----------✨✨✨");
        } catch (Exception e) {
            System.err.println("Error in Profile Flow: " + e.getMessage());
            throw e;
        }
    }
}