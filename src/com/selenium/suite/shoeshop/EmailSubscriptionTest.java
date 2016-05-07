
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

public class EmailSubscriptionTest extends TestSuiteBase{
	
	
public int count = -1;
	
	public String[] runModes=null;
	
	public boolean isPass = false;
	
	public boolean isSkip = false;
	
	public boolean isFailure = false;
	
	public boolean isTestSkip = false;
	
	public boolean isTestFailure= false;
	
	@BeforeTest
	public void verifyTestCaseRunmode() throws IOException
	{
		initialize();
		
		if(!TestUtil.isTestCaseRunnable(suiteShoeShopXLS, "TestCases", "EmailSubscriptionTest"))
		{
			isTestSkip = true;
			throw new SkipException("Skipping the testcase execution as the runmode is set to N");
			
		}
		
		runModes = TestUtil.getDataSetRunmodes(suiteShoeShopXLS, "EmailSubscriptionTest");
	}
	
	
	@Test (dataProvider="getTestData")
	public void testEmailSubscription(String email,String browser,String version, String platform) throws IOException, InterruptedException
	{
	
		count++;
		
		isPass = false;
		
		isSkip = false;
		
		isFailure = false;
		System.out.println("browser:"+browser+",version:"+version+",platform:"+platform);
		
		if(runModes[count].equalsIgnoreCase("N"))
		{
			isSkip = true;
			throw new SkipException("Skipping the testcase execution for the dataset as the runmode is set to N-"+email);
		}
		try{
			openBrowser(browser,version,platform);
						
			driver.get(CONFIG.getProperty("testSiteURL"));
			
			Thread.sleep(1000);
			
			driver.findElement(By.xpath(OR.getProperty("remind_email_input_xpath"))).sendKeys(email);
			
			driver.findElement(By.xpath(OR.getProperty("remind_email_submit_xpath"))).click();
			
			List<WebElement> successDivElms = driver.findElements(By.xpath(OR.getProperty("subscription_success_div_xpath")));
			
			if(successDivElms.size()<=0)
			{
				isFailure=true;
				isTestFailure=true;
			}
			
			Assert.assertTrue(successDivElms.size()>0,"Email Subscription is not successful");
			
			if(!successDivElms.get(0).getText().equalsIgnoreCase(OR.getProperty("subscription_successs_message")+" "+email))
			{
				isFailure = true;
				isTestFailure = true;
			}
			
			Assert.assertEquals(successDivElms.get(0).getText(), OR.getProperty("subscription_successs_message")+" "+email);
			
			isPass= true;
			
		}catch(Exception ex)
		{
			isFailure = true;
			isTestFailure = true;
			Assert.fail("Email Subscription is not successful for the test email-"+email+"->"+ex.getMessage());
		}
	}

	@AfterMethod
	public void updateDataSetStatus() throws IOException
	{
		System.out.println("isFailure:"+isFailure);
		if(isPass)
		{
			suiteShoeShopXLS.setCellData("EmailSubscriptionTest", count+2, "Result", "Pass");
		}
		else if(isSkip){
			
			suiteShoeShopXLS.setCellData("EmailSubscriptionTest", count+2, "Result", "Skipped");
		}
		else if(isFailure){
			
			suiteShoeShopXLS.setCellData("EmailSubscriptionTest", count+2, "Result", "Fail");
		}
		
		closeBrowser();
	}
	
	@AfterTest
	public void updateTestStatus() throws IOException
	{
		
		int rows = suiteShoeShopXLS.getRowCount("TestCases");
		
		int row=-1;
		
		for(int rowNum=2;rowNum<=rows;rowNum++)
		{
			if(suiteShoeShopXLS.getCellData("TestCases", rowNum, 1).equalsIgnoreCase("EmailSubscriptionTest"))
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
		Object[][] data = TestUtil.getTestCaseData(suiteShoeShopXLS, "EmailSubscriptionTest");
		
		return data;
	}

}
