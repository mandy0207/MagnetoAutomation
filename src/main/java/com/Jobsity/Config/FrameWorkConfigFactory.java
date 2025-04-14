package com.Jobsity.Config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.aeonbits.owner.ConfigCache;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class FrameWorkConfigFactory {

	public static Frameworkconfig getConfig() {
		return ConfigCache.getOrCreate(Frameworkconfig.class);
	}

	public static void writeToPropertyFile(String propertyFile, String propertyName, String newValue)
			throws FileNotFoundException, IOException {
		File configFile = new File(propertyFile);
		Properties properties = new Properties();

		try (FileInputStream inputStream = new FileInputStream(configFile)) {
			properties.load(inputStream);

			// Modify the specified property
			properties.setProperty(propertyName, newValue);

			// Save the updated properties back to the file
			try (FileOutputStream outputStream = new FileOutputStream(configFile)) {
				properties.store(outputStream, null);
			}
		}
	}
}
