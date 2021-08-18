package com.amex.jsontest;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.amex.csvfiles.models.User;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
@Slf4j
public class GoogleAccountTest {
	private WebDriver webDriver;
	private WebElement element;
	private String driverPath,accountUrl,fileName;
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
		accountUrl=resourceBundle.getString("accountUrl");
		fileName=resourceBundle.getString("excelFileName");
		System.setProperty("webdriver.chrome.driver",driverPath);
		//WebDriverManager.chromedriver().setup();
		webDriver=new ChromeDriver();			
	}
	
	@Test
	public void accountCreationTest() {		
		webDriver.get(accountUrl);
		webDriver.manage().window().maximize();
		WebDriverWait wait=new WebDriverWait(webDriver,10);
		//wait.until(ExpectedConditions.visibilityOfElementLocated();
		log.info(webDriver.getTitle());	
		JSONParser parser=new JSONParser();
		try(FileReader fileReader=new FileReader("users.json")){
			Object object=parser.parse(fileReader);
			JSONArray usersArrays=(JSONArray) object;
			List<String> usersList=new ArrayList<String>();
			for(Object data:usersArrays) {
				log.info(data.toString());
				usersList.add(data.toString());
			}
			usersList.stream().forEach(System.out::println);
			ObjectMapper mapper=new ObjectMapper();
			Map<String,Object> map=null;
			for(String user:usersList)
			{
			  map=mapper.readValue(user, Map.class);
			  log.info(map.values().toString());
			}
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

    @AfterTest
	public void afterTest() {
		webDriver.close();
	}

}
