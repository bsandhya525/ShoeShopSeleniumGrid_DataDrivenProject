package com.selenium.suite;

import java.io.IOException;

import org.testng.SkipException;
import org.testng.annotations.BeforeSuite;

import com.selenium.base.TestBase;
import com.selenium.util.TestUtil;

public class TestSuiteBase extends TestBase{
	
	@BeforeSuite
	public void verifyTestSuiteRunmode() throws IOException
	{
		initialize();
		
		if(!TestUtil.isTestSuiteRunnable(suiteXLS, "TestSuite", "ShoeShopSuite"))
		{
			throw new SkipException("Skipping the testsuite execution as runmode is set to N");
		}
		
		System.out.println("Suite is runnable");
	}

}
