package com.mobile.utility;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelDataProvider 
{
	static String filePath1;
	static String sheetName1;
	static FileInputStream file;
	static XSSFWorkbook wb;
	static XSSFSheet sheet;
	
	List<String> sheetNames;
	
	public static Object[][] getTestData(String filePath, String sheetName) throws Exception
	{
		filePath1=filePath;
		sheetName1=sheetName;
		
		file= new FileInputStream(filePath1);
        wb = new XSSFWorkbook(file);
        sheet = wb.getSheet(sheetName1);
        
        int lastRowNum = sheet.getLastRowNum() ;
        int lastCellNum = sheet.getRow(0).getLastCellNum();
        Object[][] obj = new Object[lastRowNum][1];

        for (int i = 0; i < lastRowNum; i++) 
        {
          Map<Object, Object> datamap = new HashMap<Object, Object>();
          for (int j = 0; j < lastCellNum; j++) 
          {
            datamap.put(sheet.getRow(0).getCell(j).toString(), sheet.getRow(i+1).getCell(j).toString());
          }
          obj[i][0] = datamap;
        }
        return  obj;
	}
	
	public List<String> getSheetName()
	{
		sheetNames = new ArrayList<String>();
		
		for (int i=0; i<wb.getNumberOfSheets(); i++) 
		{
		    sheetNames.add( wb.getSheetName(i) );
		}
		return sheetNames;
	}
	
	/*@SuppressWarnings("resource")
	public static Object[][] getTestData(String filePath, String sheetName) throws Exception
    {
		filePath1=filePath;
		sheetName1=sheetName;
		
		file= new FileInputStream(filePath1);
        wb = new XSSFWorkbook(file);
        sheet = wb.getSheet(sheetName1);
        
        int rowCount = sheet.getLastRowNum();
        int column = sheet.getRow(0).getLastCellNum();
        
        Object[][] data = new Object[rowCount][column];
        
        for (int i = 1; i <= rowCount; i++) 
        {
            XSSFRow row = sheet.getRow(i);
            for (int j = 0; j < column; j++) 
            {
                XSSFCell cell = row.getCell(j);
                DataFormatter formatter = new DataFormatter();
                String val = formatter.formatCellValue(cell);
                data[i - 1][j] = val;
            }
        }

        return data;
   }*/
	
	public static void setTestData(int row, int col, String actualResult, String result, String moduleName) throws Exception
	{
		file= new FileInputStream(filePath1);
        wb = new XSSFWorkbook(file);
        sheetName1=moduleName;
        sheet = wb.getSheet(sheetName1);
        
     //   int rowCount = sheet.getLastRowNum();
     //   int column = sheet.getRow(0).getLastCellNum();
        
        Cell cell = null; // declare a Cell object
        Cell cell1 = null;
        
        System.out.println("**************************************Inside set ****************");
        System.out.println("Row and column : "+row+" "+col);
       
        cell = sheet.getRow(row).getCell(col);   // Access the second cell in second row to update the value
          
        cell.setCellValue(actualResult);  // Get current cell value value and overwrite the value
        
        cell1 = sheet.getRow(row).getCell(col+1);   // Access the second cell in second row to update the value
        
        cell1.setCellValue(result);  // Get current cell value value and overwrite the value
          
        file.close(); //Close the InputStream
         
        FileOutputStream output_file =new FileOutputStream(filePath1);  //Open FileOutputStream to write updates
          
        wb.write(output_file); //write changes
          
        output_file.close();
     }
} 

