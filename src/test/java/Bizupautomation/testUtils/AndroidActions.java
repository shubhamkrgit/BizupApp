package Bizupautomation.testUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import javax.mail.AuthenticationFailedException;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.appium.java_client.android.AndroidDriver;

public class AndroidActions {

	AndroidDriver driver;

	public List<HashMap<String, String>> getJsonData(String jsonFilePath) throws IOException {

		// Covert json file content to json string
		String jsonContent = FileUtils.readFileToString(new File(jsonFilePath), StandardCharsets.UTF_8);

		ObjectMapper mapper = new ObjectMapper();
		List<HashMap<String, String>> data = mapper.readValue(jsonContent,
				new TypeReference<List<HashMap<String, String>>>() {
				});
		return data;
	}

	public String getScreenshotPath(String testCaseName, AndroidDriver driver) throws IOException {
		File source = driver.getScreenshotAs(OutputType.FILE);
		String destinationFile = System.getProperty("user.dir") + "/reports/" + testCaseName + ".png";
		FileUtils.copyFile(source, new File(destinationFile));
		return destinationFile;
		// 1. capture and place in folder
		// 2. extent report pick file and attach to report
	}

	public void Terminate(AndroidDriver driver) {
		driver.terminateApp("com.sot.bizup.debug");
	}

	public void RemoveApp(AndroidDriver driver) {
		driver.removeApp("com.sot.bizup.debug");
	}

	public void Restart(AndroidDriver driver) throws InterruptedException {
		driver.terminateApp("com.sot.bizup.debug");
		driver.activateApp("com.sot.bizup.debug");
		Thread.sleep(3000);
	}

	public static void sendEmailWithAttachment(String toEmail, String subject, String body, String filePath)
			throws IOException {
		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream(
				System.getProperty("user.dir") + "\\src\\main\\java\\buyer\\resources\\data.properties");
		prop.load(fis);

		String fromEmail = prop.getProperty("EMAIL_USERNAME");
		String password = prop.getProperty("EMAIL_PASSWORD");

		if (fromEmail == null || password == null) {
			throw new IllegalArgumentException("EMAIL_USERNAME or EMAIL_PASSWORD environment variables are not set.");
		}

		if (toEmail == null || toEmail.isEmpty()) {
			throw new IllegalArgumentException("Recipient email address (toEmail) cannot be null or empty.");
		}

		// SMTP server configuration
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");

		// Create session
		Session session = Session.getInstance(props, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(fromEmail, password);
			}
		});

		try {
			// Create email message
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(fromEmail));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
			message.setSubject(subject);

			// Create email body part with HTML content
			MimeBodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setContent(body, "text/html; charset=utf-8"); // ✅ Ensures HTML is rendered properly

			// Add attachment
			MimeBodyPart attachmentPart = new MimeBodyPart();
			File file = new File(filePath);
			if (!file.exists()) {
				throw new Exception("Attachment file not found: " + filePath);
			}
			attachmentPart.attachFile(filePath);

			// Combine parts
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);
			multipart.addBodyPart(attachmentPart);

			// Set content
			message.setContent(multipart);

			// Send email
			Transport.send(message);
			System.out.println("✅ Email with attachment sent successfully!");
		} catch (AuthenticationFailedException e) {
			System.err.println("❌ Authentication failed! Check your email credentials or enable App Passwords.");
			e.printStackTrace();
		} catch (Exception e) {
			System.err.println("❌ Error occurred while sending email: " + e.getMessage());
			e.printStackTrace();
		}
	}

}