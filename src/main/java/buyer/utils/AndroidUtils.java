// AndroidUtils.java
package buyer.utils;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.google.common.collect.ImmutableMap;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;

public class AndroidUtils {
	protected final AndroidDriver driver;
	private static final String APP_PACKAGE = "com.sot.bizup.debug";
	private static final Duration DEFAULT_WAIT = Duration.ofSeconds(5);

	public AndroidUtils(AndroidDriver driver) {
		this.driver = driver;
	}

	public void launchApp() {
		try {
			driver.activateApp(APP_PACKAGE);
			Thread.sleep(DEFAULT_WAIT.toMillis());
			System.out.println("App Launched ✔");
		} catch (Exception e) {
			System.err.println("App Launch Failed ❌: " + e.getMessage());
			throw new RuntimeException("Failed to launch app", e);
		}

	}

	public void RestartApp() {
		try {
			driver.terminateApp(APP_PACKAGE);
			driver.activateApp(APP_PACKAGE);
			Thread.sleep(DEFAULT_WAIT.toMillis());
			System.out.println("App Restarted ✔");
		} catch (Exception e) {
			System.err.println("App Restart Failed ❌: " + e.getMessage());
			throw new RuntimeException("Failed to restart app", e);
		}

	}

	protected void waitForStability() {
		try {
			Thread.sleep(2000); // Base stability wait
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	// Back Method
	public void Back() {
		driver.pressKey(new KeyEvent(AndroidKey.BACK));
	}

	// Enter Method
	public void Enter() {
		driver.pressKey(new KeyEvent(AndroidKey.ENTER));
	}

	// Clear App Data Method
	public void clearAppData() {
		try {
			String appPackage = "com.sot.bizup.debug";
			Runtime.getRuntime().exec("adb shell pm clear " + appPackage);
			System.out.println("App Data Cleared ✔");
		} catch (Exception e) {
			System.out.println("App Data Not Cleared ❌");
		}
	}

	// Click method for ID
	public void ClickId(String element) {
		driver.findElement(By.id(element)).click();
	}

	// Click method for xpath
	public void ClickXp(String element) {
		driver.findElement(By.xpath(element)).click();
	}

	// Send message in the chat
	public void SendKey(String message) throws InterruptedException {
		driver.findElement(By.xpath("//android.widget.EditText")).sendKeys(message);
		ClickXp("//android.widget.Button[@text=\"\"]");
		driver.hideKeyboard();
		Thread.sleep(4000);
	}

	// Wait for element
	public void waitUntilText(WebElement element, String text) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.attributeContains(element, "text", text));
		return;
	}

	public void HomeWait() {
		WebElement homeWait = driver.findElement(By.id("com.sot.bizup.debug:id/mtTopAll"));
		waitUntilText(homeWait, "All");
	}

	public void SearchWait() {
		WebElement search = driver.findElement(By.xpath("//android.widget.RadioButton[@text=\"More\"]"));
		waitUntilText(search, "More");
	}

	public void VideoWait() {
		WebElement video = driver.findElement(By.id("com.sot.bizup.debug:id/mbGood"));
		waitUntilText(video, "Catalog");
	}

	public void SellerPageWait() {
		WebElement sellerPage = driver.findElement(By.id("com.sot.bizup.debug:id/mbDealKare"));
		waitUntilText(sellerPage, "Chat");
	}

	public void CollectionPageWait() {
		By collectionPage = By.id("com.sot.bizup.debug:id/ivSuperSellertext");
		WebElement CpWait = driver.findElement(collectionPage);
		waitUntilText(CpWait, "FAST SELLING PRODUCTS CURATED BY BIZUP");
	}

	public void ProfileWait() {
		By profile = By.id("com.sot.bizup.debug:id/tvYourProfile");
		WebElement profileWait = driver.findElement(profile);
		waitUntilText(profileWait, "Your Profile,");
	}

	public void ChatWait() {
		By chat = By.xpath("//android.widget.Image[@text=\"Salesman Logo\"]");
		WebElement chatWait = driver.findElement(chat);
		waitUntilText(chatWait, "Salesman Logo");
	}

	// Home to Video page navigation
	public void HomeToVideoFeed() throws InterruptedException {
		By videoThumb = By
				.xpath("(//android.widget.ImageView[@resource-id=\"com.sot.bizup.debug:id/ivThumbnail\"])[4]");

		ScrollEle("Free COD by Bizup");
		driver.findElement(videoThumb).click();

		VideoWait();
		System.out.println("Landed on Videofeed ✔");
	}

