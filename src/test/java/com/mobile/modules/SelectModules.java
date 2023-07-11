package com.mobile.modules;

import java.util.List;
import java.util.Map;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.mobile.base.GetRequest;
import com.mobile.base.TestBase;
import com.mobile.utility.ConfigDataReader;
import com.mobile.utility.ExcelDataProvider;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class SelectModules
{
	GetRequest getRqst = new GetRequest();
	ExcelDataProvider excelDataProvider = new ExcelDataProvider();
	VerifyGetOrPostRequest verifyGetOrPostRequest = new VerifyGetOrPostRequest();
	
	String filePath = System.getProperty("user.dir") + "\\TestData\\API_Automation_Test_Data.xlsx";
	String sheetName = "MainSheet";
	String moduleName=null;
	
	int row=0;	
	
	@DataProvider(name="userData")
    public Object[][] getTestData() throws Exception
    {
        Object[][] data = ExcelDataProvider.getTestData(filePath, sheetName);
        return data;
    }
	
	@Test(dataProvider="userData")
	public void verifyMainSheet(Map<Object, Object> map) throws Exception
	{
		if(((String) map.get("ExecuteTC")).contentEquals("Yes"))
		{
			moduleName = (String) map.get("ModuleName");
			System.out.println("Module name is : "+moduleName);
			
			List<String> sheetName = excelDataProvider.getSheetName();
			
		//	System.out.println("List of SheetName is : "+sheetName);
			
			if(sheetName.contains(moduleName))
			{
				//System.out.println("Found Module name in sheet name");
				
				if(((String) map.get("Method")).equalsIgnoreCase("GET"))
				{
			//		System.out.println("Execute GET Method");
		
					verifyGetOrPostRequest.getRequest(filePath, moduleName, map);
	
				}
				else if (((String) map.get("Method")).equalsIgnoreCase("POST"))
				{
			//		System.out.println("Execute POST Method");
					verifyGetOrPostRequest.postRequest(filePath, moduleName, map);
				}
				else
				{
					System.out.println("No code written for this method");
				}
			}
			else
			{
				System.out.println("Module name not found in sheet name");
			}
			
		}
		else if(((String) map.get("ExecuteTC")).contentEquals("No"))
		{
			System.out.println("No need to run "+(String) map.get("ModuleName")+" module");
		}
		else
		{
			System.out.println("expected result is not same as actual result");
		}
	}
}
