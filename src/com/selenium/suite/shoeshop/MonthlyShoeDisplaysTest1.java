package com.selenium.suite.shoeshop;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.selenium.suite.TestSuiteBase;
import com.selenium.util.TestUtil;

public class MonthlyShoeDisplaysTest1 extends TestSuiteBase{

	
public int count = -1;

public String browser = null;
	
	public String[] runModes=null;
	
	public boolean isPass = false;
	
	public boolean isSkip = false;
	
	public boolean isFailure = false;
	
	public boolean isTestSkip = false;
	
	public boolean isTestFailure= false;
	
	@Parameters({"browser","version","platform"})
	@BeforeTest
	public void verifyTestCaseRunmode(String browser,String version,String platform) throws IOException
	{
		initialize();
		
		this.browser = browser;
		
		if(!TestUtil.isTestCaseRunnable(suiteShoeShopXLS, "TestCases", "MonthlyShoeDisplaysTest_"+browser))
		{
			isTestSkip = true;
			throw new SkipException("Skipping the testcase execution as the runmode is set to N");
			
		}
		
		runModes = TestUtil.getDataSetRunmodes(suiteShoeShopXLS, "MonthlyShoeDisplaysTest_"+browser);
		
		System.out.println("browser:"+browser+",version:"+version+",platform:"+platform);
		
		openBrowser(browser,version,platform);
	}
	
	
	@Test (dataProvider="getTestData")
	public void testMontlyShoeDisplays(String monthName) throws IOException, InterruptedException
	{
	
		count++;
		
		isPass = false;
		
		isSkip = false;
		
		isFailure = false;
		
		if(runModes[count].equalsIgnoreCase("N"))
		{
			isSkip = true;
			throw new SkipException("Skipping the testcase execution for the dataset as the runmode is set to N-"+monthName);
		}
		try{
									
			driver.get(CONFIG.getProperty("testSiteURL"));
			
			Thread.sleep(1000);
			
			driver.findElement(By.linkText(monthName)).click();
			
			Thread.sleep(1000);
			
		    List<WebElement> listElms = driver.findElements(By.xpath(OR.getProperty("shoeList_xpath")));
		    
		    System.out.println("Number of shoes displayed:"+listElms.size());
		    
		    if(listElms.size() > 0)
		    {
			    for(int i=0;i<listElms.size();i++)
			    {
			    	
			    	String brand = driver.findElement(By.xpath(OR.getProperty("shoeList_xpath")+"["+(i+1)+"]"+"/div/table/tbody/tr[1]/td[2]")).getText();
			    	String name = driver.findElement(By.xpath(OR.getProperty("shoeList_xpath")+"["+(i+1)+"]"+"/div/table/tbody/tr[2]/td[2]")).getText();
			    	String price = driver.findElement(By.xpath(OR.getProperty("shoeList_xpath")+"["+(i+1)+"]"+"/div/table/tbody/tr[3]/td[2]")).getText();
			    	String desc = driver.findElement(By.xpath(OR.getProperty("shoeList_xpath")+"["+(i+1)+"]"+"/div/table/tbody/tr[4]/td[2]")).getText();
			    	String month = driver.findElement(By.xpath(OR.getProperty("shoeList_xpath")+"["+(i+1)+"]"+"/div/table/tbody/tr[5]/td[2]")).getText();
			    	
			    	System.out.println("brand:"+brand);
			    	
			    	System.out.println("name:"+name);
			    	
			    	System.out.println("price:"+ price);
			    	
			    	System.out.println("desc:"+desc);
			    	
			    	System.out.println("month:"+month);
			    	
			    				    	
			    	if(brand.length()<=0 || name.length()<=0 || price.length()<=0 || desc.length()<=0 || month.length()<=0)
			    	{
			    		isFailure=true;
			    		isTestFailure=true;
			    	}
			    	
			    	
			    	
			    	Assert.assertTrue(brand.length()>0, "There is no Brand of the shoe");
			    	
			    	
			    	Assert.assertTrue(name.length()>0,"There is no name of the shoe");
			    	
			    	
			    	Assert.assertTrue(price.length()>0,"There is no price of the shoe");
			    	
			    	
			    	Assert.assertTrue(desc.length()>0,"There is no description of the shoe");
			    	
			    	
			    	Assert.assertTrue(month.length()>0,"There is no release month of the shoe");
			    	
			    	
			    	WebElement imgTd = driver.findElement(By.xpath(OR.getProperty("shoeList_xpath")+"["+(i+1)+"]"+"/div/table/tbody/tr[6]/td[1]"));
			    	
			    	List<WebElement> imgElms = imgTd.findElements(By.tagName("img"));
			    	
			    	System.out.println("Image Elements:"+imgElms.size());
			    	
			    	Assert.assertTrue(imgElms.size()>0,"There is no Shoe Image");
			    	
			    	String imgSrc = imgElms.get(0).getAttribute("src");
			    	
			    	System.out.println("image source:"+imgSrc);
			    	
			    	if(imgSrc.length()<=0)
			    	{
			    		isFailure=true;
			    		isTestFailure=true;
			    	}
			    	
			    	Assert.assertTrue(imgSrc.length()>0, "Image src is null.No Image to display.");
			    	
			       	isPass = true;
			    }
			    	
		    }
		    else{
		    	
		        System.out.println("There are no records to display...");
		    	isPass = true;
		    }
		}catch(Exception ex)
		{
			isFailure = true;
			isTestFailure = true;
			Assert.fail("Shoes are not displayed properly for the test month-"+monthName+"->"+ex.getMessage());
		}
	}

	@AfterMethod
	public void updateDataSetStatus() throws IOException
	{
		System.out.println("isFailure:"+isFailure);
		if(isPass)
		{
			suiteShoeShopXLS.setCellData("MonthlyShoeDisplaysTest_"+browser, count+2, "Result", "Pass");
		}
	    if(isSkip){
			
			suiteShoeShopXLS.setCellData("MonthlyShoeDisplaysTest_"+browser, count+2, "Result", "Skipped");
		}
		if(isFailure){
			
			suiteShoeShopXLS.setCellData("MonthlyShoeDisplaysTest_"+browser, count+2, "Result", "Fail");
		}
		
		
	}
	
	@AfterTest
	public void updateTestStatus() throws IOException
	{
		
		int rows = suiteShoeShopXLS.getRowCount("TestCases");
		
		int row=-1;
		
		for(int rowNum=2;rowNum<=rows;rowNum++)
		{
			if(suiteShoeShopXLS.getCellData("TestCases", rowNum, 1).equalsIgnoreCase("MonthlyShoeDisplaysTest_"+browser))
			{
				row = rowNum;
				break;
			}
		}
		
		
		if(isTestSkip)
		{
			suiteShoeShopXLS.setCellData("TestCases", row, "Result", "Skipped");
		}
		else if(isTestFailure)
		{
			suiteShoeShopXLS.setCellData("TestCases", row, "Result", "Fail");
		}
		else{
			
			suiteShoeShopXLS.setCellData("TestCases", row, "Result", "Pass");
		}
		
		closeBrowser();
	}
	
	
	@DataProvider
	public Object[][] getTestData()
	{
		Object[][] data = TestUtil.getTestCaseData(suiteShoeShopXLS, "MonthlyShoeDisplaysTest_"+browser);
		
		return data;
	}
}

	