	// Getting seller name from seller page
	public String SpSellerName() {
		String sellerName = driver.findElement(By.id("com.sot.bizup.debug:id/mtSellerName")).getText();
		System.out.println("Seller name is :- " + sellerName);
		return sellerName;
	}

	// Home to seller page navigation
	public void VideoToCollection() throws InterruptedException {
		ClickId("com.sot.bizup.debug:id/mbGood");
		CollectionPageWait();
		System.out.println("Landed on the Collection page ✔");
	}

	// Scroll without element
	public void Scroll() {
		boolean canScrollMore;
		do {
			canScrollMore = (Boolean) ((JavascriptExecutor) driver).executeScript("mobile: scrollGesture", ImmutableMap
					.of("left", 100, "top", 100, "width", 200, "height", 200, "direction", "down", "percent", 3.0));
		} while (canScrollMore);
	}

	// Scroll to find Element
	public void ScrollEle(String ele) {
		driver.findElement(AppiumBy
				.androidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\"" + ele + "\"));"));
	}

	// Toast Message Method
	@SuppressWarnings("deprecation")
	public String Toast() {

		String message = "";
		By toast = By.xpath("//android.widget.Toast");

		boolean istoast = driver.findElements(toast).size() > 0;

		if (istoast) {
			message = driver.findElement(toast).getAttribute("name");
			System.out.println("Toast Message Appers ✔");
			System.out.println(message);
		} else {
			System.out.println("Toast Message not Appers ❌");
			Assert.fail("Condition failed, marking test as failed");
		}

		return message;
	}

	// Chat Methods
	public void ShortChat() throws InterruptedException {
		checkAndSend("Hello");
		checkAndSend("Shirt chahiye");
	}

	public void LongChat() throws InterruptedException {
		checkAndSend("Hi", "Pant chahiye", "aur dekhao");
		checkAndSend("COD milega??", "Delivery charge kitna lagega??", "Delivery kab tak hogi??");
	}

	private void checkAndSend(String... messages) throws InterruptedException {
		By chatButton = By.xpath("//android.widget.Button[@text=\"\"]");
		By sendButton = By.id("com.whatsapp:id/send");

		if (driver.findElements(chatButton).size() > 0) {
			ClickXp("//android.widget.Button[@text=\"\"]");
			System.out.println("Message send ✔");
			for (String message : messages) {
				SendKey(message);
			}
		} else if (driver.findElements(sendButton).size() > 0) {
			System.out.println("Landed on WhatsApp ✔");
			Thread.sleep(2000);
			Back();
			Back();
		}
	}

	public void WhatsAppEnable() throws InterruptedException {

		// WhatsApp Enable Click
		By secondElement = By.xpath("//android.widget.TextView[@text=\"ओनर से बात करे\"]");
		By firstElement = By.xpath("//android.widget.Button[@text=\"बात करे\"]");

		// Check if the first element exists
		if (driver.findElements(firstElement).size() > 0) {
			// If the first element exists, click it
			driver.findElement(firstElement).click();
		} else {
			// If the first element doesn't exist, check if the second element exists
			driver.findElement(secondElement).click();
		}

		// Check for PreEnquiryVideo
		PreEnquiryVideoCheck();

		// WhatsApp Check
		WhatsAppCheck();

		// Answer feedback question
		FeedbackQue();
	}

