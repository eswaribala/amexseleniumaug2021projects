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
import org.testng.annotations.DataProvider;
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
	/*
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
			webDriver.findElement(By.name("userName")).sendKeys(user.getEmail());
			webDriver.findElement(By.name("address1")).sendKeys(user.getAddress());
			webDriver.findElement(By.name("city")).sendKeys(user.getCity());
			webDriver.findElement(By.name("state")).sendKeys(user.getState());
			webDriver.findElement(By.name("postalCode")).sendKeys(user.getPostalCode());
			webDriver.findElement(By.name("country")).sendKeys(user.getCountry());
			webDriver.findElement(By.name("email")).sendKeys(user.getUserName());
			webDriver.findElement(By.name("password")).sendKeys(user.getPassword());
			webDriver.findElement(By.name("confirmPassword")).sendKeys(user.getConfirmPassword());
			webDriver.findElement(By.name("submit")).click();
			wait=new WebDriverWait(webDriver,10);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[2]/table/tbody/tr/td[2]/table/tbody/tr[4]/td/table/tbody/tr/td[2]/table/tbody/tr[3]/td/p[1]/font/b")));
			log.info(webDriver.findElement(By.xpath("/html/body/div[2]/table/tbody/tr/td[2]/table/tbody/tr[4]/td/table/tbody/tr/td[2]/table/tbody/tr[3]/td/p[1]/font/b")).getText());
			webDriver.get(newToursUrl);
			webDriver.findElement(By.partialLinkText("Register")).click();
		}
		
		
		
	}
	
	*/
	@Test(dataProvider = "customerDataProvider")
	public void testDataProviderRegister(String v1, String v2) {
		//webDriver.get(newToursUrl);
		webDriver.manage().window().maximize();
		WebDriverWait wait=new WebDriverWait(webDriver,10);
		//wait.until(ExpectedConditions.visibilityOfElementLocated();
		//log.info(webDriver.getTitle());	
		log.info(v1+","+v2);
		//getCustomerDataAsDataProvider();
		
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
		
	@DataProvider(name="customerDataProvider")
	public Object[][] getCustomerDataAsDataProvider(){
		String[][] arrayExcelData = null;
		try {
			fin=new FileInputStream("customerdata - v1.xlsx");
			workBook=new XSSFWorkbook(fin);
			sheet=workBook.getSheetAt(1);
	        int totalNoOfCols =  sheet.getRow(0).getLastCellNum(); 
			int totalNoOfRows = sheet.getLastRowNum();
			log.info(totalNoOfCols+","+totalNoOfRows);
			
			arrayExcelData = new String[totalNoOfRows+1][totalNoOfCols];
			
			int i=0;
			int j=0;
			
			itr=sheet.iterator();
			
			//itr.next();
			
		
			while(itr.hasNext()) {
				row=itr.next();					
				cellItr=row.cellIterator();
				while(cellItr.hasNext()) {
					cell=cellItr.next();					
					log.info(cell.getStringCellValue());
					arrayExcelData[i][j] = cell.getStringCellValue();
					j++;
				}
				
				j=0;
				i++;
				
			}
			
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		Object[][] arrayObject =arrayExcelData;
	return arrayObject ;
	
}
    @AfterTest
	public void afterTest() {
		webDriver.close();
	}
}
