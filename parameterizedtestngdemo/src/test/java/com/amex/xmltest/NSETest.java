package com.amex.xmltest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.ResourceBundle;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

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
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.amex.csvfiles.models.User;

import lombok.extern.slf4j.Slf4j;
@Slf4j
public class NSETest {
	private WebDriver webDriver;
	private WebElement element;
	private String driverPath,nseUrl,fileName;
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
		nseUrl=resourceBundle.getString("baseUrl");
		fileName=resourceBundle.getString("xmlFileName");
		System.setProperty("webdriver.chrome.driver",driverPath);
		//WebDriverManager.chromedriver().setup();
		webDriver=new ChromeDriver();			
	}
	@Test(dataProvider = "companyData")
	public void nseReportTest( String v1,String v2) {
		webDriver.get(nseUrl);
		webDriver.manage().window().maximize();
		WebDriverWait wait=new WebDriverWait(webDriver,10);
		//wait.until(ExpectedConditions.visibilityOfElementLocated();
		log.info(webDriver.getTitle());
		log.info(v2);
		webDriver.findElement(By.name("q")).sendKeys(v2);
	}
	
	@DataProvider(name="companyData")
	public Object[][] readXMLData() {
		 // Instantiate the Factory
	      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	      String[][] companyList=null;
	      Object[][] compList=null;
	      try {

	          // optional, but recommended
	          // process XML securely, avoid attacks like XML External Entities (XXE)
	          dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);

	          // parse XML file
	          DocumentBuilder db = dbf.newDocumentBuilder();

	          Document doc = db.parse(new File(fileName));

	           doc.getDocumentElement().normalize();

	         

	          // get <staff>
	          NodeList list = doc.getElementsByTagName("company");

	          companyList=new String[list.getLength()][2];
	          for (int temp = 0; temp < list.getLength(); temp++) {

	              Node node = list.item(temp);

	              if (node.getNodeType() == Node.ELEMENT_NODE) {

	                  Element element = (Element) node;	                 

	                  // get text
	                  String sno = element.getElementsByTagName("sno").item(0).getTextContent();
	                  String symbol = element.getElementsByTagName("symbol").item(0).getTextContent();
	                  String name = element.getElementsByTagName("name").item(0).getTextContent();

	                  
	                  
	                  log.info("Company Id : " +sno);
	                 log.info("Symbol : " +symbol);
	                  log.info("Name : " +name);
	                  companyList[temp][1]=name;
	                  
	              }
	          }

	      } catch (ParserConfigurationException | SAXException | IOException e) {
	          e.printStackTrace();
	      }
	      compList=companyList;
	      return compList;
	}
	@AfterTest
	public void afterTest() {
		webDriver.close();
	}
}
