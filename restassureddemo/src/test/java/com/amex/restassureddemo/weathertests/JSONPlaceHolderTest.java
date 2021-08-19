package com.amex.restassureddemo.weathertests;

import lombok.extern.slf4j.Slf4j;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

@Slf4j
public class JSONPlaceHolderTest {
	private FileInputStream fin;
	private Workbook workBook;
	private Sheet sheet;
	private Iterator<Row> itr;
	private Iterator<Cell> cellItr;
	private Row row;
	private Cell cell;
	@Test
	public void getUsersTest() {
		//chain of method calls
		log.info(given().get("https://jsonplaceholder.typicode.com/users")
				.body().asString());
	    
	}

	@Test(dataProvider = "userDataProvider")
	public void getUserByPathParamTest(String v1) {
		log.info(String.valueOf(v1));
		//chain of method calls
		log.info(given().pathParam("id",v1).when()				
				.get("https://jsonplaceholder.typicode.com/users/{id}.json")
				.body().asString());
	    
	}
	
	
	@Test(dataProvider = "customerDataProvider")
	public void getCustomerByQueryParamTest(Object v1) {
		Customer customer=null;
		Gson gson=new Gson();
		//for(Object data:userDataProvider().values()) {
			//log.info(data.toString());
			customer=gson.fromJson(v1.toString(),Customer.class);
		//chain of method calls
		log.info(given().queryParam("CUSTOMER_ID", customer.getCustomerId())
				.queryParam("PASSWORD", customer.getPassword())
				.queryParam("Account_No",customer.getAccountNo())
				.when()				
				.get("http://demo.guru99.com/V4/sinkministatement.php")
				.body().asString());
	    
	}
	
	@DataProvider(name="userDataProvider")
	public Object[] getUserDataAsDataProvider(){
		String[] arrayExcelData = null;
		double userData=0;
		try {
			fin=new FileInputStream("users.xlsx");
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
					if(cell.getCellType().equals(CellType.NUMERIC))
					   userData=cell.getNumericCellValue();
					
				}
				
				arrayExcelData[i]=String.valueOf(userData);
				i++;
				userData=0;
			}
			
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	
	return arrayExcelData ;
	
}
	
	@DataProvider(name="customerDataProvider")
	public Object[] customerDataProvider() {
		JSONParser parser=new JSONParser();
		Object[] obj=null;
		Map<String,Object> map=null;
		try(FileReader fileReader=new FileReader("customerdata.json")){
			Object object=parser.parse(fileReader);
			JSONArray customersArrays=(JSONArray) object;
			List<String> customersList=new ArrayList<String>();
			for(Object data:customersArrays) {
				//log.info(data.toString());
				customersList.add(data.toString());
			}
			customersList.stream().forEach(System.out::println);
			ObjectMapper mapper=new ObjectMapper();
			//log.info(""+usersList.size());
			
			obj = new Object[customersList.size()];
			int i=0;
			map=new HashMap<String,Object>();
			for(String customer:customersList)
			{
			  map.put(String.valueOf(i),mapper.readValue(customer, Map.class));
			  obj[i]=customer;
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
		return obj;
	}
	

}
