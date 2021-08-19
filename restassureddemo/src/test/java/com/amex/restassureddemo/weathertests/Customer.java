package com.amex.restassureddemo.weathertests;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
	private long customerId;
	private long accountNo;
	private String password;
}
