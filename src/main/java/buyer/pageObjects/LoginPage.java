// LoginPage.java
package buyer.pageObjects;

import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class LoginPage extends HomePage {
	private final AndroidDriver driver;
	private static final String APK_PATH = "C:\\Users\\lenovo\\eclipse-workspace\\bizup\\src\\test\\java\\resources\\Bizup-3.0.1-debug(148).apk";
	private static final String APP_PACKAGE = "com.sot.bizup.debug";

	@AndroidFindBy(id = "com.sot.bizup.debug:id/ivHindiBg")
	private WebElement hindi;

	@AndroidFindBy(id = "com.sot.bizup.debug:id/tvEnglish")
	private WebElement english;

	@AndroidFindBy(id = "com.sot.bizup.debug:id/mbContinue")
	private WebElement continueBtn;

	@AndroidFindBy(id = "com.truecaller:id/tv_continueWithDifferentNumber")
	private WebElement trueCaller;

	@AndroidFindBy(id = "com.sot.bizup.debug:id/etMobileNo")
	private WebElement mobileNumber;

	public LoginPage(AndroidDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}

	public LoginPage setMobileNumber(String number) {
		try {
			handleTrueCaller();
			mobileNumber.sendKeys(number);
			NotificationPermission();
			HomeWait();
		} catch (Exception e) {
			System.err.println("Failed to set mobile number: " + e.getMessage());
			throw new RuntimeException("Mobile number setup failed", e);
		}
		return this;
	}

	private void handleTrueCaller() {
		By trueCallerLocator = By.id("com.truecaller:id/tv_continueWithDifferentNumber");
		if (driver.findElements(trueCallerLocator).size() > 0) {
			driver.findElement(trueCallerLocator).click();
			System.out.println("TrueCaller handled ✔");
		} else {
			System.out.println("TrueCaller not enabled on device ❌");
		}
	}

	public LoginPage SelectLanguage() {
		try {
			By langTitle = By.id("com.sot.bizup.debug:id/tvChooseLanguage");
			if (driver.findElements(langTitle).size() > 0) {
				english.click();
				continueBtn.click();
				System.out.println("Language selected successfully ✔");
			}
		} catch (Exception e) {
			System.err.println("Language selection failed: " + e.getMessage());
			throw new RuntimeException("Language selection failed", e);
		}
		return this;
	}

	public LoginPage Install() {
		try {
			if (!driver.isAppInstalled(APP_PACKAGE)) {
				driver.installApp(APK_PATH);
				System.out.println("App installed successfully ✔");
			} else {
				System.out.println("App already installed ✔");
				clearAppData();
			}
		} catch (Exception e) {
			System.err.println("Installation failed: " + e.getMessage());
			throw new RuntimeException("App installation failed", e);
		}
		return this;
	}

	public LoginPage Login() {
		try {
			String randomNumber = generateRandomPhoneNumber();
			return setMobileNumber(randomNumber);
		} catch (Exception e) {
			System.err.println("Login failed: " + e.getMessage());
			throw new RuntimeException("Login process failed", e);
		}
	}

	private String generateRandomPhoneNumber() {
		Random rand = new Random();
		StringBuilder phoneNumber = new StringBuilder("11");
		for (int i = 0; i < 8; i++) {
			phoneNumber.append(rand.nextInt(10));
		}
		return phoneNumber.toString();
	}
}