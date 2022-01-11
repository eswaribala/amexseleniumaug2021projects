package com.amex.tests;

import java.util.ResourceBundle;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

public class PhoneSearchTestNGV1Test {

	@Test
	public void canadaSearchTest() {
		ResourceBundle resourceBundle=ResourceBundle.getBundle("canadasearch");

		System.out.println(resourceBundle.getString("webDriverPath"));
		System.out.println(resourceBundle.getString("baseUrl"));
		//WebDriverManager.chromedriver().setup();
		String driverPath=resourceBundle.getString("webDriverPath");
		String baseUrl=resourceBundle.getString("baseUrl");
		System.setProperty("webdriver.chrome.driver",driverPath);
		WebDriver webDriver=new ChromeDriver();
		webDriver.get(baseUrl);
		System.out.println(webDriver.getTitle());
		webDriver.close();
		
	}
}
