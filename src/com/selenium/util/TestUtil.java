package com.selenium.util;

public class TestUtil {
	
	
	public static boolean isTestSuiteRunnable(XLSReader suitexls,String sheetName,String suiteName)
	{
		boolean runnable = false;
		
		if(suitexls != null)
		{
			int rows = suitexls.getRowCount(sheetName);
			
			for(int row=2;row<=rows;row++)
			{
				if(suitexls.getCellData(sheetName, row, 1).equalsIgnoreCase(suiteName))
				{
					if(suitexls.getCellData(sheetName, row, "Runmode").equalsIgnoreCase("Y"))
					{
						runnable = true;
						
						break;
					}
				}
			}
		}
		
		return runnable;
	}

	
	public static boolean isTestCaseRunnable(XLSReader currentSuiteXLS,String sheetName,String testCaseName)
	{
		System.out.println("inside isTestCaseRunnable method");
		boolean runnable = false;
		
		if(currentSuiteXLS != null)
		{
			int rows = currentSuiteXLS.getRowCount(sheetName);
			
			System.out.println("rows:"+rows);
			
			for(int rowNum=2;rowNum<=rows;rowNum++)
			{
				if(currentSuiteXLS.getCellData(sheetName, rowNum, 1).equalsIgnoreCase(testCaseName))
				{
					if(currentSuiteXLS.getCellData(sheetName, rowNum, "Runmode").equalsIgnoreCase("Y"))
					{
						System.out.println("runmode is Y");
						runnable = true;
						
						break;
					}
				}
			}
		}
		
		return runnable;
	}
	
	
	public static String[] getDataSetRunmodes(XLSReader currentSuiteXLS,String sheetName)
	{
		String[] runmodes = null;
		
		if(currentSuiteXLS != null)
		{
			int rows = currentSuiteXLS.getRowCount(sheetName);
			
			runmodes = new String[rows-1];
			
			for(int rowNum=2;rowNum<=rows;rowNum++)
			{
				runmodes[rowNum-2]= currentSuiteXLS.getCellData(sheetName, rowNum, "Runmode");
			}
		}
		
		return runmodes;
	}
	
	
	
	public static Object[][] getTestCaseData(XLSReader currentSuiteXLS,String sheetName)
	{
		Object[][] data = null;
		
		if(currentSuiteXLS != null)
		{
			int rows = currentSuiteXLS.getRowCount(sheetName);
			
			int cols = currentSuiteXLS.getColumnCount(sheetName);
			
			System.out.println("Rows:"+rows+",Cols:"+cols);
			
			data = new Object[rows-1][cols-2];
			
			for(int rowNum=2; rowNum<=rows;rowNum++)
			{
				for(int colNum=1;colNum<=cols-2;colNum++)
				{
					data[rowNum-2][colNum-1]=currentSuiteXLS.getCellData(sheetName, rowNum, colNum);
				}
			}
		}
		
		
		return data;
	}
}
