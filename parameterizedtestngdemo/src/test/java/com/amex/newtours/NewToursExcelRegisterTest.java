package com.amex.newtours;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.amex.csvfiles.models.User;

import lombok.extern.slf4j.Slf4j;
@Slf4j
public class NewToursExcelRegisterTest {
	private WebDriver webDriver;
	private WebElement element;
	private String driverPath,newToursUrl,fileName;
	private   ResourceBundle resourceBundle;
	private Workbook workBook;
	private FileInputStream fin;
	private Sheet sheet;
	private Iterator<Row> itr;
	private Iterator<Cell> cellItr;
	private Row row;
	private Cell cell;
	private Hashtable<Integer,User> userData;
	@BeforeTest
	public void beforeTest() {
		
		resourceBundle=ResourceBundle.getBundle("googlesearch");	
	
		driverPath=resourceBundle.getString("webDriverPath");
		newToursUrl=resourceBundle.getString("newToursUrl");
		fileName=resourceBundle.getString("excelFileName");
		System.setProperty("webdriver.chrome.driver",driverPath);
		//WebDriverManager.chromedriver().setup();
		webDriver=new ChromeDriver();
		
		
	}
	
	@Test
	public void testRegister() {
		webDriver.get(newToursUrl);
		webDriver.manage().window().maximize();
		WebDriverWait wait=new WebDriverWait(webDriver,10);
		//wait.until(ExpectedConditions.visibilityOfElementLocated();
		log.info(webDriver.getTitle());	
		Hashtable<Integer,User> testData=getCustomerData();
		webDriver.findElement(By.partialLinkText("Register")).click();
		for(User user:testData.values()) {
			log.info(user.toString());
			webDriver.findElement(By.name("firstName")).sendKeys(user.getFirstName());
			webDriver.findElement(By.name("lastName")).sendKeys(user.getLastName());
			webDriver.findElement(By.name("phone")).sendKeys(user.getPhone());
			webDriver.findElement(By.name("firstName")).sendKeys(user.getFirstName());
			
		}
		
		
		
	}
	public Hashtable<Integer,User> getCustomerData(){
		User user=null;
		List<String> rowData=new ArrayList<String>();
		userData=new Hashtable<Integer,User>();
		int count=1;
		try {
			fin=new FileInputStream(fileName);
			workBook=new XSSFWorkbook(fin);
			sheet=workBook.getSheetAt(0);
			itr=sheet.iterator();
			itr.next();
			while(itr.hasNext()) {
				row=itr.next();
				user=new User();
				rowData=new ArrayList<String>();
				cellItr=row.cellIterator();
				while(cellItr.hasNext()) {
					cell=cellItr.next();					
					//log.info(cell.getStringCellValue());
					rowData.add(cell.getStringCellValue());
				}
				user.setFirstName(rowData.get(0));
				user.setLastName(rowData.get(1));
				user.setPhone(rowData.get(2));
				user.setEmail(rowData.get(3));
				user.setAddress(rowData.get(4));
				user.setCity(rowData.get(5));
				user.setState(rowData.get(6));
				user.setPostalCode(rowData.get(7));
				user.setCountry(rowData.get(8));
				user.setUserName(rowData.get(9));
				user.setPassword(rowData.get(10));
				user.setConfirmPassword(rowData.get(11));
				userData.put(count, user);
				count++;
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return userData;
	}
	
    @AfterTest
	public void afterTest() {
		webDriver.close();
	}
}
