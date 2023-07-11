package com.mobile.base;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.json.simple.JSONObject;

import com.mobile.utility.ConfigDataReader;
import com.mobile.utility.ExcelDataProvider;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class GetRequest extends TestBase
{
  	@SuppressWarnings({ "deprecation", "unchecked" })
	public void getRequest(String username, String password, String planid, String url, String expectedResult, int row, String moduleName) throws Exception
	{
  		//System.out.println("URL is**********. :"+url+"Expected result is.********* : "+expectedResult+"Row no is :*********"+row);
  		
  		System.out.println("Inside Get Request..........!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
			//Specify URL
			RestAssured.baseURI=ConfigDataReader.ConfigDataProvider();
			
			//Request Object
			RequestSpecification httpRequest = RestAssured.given();
			
			String token = TestBase.token(username, password);
		//	System.out.println("Token is :::::::::::::::::::::::::"+token);
			
			httpRequest.param("isItFirstLoad", "false");
			
			httpRequest.queryParam("PlanId", planid);
			
			httpRequest.header("Content-Type", "application/json");
			httpRequest.header("uid", "CINEEIIIC");
			httpRequest.header("sofwareapp", "false");
			httpRequest.header("storedotp", "true");
			httpRequest.header("Authorization", token);
			
			Response response = httpRequest.request(Method.GET, url);
			
			int statusCode = response.getStatusCode();
			System.out.println("Status Code of Response is "+statusCode);
							
			String responseBody = response.getBody().asString();
			System.out.println("Response body is : "+ responseBody);
			
			String date,result = null;
			
			if(responseBody.contains("BalAsOfDate"))
			{
				JsonPath jsonPathEvaluator = response.jsonPath();
				date = jsonPathEvaluator.get("BalAsOfDate");
				System.out.println("Date received from Response " + date);

				result = responseBody.replace(date, "");
								
				System.out.println("Response body is : "+result);
			}
			
			
			
				if(expectedResult.contentEquals(responseBody))
				{
					//Code to write actual result in excel
					
					int col = 8;
					String result1 = "Pass";
					ExcelDataProvider.setTestData(row, col, responseBody, result1, moduleName);
					
					System.out.println("expected result is same as actual result");
				}
				else
				{
					int col = 8;
					String result1 = "Fail";
					ExcelDataProvider.setTestData(row, col, responseBody, result1, moduleName);
					
					System.out.println("expected result is not same as actual result");
				}
		}
}
