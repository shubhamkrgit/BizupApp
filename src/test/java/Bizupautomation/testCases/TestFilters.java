package Bizupautomation.testCases;

//import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import Bizupautomation.testUtils.Base;
import buyer.pageObjects.HomePage;
import buyer.pageObjects.VideoPage;

public class TestFilters extends Base {

//	@BeforeMethod(alwaysRun = true)
//	public void preSetup() throws InterruptedException {
//		Restart(driver);
//	}

//	 @BeforeMethod
//	    public void setUp() {
//	        // Add any specific setup needed for profile tests
//	        try {
//	            Thread.sleep(2000); // Give app time to stabilize
//	        } catch (InterruptedException e) {
//	            e.printStackTrace();
//	        }
//	    }

	@Test
	public void HomeReset() throws InterruptedException {
		HomePage home = new HomePage(driver);

		System.out.println("✨✨✨------- Home Reset Flow start ------✨✨✨");

		home.RestartApp();

		// ---------- Product reset message check
		home.ClearFilters();

		home.GenderFilter("Men");

		home.ProductSelect("Saree");

		home.ResetMsg();

		// ---------- Price reset mesage check
		home.ClearFilters();

		home.ProductSelect("Lower");

		home.PriceSelect("100 and below");

		home.ResetMsg();

		// ----------- City reset message check
		home.ClearFilters();

		home.ProductSelect("Saree");

		home.CitySelect(2);

		home.ResetMsg();

		System.out.println("✨✨✨-------- Home Reset Flow checked ✅ ------✨✨✨");
	}

	@Test
	public void VideoReset() throws InterruptedException {
		VideoPage video = new VideoPage(driver);

		System.out.println("✨✨✨------- Video Reset Flow start -------✨✨✨");

		video.RestartApp();

		video.HomeToVideoFeed();

		// ---------- Product reset message check
		video.ClearFilters();

		video.PriceSelect("100 and below");

		video.ProductSelect("Sweaters", "स्वीटर्स");

		video.ResetMsg();

		// ---------- Price reset mesage check
		video.ClearFilters();

		video.ProductSelect("Kurti Set", "कुर्टी सेट");

		video.PriceSelect("100 and below");

		video.ResetMsg();

		// ----------- City reset message check
		video.ClearFilters();

		video.ProductSelect("Saree", "साड़ी");

		video.CitySelect(2);

		video.ResetMsg();

		System.out.println("✨✨✨------- Video Reset Flow checked ✅ ------✨✨✨");

	}

}