//	public void CatalogEnq() throws InterruptedException {
//
//		// Full screen catalog enquiry
//		ClickXp("(//android.widget.ImageView[@resource-id=\"com.sot.bizup.debug:id/ivItem\"])[1]");
//		System.out.println("Catalog Full view displayed ✔");
//		ClickXp("(//android.widget.Button[@resource-id=\"com.sot.bizup.debug:id/mbShortlist\"])[1]");
//		ClickId("com.sot.bizup.debug:id/ivCross");
//
//		// Selecting Catalog
//		ClickXp("(//android.widget.Button[@resource-id=\"com.sot.bizup.debug:id/mbShortlist\"])[2]");
//		ClickId("com.sot.bizup.debug:id/mbPlaceOrder");
//
//		// PreEnquiryQuestion Check
//		PreEnquiryQue();
//
//		// Check for PreEnquiryVideo
//		PreEnquiryVideoCheck();
//
//		// Enter the text on the Chat box
//		LongChat1();
//		driver.hideKeyboard();
//		Back(); 
//	}

	public void PayCartPage() {
		By payButton = By.xpath("//android.widget.TextView[@text='Pay']");
		By popUpPayButton = By.xpath("//android.widget.TextView[@text=\" में बुक करें\"]");

		try {
			if (driver.findElements(payButton).size() > 0) {
				System.out.println("Landed on Cart Page ✔");
				driver.findElement(payButton).click();
				if (driver.findElements(popUpPayButton).size() > 0) {
//				driver.findElement(popUpPayButton).click();
					System.out.println("Pop Up pay button Displayed ✔");
					System.out.println("Payment working ✔");
				} else {
					System.out.println("Pop Up pay button not Displayed ❌");
					Assert.fail("Pop Up pay button not Displayed ❌");
				}

			} else {
				System.out.println("Not Landed on Cart Page ❌");
				Assert.fail("Not Landed on Cart Page ❌");
			}
		} catch (Exception e) {
			System.out.println("Not Landed on Cart Page ❌" + e);
			Assert.fail("Not Landed on Cart Page ❌" + e);
		}

	}

	public void CatalogAddToCart() throws InterruptedException {

		By catalogImg = By.xpath("(//android.widget.ImageView[@resource-id=\"com.sot.bizup.debug:id/ivItem\"])[1]");
		By catalogSelect1 = By
				.xpath("(//android.widget.Button[@resource-id=\"com.sot.bizup.debug:id/mbShortlist\"])[1]");
		By catalogSelect2 = By
				.xpath("(//android.widget.Button[@resource-id=\"com.sot.bizup.debug:id/mbShortlist\"])[2]");
		By closeButton = By.id("com.sot.bizup.debug:id/ivCross");
		By orderbutton = By.id("com.sot.bizup.debug:id/mbPlaceOrder");

		try {

			if (driver.findElements(orderbutton).size() > 0) {
				driver.findElement(catalogImg).click();

				if (driver.findElements(closeButton).size() > 0) {
					System.out.println("Full catalog view open ✔");

					driver.findElement(catalogSelect1).click();
					System.out.println("Catalog added ✔");

					driver.findElement(closeButton).click();
					System.out.println("Full catalog view closed ✔");

				} else {
					System.out.println("Full catalog view not open ❌");
					Assert.fail("Full catalog view not open ❌");
				}

				driver.findElement(catalogSelect2).click();
				System.out.println("Catalog added ✔");

				driver.findElement(orderbutton).click();
				System.out.println("Order button clicked ✔");
			}

			else {
				System.out.println("Order button not displayed ❌");
				Assert.fail("Order button not displayed ❌");
			}

			// PreEnquiryQuestion Check
			PreEnquiryQue();
		}

		catch (Exception e) {
			System.out.println("Order button not displayed ❌" + e);
			Assert.fail("Order button not displayed ❌" + e);
		}

	}

	public void CatalogEnquiry() throws InterruptedException {

		// CoachMark Check
		CoachmarkCheck("//android.widget.ImageView[@resource-id=\"com.sot.bizup.debug:id/ivDealsCoachmarkText\"]");
		CoachmarkCheck("/hierarchy/android.widget.FrameLayout/android.view.ViewGroup/android.widget.ImageView");
		ClickId("com.sot.bizup.debug:id/mtSellerCatalog");

		// Selecting Catalog
		ClickXp("(//android.widget.Button[@resource-id=\"com.sot.bizup.debug:id/mbShortlist\"])[1]");
		ClickXp("(//android.widget.Button[@resource-id=\"com.sot.bizup.debug:id/mbShortlist\"])[2]");
		ClickId("com.sot.bizup.debug:id/mbDealKare");

		// PreEnquiryQuestion Check
		PreEnquiryQue();

		// Check for PreEnquiryVideo
		PreEnquiryVideoCheck();
	}

	public void shortSellerEnquiry() throws InterruptedException {

		By chatButton = By.id("com.sot.bizup.debug:id/mbDealKare");

		try {
			if (driver.findElements(chatButton).size() > 0) {

				// Click on Chat button
				driver.findElement(chatButton).click();
				System.out.println("Chat button clicked ✔");

				// PreEnquiryQuestion Check
				PreEnquiryQue();

				// Check for PreEnquiryVideo
				PreEnquiryVideoCheck();

				// Enter the text on the Chat box
				ShortChat();

				// Back from Chat
				Back();
			} else {
				System.out.println("Chat button not displayed ❌");
				Assert.fail("Chat button not displayed ❌");
			}

		} catch (Exception e) {
			System.out.println("Short Seller Enquiry error" + e);
		}
	}

	public void shortestEnquiry() throws InterruptedException {

		// Click on Baat Kare button
		ClickId("com.sot.bizup.debug:id/mbDealKare");

		// PreEnquiryQuestion Check
		PreEnquiryQue();

		// Check for PreEnquiryVideo
		PreEnquiryVideoCheck();

		// Enter the text on the Chat box
		ShortChat();

	}

	// CoachMark Check
	public void CoachmarkCheck(String CoachEle) {
		By coachMark = By.xpath(CoachEle);

		if (driver.findElements(coachMark).size() > 0) {
			// If the coachMark exists, click it
			driver.findElement(coachMark).click();
			System.out.println("CoachMark Displayed ✔");
		}
	}

	// PreEnquiryVideo Check
