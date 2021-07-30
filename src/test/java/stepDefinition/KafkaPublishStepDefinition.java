package stepDefinition;

import runner.Driver;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/*import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;*/

public class KafkaPublishStepDefinition {
	
	private Driver driver = new Driver();
	
	@Given("I create a new request with (.*) service$")
	public void createNewRequest(String serviceName) {
		
	    driver.createURI(serviceName);
	}

	@Given("I add the (.*) endpoint to the service$")
	public void addEndPoint(String endpoint) {
		
	   driver.addEndPoint(endpoint);
	}
	
	@When("^I pass (.*) as content type$")
    public void setContentType(String contentType) throws Throwable {
        driver.setContentType(contentType);
    }
	
	@When("^I pass (.*) as x-correlation-id$")
    public void setCorrelationId(String correlationId) throws Throwable {
        driver.setCorrelationId(correlationId);
    }
	
	@And("I send the values of (.*) in the request body$")
	public void setRequestBody(String filePath) throws Throwable{
	    
		driver.setRequestBody(filePath);
	}
	
	@When("^I send the (.*) request to the service$")
    public void sendRequest(String method) throws Throwable {
		System.out.println("Checking..."+ method);
        driver.sendRequest(method);
    }
	
	@Then("I get the (\\d+) response code")
	public void responseCodeValidation(int responseCode) throws Throwable {
    	driver.expectedResponse(responseCode);
    }
	
	@Then("^I expect the values of (.*) in the response body$")
    public void responseAttributeValidation(String filePath) throws Throwable {
        driver.validateResponse(filePath);
    }
}
