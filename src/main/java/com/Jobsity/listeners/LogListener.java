package com.Jobsity.listeners;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.Jobsity.utils.LoggerUtil;

public class LogListener implements ITestListener {

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
		LoggerUtil.getLogger().fatal(getTestName(result) + " : Test Failed : " + cause);
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		LoggerUtil.log(getTestName(result) + " : Test Skipped");
	}

	@Override
	public void onTestStart(ITestResult result) {
		LoggerUtil.log(getTestName(result) + ": Test started");
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		LoggerUtil.log(getTestName(result) + " : Test Passed");
	}
}
