package com.amex.dbtests;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.amex.csvfiles.models.User;

import lombok.extern.slf4j.Slf4j;
@Slf4j
public class NSEDBTest {
	private WebDriver webDriver;
	private WebElement element;
	private String driverPath,nseUrl,fileName;
	private   ResourceBundle resourceBundle;
    private Connection conn;     
	@BeforeTest
	public void beforeTest() {		
		resourceBundle=ResourceBundle.getBundle("googlesearch");	
		driverPath=resourceBundle.getString("webDriverPath");
		nseUrl=resourceBundle.getString("nseUrl");
		fileName=resourceBundle.getString("xmlFileName");
		try {
			conn=DBHelper.getConnection();
			if(conn!=null) {
				log.info("DB Connection ready....");
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			log.info(e.getMessage());
		}
		
	
		System.setProperty("webdriver.chrome.driver",driverPath);
		//WebDriverManager.chromedriver().setup();
		webDriver=new ChromeDriver();			
	}
	@Test
	public void nseReportTest() {
		webDriver.get(nseUrl);
		webDriver.manage().window().maximize();
		WebDriverWait wait=new WebDriverWait(webDriver,10);
		//wait.until(ExpectedConditions.visibilityOfElementLocated();
		log.info(webDriver.getTitle());
		String result=null;
		List<WebElement> rows=webDriver.findElements(By.xpath("//*[@id=\"tab1_tableGainer\"]/table/tbody/tr"));
		log.info("Rows"+rows.size());
		 List<WebElement> cols=webDriver.findElements(By.xpath("//*[@id=\"tab1_tableGainer\"]/table/tbody/tr[1]/td"));
		 log.info("Col Count"+cols.size());
		for(int i=1;i<=rows.size();i++) {
			for(int j=1;j<=cols.size();j++) {
				result=webDriver.findElement(By.xpath("//*[@id=\"tab1_tableGainer\"]/table/tbody/tr["+i+"]/td["+j+"]")).getText();
		      log.info(result);
		      
			   } 					
			}
			
			
	}
	@AfterTest
	public void afterTest() {
		webDriver.close();
	}
}
