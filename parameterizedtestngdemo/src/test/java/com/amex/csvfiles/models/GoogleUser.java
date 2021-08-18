package com.amex.csvfiles.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoogleUser {
	private String firstName;
	private String lastName;
	private String userName;
	private String password;
	private String confirmPassword;
	
	

}
