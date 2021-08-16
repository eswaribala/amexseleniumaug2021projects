package com.amex.tests;

import org.testng.annotations.Test;

import java.util.ResourceBundle;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;

public class PhoneSearchTestNGTest {
 
	private WebDriver webDriver;
  @BeforeClass
  public void beforeTest() {
	  
	  ResourceBundle resourceBundle=ResourceBundle.getBundle("canadasearch");		
		
		String driverPath=resourceBundle.getString("webDriverPath");
		String baseUrl=resourceBundle.getString("baseUrl");
		System.setProperty("webdriver.chrome.driver",driverPath);
		webDriver=new ChromeDriver();
		webDriver.get(baseUrl);
  }
  
  @Test
  public void canadaSearchTitleTest() {
	  Assert.assertEquals(webDriver.getTitle(), "Free People Search, Reverse Phone Lookup, Business Telephone Directory | Canada411.ca");
  }

  @AfterClass
  public void afterClassTest() {
	  webDriver.close();
  }
  
}
