package com.amex.restassureddemo.weathertests;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFRow.CellIterator;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import junit.framework.Assert;
import lombok.extern.slf4j.Slf4j;
@Slf4j
public class WeatherTest {
	
	private FileInputStream fin;
	private Workbook workBook;
	private Sheet sheet;
	private Iterator<Row> itr;
	private Iterator<Cell> cellItr;
	private Row row;
	private Cell cell;
	
	@BeforeTest
	public void beforeTest() {
		RestAssured.baseURI="https://goweather.herokuapp.com/weather/";
	}
	
	@Test(dataProvider = "cityDataProvider")
	public void responseTest(String v1) {
		log.info(v1);
		RequestSpecification httpRequest=RestAssured.given();
		Response response=httpRequest.request(Method.GET,v1.toString());		
		log.info(response.getBody().asString());
		Assert.assertTrue(response.getBody().asString().contains("temperature"));
	}


	@Test(dataProvider = "cityDataProvider")
	public void statusCodeTest(String v1) {		
		RequestSpecification httpRequest=RestAssured.given();
		Response response=httpRequest.request(Method.GET,v1);		
		Assert.assertEquals(200, response.getStatusCode()); 
	}
	
	//Assert.assertEquals(statusLine /*actual value*/, "HTTP/1.1 200 OK" /
	@Test(dataProvider = "cityDataProvider")
	public void statusLineTest(String v1) {		
		RequestSpecification httpRequest=RestAssured.given();
		Response response=httpRequest.request(Method.GET,v1);		
		Assert.assertEquals("HTTP/1.1 200 OK", response.getStatusLine()); 
	}
	
	@Test(dataProvider = "cityDataProvider")
	public void contentTypeTest(String v1) {		
		RequestSpecification httpRequest=RestAssured.given();
		Response response=httpRequest.request(Method.GET,v1);		
		//log.info(response.getHeader("Content-Type"));
		Assert.assertTrue(response.getHeader("Content-Type").contains("application/json")); 
	}
	
	
	@DataProvider(name="cityDataProvider")
	public Object[] getCityDataAsDataProvider(){
		String[] arrayExcelData = null;
		String cityData=null;
		try {
			fin=new FileInputStream("cities.xlsx");
			workBook=new XSSFWorkbook(fin);
			sheet=workBook.getSheetAt(0);
	        int totalNoOfCols =  sheet.getRow(0).getLastCellNum(); 
			int totalNoOfRows = sheet.getLastRowNum();
			log.info(totalNoOfCols+","+totalNoOfRows);
			
			arrayExcelData = new String[totalNoOfRows];
			//cityData=new ArrayList<String>();
			int i=0;
			int j=0;
			
			itr=sheet.iterator();
			
			itr.next();
			
		
			while(itr.hasNext()) {
				row=itr.next();					
				cellItr=row.cellIterator();
				while(cellItr.hasNext()) {
					cell=cellItr.next();					
					//log.info(cell.getStringCellValue());
					cityData=cell.getStringCellValue();
					
				}
				
				arrayExcelData[i]=cityData.toString();
				i++;
				cityData=null;
			}
			
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		Object[] arrayObject =arrayExcelData;
	return arrayObject ;
	
}

}
