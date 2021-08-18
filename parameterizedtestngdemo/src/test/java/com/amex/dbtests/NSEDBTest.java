package com.amex.dbtests;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
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

import com.amex.csvfiles.models.NSEShare;
import com.amex.csvfiles.models.User;

import lombok.extern.slf4j.Slf4j;
@Slf4j
public class NSEDBTest {
	private WebDriver webDriver;
	private WebElement element;
	private String driverPath,nseUrl,fileName;
	private   ResourceBundle resourceBundle;
	private PreparedStatement pre;
    private Connection conn;     
	@BeforeTest
	public void beforeTest() {		
		resourceBundle=ResourceBundle.getBundle("googlesearch");	
		driverPath=resourceBundle.getString("webDriverPath");
		nseUrl=resourceBundle.getString("nseUrl");
		fileName=resourceBundle.getString("xmlFileName");
		
		
	
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
		 List<String> rowData=new ArrayList<String>();
		 List<NSEShare> nseList=new ArrayList<NSEShare>();
		 NSEShare nseShare=null;
		for(int i=1;i<=rows.size();i++) {
			for(int j=1;j<=cols.size();j++) {
				result=webDriver.findElement(By.xpath("//*[@id=\"tab1_tableGainer\"]/table/tbody/tr["+i+"]/td["+j+"]")).getText();
		     log.info(result);
		      rowData.add(result);
		      
			   } 
						
			  nseShare=new NSEShare();			
			  nseShare.setSymbol(rowData.get(0)+LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
			  nseShare.setLtp(rowData.get(1));
			  nseShare.setChangePerc(Float.parseFloat(rowData.get(2)));
			  nseShare.setVolume(rowData.get(3));
			  nseList.add(nseShare);
			  rowData=new ArrayList<String>();
			  
			}
		
		 nseList.stream().forEach(System.out::println);
		 storeData(nseList);
	}
	
	
	public void storeData(List<NSEShare> shareList) {
		
		ResourceBundle resourceBundle=ResourceBundle.getBundle("googlesearch");
		String query=resourceBundle.getString("gainerloserQuery");
		try {
			conn=DBHelper.getConnection();
			
			if(conn!=null) {
				log.info("DB Connection ready....");
				conn.setAutoCommit(false);
				pre=conn.prepareStatement(query);
				for(NSEShare nseShare:shareList) {
					pre.setString(1, nseShare.getSymbol());
					pre.setString(2, nseShare.getLtp());
					pre.setFloat(3, nseShare.getChangePerc());
					pre.setString(4, nseShare.getVolume());
					pre.addBatch();
				}
				int[] rows=pre.executeBatch();
				conn.commit();
				log.info("Rows Inserted",rows.length);
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			log.info(e.getMessage());
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}
	@AfterTest
	public void afterTest() {
		webDriver.close();
	}
}
