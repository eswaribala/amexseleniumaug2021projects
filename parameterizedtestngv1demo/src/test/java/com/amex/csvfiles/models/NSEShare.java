package com.amex.csvfiles.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NSEShare {

	private String symbol;
	private String ltp;
	private float changePerc;
	private String volume;
}
