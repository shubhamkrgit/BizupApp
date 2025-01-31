package Bizupautomation.testUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import io.appium.java_client.android.AndroidDriver;

public class Listeners extends AndroidActions implements ITestListener {
	AndroidDriver driver;

	ExtentTest test;

	ExtentReports extent = ExtentReporterNG.getReporterObject();

	private List<String> failedTestCases = new ArrayList<>();
	private List<String> failedErrorMessages = new ArrayList<>();

	@Override
	public void onTestStart(ITestResult result) {
		test = extent.createTest(result.getMethod().getMethodName());
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		test.log(Status.PASS, "Test Passed");
	}

	@Override
	public void onTestFailure(ITestResult result) {
		// Log the failure in the report
		test.fail(result.getThrowable());

		AndroidDriver driver = null;
		try {
			driver = (AndroidDriver) result.getTestClass().getRealClass().getField("driver").get(result.getInstance());
		} catch (Exception e) {
			e.printStackTrace();
		}

		String screenshotPath = "";
		try {
			// Capture screenshot and add to the report
			screenshotPath = getScreenshotPath(result.getMethod().getMethodName(), driver);
			String screenshotDescription = result.getMethod().getMethodName() + " failed";
			test.addScreenCaptureFromPath(screenshotPath, screenshotDescription);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Track failed test case names and error messages
		failedTestCases.add(result.getMethod().getMethodName());
		failedErrorMessages.add(result.getThrowable().toString());

	}

	@Override
	public void onFinish(ITestContext context) {
		try {
			extent.flush();

			int totalTests = context.getAllTestMethods().length;
			int passedTests = context.getPassedTests().size();
			int failedTests = context.getFailedTests().size();
			int skippedTests = context.getSkippedTests().size();

			StringBuilder body = new StringBuilder();
			body.append("<html><body style='font-family: Arial, sans-serif;'>");
			body.append("<h2 style='color: #2E86C1;'>🔹 Test Automation Execution Summary 🔹</h2>");

			// Test summary table
			body.append(
					"<table border='1' cellpadding='8' cellspacing='0' style='border-collapse: collapse; width: 100%; max-width: 600px;'>");
			body.append("<tr style='background-color: #2E86C1; color: white;'>");
			body.append("<th>📌 Metric</th><th>📊 Count</th>");
			body.append("</tr>");
			body.append("<tr><td>Total Tests</td><td><b>" + totalTests + "</b></td></tr>");
			body.append("<tr><td style='color: green;'>✅ Passed</td><td><b>" + passedTests + "</b></td></tr>");
			body.append("<tr><td style='color: red;'>❌ Failed</td><td><b>" + failedTests + "</b></td></tr>");
			body.append("<tr><td style='color: orange;'>⚠️ Skipped</td><td><b>" + skippedTests + "</b></td></tr>");
			body.append("</table><br>");

			// Test Case Details Table
			body.append("<h3>📋 Detailed Test Case Report</h3>");
			body.append(
					"<table border='1' cellpadding='8' cellspacing='0' style='border-collapse: collapse; width: 100%; max-width: 800px;'>");
			body.append("<tr style='background-color: #2E86C1; color: white;'>");
			body.append("<th>🔢 #</th><th>📝 Test Case Name</th><th>📄 Description</th><th>📌 Status</th>");
			body.append("</tr>");

			int index = 1;
			for (ITestNGMethod method : context.getAllTestMethods()) {
				String testName = method.getMethodName();
				String description = method.getDescription();
				String status = "✅ Passed"; // Default status

				if (context.getFailedTests().getResults(method).size() > 0) {
					status = "❌ Failed";
				} else if (context.getSkippedTests().getResults(method).size() > 0) {
					status = "⚠️ Skipped";
				}

				body.append("<tr>");
				body.append("<td>" + index++ + "</td>");
				body.append("<td><b>" + testName + "</b></td>");
				body.append("<td>" + (description.isEmpty() ? "No description provided" : description) + "</td>");
				body.append("<td style='font-weight: bold; color: "
						+ (status.contains("Failed") ? "red" : status.contains("Skipped") ? "orange" : "green") + ";'>"
						+ status + "</td>");
				body.append("</tr>");
			}

			body.append("</table><br>");

			// Status message
			if (failedTests > 0) {
				body.append("<h3 style='color: red;'>🚨 Some tests failed!</h3>");
			} else if (skippedTests > 0) {
				body.append("<h3 style='color: orange;'>⚠️ All tests completed but some were skipped!</h3>");
			} else if (passedTests == totalTests) {
				body.append("<h3 style='color: green;'>🎉 All tests passed successfully!</h3>");
			}

			body.append("<p>📎 Please find the attached test report for detailed results.</p>");
			body.append("<p>Regards,<br><b>Automation Team</b></p>");
			body.append("</body></html>");

			// Generate report filename
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH");
			String timestamp = sdf.format(new Date());
			String reportFilePath = System.getProperty("user.dir") + "\\reports\\TestReport_" + timestamp + ".html";

			sendEmailWithAttachment("shubham.bizup@gmail.com", "🚀 Test Automation Execution Report", body.toString(),
					reportFilePath);
		} catch (Exception e) {
			System.err.println("Error in onFinish(): " + e.getMessage());
			e.printStackTrace();
		}
	}

}
