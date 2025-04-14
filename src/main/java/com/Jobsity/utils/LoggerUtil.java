package com.Jobsity.utils;

import org.apache.log4j.Logger;

import com.Jobsity.listeners.LogListener;

public class LoggerUtil {
	/** The logger. */
	private static Logger logger = Logger.getLogger(LogListener.class);

	/**
	 * Gets the logger.
	 *
	 * @return the logger
	 */
	public static Logger getLogger() {
		return logger;
	}

	/**
	 * Log.
	 *
	 * @param message the message
	 */
	public static void log(String message) {
		logger.info(message);
	}
}
