package com.amex.restassureddemo.weathertests;

import lombok.extern.slf4j.Slf4j;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

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

}
