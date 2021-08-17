package com.amex.parameterizedtests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DataProviderTest {
	
	
	private WebDriver webDriver;
	private WebElement element;
	private String driverPath,baseUrl,canadaSearchUrl;
	private   ResourceBundle resourceBundle;
	  @BeforeTest
	  public void beforeTest() {
		  
		 resourceBundle=ResourceBundle.getBundle("googlesearch");		
			
			driverPath=resourceBundle.getString("webDriverPath");
			baseUrl=resourceBundle.getString("baseUrl");
			canadaSearchUrl=resourceBundle.getString("canadaSearchUrl");
			System.setProperty("webdriver.chrome.driver",driverPath);
			//WebDriverManager.chromedriver().setup();
			webDriver=new ChromeDriver();
			
		  }
	@Test
	@Parameters({"author","searchKey1","searchKey2","data"})
	public void suiteParameterizedTest(@Optional("Abc") String author, 
			String searchKey1, String searchKey2, String ls) {
		log.info("Size"+ls);
		List<String> list=Arrays.asList(ls);
		log.info("Size"+list.size());
		list.stream().forEach(System.out::println);
		webDriver.get(baseUrl);
		webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		webDriver.manage().window().maximize();
		
		WebElement webElement=webDriver.findElement(By.name("q"));
		webElement.sendKeys(searchKey1);
		
		log.info("Search Key"+searchKey1);
		webElement=webDriver.findElement(By.name("q"));
		webElement.sendKeys(searchKey2);
		
		log.info("Search Key"+searchKey2);
		
	}
	
	@Test(dataProvider = "phoneNumbers")	
	public void canadaSearchDataProviderTest(String userName, 
			String phoneNumber) {
		String result=null;
		webDriver.get(canadaSearchUrl);
		webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		webDriver.manage().window().maximize();
		
		element=webDriver.findElement(By.id("c411PeopleReverseWhat"));
		  element.sendKeys(phoneNumber);
		 element= webDriver.findElement(By.id("c411PeopleReverseFind"));
		 element.click();
		List<WebElement> elements=webDriver.findElements(By.xpath("//*[@id=\"ypgBody\"]/div[3]/div/div[1]/div[2]/div[1]/div[1]/h1/span"));
	    if(elements.size()!=0) {
	      element=webDriver.findElement(By.xpath("//*[@id=\"ypgBody\"]/div[3]/div/div[1]/div[2]/div[1]/div[1]/h1/span"));
	       result=element.getText();
	       log.info("Result"+result);
	       
	    }
	    //Assert.assertEquals(result, "Golf Town");
		
	}
	
	@DataProvider(name="phoneNumbers")
    public Object[][] getDataFromDataprovider(){
    return new Object[][] 
    	{
            { "User1", "905-841-0191" },
            { "User2", "647-846-8449" },
            { "User3", "416-244-2644" }
        };

    }
	
	@AfterTest
	public void afterTest() {
		webDriver.close();
	}

}
