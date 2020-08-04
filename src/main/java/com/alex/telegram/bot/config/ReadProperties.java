package com.alex.telegram.bot.config;

import java.io.*;
import java.util.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ReadProperties {
	
	private static final Logger logger = LogManager.getLogger(ReadProperties.class);

	public static String getProperty(String propName)  {
		Properties prop = null;
		try {
			prop = readPropertiesFile("src" + File.separator + "main" + File.separator + "resources"
					+ File.separator + "application.properties");
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		return prop.getProperty(propName);
	}

	public static Properties readPropertiesFile(String fileName) throws IOException {
		FileInputStream fis = null;
		Properties prop = null;
		try {
			fis = new FileInputStream(fileName);
			prop = new Properties();
			prop.load(fis);
		} catch (FileNotFoundException fnfe) {
			logger.error(fnfe.getMessage());
		} catch (IOException ioe) {
			logger.error(ioe.getMessage());
		} finally {
			fis.close();
		}
		return prop;
	}

}
