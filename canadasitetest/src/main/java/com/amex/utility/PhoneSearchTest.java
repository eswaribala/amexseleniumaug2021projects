package com.amex.utility;

import java.util.ResourceBundle;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class PhoneSearchTest {	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
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
