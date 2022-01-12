package com.amex.karateapi.testscripts;
import org.junit.runner.RunWith;

import com.intuit.karate.junit4.Karate;

import io.cucumber.junit.CucumberOptions;




@RunWith(Karate.class)
@CucumberOptions(plugin = {"pretty","html:target/cucumber-reports","json:target/cucumber.json"},features = {
		"src/test/java/com/amex/karateapi/testscripts/userDetails.feature"
		},glue="com/amex/karateapi/testscripts")
public class TestRunner 
{
	
}