package runner;

import java.io.File;

import org.junit.runner.RunWith;

import com.vimalselvam.cucumber.listener.Reporter;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"src/test/java/features"},
        tags = "~@Ignore",
        glue = {"stepDefinition"},
        plugin = {"pretty","html:target/cucumber-reports", "json:target/cucumber.json",
        			"com.vimalselvam.cucumber.listener.ExtentCucumberFormatter:target/cucumber-reports/report.html"},
        monochrome = true, //display the console output in a proper readable format
        strict = true, //it will check if any step is not defined in step definition file
        dryRun = false) //to check the mapping is proper between feature file and step def file)
public class TestRunner {
	public static void writeExtentReport() {
        Reporter.loadXMLConfig(new File("config/cucumberReports.xml"));
  }
}
