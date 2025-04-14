package com.Jobsity.listeners;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.Jobsity.manager.ExtentReportManager;
import com.Jobsity.utils.ReportUtil;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

public class ReportListener implements ITestListener {
	/**
	 * Gets the test description.
	 *
	 * @param result the result
	 * @return the test description
	 */

	public String getTestDescription(ITestResult result) {
		return result.getMethod().getDescription() != null ? result.getMethod().getDescription() : getTestName(result);
	}

	/**
	 * Gets the test name.
	 *
	 * @param result the result
	 * @return the test name
	 */
	public String getTestName(ITestResult result) {
		return result.getTestName() != null ? result.getTestName()
				: result.getMethod().getConstructorOrMethod().getName();
	}

	@Override
	public void onFinish(ITestContext context) {
		ExtentReportManager.getExtentReports().flush();
		}
	

	@Override
	public void onStart(ITestContext context) {
		
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
	}

	@Override
	public void onTestFailure(ITestResult result) {
		Throwable t = result.getThrowable();
		String cause = "";
		if (t != null) {
			cause = t.getMessage();
		}
		ReportUtil.addScreenShot(Status.FAIL, "Test Failed : " + cause);
		// String Methodname = result.getMethod().getMethodName();

		

	}

	@Override
	public void onTestSkipped(ITestResult result) {
		
		 if (result.getStatus() == ITestResult.SKIP) {
			 ReportUtil.logMessage(Status.SKIP, "Test Skipped");
		        // Remove skipped test from the report
		        removeCurrentTest(ExtentReportManager.getExtentReports(), ExtentReportManager.getCurrentTest());
		    }
		
	}

	public void removeCurrentTest(final ExtentReports reports, final ExtentTest extentTest) {
		reports.removeTest(extentTest);
	}

	@Override
	public void onTestStart(ITestResult result) {
		String testName = result.getMethod().getMethodName();
		System.out.println("Test case started = " + testName);
		ExtentReportManager.startTest(getTestName(result), getTestDescription(result));

	}

	@Override
	public void onTestSuccess(ITestResult result) {
		ReportUtil.logMessage(Status.PASS, "Test Passed");

	}
}
