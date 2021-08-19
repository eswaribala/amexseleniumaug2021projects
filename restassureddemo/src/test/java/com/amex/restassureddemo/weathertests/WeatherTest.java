package com.amex.restassureddemo.weathertests;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;
@Slf4j
public class WeatherTest {
	
	@Test
	public void responseTest() {
		RestAssured.baseURI="https://goweather.herokuapp.com/weather/";
		RequestSpecification httpRequest=RestAssured.given();
		Response response=httpRequest.request(Method.GET,"/Chennai");
		
		log.info(response.getBody().asString());
	}

}
