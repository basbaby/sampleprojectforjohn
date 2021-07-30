Feature: Testing the API's

Scenario: Publish Message to Token
Given I create a new request with https://localhost:8082/apix-kafka-sapi/v1/api/ service
And I add the kafka-api/unit/publish endpoint to the service
And  I pass application/json as content type
And  I pass correlation_id as x-correlation-id
And I send the values of src/test/resources/cucumberResources/publishMessageInput.json in the request body
And I send the POST request to the service
Then I get the 200 response code
Then I expect the values of src/test/resources/cucumberResources/publisheMessageOutput.json in the response body

Scenario: Consume Message from Token
Given I create a new request with https://localhost:8082/apix-kafka-sapi/v1/api/ service
And I add the kafka-api/unit/consume endpoint to the service
And  I pass correlation_id as x-correlation-id
And I send the GET request to the service
Then I get the 200 response code
