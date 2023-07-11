package com.mobile.modules;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.mobile.base.GetRequest;
import com.mobile.base.PostRequest;
import com.mobile.base.TestBase;
import com.mobile.utility.ConfigDataReader;
import com.mobile.utility.ExcelDataProvider;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class VerifyGetOrPostRequest extends TestBase
{
	GetRequest getRqst = new GetRequest();
	PostRequest postRqst = new PostRequest();
	
	public void getRequest(String filePath, String moduleName,Map<Object, Object> map) throws Exception
	{
		File file =    new File(filePath);
		
		FileInputStream fis = new FileInputStream(file);
		
		XSSFWorkbook wb=new XSSFWorkbook(fis);
		
		XSSFSheet sheet=wb.getSheet(moduleName);
		
		int lastRowNum = sheet.getLastRowNum() ;
        int lastCellNum = sheet.getRow(0).getLastCellNum();
        
        Object[][] obj = new Object[lastRowNum][1];
        
        Map<Object, Object> datamap = new HashMap<Object, Object>();
        
        String username = null;
        String password = null;
        String planid = null;
        String url = null;
        String expectedResult = null;
        int row=0;	

        for (int i = 0; i < lastRowNum; i++) 
        {
          row++;
         // System.out.println("Row No :"+row);
        	
          for (int j = 0; j < lastCellNum; j++) 
          {
            datamap.put(sheet.getRow(0).getCell(j).toString(), sheet.getRow(i+1).getCell(j).toString());
          }
          
          obj[i][0] = datamap;
         // System.out.println("Data is "+obj[i][0]);
          
          
          for(Map.Entry<Object, Object> data : datamap.entrySet())
          {
        	  //System.out.println(String.format("Key (name) is: %s, Value (age) is : %s", data.getKey(), data.getValue()));
        	  if(data.getKey().equals("URL"))
        	  {
        		  url = (String) data.getValue();
        		  System.out.println("URL is : "+url);
        	  }
        	  else if(data.getKey().equals("Username"))
        	  {
        		  username = (String) data.getValue();
        		  System.out.println("Username is : "+username);
        	  }
        	  else if(data.getKey().equals("Password"))
        	  {
        		  password = (String) data.getValue();
        		  System.out.println("Password is : "+password);
        	  }
        	  else if(data.getKey().equals("PlanID"))
        	  {
        		  planid = (String) data.getValue();
        		  System.out.println("PlanID is : "+planid);
        	  }
        	  else if(data.getKey().equals("ExpectedResult"))
        	  {
        		  expectedResult = (String) data.getValue();
        		  System.out.println("ExpectedResult is : "+expectedResult);
        	  }  
        	  else
        	  {
        		  System.out.println("No url found");
        	  }
         
          }
          
        //  System.out.println("URL is.......... :"+url+"Expected result is.......... : "+expectedResult+"Row no is :........."+row);
          
        getRqst.getRequest(username, password, planid, url, expectedResult, row, moduleName);
        
       }
       
        
		/*int rowCount=sheet.getLastRowNum()-sheet.getFirstRowNum();
		
		for(int i=0;i<=rowCount;i++){
            
            //get cell count in a row
            int cellcount=sheet.getRow(i).getLastCellNum();
            
            //iterate over each cell to print its value
            System.out.println("Row"+ i+" data is :");
            
            for(int j=0;j<cellcount;j++)
            {
                System.out.print(sheet.getRow(i).getCell(j).getStringCellValue() +",");
            }
            System.out.println();
        }*/
		
		
		/*
		
		System.out.println("Test case No is :"+moduleName.get("SSN"));
		System.out.println("Test case No is :"+moduleName.get("URL"));
		System.out.println("Username is :"+moduleName.get("Username"));
		System.out.println("Password is :"+moduleName.get("Password"));
		System.out.println("method is :"+moduleName.get("Method"));
		System.out.println("planID is :"+moduleName.get("PlanID"));
		System.out.println("scenario is :"+moduleName.get("Scenario"));
		System.out.println("expectedReult is :"+moduleName.get("ExpectedResult"));
		System.out.println("actualResult is :"+moduleName.get("ActualResult"));
		System.out.println("result is :"+moduleName.get("PassOrFail"));

		String expectedResult = (String) moduleName.get("ExpectedResult");
		String URL = (String) moduleName.get("URL");
		*/
		
		//row++;
		
		//System.out.println("Row No :"+row);
		
		//getRqst.getRequest(URL, expectedResult, row);

		}
	

	public void postRequest(String filePath, String moduleName,Map<Object, Object> map) throws Exception
	{
		File file =    new File(filePath);
		
		FileInputStream fis = new FileInputStream(file);
		
		XSSFWorkbook wb=new XSSFWorkbook(fis);
		
		XSSFSheet sheet=wb.getSheet(moduleName);
		
		int lastRowNum = sheet.getLastRowNum() ;
        int lastCellNum = sheet.getRow(0).getLastCellNum();
        
        Object[][] obj = new Object[lastRowNum][1];
        
        Map<Object, Object> datamap = new HashMap<Object, Object>();
        
        String username = null;
        String password = null;
        String planid = null;
        String url = null;
        String body = null;
        String expectedResult = null;
        int row=0;	

        for (int i = 0; i < lastRowNum; i++) 
        {
          row++;
          System.out.println("Row No :"+row);
        	
          for (int j = 0; j < lastCellNum; j++) 
          {
            datamap.put(sheet.getRow(0).getCell(j).toString(), sheet.getRow(i+1).getCell(j).toString());
          }
          
          obj[i][0] = datamap;
          System.out.println("Data is "+obj[i][0]);
          
          
          for(Map.Entry<Object, Object> data : datamap.entrySet())
          {
        	  //System.out.println(String.format("Key (name) is: %s, Value (age) is : %s", data.getKey(), data.getValue()));
        	  if(data.getKey().equals("URL"))
        	  {
        		  url = (String) data.getValue();
        		  System.out.println("URL is : "+url);
        	  }
        	  else if(data.getKey().equals("Username"))
        	  {
        		  username = (String) data.getValue();
        		  System.out.println("Username is : "+username);
        	  }
        	  else if(data.getKey().equals("Password"))
        	  {
        		  password = (String) data.getValue();
        		  System.out.println("Password is : "+password);
        	  }
        	  else if(data.getKey().equals("PlanID"))
        	  {
        		  planid = (String) data.getValue();
        		  System.out.println("PlanID is : "+planid);
        	  }
        	  else if(data.getKey().equals("Body"))
        	  {
        		  body = (String) data.getValue();
        		  System.out.println("Body is : "+body);
        	  }
        	  else if(data.getKey().equals("ExpectedResult"))
        	  {
        		  expectedResult = (String) data.getValue();
        		  System.out.println("ExpectedResult is : "+expectedResult);
        	  }  
        	  else
        	  {
        		  System.out.println("No url found");
        	  }
         
          }
          
          //System.out.println("URL is.......... :"+url+"Expected result is.......... : "+expectedResult+"Row no is :........."+row);
          
        postRqst.postRequest(username, password, planid, url, body, expectedResult, row, moduleName);
        
       }

	}
}