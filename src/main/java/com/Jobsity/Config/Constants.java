package com.Jobsity.Config;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class Constants {
	/** The Constant WORKING_DIRECTORY. */
	public static final String WORKING_DIRECTORY = System.getProperty("user.dir");

	/** The Constant REPORT_DIRECTORY. */
	public static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");

	
	public final static String EXTENTREPORT_DIR = WORKING_DIRECTORY + "/ExtentReports";
    public final static String REPORTNAME="AutomationResult."+Constants.getTimestamp()+".html";
	/** The Constant PROJECT_NAME. */
	public final static String PROJECT_NAME = "Kinship Automation Framework";
	/** The Constant EXTENT_CONFIG_PATH. */
	public final static String EXTENT_CONFIG_PATH = String
			.format("%s/src/test/resources/extentConfig/extent-config.xml", WORKING_DIRECTORY);




	public static String getTimestamp() {
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		return sdf.format(timestamp);
				
}

}