//	public void PreEnquiryVideoCheck() throws InterruptedException {
//
//		By preEnquiryVideo = By.id("com.sot.bizup.debug:id/mbButton");
//		WebElement element = driver.findElement(preEnquiryVideo);
//
//		if (driver.findElements(preEnquiryVideo).size() > 0) {
//			// If the PreEnquiryVideo exists, skip it
//			Wait(element, "SKIP VIDEO");
//			ClickId("com.sot.bizup.debug:id/mbButton");
//			System.out.println("Pre-Enquiry Video Displayed ✔");
//		}
//	}

	public void PreEnquiryVideoCheck() throws InterruptedException {

		By preEnquiryVideo = By.id("com.sot.bizup.debug:id/mbButton");

		if (driver.findElements(preEnquiryVideo).size() > 0) {
			// If the PreEnquiryVideo exists, skip it
			Thread.sleep(8000);
			driver.findElement(preEnquiryVideo).click();
			System.out.println("Pre-Enquiry Video Displayed ✔");
		}
	}

	// Feedback Question
	public void FeedbackQue() {
		try {
			By FeedbackQ = By.id("com.sot.bizup.debug:id/mtQuestion");

			if (driver.findElements(FeedbackQ).size() > 0) {
				ClickId("com.sot.bizup.debug:id/mbPositive");
				ClickId("com.sot.bizup.debug:id/mbMessage");
				driver.pressKey(new KeyEvent(AndroidKey.BACK));
				System.out.println("Feedback Question working ✔");
			}
		} catch (Exception e) {
			Assert.fail("Feedback Question error" + e);
		}
	}

	// Feedback Question
	public void PreEnquiryQue() {

		By PreEnqQ = By.id("com.sot.bizup.debug:id/mtQuestion");
		try {
			if (driver.findElements(PreEnqQ).size() > 0) {
				ClickId("com.sot.bizup.debug:id/mbPositive");
				System.out.println("PreEnquiry Question working ✔");
			}
		} catch (Exception e) {
			Assert.fail("PreEnquiry Question error" + e);
		}

	}

	// WhatsApp Check
	public void WhatsAppCheck() {
		try {
			By WhatsAppCheck = By.id("com.whatsapp:id/send");

			if (driver.findElements(WhatsAppCheck).size() > 0) {
				System.out.println("Landed on WhatsApp ✔");
				Thread.sleep(2000);
				Back();
				Back();
			}
		} catch (Exception e) {
			Assert.fail("WhatsApp Landing Failed" + e);
		}
	}

	public void Agent() {
		try {
			By agent = By.id("com.sot.bizup.debug:id/fab");

			if (driver.findElements(agent).size() > 0) {
				ClickId("com.sot.bizup.debug:id/fab");
				System.out.println("Agent Clicked ✔");
			}
		} catch (Exception e) {
			Assert.fail("Agent failed " + e);
		}

	}

	public void Chat() {
		try {
			ShortChat();
			driver.hideKeyboard();
			Thread.sleep(2000);
			System.out.println("Chat Sucessfull ✔");
		} catch (Exception e) {
			Assert.fail("Chat error" + e);
		}
	}

}
