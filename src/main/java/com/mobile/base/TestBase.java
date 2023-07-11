package com.mobile.base;

import java.util.Map;

import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import com.mobile.utility.ConfigDataReader;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class TestBase 
{

	static String finalToken = null;
	
	@SuppressWarnings("unchecked")
	//@BeforeMethod
	@Test(dataProvider="userData")
	public static void loginAndGenerateToken(String username, String password)
	{
		//Specify URL
		RestAssured.baseURI=ConfigDataReader.ConfigDataProvider();
		
		//Request Object
		RequestSpecification httpRequest = RestAssured.given();
		
		//Response Object
		JSONObject requestParams = new JSONObject();
		requestParams.put("UserId", username);
		requestParams.put("Password", password);
		requestParams.put("DeviceId", "WEB.DEVICEID");
		
		httpRequest.header("Content-Type", "application/json");
		
		httpRequest.body(requestParams.toJSONString());
		
		Response response = httpRequest.request(Method.POST, "/rawebrestservice/mobile/login");
		
		String UserPrimaryId = response.jsonPath().get("UserPrimaryId");
		String Token = response.jsonPath().get("Token");
		String DeviceId = response.jsonPath().get("DeviceId");
		
		System.out.println("UserPrimaryId is "+UserPrimaryId+"\n Token"+Token+"\n DeviceId"+DeviceId);
		
		RequestSpecification httpRequest1 = RestAssured.given();
			
		httpRequest1.queryParam("userID", username);
			
		httpRequest1.header("x-ltoken", Token);
		httpRequest1.header("x-pid", UserPrimaryId);
		httpRequest1.header("x-did", DeviceId);
			
		Response response1 = httpRequest1.request(Method.GET, "rawebrestservice/Token");
			
		int statusCode = response1.getStatusCode();
		System.out.println("Status Code of Response is "+statusCode);
		
		String responseBody = response1.getBody().asString();
		System.out.println("Response body is : "+ responseBody);

		//Remove double quote
		String token1 = responseBody.substring(1, responseBody.length() - 1);
			
		//add word Bearer
		finalToken = "Bearer " + token1;  

	}
	public static String token()
	{	
		String token = finalToken;
		return token;
		
	}
	public static String token(String username, String passwrod)
	{	
	
		if(finalToken==null)
		{
			loginAndGenerateToken(username, passwrod);
		}
		return finalToken;
		
	}
}
