package runnerFiles;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;


@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"pretty","html:target/cucumber-reports","json:target/cucumber.json"},features = {
		"src/test/java/runnerFiles/login.feature"
		},glue="runnerFiles")
public class RunCucumberTest {

}

