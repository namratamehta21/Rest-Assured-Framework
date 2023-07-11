package com.mobile.base;

import java.util.Map;

import org.apache.http.entity.StringEntity;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.mobile.utility.ConfigDataReader;
import com.mobile.utility.ExcelDataProvider;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class PostRequest extends TestBase
{
	public void postRequest(String username, String password, String planid, String url, String body, String expectedResult, int row, String moduleName) throws Exception
	{
		//Specify URL
		RestAssured.baseURI=ConfigDataReader.ConfigDataProvider();
		
		//Request Object
		RequestSpecification httpRequest = RestAssured.given();
		
		String token = TestBase.token(username, password);
		
		//httpRequest.param("isItFirstLoad", "false");
		
		//httpRequest.queryParam("PlanId", "MyRetWealth");
		
		httpRequest.header("Content-Type", "application/json");
		httpRequest.header("uid", "CKVPCKIII");
		httpRequest.header("sofwareapp", "false");
		httpRequest.header("storedotp", "true");
		httpRequest.header("Authorization", token);
				
		/*JSONParser jsonParser = new JSONParser();
		JSONObject obj = (JSONObject) jsonParser.parse(body);

		String reqJsonbody = obj.toString();
		
		System.out.println("shgfdshfjsd : "+reqJsonbody);
		 
		 */
		httpRequest.body(body);
		
		Response response = httpRequest.request(Method.POST, url);
		
		int statusCode = response.getStatusCode();
		System.out.println("Status Code of Response is "+statusCode);
						
		String responseBody = response.getBody().asString();
		System.out.println("Response body is : "+ responseBody);
		
			if(expectedResult.contentEquals(responseBody))
			{
				//Code to write actual result in excel
				
				int col = 9;
				String result1 = "Pass";
				ExcelDataProvider.setTestData(row, col, responseBody, result1, moduleName);
				
				System.out.println("expected result is same as actual result");
			}
			else
			{
				int col = 9;
				String result1 = "Fail";
				ExcelDataProvider.setTestData(row, col, responseBody, result1, moduleName);
				
				System.out.println("expected result is not same as actual result");
			}

	}
}
