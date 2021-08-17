package com.amex.parameterizedtests;

import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DataProviderTest {
	
	
	private WebDriver webDriver;
	private WebElement element;
	private String driverPath,baseUrl;
	private   ResourceBundle resourceBundle;
	  @BeforeTest
	  public void beforeTest() {
		  
		 resourceBundle=ResourceBundle.getBundle("googlesearch");		
			
			driverPath=resourceBundle.getString("webDriverPath");
			baseUrl=resourceBundle.getString("baseUrl");
			System.setProperty("webdriver.chrome.driver",driverPath);
			//WebDriverManager.chromedriver().setup();
			webDriver=new ChromeDriver();
			
		  }
	@Test
	@Parameters({"author","searchKey"})
	public void suiteParameterizedTest(@Optional("Abc") String author, 
			String searchKey) {
		webDriver.get(baseUrl);
		webDriver.manage().window().maximize();
		
		WebElement webElement=webDriver.findElement(By.name("q"));
		webElement.sendKeys(searchKey);
		log.info("Serach Key",searchKey);
		
	}
	

}
