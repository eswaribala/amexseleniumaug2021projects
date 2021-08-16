package com.amex.utility;

import java.util.ResourceBundle;

public class PhoneSearchTest {	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		ResourceBundle resourceBundle=ResourceBundle.getBundle("canadasearch");

		System.out.println(resourceBundle.getString("webDriverPath"));
		
	}

}
