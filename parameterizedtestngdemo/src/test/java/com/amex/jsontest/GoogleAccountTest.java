package com.amex.jsontest;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.amex.csvfiles.models.GoogleUser;
import com.amex.csvfiles.models.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

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
		GoogleUser user=null;
		Gson gson=new Gson();
		for(Object data:userDataProvider().values()) {
			//log.info(data.toString());
			user=gson.fromJson(data.toString(), GoogleUser.class);
			log.info(user.getFirstName());
			webDriver.findElement(By.name("firstName")).sendKeys(user.getFirstName());
			webDriver.findElement(By.name("lastName")).sendKeys(user.getFirstName());
			webDriver.findElement(By.name("Username")).sendKeys(user.getUserName());
			webDriver.findElement(By.name("Passwd")).sendKeys(user.getPassword());
			webDriver.findElement(By.name("ConfirmPasswd")).sendKeys(user.getConfirmPassword());
			webDriver.findElement(By.id("i3")).click();
			webDriver.get(accountUrl);
			webDriver.manage().window().maximize();
			wait=new WebDriverWait(webDriver,10);
		}
	}
	
	//@DataProvider(name="userData")
	public Map<String,Object> userDataProvider() {
		JSONParser parser=new JSONParser();
		//Object[][] obj=null;
		Map<String,Object> map=null;
		try(FileReader fileReader=new FileReader("users.json")){
			Object object=parser.parse(fileReader);
			JSONArray usersArrays=(JSONArray) object;
			List<String> usersList=new ArrayList<String>();
			for(Object data:usersArrays) {
				//log.info(data.toString());
				usersList.add(data.toString());
			}
			usersList.stream().forEach(System.out::println);
			ObjectMapper mapper=new ObjectMapper();
			//log.info(""+usersList.size());
			
			//obj = new Object[usersList.size()][2];
			int i=0;
			map=new HashMap<String,Object>();
			for(String user:usersList)
			{
			  map.put(String.valueOf(i),mapper.readValue(user, Map.class));
			//  obj[i][1]=map;
			 // log.info(map.values().toString());
			  i++;
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
		return map;
	}

    @AfterTest
	public void afterTest() {
		webDriver.close();
	}

}
