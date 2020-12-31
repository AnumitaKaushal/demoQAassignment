package com.automation.demoqa.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropUtils {

	static Properties props;
	static String pathForOR = System.getProperty("user.dir") + "\\src\\main\\resources\\"
			+ "OR.properties";

	public static File fetchORFile(String pathForOR) {
		File orFile = new File(pathForOR);
		if (orFile.exists()) {
		} else {
			System.out.println("File[" + pathForOR + "] not found");
		}
		return orFile;
	}

	public static void loadProperties(String pathForOR) {
		File file = fetchORFile(pathForOR);

		props = new Properties();

		try {
			FileInputStream object = new FileInputStream(file);
			props.load(object);
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Input Output exception");
			e.printStackTrace();
		}

	}

	public static String fetchProperty(String propName, String pathForOR) throws Exception {
		loadProperties(pathForOR);
		String prop = props.getProperty(propName);
		if (prop == null || prop.isEmpty()) {
			throw new Exception("Property[" + propName + "] missing ");
		} else {
			return prop;
		}

	}


}
