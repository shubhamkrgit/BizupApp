package buyer.pageObjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class ChatPage {

	AndroidDriver driver;

	public ChatPage(AndroidDriver driver)

	{
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}

	@AndroidFindBy(id = "com.sot.bizup.debug:id/fab")
	private WebElement AgentIcon;

	@AndroidFindBy(xpath = "//android.widget.EditText")
	private WebElement message1;

	@AndroidFindBy(xpath = "//android.widget.Button[@text=\"\"]")
	private WebElement send;

	@AndroidFindBy(xpath = "//android.widget.EditText")
	private WebElement message2;

	@AndroidFindBy(xpath = "//android.widget.TextView[@text=\"ओनर से बात करे\"]")
	private WebElement whatsAppEnableTop;

	@AndroidFindBy(xpath = "//android.widget.Button[@text=\"बात करे\"]")
	private WebElement whatsAppEnableBottom;

	public void clickAgent() {
		AgentIcon.click();
	}

}
