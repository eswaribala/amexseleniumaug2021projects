package com.amex.csvfiles;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.opencsv.CSVWriter;

import lombok.extern.slf4j.Slf4j;
@Slf4j
public class GoldenChennaiWriteCSVTest {
	private WebDriver webDriver;
	private WebElement element;
	private String driverPath,goldenChennaiUrl,canadaSearchUrl,fileName;
	private   ResourceBundle resourceBundle;
	  @BeforeTest
	  public void beforeTest() {
		  
		 resourceBundle=ResourceBundle.getBundle("googlesearch");		
			
			driverPath=resourceBundle.getString("webDriverPath");
			
			goldenChennaiUrl=resourceBundle.getString("goldenChennaiUrl");
			fileName=resourceBundle.getString("fileName");
			System.setProperty("webdriver.chrome.driver",driverPath);
			
			webDriver=new ChromeDriver();
			
		  }
	  @Test
	  public void readGoldenRateTest() throws IOException {
			webDriver.get(goldenChennaiUrl);			
			webDriver.manage().window().maximize();
			WebDriverWait wait=new WebDriverWait(webDriver,10);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div/main/h1")));
			log.info(webDriver.findElement(By.xpath("/html/body/div/main/h1")).getText());		
		
			File file=new File(fileName);
			FileWriter fileWriter=null;
			CSVWriter csvWriter=null;
			String result=null;
			String[] data=null;
			StringBuffer buffer=new StringBuffer();
			try {
				fileWriter=new FileWriter(file);
				csvWriter=new CSVWriter(fileWriter);
				List<WebElement> rows=webDriver.findElements(By.xpath("//*[@id=\"zb\"]/table/tbody/tr"));
				 log.info("Row Count"+rows.size());
				 List<WebElement> cols=webDriver.findElements(By.xpath("//*[@id=\"zb\"]/table/tbody/tr[1]/td"));
				 log.info("Col Count"+cols.size());
					for(int i=1;i<=rows.size();i++) {
						for(int j=1;j<=cols.size();j++) {
							result=webDriver.findElement(By.xpath("//*[@id=\"zb\"]/table/tbody/tr["+i+"]/td["+j+"]")).getText();
					      log.info(result);
					      buffer.append(result);
						 					
						}
						
						log.info(buffer.toString());
						data=new String[] {buffer.toString()};
						csvWriter.writeNext(data);
						buffer=new StringBuffer();
						}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally {
				csvWriter.close();
			}
		 
			}
	  
	  @AfterTest
	  public void afterTest() {
		 webDriver.close();
	  }
}
