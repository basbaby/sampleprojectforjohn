package runner;

import java.io.IOException;
import java.util.Map;

import org.hamcrest.Matcher;
import org.json.simple.JSONObject;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import com.fasterxml.jackson.databind.ObjectMapper;


import io.restassured.RestAssured;
import io.restassured.matcher.ResponseAwareMatcher;
import io.restassured.response.ResponseBody;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;


import utils.HelperUtil;

public class Driver {
	private String uri;
	private String contentType;
	private String body;
	private ValidatableResponse response;
	private String correlationId;
	
	private static final String RANDOM = "RANDOM";
	private static final String GET = "GET";
	private static final String POST = "POST";
	
	public void createURI(String serviceName) {
		this.uri = serviceName;
	}
	
	public void addEndPoint(String endpoint) {
		this.uri = uri.concat(endpoint);
	}
	
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	
	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}
	
	public void setRequestBody(String filePath) throws IOException{
		
		String jsonString = HelperUtil.getJsonStringFromPath(filePath);
		
		if(jsonString != null && jsonString.contains(RANDOM)) {
			jsonString = getManipulatedString(jsonString);
		}
		this.body = jsonString;
	}
	
	@SuppressWarnings("unchecked")
	private String getManipulatedString(String jsonString) throws IOException {
		JSONObject jsonObject = new JSONObject();
		ObjectMapper objectmapper = new ObjectMapper();
		
		Map<String, String> map = objectmapper.readValue(jsonString, Map.class);
		for(Map.Entry<String, String> entrySet: map.entrySet()) {
			String value = entrySet.getValue();
			if(value.contains(RANDOM)) {
				entrySet.setValue(HelperUtil.getRandomValue(value));
			}
			jsonObject.put(entrySet.getKey(), entrySet.getValue());
		}
		return jsonObject.toJSONString();
	}
	
	public void sendRequest(String requestMethod) {
		if(GET.equalsIgnoreCase(requestMethod)) {
			RequestSpecification request = RestAssured.given();
			/*request.header("client_id","498af4776f5b4728abb18033899bada3")
			       .header("client_secret","7549FF1A280d414abcf48dfB7C4E6321");*/
			request.header("x-correlation-id",this.correlationId);
			
			response = request.given().and().get(uri).then();
			
			//response = given().and().get(uri).then();
		}else if(POST.equalsIgnoreCase(requestMethod)) {
			 RequestSpecification request = RestAssured.given();
				request.header("Content-Type", this.contentType)
				       .header("x-correlation-id",this.correlationId);
				request.body(this.body);
		 		
			 response =	request.post(uri).then();
		 }
	}
	
	public void expectedResponse(int responseCode) {
		response.statusCode(responseCode);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void validateResponse(String filePath) throws IOException {
		ObjectMapper objectmapper = new ObjectMapper();
		Map<String, String> map = objectmapper.readValue(HelperUtil.getJsonStringFromPath(filePath), Map.class);
				
		for(Map.Entry<String, String> entrySet: map.entrySet()) {
			String key = entrySet.getKey();
			final String value = entrySet.getValue();
			response.body(key, new ResponseAwareMatcher() {
	             public Matcher matcher(ResponseBody response) throws Exception {
					return equalTo(value);
				}
	        });
		}
	}
	
}
