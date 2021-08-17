package com.amex.tests;

import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;

public class PhoneSearchTestNGTest {
 
	private WebDriver webDriver;
	private WebElement element;
  @BeforeClass
  public void beforeTest() {
	  
	  ResourceBundle resourceBundle=ResourceBundle.getBundle("canadasearch");		
		
		String driverPath=resourceBundle.getString("webDriverPath");
		String baseUrl=resourceBundle.getString("baseUrl");
		System.setProperty("webdriver.chrome.driver",driverPath);
		//WebDriverManager.chromedriver().setup();
		webDriver=new ChromeDriver();
		webDriver.get(baseUrl);
		webDriver.manage().window().maximize();
		//webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		FluentWait wait=new FluentWait(webDriver).withTimeout(1000,TimeUnit.MICROSECONDS);
		
		Object result=wait.until(new Function<WebDriver,String>(){
			public String apply(WebDriver webDriver) {
				return webDriver.getTitle();
			}
		});
		
		Assert.assertEquals(result.toString(), "Free People Search, Reverse Phone Lookup, Business Telephone Directory | Canada411.ca");
  }
  
 // @Test
 // public void canadaSearchTitleTest() {
//	  Assert.assertEquals(webDriver.getTitle(), "Free People Search, Reverse Phone Lookup, Business Telephone Directory | Canada411.ca");
 // }
  @Test
  public void canadaSearchFindPeopleTest() {
	  String result=null;
	  FluentWait wait=new FluentWait(webDriver).withTimeout(3000,TimeUnit.MICROSECONDS);
		
		Object result1=wait.until(new Function<WebDriver,String>(){
			
			public String apply(WebDriver webDriver) {
				String result=null;
				element=webDriver.findElement(By.id("c411PeopleReverseWhat"));
				  element.sendKeys("905-841-0191");
				 element= webDriver.findElement(By.id("c411PeopleReverseFind"));
				 element.click();
				List<WebElement> elements=webDriver.findElements(By.xpath("//*[@id=\"ypgBody\"]/div[3]/div/div[1]/div[2]/div[1]/div[1]/h1/span"));
			    if(elements.size()!=0) {
			      element=webDriver.findElement(By.xpath("//*[@id=\"ypgBody\"]/div[3]/div/div[1]/div[2]/div[1]/div[1]/h1/span"));
			       result=element.getText();
			       System.out.println(result);
			       
			    }
			    return result;
			}
		});
	 
    Assert.assertEquals(result1, "Golf Town");
   // webDriver.close();
  }
  
  @AfterClass
  public void afterClassTest() {
	  webDriver.close();
  }
  
}
