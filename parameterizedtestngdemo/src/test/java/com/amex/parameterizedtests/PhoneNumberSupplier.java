package com.amex.parameterizedtests;

import org.testng.annotations.DataProvider;

public class PhoneNumberSupplier {

	@DataProvider(name="phoneNumbersExternal")
    public Object[][] getDataFromDataprovider(){
		/*
		HashMap<String,String> data=new HashMap<String,String>();
		data.put("Golf Town", "905-841-0191" );
		data.put("647-846-8449", "647-846-8449");
		data.put("Royal Building Supplies Ltd", "416-244-2644" );
		
		return data.entrySet().iterator();
    */
		return new Object[][] 
    	{
            { "Golf Town", "905-841-0191" },
            { "647-846-8449", "647-846-8449" },
            { "Royal Building Supplies Ltd", "416-244-2644" }
        };
        
    }
}
