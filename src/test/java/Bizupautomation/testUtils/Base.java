package Bizupautomation.testUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.time.Duration;
import java.util.Properties;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;

public class Base extends AndroidActions {

	public AndroidDriver driver;
	public AppiumDriverLocalService service;
	private Properties prop;

	private static final String NODE_JS_MAIN_PATH = "C:\\Users\\lenovo\\AppData\\Roaming\\npm\\node_modules\\appium\\build\\lib\\main.js";
	private static final String DATA_PROPERTIES_PATH = System.getProperty("user.dir")
			+ "\\src\\main\\java\\buyer\\resources\\data.properties";

	@BeforeClass(alwaysRun = true)
	public void configureAppium() throws URISyntaxException, IOException {
		prop = loadProperties(DATA_PROPERTIES_PATH);

		String ipAddress = System.getProperty("ipAddress", prop.getProperty("ipAddress"));
		String port = prop.getProperty("port");
		String deviceName = prop.getProperty("Device3");

		startAppiumService(ipAddress, Integer.parseInt(port));

		driver = initializeDriver(deviceName);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
	}

	private void startAppiumService(String ipAddress, int port) {
		service = new AppiumServiceBuilder().withAppiumJS(new File(NODE_JS_MAIN_PATH)).withIPAddress(ipAddress)
				.usingPort(port).build();
		service.start();
	}

	private AndroidDriver initializeDriver(String deviceName) {
		UiAutomator2Options options = new UiAutomator2Options();
		options.setDeviceName(deviceName);
		return new AndroidDriver(service.getUrl(), options);
	}

	private Properties loadProperties(String filePath) throws IOException {
		Properties properties = new Properties();
		try (FileInputStream fis = new FileInputStream(filePath)) {
			properties.load(fis);
		}
		return properties;
	}

	@AfterClass(alwaysRun = true)
	public void tearDown() {
		if (driver != null) {
			driver.quit();
		}
		if (service != null) {
			service.stop();
		}
	}
}
