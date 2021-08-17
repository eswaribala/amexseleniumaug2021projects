package com.amex.newtours;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
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
	@BeforeTest
	public void beforeTest() {
		
		resourceBundle=ResourceBundle.getBundle("googlesearch");		
		
		driverPath=resourceBundle.getString("webDriverPath");
		newToursUrl=resourceBundle.getString("newToursUrl");
		fileName=resourceBundle.getString("excelFileName");
		System.setProperty("webdriver.chrome.driver",driverPath);
		//WebDriverManager.chromedriver().setup();
		webDriver=new ChromeDriver();
		getCustomerData();
		
	}
	
	@Test
	public void testRegister() {
		webDriver.get(newToursUrl);
		webDriver.manage().window().maximize();
		WebDriverWait wait=new WebDriverWait(webDriver,10);
		//wait.until(ExpectedConditions.visibilityOfElementLocated();
		log.info(webDriver.getTitle());		
	}
	public void getCustomerData(){
		
		try {
			fin=new FileInputStream(fileName);
			workBook=new XSSFWorkbook(fin);
			sheet=workBook.getSheetAt(0);
			itr=sheet.iterator();
			itr.next();
			while(itr.hasNext()) {
				row=itr.next();
				cellItr=row.cellIterator();
				while(cellItr.hasNext()) {
					cell=cellItr.next();
					 if(cell.getCellType()==CellType.STRING)
					 {
						 log.info(cell.getStringCellValue());
					 }
				}
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
    @AfterTest
	public void afterTest() {
		webDriver.close();
	}
}
