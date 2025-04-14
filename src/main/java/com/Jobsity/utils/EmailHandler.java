package com.Jobsity.utils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.testng.IReporter;
import org.testng.IResultMap;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.xml.XmlSuite;

import com.Jobsity.Config.FrameWorkConfigFactory;
import com.Jobsity.Config.Frameworkconfig;
import com.Jobsity.enums.BrowserType;

public class EmailHandler implements IReporter {
	public static final String EMAIL_SIGNATURE = "<br>Regards<br>Automation Team";
	private static long suiteStartTime;
	public Frameworkconfig config;
	public static String environment;
	static String executionTime;
	String suiteType = getSuiteType(environment);
	static String currentTime = UniqueGenerator.getCurrentTime();
	static String automationReportLink;
	public static Object url;
	public static String brand;
	private static List<String> automationReportEmail;
	private final ThreadLocal<AtomicInteger> totalPassed = ThreadLocal.withInitial(() -> new AtomicInteger(0));
	private final ThreadLocal<AtomicInteger> totalFailed = ThreadLocal.withInitial(() -> new AtomicInteger(0));

	@Override
	public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
		// You can customize this method to gather information about passed and failed
		// tests

		config = FrameWorkConfigFactory.getConfig();
		environment = getEnvironment(config);
		List<String> recipientEmails = automationReportEmail;
		BrowserType browserName = System.getProperty("browserName") != null
				? BrowserType.valueOf(System.getProperty("browserName").toUpperCase())
				: FrameWorkConfigFactory.getConfig().browserName();
		 ExecutorService executor = Executors.newFixedThreadPool(10); 
		for (ISuite suite : suites) {
			Map<String, ISuiteResult> suiteResults = suite.getResults();
			for (ISuiteResult result : suiteResults.values()) {
				ITestContext testContext = result.getTestContext();
				IResultMap passedTests = testContext.getPassedTests();
				IResultMap failedTests = testContext.getFailedTests();

				// Print the test context name
				System.out.println("********  test name *********  =  " + result.getTestContext().getName());
//                System.out.println("Passed Test : "+passedTests );
//                System.out.println("Failed Test : "+failedTests );
				// Update totalPassed and totalFailed counters
				System.out.println("Total Passed Test :" + testContext.getPassedTests().getAllResults().size());
				System.out.println("Total Failed Tests :" + testContext.getFailedTests().getAllResults().size());

				totalPassed.get().addAndGet(passedTests.size());
				totalFailed.get().addAndGet(failedTests.size());
				

			}
		}
		try {
			sendEmailWithAutomationResults(totalPassed.get().get(), totalFailed.get().get(), config, recipientEmails,
					environment, browserName);
		} catch (EmailException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	

	public static void setSuiteStartTime(long startTime) {
		suiteStartTime = startTime;
	}

	public void sendEmailWithAutomationResults(int totalPassed, int totalFailed, Frameworkconfig config,
			List<String> recipientEmails, String environment, BrowserType browserName)
			throws EmailException, IOException {
		HtmlEmail email = createHtmlEmail(config, recipientEmails);
		String suiteType = getSuiteType(environment);
		email.setSubject(environment+" "+getGroupType(System.getProperty("groups"))+" - Test Execution Summary - "  + currentTime);
		//email.setSubject("Automation Suite Execution Summary " + environment + " " + currentTime);
		// Build the email message
		StringBuilder emailMessage = new StringBuilder();
		executionTime = getFormattedExecutionTime();
		emailMessage
				.append("Hi,<br><br>Please find attached the Automation execution summary for " + "<strong>" + suiteType
						+ "</strong>" + " ")
				.append("<span style='font-weight: bold;'>").append(environment).append("</span><br>");
		
		emailMessage.append("<span style='font-weight: bold;'>Executed Suite: </span>")
		.append("<span style='font-weight: bold;'>").append(getGroupType(System.getProperty("groups"))).append("</span><br>");
		emailMessage.append("<span style='font-weight: bold;'>Total Passed Automation Flows: </span>")
				.append("<span style='font-weight: bold;'>").append(totalPassed).append("</span><br>");
		emailMessage.append("<span style='font-weight: bold;'>Total Failed Automation Flows: </span>")
				.append("<span style='font-weight: bold;'>").append(totalFailed).append("</span><br><br>");
		emailMessage.append("<span style='font-weight: bold;'>Total Suite Execution Time: </span>")
				.append("<span style='font-weight: bold;'>").append(executionTime).append("</span><br>");
		emailMessage.append("<span style='font-weight: bold;'>Executed in: </span>")
				.append("<span style='font-weight: bold;'>").append(browserName).append("</span><br><br>");
		/*
		 * emailMessage.append("<span style='font-weight: bold;'>Report Link: </span>")
		 * .append("<span style='font-weight: bold;'>").append(uploadReport()).append(
		 * "</span><br><br>");
		 */
		emailMessage.append("Feel free to reach out if you have any questions.<br>");
		emailMessage.append(EMAIL_SIGNATURE);

		email.setMsg(emailMessage.toString());
		System.out.println(emailMessage);
		// Send the email
		email.send();
		System.out.println("email sent");
		

	}

	public static void sendConsolidatedEmail(Frameworkconfig config, String emailMSG, String OUTPUT_DIRECTORY,
			Map<String, HashMap<String, String>> brokenLinksData) {

		List<String> recipientEmails = config.recipientEmail();

		try {
			HtmlEmail email = createHtmlEmail(config, recipientEmails);

			email.setSubject("Consolidated Kinship Broken Links Report " + UniqueGenerator.getCurrentTime());
			email.setMsg("Consolidated Results:\n\n" + emailMSG + "\n"
					+ "Feel free to reach out if you have any questions.<br>" + EmailHandler.EMAIL_SIGNATURE);

			File directory = new File(OUTPUT_DIRECTORY);
			if (directory.exists() && directory.isDirectory()) {
				File[] files = directory.listFiles();

				if (files != null && files.length > 0) {
					for (File file : files) {
						if (file.isFile() && file.length() > 0) {
							// Check if the file has data (non-header rows)
							try {
								// Explicitly specify the charset (e.g., UTF-8)
								List<String> lines = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
								if (lines.size() > 1) {
									EmailAttachment attachment = new EmailAttachment();
									attachment.setPath(file.getAbsolutePath());
									attachment.setDisposition(EmailAttachment.ATTACHMENT);
									email.attach(attachment);
								}
							} catch (IOException ex) {
								System.err.println("Error reading lines from file: " + file.getAbsolutePath());
								ex.printStackTrace();
							}
						}
					}
				} else {
					System.out.println("No files found in the directory: " + OUTPUT_DIRECTORY);
				}
			} else {
				System.out.println("Directory not found: " + OUTPUT_DIRECTORY);
			}

			email.send();

			System.out.println("Email sent successfully.");
			LoggerUtil.log("Email sent successfully.");
		} catch (Exception e) {
			e.printStackTrace();
		}
		

	}

	public static HtmlEmail createHtmlEmail(Frameworkconfig config, List<String> recipientEmails)
			throws EmailException {
		System.out.println("Creating  Report");
		HtmlEmail email = new HtmlEmail();
		email.setHostName(config.hostName());
		email.setSmtpPort(config.SMTPPORT());
		email.setAuthenticator(new DefaultAuthenticator(config.senderEmail(),
				PasswordProtector.decryptPassword(config.gmailAuthenticationToken())));
		email.setStartTLSRequired(true);
		email.setFrom(config.senderEmail(), "Jobsity Automation");

		// Add recipients
		for (String recipientEmail : recipientEmails) {
			System.out.println(recipientEmail);
			email.addTo(recipientEmail);
		}
		return email;
	}

	private String getFormattedExecutionTime() {
		long executionTimeMillis = System.currentTimeMillis() - suiteStartTime;
		long minutes = (executionTimeMillis / 1000) / 60;
		long seconds = (executionTimeMillis / 1000) % 60;
		return minutes + " minutes and " + seconds + " seconds";
	}

	public static String getCSVFilePath(String environment, String OUTPUT_DIRECTORY) {
		File directory = new File(OUTPUT_DIRECTORY);
		if (!directory.exists()) {
			directory.mkdirs();
		}
		return directory.getPath() + File.separator + "brokenlinks_" + environment + ".csv";
	}

	public static String getEnvironment(Frameworkconfig config) {
		if (System.getProperty("jobsityEnvironment") != null) {
			environment = System.getProperty("jobsityEnvironment");
			url = config.getProperty(environment);
			automationReportEmail = config.jobsityrecipientAutomationReportEmail();
		}
		else {
			
			// defaults
			//environment = config.kinshipEnvironment(); // or config.theKinEnvironment();
			//automationReportEmail = config.recipientAutomationReportEmail();
		}
		return environment;
	}

	public static String getSuiteType(String environment) {
		String suiteType = "";
		if (System.getProperty("jobsityEnvironment") != null) {
			suiteType = "Jobsity Web";
		}
		return suiteType;
	}

	

	

	public static String getBrand() {
		if (System.getProperty("kinshipEnvironment") != null) {
			brand = "Kinship Web";
		}
		else {
			brand = " ";
		}
		return brand;
	}
	
	
	
	   public static String getGroupType(String group) {
		   System.out.println("Group found is "+group);
		   if(group.toLowerCase().contains("regression")) {
			   return "Regression";
		   }
		   else {
			   return "Regression";
		   }
	   }

	  
}
