package com.amex.csvfiles.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
	private String firstName;
	private String lastName;
	private String phone;
	private String email;
	private String address;
	private String city;
	private String state;
	private String postalCode;
	private String country;
	private String userName;
	private String password;
	private String confirmPassword;
	
	

}
